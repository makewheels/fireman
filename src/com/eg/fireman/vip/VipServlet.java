package com.eg.fireman.vip;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.WebUtil;

import alipay.AlipayConfig;
import alipay.AlipayUtil;

public class VipServlet extends HttpServlet {
	private static final long serialVersionUID = 6350472610869327200L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 校验登录状态
		User user = (User) session.getAttribute("user");
		if (user == null) {
			WebUtil.writeToLogin(response);
			return;
		}
		String method = request.getParameter("method");
		if (method == null) {
			WebUtil.writeToLogin(response);
			return;
		}
		// 检查是否绑定了手机
		String userId = user.getUserId();
		User phoneUser = UserJdbc.getUserByUserId(userId);
		String phone = phoneUser.getPhone();
		if (phone == null || phone.equals("")) {
			session.setAttribute("isVipPage", true);
			WebUtil.writeToPageMessage(response, "../me/bindPhone.html");
			return;
		}
		// 激活码
		if (method.equals("submitCode")) {
			// 先检查上次提交激活码时间戳
			Long lastTimestamp = (Long) session.getAttribute("lastSubmitActiveCodeTimestamp");
			if (lastTimestamp != null) {
				long timeDiff = System.currentTimeMillis() - lastTimestamp;
				if (timeDiff < 15000) {
					WebUtil.writeErrorMessage(response, "频率太高，稍后再试");
					return;
				}
			}
			session.setAttribute("lastSubmitActiveCodeTimestamp", System.currentTimeMillis());
			String code = request.getParameter("code");
			if (code == null || code.equals("")) {
				WebUtil.writeErrorMessage(response, "激活码为空");
				return;
			}
			// 验证正确性
			ActiveCode activeCode = VipJdbc.getActiveCodeByCode(code);
			if (activeCode == null) {
				WebUtil.writeErrorMessage(response, "激活码错误");
				return;
			}
			// 验证是否已经被激活了
			if (activeCode.getIsActive() == 1) {
				WebUtil.writeErrorMessage(response, "此激活码已失效");
				return;
			}
			long days = activeCode.getDays();
			// 修改数据库中用户会员信息
			long updateExpireAt = VipUtil.updateUserVipState(userId, days);
			// 修改激活码状态为已激活
			VipJdbc.activeACodeByUserId(activeCode.getId(), userId);
			// 更新session中的user
			user.setIsVip(1);
			user.setVipExpireAt(updateExpireAt + "");
			session.setAttribute("user", user);
			// 跳转回设置主页
			WebUtil.writeToPageMessage(response, "../vip/vipIndex.html");
		} else if (method.equals("submitOrder")) {
			// 提交订单
			String days = request.getParameter("day");
			if (days == null) {
				WebUtil.writeErrorMessage(response, "错误");
				return;
			}
			if (days.equals("15") == false && days.equals("30") == false) {
				WebUtil.writeErrorMessage(response, "错误");
				return;
			}
			int day = Integer.parseInt(days);
			String userAgent = request.getHeader("user-agent");
			AlipayOrder alipayOrder = VipUtil.createAlipayOrder(userId, day, userAgent);
			// 现在订单已经保存好了，该引导用户支付了
			// 判断用户的浏览器是微信浏览器还是其他浏览器
			String subject = "消防习题会员" + days + "天";
			// 如果是微信浏览器
			if (userAgent.contains("MicroMessenger")) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String timestamp = simpleDateFormat.format(new Date());
				Map<String, String> paramsMap = new HashMap<>();
				paramsMap.put("format", "json");
				paramsMap.put("sign_type", "RSA2");
				paramsMap.put("charset", "UTF-8");
				paramsMap.put("app_id", "2018021402198213");
				paramsMap.put("method", "alipay.trade.wap.pay");
				paramsMap.put("version", "1.0");
				paramsMap.put("timestamp", timestamp);
				paramsMap.put("return_url", AlipayConfig.return_url);
				paramsMap.put("notify_url", AlipayConfig.notify_url);
				String bizcontent = "{\"out_trade_no\":\"" + alipayOrder.getOrderId()
						+ "\",\"product_code\":\"QUICK_WAP_PAY\",\"subject\":\"" + subject + "\",\"total_amount\":\""
						+ alipayOrder.getAmountString() + "\"}";
				paramsMap.put("biz_content", bizcontent);
				String sign = null;
				try {
					sign = AlipaySignature.rsaSign(AlipaySignature.getSignContent(paramsMap),
							AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGNTYPE);
				} catch (AlipayApiException e) {
					e.printStackTrace();
				}
				session.setAttribute("sign", sign);
				session.setAttribute("orderId", alipayOrder.getOrderId());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("subject", subject);
				session.setAttribute("amount", alipayOrder.getAmountString());
				session.setAttribute("timestamp", timestamp);
				session.setAttribute("return_url", AlipayConfig.return_url);
				session.setAttribute("notify_url", AlipayConfig.notify_url);
				WebUtil.writeToPageMessage(response, "confirmPay_wx.jsp");
			} else {
				// 如果是其他浏览器
				session.setAttribute("orderId", alipayOrder.getOrderId());
				session.setAttribute("username", user.getUsername());
				session.setAttribute("subject", subject);
				session.setAttribute("amount", alipayOrder.getAmountString());
				WebUtil.writeToPageMessage(response, "confirmPay.jsp");
			}
		} else if (method.equals("requestAlipay")) {
			String orderId = request.getParameter("orderId");
			if (orderId == null) {
				WebUtil.requestDispatcher("/index.html", request, response);
				return;
			}
			AlipayOrder alipayOrder = VipJdbc.getAlipayOrderByOrderId(orderId);
			if (alipayOrder == null) {
				WebUtil.requestDispatcher("/index.html", request, response);
				return;
			}
			String subject = "消防习题会员" + alipayOrder.getDay() + "天";
			String form = AlipayUtil.getWapForm(subject, alipayOrder.getOrderId(), alipayOrder.getAmountString());
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(form);
			response.getWriter().flush();
			response.getWriter().close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
