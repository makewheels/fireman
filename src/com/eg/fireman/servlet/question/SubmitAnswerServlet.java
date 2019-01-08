package com.eg.fireman.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.eg.fireman.bean.CheckQuestion;
import com.eg.fireman.bean.MysqlQuestion;
import com.eg.fireman.bean.SingleQuestion;
import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.QuestionJdbc;
import com.eg.fireman.service.QuestionService;
import com.eg.fireman.util.Result;

public class SubmitAnswerServlet extends HttpServlet implements SubmitAnswerServletInterface {
	private static final long serialVersionUID = -4892940752938793997L;
	private QuestionService questionService;

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	{
		if (questionService == null) {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
			questionService = (QuestionService) context.getBean("questionService");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
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
		String ip = request.getRemoteAddr();
		String questionIdParameter = request.getParameter("questionId");
		int questionId = 0;
		if (questionIdParameter != null) {
			questionId = Integer.parseInt(questionIdParameter);
		}
		session.setAttribute("questionId", questionId);
		String userAnswer = request.getParameter("answer");
		String isRandom = request.getParameter("random");
		String userAgent = request.getHeader("user-agent");
		String type = request.getParameter("type");
		String standardAnswer = null;
		int pian = 0;
		int chapter = 0;
		int no = 0;
		Result result = new Result();
		// 老的多选题
		if (type == null || "".equals(type)) {
			type = "multiple";
			MysqlQuestion mysqlQuestion = QuestionJdbc.getMysqlQuestionById(questionId);
			standardAnswer = mysqlQuestion.getAnswer();
		} else if (type.equals("single")) {
			// 如果是单选题
			SingleQuestion singleQuestion = QuestionJdbc.getSingleQuestionById(questionId);
			standardAnswer = singleQuestion.getAnswer();
			pian = singleQuestion.getPian();
			chapter = singleQuestion.getChapter();
			no = singleQuestion.getNo();
		} else if (type.equals("check")) {
			// 如果是判断题
			CheckQuestion checkQuestion = QuestionJdbc.getCheckQuestionById(questionId);
			standardAnswer = checkQuestion.getAnswer();
			pian = checkQuestion.getPian();
			chapter = checkQuestion.getChapter();
			no = checkQuestion.getNo();
		}
		// 如果答案正确
		if (userAnswer.equals(standardAnswer)) {
			result.setResult(true);
		} else {
			// 如果答案错误
			result.setResult(false);
			if (type.equals("check")) {
				if (standardAnswer.equals("T")) {
					standardAnswer = "对";
				} else {
					standardAnswer = "错";
				}
			}
			result.setCorrectAnswer(standardAnswer);
			String userId = user.getUserId();
			// 处理新错题
			questionService.saveNewWrongQuestion(userId, type, questionId, userAnswer, isRandom, pian, chapter, no);
		}
		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}
		QuestionJdbc.insertSubmitRecord(userAgent, ip, browserId, jsessionid, questionId, userAnswer, standardAnswer,
				result.isResult() + "", isRandom, type, pian, chapter, no, userId);
		String jsonString = JSON.toJSONString(result);
		out.print(jsonString);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
