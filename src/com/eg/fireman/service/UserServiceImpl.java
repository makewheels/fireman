package com.eg.fireman.service;

import org.springframework.transaction.annotation.Transactional;

import com.eg.fireman.bean.User;
import com.eg.fireman.dao.UserDao;

@Transactional
public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUserByPhone(String phone) {
		return userDao.getUserByPhone(phone);
	}

	@Override
	public void updateUser(User user) {
		userDao.updateUser(user);
	}

}
