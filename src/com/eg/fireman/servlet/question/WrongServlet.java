package com.eg.fireman.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
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
import com.eg.fireman.bean.WrongQuestion;
import com.eg.fireman.bean.result.WrongCheckQuestion;
import com.eg.fireman.bean.result.WrongChoiceQuestion;
import com.eg.fireman.jdbc.QuestionJdbc;
import com.eg.fireman.service.QuestionService;
import com.eg.fireman.util.WebUtil;
import com.eg.fireman.vip.VipUtil;

@SuppressWarnings("unchecked")
public class WrongServlet extends HttpServlet {
	private static final long serialVersionUID = 3134310268829865504L;
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
		if (user == null) {
			response.sendRedirect("index.html");
			return;
		}
		String userId = user.getUserId();
		String method = request.getParameter("method");
		if (method == null) {
			response.sendRedirect("index.html");
			return;
		}
		boolean isVip = VipUtil.isVip(user.getUserId());
		if (isVip == false) {
			session.setAttribute("msg", "错题重做功能");
			response.sendRedirect("vip/needVip.jsp");
			return;
		}
		// 每次申请进入错题页面的时候，开始筹备错题
		if (method.equals("toMultiple")) {
			// 到多选错题页面
			List<WrongQuestion> wrongList = questionService.getWrongQuestionListByUserId(userId, "multiple", 0);
			if (wrongList.size() == 0) {
				session.setAttribute("msg", "您没有多选错题");
				response.sendRedirect("error.jsp");
				return;
			}
			session.setAttribute("wrongMultiple", wrongList);
			response.sendRedirect("wrong/wrongMultiple.html");
		} else if (method.equals("toSingle")) {
			// 到单选错题页面
			List<WrongQuestion> wrongList = questionService.getWrongQuestionListByUserId(userId, "single", 0);
			if (wrongList.size() == 0) {
				session.setAttribute("msg", "您没有单选错题");
				response.sendRedirect("error.jsp");
				return;
			}
			session.setAttribute("wrongSingle", wrongList);
			response.sendRedirect("wrong/wrongSingle.html");
		} else if (method.equals("toCheck")) {
			// 到单选错题页面
			List<WrongQuestion> wrongList = questionService.getWrongQuestionListByUserId(userId, "check", 0);
			if (wrongList.size() == 0) {
				session.setAttribute("msg", "您没有判断错题");
				response.sendRedirect("error.jsp");
				return;
			}
			session.setAttribute("wrongCheck", wrongList);
			response.sendRedirect("wrong/wrongCheck.html");
		} else if (method.equals("requestMultiple")) {
			// 请求多选错题
			List<WrongQuestion> wrongList = (List<WrongQuestion>) session.getAttribute("wrongMultiple");
			if (wrongList == null) {
				WebUtil.writeToPageMessage(response, "../wrongIndex.html");
				return;
			}
			// 如果已经被删没了
			if (wrongList.size() == 0) {
				PrintWriter out = response.getWriter();
				out.write("\"{\\\"result\\\":\\\"toPage\\\",\\\"message\\\":\\\"\\\"wrongIndex.jsp\"\"}");
				out.flush();
				out.close();
				return;
			}
			// 按照之前保存的进度查找错题
			Integer progress = (Integer) session.getAttribute("wrongProgress_multiple");
			String message = "";
			if (progress == null) {
				progress = 0;
			} else if (progress == wrongList.size()) {
				progress = 0;
				if (wrongList.size() > 0) {
					message = "您已完成多选错题，现在从头再来";
				}
			}
			WrongQuestion wrongQuestion = wrongList.get(progress);
			progress++;
			session.setAttribute("wrongProgress_multiple", progress);
			// 根据questionId查题
			MysqlQuestion findQuestion = QuestionJdbc.getMysqlQuestionById(wrongQuestion.getQuestionId());
			// 组装错题，返回给前台
			WrongChoiceQuestion wrongChoiceQuestion = new WrongChoiceQuestion();
			wrongChoiceQuestion.setQuestion(findQuestion.getQuestion());
			wrongChoiceQuestion.setOptionA(findQuestion.getOptionA());
			wrongChoiceQuestion.setOptionB(findQuestion.getOptionB());
			wrongChoiceQuestion.setOptionC(findQuestion.getOptionC());
			wrongChoiceQuestion.setOptionD(findQuestion.getOptionD());
			wrongChoiceQuestion.setChapter(wrongQuestion.getChapter());
			wrongChoiceQuestion.setCreateTime(wrongQuestion.getCreateTime());
			wrongChoiceQuestion.setId(wrongQuestion.getId());
			wrongChoiceQuestion.setIsDelete(wrongQuestion.getIsDelete());
			wrongChoiceQuestion.setNo(wrongQuestion.getNo());
			wrongChoiceQuestion.setPian(wrongQuestion.getPian());
			wrongChoiceQuestion.setQuestionId(wrongQuestion.getQuestionId());
			wrongChoiceQuestion.setQuestionType(wrongQuestion.getQuestionType());
			wrongChoiceQuestion.setWrongAnswer(wrongQuestion.getAnswer());
			wrongChoiceQuestion.setWrongInfo("此题累计错：" + wrongQuestion.getWrongTimeCount() + "次");
			wrongChoiceQuestion.setMessage(message);
			WebUtil.writeJson(response, JSON.toJSONString(wrongChoiceQuestion));
		} else if (method.equals("requestSingle")) {
			// 请求单选错题
			List<WrongQuestion> wrongList = (List<WrongQuestion>) session.getAttribute("wrongSingle");
			if (wrongList == null) {
				WebUtil.writeToPageMessage(response, "../wrongIndex.html");
				return;
			}
			// 如果已经被删没了
			if (wrongList.size() == 0) {
				PrintWriter out = response.getWriter();
				out.write("\"{\\\"result\\\":\\\"toPage\\\",\\\"message\\\":\\\"\\\"wrongIndex.jsp\"\"}");
				out.flush();
				out.close();
				return;
			}
			// 按照之前保存的进度查找错题
			Integer progress = (Integer) session.getAttribute("wrongProgress_single");
			String message = "";
			if (progress == null) {
				progress = 0;
			} else if (progress == wrongList.size()) {
				progress = 0;
				if (wrongList.size() > 0) {
					message = "您已完成多选错题，现在从头再来";
				}
			}
			WrongQuestion wrongQuestion = wrongList.get(progress);
			progress++;
			session.setAttribute("wrongProgress_single", progress);
			// 根据questionId查题
			SingleQuestion findQuestion = QuestionJdbc.getSingleQuestionById(wrongQuestion.getQuestionId());
			// 组装错题，返回给前台
			WrongChoiceQuestion wrongChoiceQuestion = new WrongChoiceQuestion();
			wrongChoiceQuestion.setQuestion(findQuestion.getQuestion());
			wrongChoiceQuestion.setOptionA(findQuestion.getOptionA());
			wrongChoiceQuestion.setOptionB(findQuestion.getOptionB());
			wrongChoiceQuestion.setOptionC(findQuestion.getOptionC());
			wrongChoiceQuestion.setOptionD(findQuestion.getOptionD());
			wrongChoiceQuestion.setChapter(wrongQuestion.getChapter());
			wrongChoiceQuestion.setCreateTime(wrongQuestion.getCreateTime());
			wrongChoiceQuestion.setId(wrongQuestion.getId());
			wrongChoiceQuestion.setIsDelete(wrongQuestion.getIsDelete());
			wrongChoiceQuestion.setNo(wrongQuestion.getNo());
			wrongChoiceQuestion.setPian(wrongQuestion.getPian());
			wrongChoiceQuestion.setQuestionId(wrongQuestion.getQuestionId());
			wrongChoiceQuestion.setQuestionType(wrongQuestion.getQuestionType());
			wrongChoiceQuestion.setWrongAnswer(wrongQuestion.getAnswer());
			wrongChoiceQuestion.setWrongInfo("此题累计错：" + wrongQuestion.getWrongTimeCount() + "次");
			wrongChoiceQuestion.setMessage(message);
			WebUtil.writeJson(response, JSON.toJSONString(wrongChoiceQuestion));
		} else if (method.equals("requestCheck")) {
			// 请求判断错题
			List<WrongQuestion> wrongList = (List<WrongQuestion>) session.getAttribute("wrongCheck");
			if (wrongList == null) {
				WebUtil.writeToPageMessage(response, "../wrongIndex.html");
				return;
			}
			// 如果已经被删没了
			if (wrongList.size() == 0) {
				PrintWriter out = response.getWriter();
				out.write("\"{\\\"result\\\":\\\"toPage\\\",\\\"message\\\":\\\"\\\"wrongIndex.jsp\"\"}");
				out.flush();
				out.close();
				return;
			}
			// 按照之前保存的进度查找错题
			Integer progress = (Integer) session.getAttribute("wrongProgress_check");
			String message = "";
			if (progress == null) {
				progress = 0;
			} else if (progress == wrongList.size()) {
				progress = 0;
				if (wrongList.size() > 0) {
					message = "您已完成多选错题，现在从头再来";
				}
			}
			WrongQuestion wrongQuestion = wrongList.get(progress);
			progress++;
			session.setAttribute("wrongProgress_check", progress);
			// 根据questionId查题
			CheckQuestion findQuestion = QuestionJdbc.getCheckQuestionById(wrongQuestion.getQuestionId());
			// 组装错题，返回给前台
			WrongCheckQuestion question = new WrongCheckQuestion();
			question.setQuestion(findQuestion.getQuestion());
			question.setChapter(wrongQuestion.getChapter());
			question.setCreateTime(wrongQuestion.getCreateTime());
			question.setId(wrongQuestion.getId());
			question.setIsDelete(wrongQuestion.getIsDelete());
			question.setNo(wrongQuestion.getNo());
			question.setPian(wrongQuestion.getPian());
			question.setQuestionId(wrongQuestion.getQuestionId());
			question.setQuestionType(wrongQuestion.getQuestionType());
			question.setWrongAnswer(wrongQuestion.getAnswer());
			question.setWrongInfo("此题累计错：" + wrongQuestion.getWrongTimeCount() + "次");
			question.setMessage(message);
			WebUtil.writeJson(response, JSON.toJSONString(question));
		} else if (method.equals("delete")) {
			// 将错题标记为已删除
			Integer id = Integer.parseInt(request.getParameter("id"));
			if (id == 0) {
				WebUtil.writeToPageMessage(response, "wrongIndex.jsp");
				return;
			}
			WrongQuestion findWrong = questionService.getWrongByUserIdAndId(userId, id);
			if (findWrong == null || findWrong.getIsDelete() == 1) {
				WebUtil.writeToPageMessage(response, "wrongIndex.jsp");
				return;
			}
			findWrong.setIsDelete(1);
			questionService.updateWrong(findWrong);
			String questionType = findWrong.getQuestionType();
			List<WrongQuestion> wrongList = null;
			String name_wrongProgress = "";
			String name_wrongList = "";
			if (questionType.equals("multiple")) {
				name_wrongProgress = "wrongProgress_multiple";
				name_wrongList = "wrongMultiple";
			} else if (questionType.equals("single")) {
				name_wrongProgress = "wrongProgress_single";
				name_wrongList = "wrongSingle";
			}
			wrongList = (List<WrongQuestion>) session.getAttribute(name_wrongList);
			if (wrongList == null) {
				WebUtil.writeToPageMessage(response, "wrongIndex.jsp");
				return;
			}
			int i;
			for (i = 0; i < wrongList.size(); i++) {
				WrongQuestion wrongQuestion = wrongList.get(i);
				if (wrongQuestion.getId() == id) {
					wrongList.remove(wrongQuestion);
					break;
				}
			}
			if (wrongList.size() == 0) {
				WebUtil.writeToPageMessage(response, "wrongIndex.jsp");
			}
			int indexProgress = (int) session.getAttribute(name_wrongProgress);
			session.setAttribute(name_wrongProgress, indexProgress - 1);
			WebUtil.writeOkMessage(response, "已移入垃圾箱");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
