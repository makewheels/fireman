package com.eg.fireman.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eg.fireman.bean.Answer;
import com.eg.fireman.bean.Question;

public class FiremanUtil {
	private static List<Question> questionList = null;
	private static List<Answer> answerList = null;
	private static Map<Integer, String> answerMap = new HashMap<>();
	static {
		if (questionList == null) {
			try {
				questionList = QuestionUtil.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (answerList == null) {
			for (Answer answer : answerList) {
				answerMap.put(answer.getQuestionId(), answer.getAnswer());
			}
		}
	}

	public static List<Question> getQuestionList() {
		return questionList;
	}

	public static void setQuestionList(List<Question> questionList) {
		FiremanUtil.questionList = questionList;
	}

	public static List<Answer> getAnswerList() {
		return answerList;
	}

	public static void setAnswerList(List<Answer> answerList) {
		FiremanUtil.answerList = answerList;
	}

	public static Map<Integer, String> getAnswerMap() {
		return answerMap;
	}

	public static void setAnswerMap(Map<Integer, String> answerMap) {
		FiremanUtil.answerMap = answerMap;
	}

}
