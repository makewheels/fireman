package com.eg.fireman.servlet.me;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.service.UserService;
import com.eg.fireman.util.SmsSender;
import com.eg.fireman.util.WebUtil;

public class BindPhoneServlet extends HttpServlet {
	private static final long serialVersionUID = -1800729048866960485L;
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	{
		if (userService == null) {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			userService = (UserService) context.getBean("userService");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// 校验登录状态
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.html");
			return;
		}
		// method：是提交手机号发验证码，还是提交验证码
		String method = request.getParameter("method");
		if (method == null) {
			request.setAttribute("msg", "发生错误");
			WebUtil.requestDispatcher("error.jsp", request, response);
			return;
		}
		if (method.equals("submitPhone")) {
			submitPhone(user, request, response, session);
		} else if (method.equals("submitSmsCode")) {
			submitSmsCode(user, request, response, session);
		} else if (method.equals("isPhoneBinded")) {
			// 是否绑定了手机
			User userByUserId = UserJdbc.getUserByUserId(user.getUserId());
			// 如果没绑定，跳转到绑定页面
			if (userByUserId.getIsPhoneVerified() == 1 && userByUserId.getPhone() != null
					&& userByUserId.getPhone().equals("") == false) {
				WebUtil.writeToPageMessage(response, "../me/bindPhone.html");
				return;
			} else {
				WebUtil.writeOkMessage(response, "phoneIsBinded");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void submitPhone(User user, HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		// 校验是否已绑定手机
		if (user.getPhone() != null && user.getIsPhoneVerified() == 1) {
			request.setAttribute("msg", "您已绑定手机");
			WebUtil.requestDispatcher("error.jsp", request, response);
			return;
		}
		// 校验手机号是否输入有误
		String phone = request.getParameter("phone");
		if (phone == null || phone.length() != 11) {
			request.setAttribute("msg", "您输入的手机号有误");
			WebUtil.requestDispatcher("error.jsp", request, response);
			return;
		}
		// 校验发送频率限制，1分钟
		Long lastSendSmsTimestamp = (Long) session.getAttribute("lastSendSmsTimestamp");
		if (lastSendSmsTimestamp != null) {
			Long timeDiff = System.currentTimeMillis() - lastSendSmsTimestamp;
			if (timeDiff < 1000 * 60) {
				request.setAttribute("msg", "发送频率太高，请稍后再试");
				WebUtil.requestDispatcher("error.jsp", request, response);
				return;
			}
		}
		// 校验该手机号是否已经有用户绑定过了
		User userByPhone = userService.getUserByPhone(phone);
		if (userByPhone != null) {
			request.setAttribute("msg", "该手机号已经被绑定");
			WebUtil.requestDispatcher("error.jsp", request, response);
			return;
		}
		// 发送短信
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		String code = stringBuilder.toString();
		boolean sendResult = SmsSender.sendBindPhone(phone, code);
		// 校验第三方返回的结果
		if (sendResult == false) {
			request.setAttribute("msg", "发送失败，请检查手机号是否正确");
			WebUtil.requestDispatcher("error.jsp", request, response);
			return;
		}
		// 能到这里，应该就说明，短信验证码已正确发送了
		// session保存手机号和验证码
		session.setAttribute("phone", phone);
		session.setAttribute("bindPhoneCode", code);
		// 保存本次发送短信的时间戳
		session.setAttribute("lastSendSmsTimestamp", new Long(System.currentTimeMillis()));
		response.sendRedirect("me/bindPhoneSubmitSmsCode.html");
	}

	public void submitSmsCode(User user, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		String codeParameter = request.getParameter("code");
		String code = (String) session.getAttribute("bindPhoneCode");
		if (codeParameter != null && code.equals(codeParameter)) {
			String phone = (String) session.getAttribute("phone");
			user.setPhone(phone);
			user.setIsPhoneVerified(1);
			userService.updateUser(user);
			session.setAttribute("user", user);
			Boolean isVipPage = (Boolean) session.getAttribute("isVipPage");
			// 绑定手机完成后，是否要往vip页面跳转
			if (isVipPage != null && isVipPage == true) {
				WebUtil.writeToPageMessage(response, "../vip/vipIndex.html");
			} else {
				WebUtil.writeToPageMessage(response, "meIndex.html");
			}
		} else {
			WebUtil.writeErrorMessage(response, "验证码错误");
		}
	}
}
