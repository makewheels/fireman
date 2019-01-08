package com.eg.fireman.servlet.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.WebUtil;

public class GetVipInfo extends HttpServlet {
	private static final long serialVersionUID = -9199051757682927791L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			WebUtil.writeToLogin(response);
			return;
		}
		String userId = user.getUserId();
		User userByUserId = UserJdbc.getUserByUserId(userId);
		if (userByUserId.getIsVip() == 0) {
			WebUtil.writeOkMessage(response, "您还不是会员");
			return;
		}
		user.setVipExpireAt(userByUserId.getVipExpireAt());
		user.setIsVip(userByUserId.getIsVip());
		session.setAttribute("user", user);
		long vipExpireAt = Long.parseLong(userByUserId.getVipExpireAt());
		if (System.currentTimeMillis() > vipExpireAt) {
			WebUtil.writeOkMessage(response, "您的会员已过期");
			return;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d");
		String expireDate = simpleDateFormat.format(new Date(vipExpireAt));
		WebUtil.writeOkMessage(response, "会员至：" + expireDate);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
