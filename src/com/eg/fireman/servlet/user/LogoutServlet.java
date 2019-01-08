package com.eg.fireman.servlet.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.ToLoginResult;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.RandomUtil;
import com.eg.fireman.util.WebUtil;

public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = -2577780022385547230L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		UserJdbc.updateLoginTokenByUserId(user.getUserId(), null);
		session.setAttribute("user", null);
		Cookie cookie = new Cookie("loginToken", RandomUtil.getRandomString());
		cookie.setMaxAge(1);
		response.addCookie(cookie);
		ToLoginResult toLoginResult = new ToLoginResult();
		toLoginResult.setResult("toLogin");
		WebUtil.writeJson(response, toLoginResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
