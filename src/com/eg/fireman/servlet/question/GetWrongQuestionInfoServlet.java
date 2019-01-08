package com.eg.fireman.servlet.question;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.WrongQuestionInfo;
import com.eg.fireman.service.QuestionService;
import com.eg.fireman.util.WebUtil;

public class GetWrongQuestionInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 9184859759061512761L;
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			String userId = user.getUserId();
			long total = questionService.getTotalWrong(userId);
			long single = questionService.getSingleTotalWrong(userId);
			long multiple = questionService.getMultipleTotalWrong(userId);
			long check = questionService.getCheckTotalWrong(userId);
			WrongQuestionInfo wrongQuestionInfo = new WrongQuestionInfo();
			wrongQuestionInfo.setTotal(total);
			wrongQuestionInfo.setSingle(single);
			wrongQuestionInfo.setMultiple(multiple);
			wrongQuestionInfo.setCheck(check);
			WebUtil.writeJson(response, wrongQuestionInfo);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
