package com.eg.fireman.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.eg.fireman.bean.Question;
import com.eg.fireman.bean.WrongQuestion;

@SuppressWarnings("unchecked")
public class WrongQuestionDao extends HibernateDaoSupport {

	public void save(WrongQuestion wrongQuestion) {
		getHibernateTemplate().save(wrongQuestion);
	}

	public Question getQuestionByTypeAndId(String type, int questionId) {
		return null;
	}

	public WrongQuestion getWrongByUserIdAndTypeAndQuestionid(String userId, String type, int questionId) {
		List<WrongQuestion> wrongQuestions = (List<WrongQuestion>) getHibernateTemplate().find(
				"from WrongQuestion where userId=? and questionType=? and questionId=?", userId, type, questionId);
		if (wrongQuestions.size() == 0) {
			return null;
		} else {
			return wrongQuestions.get(0);
		}
	}

	/**
	 * 错误次数加1
	 */
	public void update(WrongQuestion wrongQuestion) {
		getHibernateTemplate().update(wrongQuestion);
	}

	public long getTotalWrong(String userId) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WrongQuestion.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isDelete", 0));
		long result = (long) criteria.uniqueResult();
		session.close();
		return result;
	}

	public long getSingleTotalWrong(String userId) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WrongQuestion.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isDelete", 0));
		criteria.add(Restrictions.eq("questionType", "single"));
		long result = (long) criteria.uniqueResult();
		session.close();
		return result;
	}

	public long getMultipleTotalWrong(String userId) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WrongQuestion.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isDelete", 0));
		criteria.add(Restrictions.eq("questionType", "multiple"));
		long result = (long) criteria.uniqueResult();
		session.close();
		return result;
	}

	public long getCheckTotalWrong(String userId) {
		Session session = getHibernateTemplate().getSessionFactory().openSession();
		Criteria criteria = session.createCriteria(WrongQuestion.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("isDelete", 0));
		criteria.add(Restrictions.eq("questionType", "check"));
		long result = (long) criteria.uniqueResult();
		session.close();
		return result;
	}

	public List<WrongQuestion> getWrongQuestionListByUserId(String userId, String type, int isDelete) {
		return (List<WrongQuestion>) getHibernateTemplate()
				.find("from WrongQuestion where userId=? and questionType=? and isDelete=?", userId, type, isDelete);
	}

	public WrongQuestion getWrongByUserIdAndId(String userId, int id) {
		List<WrongQuestion> wrongQuestions = (List<WrongQuestion>) getHibernateTemplate()
				.find("from WrongQuestion where userId=? and id=?", userId, id);
		if (wrongQuestions.size() == 0) {
			return null;
		} else {
			return wrongQuestions.get(0);
		}
	}

}
