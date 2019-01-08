package com.eg.fireman.service;

import com.eg.fireman.bean.User;

public interface UserService {

	User getUserByPhone(String phone);

	void updateUser(User user);
}
