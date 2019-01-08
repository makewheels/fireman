package com.eg.fireman.servlet.me;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.eg.fireman.bean.Statistics;
import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.QuestionJdbc;

public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = -7554000117988326459L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		int totalRequest = QuestionJdbc.getTotalRequest();
		int totalSubmit = QuestionJdbc.getTotalSubmit();
		int totalSubmitCorrect = QuestionJdbc.getTotalSubmitCorrect();
		Statistics statistics = new Statistics();
		statistics.setTotalRequest(totalRequest);
		statistics.setTotalSubmit(totalSubmit);
		statistics.setTotalCorrect(totalSubmitCorrect);
		statistics.setCorrectRate(totalSubmitCorrect * 100 / totalSubmit + "%");
		String jsonString = JSON.toJSONString(statistics);
		HttpSession session = request.getSession();
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
		User user = (User) session.getAttribute("user");
		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}
		QuestionJdbc.insertStatisticsLog(userAgent, ip, browserId, jsessionid, userId);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		session.setAttribute("time", simpleDateFormat.format(new Date()));
		out.print(jsonString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
