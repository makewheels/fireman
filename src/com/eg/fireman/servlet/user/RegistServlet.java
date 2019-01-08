package com.eg.fireman.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.MessageReturnResult;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.Md5Util;
import com.eg.fireman.util.RandomUtil;

public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 8811048288985365995L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User findUser = UserJdbc.getUserByUsername(username);
		MessageReturnResult messageReturnResult = new MessageReturnResult();
		if (findUser != null) {
			// 如果查到，已经用这个用户名对应的用户了
			messageReturnResult.setResult("error");
			messageReturnResult.setMessage("该用户名已存在，请换一个再试！");
		} else {
			// 没有该用户，保存
			String userId = RandomUtil.getUuid();
			UserJdbc.saveUser(username, Md5Util.md5(password), userId);
			messageReturnResult = new MessageReturnResult();
			messageReturnResult.setResult("ok");
			messageReturnResult.setMessage("注册成功！");
		}
		out.println(JSON.toJSONString(messageReturnResult));
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
