package com.eg.fireman.servlet.me;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.QuestionJdbc;

public class FeedbackServlet extends HttpServlet {
	private static final long serialVersionUID = 3788392806176252602L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String question = request.getParameter("question");
		String contact = request.getParameter("contact");
		PrintWriter out = response.getWriter();
		boolean hasBrowserId = false;
		String browserId = null;
		String jsessionid = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if (name.equals("browserId")) {
					hasBrowserId = true;
					browserId = cookie.getValue();
				} else if (name.equals("JSESSIONID")) {
					jsessionid = cookie.getValue();
				}
			}
		}
		if (hasBrowserId == false) {
			browserId = UUID.randomUUID().toString();
			Cookie cookie = new Cookie("browserId", browserId);
			cookie.setMaxAge(31104000);
			response.addCookie(cookie);
		} else {
			Cookie cookie = new Cookie("browserId", browserId);
			cookie.setMaxAge(31104000);
			response.addCookie(cookie);
		}
		String userAgent = request.getHeader("user-agent");
		String ip = request.getRemoteAddr();
		String userId = null;
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			userId = user.getUserId();
		}
		QuestionJdbc.saveFeedback(question, contact, userAgent, ip, browserId, jsessionid, userId);
		out.print("ok");
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
