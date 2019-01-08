package alipay;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.vip.AlipayOrder;
import com.eg.fireman.vip.VipJdbc;

/**
 * 客户端浏览器回调returnUrl
 * 
 * @author Administrator
 *
 */
public class AlipayReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 2968116084971366572L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		// 商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
		AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
				AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,
				AlipayConfig.SIGNTYPE);
		AlipayTradeQueryRequest alipay_request = new AlipayTradeQueryRequest();
		String parameterMapJsonString = JSON.toJSONString(request.getParameterMap());
		System.out.println("return-parameterMapJsonString-start");
		System.out.println(parameterMapJsonString);
		System.out.println("return-parameterMapJsonString-end");
		AlipayTradeQueryModel model = new AlipayTradeQueryModel();
		model.setOutTradeNo(out_trade_no);
		model.setTradeNo(trade_no);
		alipay_request.setBizModel(model);
		AlipayTradeQueryResponse alipay_response = null;
		try {
			alipay_response = client.execute(alipay_request);
		} catch (AlipayApiException e1) {
			e1.printStackTrace();
		}
		if (alipay_response.getTradeStatus().equals("TRADE_SUCCESS") == false) {
			response.getWriter().write("发生错误！");
			return;
		}
		System.out.println("trade_no: " + trade_no);
		// 订单金额
		String total_amount = alipay_response.getTotalAmount();
		// 交易状态
		String trade_status = alipay_response.getTradeStatus();
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
		HttpSession session = request.getSession();
		AlipayOrder alipayOrderByOrderId = VipJdbc.getAlipayOrderByOrderId(out_trade_no);
		String vipExpireAt = null;
		if (alipayOrderByOrderId == null) {
			session.setAttribute("payResult", "出现错误，稍后再试");
		} else {
			String userId = alipayOrderByOrderId.getUserId();
			User userByUserId = UserJdbc.getUserByUserId(userId);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
			vipExpireAt = simpleDateFormat.format(new Date(Long.parseLong(userByUserId.getVipExpireAt())));
			if (trade_status.equals("TRADE_SUCCESS")) {
				session.setAttribute("payResult", "支付成功");
			} else {
				session.setAttribute("payResult", "出现错误，稍后再试");
			}
		}
		session.setAttribute("amount", total_amount);
		session.setAttribute("out_trade_no", out_trade_no);
		session.setAttribute("trade_no", trade_no);
		session.setAttribute("vipExpireAt", vipExpireAt);
		response.sendRedirect(request.getContextPath() + "/vip/payReturn.jsp");
		System.out.println("return end");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
