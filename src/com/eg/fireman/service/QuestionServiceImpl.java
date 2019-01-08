package com.eg.fireman.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.eg.fireman.bean.WrongQuestion;
import com.eg.fireman.dao.WrongQuestionDao;

@Transactional
public class QuestionServiceImpl implements QuestionService {
	private WrongQuestionDao wrongQuestionDao;

	public void setWrongQuestionDao(WrongQuestionDao wrongQuestionDao) {
		this.wrongQuestionDao = wrongQuestionDao;
	}

	public void saveNewWrongQuestion() {
		wrongQuestionDao.save(new WrongQuestion());
	}

	/**
	 * 处理新错题
	 */
	@Override
	public void saveNewWrongQuestion(String userId, String type, int questionId, String answer, String isRandom,
			int pian, int chapter, int no) {
		// 如果他已经有了这个错题，那就把wrongTimeCount加1
		WrongQuestion findWrongQuestion = wrongQuestionDao.getWrongByUserIdAndTypeAndQuestionid(userId, type,
				questionId);
		if (findWrongQuestion != null) {
			findWrongQuestion.setWrongTimeCount(findWrongQuestion.getWrongTimeCount() + 1);
			wrongQuestionDao.update(findWrongQuestion);
		} else {
			// 如果该用户还没有这道题，新建记录保存到数据库
			WrongQuestion wrongQuestion = new WrongQuestion();
			wrongQuestion.setUserId(userId);
			wrongQuestion.setQuestionType(type);
			wrongQuestion.setQuestionId(questionId);
			wrongQuestion.setAnswer(answer);
			wrongQuestion.setIsRandom(isRandom);
			wrongQuestion.setPian(pian);
			wrongQuestion.setChapter(chapter);
			wrongQuestion.setNo(no);
			wrongQuestion.setWrongTimeCount(1);
			wrongQuestionDao.save(wrongQuestion);
		}
	}

	@Override
	public long getTotalWrong(String userId) {
		return wrongQuestionDao.getTotalWrong(userId);
	}

	@Override
	public long getSingleTotalWrong(String userId) {
		return wrongQuestionDao.getSingleTotalWrong(userId);
	}

	@Override
	public long getMultipleTotalWrong(String userId) {
		return wrongQuestionDao.getMultipleTotalWrong(userId);
	}

	@Override
	public long getCheckTotalWrong(String userId) {
		return wrongQuestionDao.getCheckTotalWrong(userId);
	}

	@Override
	public List<WrongQuestion> getWrongQuestionListByUserId(String userId, String type, int isDelete) {
		return wrongQuestionDao.getWrongQuestionListByUserId(userId, type, isDelete);
	}

	@Override
	public WrongQuestion getWrongByUserIdAndId(String userId, int id) {
		return wrongQuestionDao.getWrongByUserIdAndId(userId, id);
	}

	@Override
	public void updateWrong(WrongQuestion findWrong) {
		wrongQuestionDao.update(findWrong);
	}

}
