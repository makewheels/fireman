package com.eg.fireman.service;

import java.util.List;

import com.eg.fireman.bean.WrongQuestion;

public interface QuestionService {
	public void saveNewWrongQuestion(String userId, String type, int questionId, String answer, String isRandom,
			int pian, int chapter, int no);

	public long getTotalWrong(String userId);

	public long getSingleTotalWrong(String userId);

	public long getMultipleTotalWrong(String userId);

	long getCheckTotalWrong(String userId);

	public List<WrongQuestion> getWrongQuestionListByUserId(String userId, String type, int isDelete);

	public WrongQuestion getWrongByUserIdAndId(String userId, int id);

	public void updateWrong(WrongQuestion findWrong);

}
