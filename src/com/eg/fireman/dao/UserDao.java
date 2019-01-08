package com.eg.fireman.dao;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.eg.fireman.bean.User;

@SuppressWarnings("unchecked")
public class UserDao extends HibernateDaoSupport {

	public User getUserByPhone(String phone) {
		List<User> list = (List<User>) getHibernateTemplate().find("from User where phone=?", phone);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public void updateUser(User user) {
		getHibernateTemplate().update(user);
	}

}
