package com.eg.fireman.easteregg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.WrongCheckQuestion;
import com.eg.fireman.bean.result.WrongChoiceQuestion;
import com.eg.fireman.util.WebUtil;
import com.eg.fireman.vip.VipUtil;

/**
 * 彩蛋servlet 2018年6月12日19:20:09
 * 
 * @author Administrator
 *
 */
public class EasterEggServlet extends HttpServlet {
	private static final long serialVersionUID = -4394775317679129242L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user == null) {
			response.sendRedirect("login.html");
			return;
		}
		String userId = user.getUserId();
		boolean isVip = VipUtil.isVip(user.getUserId());
		if (isVip == false) {
			session.setAttribute("msg", "彩蛋之：最高错误率的题");
			response.sendRedirect("vip/needVip.jsp");
			return;
		}
		// 是哪个彩蛋
		String name = request.getParameter("name");
		if (name == null || name.equals("")) {
			response.sendRedirect("index.html");
			return;
		}
		// 方法
		String method = request.getParameter("method");
		if (method == null || method.equals("")) {
			response.sendRedirect("index.html");
			return;
		}
		String questionType = request.getParameter("questionType");
		// 最高错误率
		if (name.equals("mostWrong")) {
			// 跳转到多选，单选，判断，主页
			if (method.equals("toPage")) {
				if (questionType == null || questionType.equals("")) {
					response.sendRedirect("index.html");
					return;
				}
				if (questionType.equals("multiple")) {
					response.sendRedirect("easterEgg/mostWrong/mostWrongMultiple.html");
				} else if (questionType.equals("single")) {
					response.sendRedirect("easterEgg/mostWrong/mostWrongSingle.html");
				} else if (questionType.equals("check")) {
					response.sendRedirect("easterEgg/mostWrong/mostWrongCheck.html");
				} else {
					response.sendRedirect("index.html");
				}
				return;
			} else if (method.equals("requestQuestion")) {
				// 请求新题
				int questionId = 0;
				if (questionType.equals("multiple")) {
					WrongChoiceQuestion question = MostWrongService.getMultipleQuestion();
					questionId = question.getQuestionId();
					WebUtil.writeJson(response, question);
				} else if (questionType.equals("single")) {
					WrongChoiceQuestion question = MostWrongService.getSingleQuestion();
					questionId = question.getQuestionId();
					WebUtil.writeJson(response, question);
				} else if (questionType.equals("check")) {
					WrongCheckQuestion question = MostWrongService.getCheckQuestion();
					questionId = question.getQuestionId();
					WebUtil.writeJson(response, question);
				} else {
					WebUtil.writeToPageMessage(response, "../../index.html");
				}
				// 写请求日志
				MostWrongService.saveRequestLog(request, userId, "true", questionType, questionId);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
