package com.eg.fireman.easteregg;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.eg.fireman.bean.CheckQuestion;
import com.eg.fireman.bean.MysqlQuestion;
import com.eg.fireman.bean.SingleQuestion;
import com.eg.fireman.bean.result.WrongCheckQuestion;
import com.eg.fireman.bean.result.WrongChoiceQuestion;
import com.eg.fireman.easteregg.mostwrong.MostWrongCheckQuestion;
import com.eg.fireman.easteregg.mostwrong.MostWrongChooseQuestion;
import com.eg.fireman.jdbc.LogJdbc;
import com.eg.fireman.jdbc.QuestionJdbc;

public class MostWrongService {
	public static WrongChoiceQuestion getMultipleQuestion() {
		int total = QuestionJdbc.getTotalMostWrongMultiple();
		MostWrongChooseQuestion mostMultiple = QuestionJdbc
				.getMostWrongMultipleQuestionById(new Random().nextInt(total) + 1);
		MysqlQuestion findQuestion = QuestionJdbc.getMysqlQuestionById(mostMultiple.getQuestionId());
		WrongChoiceQuestion wrongChoiceQuestion = new WrongChoiceQuestion();
		wrongChoiceQuestion.setQuestion(findQuestion.getQuestion());
		wrongChoiceQuestion.setOptionA(findQuestion.getOptionA());
		wrongChoiceQuestion.setOptionB(findQuestion.getOptionB());
		wrongChoiceQuestion.setOptionC(findQuestion.getOptionC());
		wrongChoiceQuestion.setOptionD(findQuestion.getOptionD());
		wrongChoiceQuestion.setChapter(mostMultiple.getChapter());
		wrongChoiceQuestion.setId(mostMultiple.getId());
		wrongChoiceQuestion.setNo(mostMultiple.getNo());
		wrongChoiceQuestion.setPian(mostMultiple.getPian());
		wrongChoiceQuestion.setQuestionId(findQuestion.getQuestionId());
		wrongChoiceQuestion.setQuestionType("multiple");
		int correctRate = (int) (mostMultiple.getCorrectRate() * 100);
		wrongChoiceQuestion.setWrongInfo("正确率：" + correctRate + "%，最多错选：" + mostMultiple.getMostWrongAnswer());
		return wrongChoiceQuestion;
	}

	public static WrongChoiceQuestion getSingleQuestion() {
		int total = QuestionJdbc.getTotalMostWrongSingle();
		MostWrongChooseQuestion mostSingle = QuestionJdbc
				.getMostWrongSingleQuestionById(new Random().nextInt(total) + 1);
		SingleQuestion findQuestion = QuestionJdbc.getSingleQuestionById(mostSingle.getQuestionId());
		WrongChoiceQuestion wrongChoiceQuestion = new WrongChoiceQuestion();
		wrongChoiceQuestion.setQuestion(findQuestion.getQuestion());
		wrongChoiceQuestion.setOptionA(findQuestion.getOptionA());
		wrongChoiceQuestion.setOptionB(findQuestion.getOptionB());
		wrongChoiceQuestion.setOptionC(findQuestion.getOptionC());
		wrongChoiceQuestion.setOptionD(findQuestion.getOptionD());
		wrongChoiceQuestion.setChapter(mostSingle.getChapter());
		wrongChoiceQuestion.setId(mostSingle.getId());
		wrongChoiceQuestion.setNo(mostSingle.getNo());
		wrongChoiceQuestion.setPian(mostSingle.getPian());
		wrongChoiceQuestion.setQuestionId(findQuestion.getId());
		wrongChoiceQuestion.setQuestionType("single");
		int correctRate = (int) (mostSingle.getCorrectRate() * 100);
		wrongChoiceQuestion.setWrongInfo("正确率：" + correctRate + "%，最多错选：" + mostSingle.getMostWrongAnswer());
		return wrongChoiceQuestion;
	}

	public static WrongCheckQuestion getCheckQuestion() {
		int total = QuestionJdbc.getTotalMostWrongCheck();
		MostWrongCheckQuestion mostCheck = QuestionJdbc.getMostWrongCheckQuestionById(new Random().nextInt(total) + 1);
		CheckQuestion findQuestion = QuestionJdbc.getCheckQuestionById(mostCheck.getQuestionId());
		WrongCheckQuestion wrongCheckQuestion = new WrongCheckQuestion();
		wrongCheckQuestion.setQuestion(findQuestion.getQuestion());
		wrongCheckQuestion.setChapter(mostCheck.getChapter());
		wrongCheckQuestion.setId(mostCheck.getId());
		wrongCheckQuestion.setNo(mostCheck.getNo());
		wrongCheckQuestion.setPian(mostCheck.getPian());
		wrongCheckQuestion.setQuestionId(findQuestion.getId());
		wrongCheckQuestion.setQuestionType("check");
		int correctRateInt = (int) (mostCheck.getCorrectRate() * 100);
		wrongCheckQuestion.setWrongInfo("正确率：" + correctRateInt + "%");
		return wrongCheckQuestion;
	}

	/**
	 * 保存请求记录
	 * 
	 * @param request
	 * @param userId
	 * @param isRandom
	 * @param questionType
	 * @param questionId
	 */
	public static void saveRequestLog(HttpServletRequest request, String userId, String isRandom, String questionType,
			int questionId) {
		String userAgent = request.getHeader("user-agent");
		String ip = request.getRemoteAddr();
		String browserId = null;
		String jsessionid = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if (name.equals("browserId")) {
					browserId = cookie.getValue();
				} else if (name.equals("JSESSIONID")) {
					jsessionid = cookie.getValue();
				}
			}
		}
		LogJdbc.insertRequestRecord(userAgent, ip, browserId, jsessionid, questionId, isRandom, questionType, userId,
				"easterEgg-mostWrong");
	}

}
