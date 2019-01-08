package com.eg.fireman.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.ToLoginResult;
import com.eg.fireman.jdbc.UserJdbc;
import com.eg.fireman.util.WebUtil;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {

	}

	private HttpServletRequest request;
	private HttpServletResponse response;

	/**
	 * 向服务器返回，跳转登录页面
	 */
	public void returnTologin() {
		ToLoginResult result = new ToLoginResult();
		result.setResult("toLogin");
		WebUtil.writeJson((HttpServletResponse) response, result);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		request = (HttpServletRequest) servletRequest;
		response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Cookie[] cookies = request.getCookies();
		// 如果session中没有user
		if (user == null) {
			if (cookies == null) {
				returnTologin();
				return;
			}
			String browserId = null;
			// 利用cookie中的loginToken，自动登录逻辑
			String browserLoginToken = "notThisToken";
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginToken")) {
					browserLoginToken = cookie.getValue();
				} else if (cookie.getName().equals("browserId")) {
					browserId = cookie.getValue();
				}
			}
			User userByLoginToken = UserJdbc.getUserByLoginToken(browserLoginToken);
			if (userByLoginToken == null) {
				returnTologin();
				return;
			} else {
				// 保存登录日志
				String ip = request.getRemoteAddr();
				UserJdbc.saveLoginLog(userByLoginToken.getUserId(), request.getHeader("user-agent"), ip, browserId,
						"loginTokenCookie", browserLoginToken);
				session.setAttribute("user", userByLoginToken);
				session.setAttribute("ip", ip);
			}
		} else {
			// 如果session中有user
			// 看ip是否一致
			String ip = request.getRemoteAddr();
			String sessionIp = (String) session.getAttribute("ip");
			if (ip.equals(sessionIp) == false) {
				user = null;
				returnTologin();
				return;
			}
			// 查数据库，对比cookie中的loginToken
			if (cookies == null) {
				returnTologin();
				return;
			}
			String loginToken = null;
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("loginToken")) {
					loginToken = cookie.getValue();
				}
			}
			String savedToken = UserJdbc.getUserByUserId(user.getUserId()).getLoginToken();
			if (loginToken == null || savedToken == null || loginToken.equals("") || savedToken.equals("")) {
				returnTologin();
				return;
			}
			// 如果两个token不一致
			if (loginToken.equals(savedToken) == false) {
				returnTologin();
				return;
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
