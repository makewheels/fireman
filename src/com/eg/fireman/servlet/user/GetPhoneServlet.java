package com.eg.fireman.servlet.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eg.fireman.bean.User;
import com.eg.fireman.bean.result.MessageReturnResult;
import com.eg.fireman.bean.result.ToLoginResult;
import com.eg.fireman.util.WebUtil;

public class GetPhoneServlet extends HttpServlet {
	private static final long serialVersionUID = 3506682502715721643L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			MessageReturnResult messageReturnResult = new MessageReturnResult();
			messageReturnResult.setResult("ok");
			String phone = user.getPhone();
			if (phone != null) {
				phone = phone.substring(0, 3) + "****" + phone.substring(7, phone.length());
			}
			messageReturnResult.setMessage(phone);
			WebUtil.writeJson((HttpServletResponse) response, messageReturnResult);
			return;
		} else {
			ToLoginResult result = new ToLoginResult();
			result.setResult("toLogin");
			WebUtil.writeJson((HttpServletResponse) response, result);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
