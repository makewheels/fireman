package com.eg.fireman.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.MessageReturnResult;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.Md5Util;
import com.eg.fireman.util.RandomUtil;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 6736228851768796751L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String passwordParam = request.getParameter("password");
		// 登录方式
		String type = request.getParameter("type");
		if (username == null || username.equals("") || passwordParam == null || passwordParam.equals("") || type == null
				|| type.equals("")) {
			MessageReturnResult result = new MessageReturnResult();
			result.setResult("error");
			result.setMessage("登录错误！");
			out.print(JSON.toJSONString(result));
			return;
		}
		User findUser = null;
		// 如果是用户名方式登录
		if (type.equals("1")) {
			findUser = UserJdbc.getUserByUsername(username);
		} else if (type.equals("2")) {
			// 手机方式登录
			findUser = UserJdbc.getUserByPhone(username);
		} else if (type.equals("3")) {
			// 邮箱方式登录
		}
		// 账号不存在
		if (findUser == null) {
			MessageReturnResult result = new MessageReturnResult();
			result.setResult("error");
			if (type.equals("1")) {
				result.setMessage("用户名不存在！");
			} else if (type.equals("2")) {
				result.setMessage("手机号不存在！");
			}
			out.print(JSON.toJSONString(result));
			return;
		}
		String password = findUser.getPassword();
		passwordParam = Md5Util.md5(passwordParam);
		// 密码匹配
		if (password.equals(passwordParam)) {
			// 保存登录日志
			String userAgent = request.getHeader("user-agent");
			String ip = request.getRemoteAddr();
			Cookie[] cookies = request.getCookies();
			boolean hasBrowserId = false;
			String browserId = null;
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					String name = cookie.getName();
					if (name.equals("browserId")) {
						hasBrowserId = true;
						browserId = cookie.getValue();
					}
				}
			}
			if (hasBrowserId == false) {
				browserId = UUID.randomUUID().toString();
				Cookie cookie = new Cookie("browserId", browserId);
				cookie.setMaxAge(31104000);
				response.addCookie(cookie);
			} else {
				Cookie cookie = new Cookie("browserId", browserId);
				cookie.setMaxAge(31104000);
				response.addCookie(cookie);
			}
			// 生成新的loginToken保存到数据库
			String loginToken = RandomUtil.getRandomString();
			UserJdbc.updateLoginTokenByUserId(findUser.getUserId(), loginToken);
			// 并回写给浏览器
			Cookie cookie = new Cookie("loginToken", loginToken);
			cookie.setMaxAge(60 * 60 * 24 * 7);
			response.addCookie(cookie);
			UserJdbc.saveLoginLog(findUser.getUserId(), userAgent, ip, browserId, type, loginToken);
			// session保存user对象
			HttpSession session = request.getSession();
			findUser.setLoginToken(loginToken);
			session.setAttribute("user", findUser);
			session.setAttribute("ip", ip);
			// 返回登录成功结果
			MessageReturnResult result = new MessageReturnResult();
			result.setResult("ok");
			result.setMessage("登录成功！");
			out.print(JSON.toJSONString(result));
			return;
		} else {
			// 密码不匹配
			MessageReturnResult result = new MessageReturnResult();
			result.setResult("error");
			if (type.equals("1")) {
				result.setMessage("用户名密码不匹配！");
			} else if (type.equals("2")) {
				result.setMessage("手机号与密码不匹配！");
			}
			out.print(JSON.toJSONString(result));
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
