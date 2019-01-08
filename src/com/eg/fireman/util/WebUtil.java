package com.eg.fireman.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.eg.fireman.bean.result.MessageReturnResult;
import com.eg.fireman.bean.result.ToLoginResult;

public class WebUtil {
	/**
	 * 向浏览器回写json
	 */
	public static void writeJson(HttpServletResponse response, Object obj) {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out != null) {
			out.print(JSON.toJSONString(obj));
			out.flush();
			out.close();
		}
	}

	/**
	 * 回写error信息
	 */
	public static void writeErrorMessage(HttpServletResponse response, String errorMessage) {
		MessageReturnResult messageReturnResult = new MessageReturnResult();
		messageReturnResult.setResult("error");
		messageReturnResult.setMessage(errorMessage);
		writeJson(response, messageReturnResult);
	}

	/**
	 * 回写Message信息
	 */
	public static void writeOkMessage(HttpServletResponse response, String message) {
		MessageReturnResult messageReturnResult = new MessageReturnResult();
		messageReturnResult.setResult("ok");
		messageReturnResult.setMessage(message);
		writeJson(response, messageReturnResult);
	}

	/**
	 * 回写toPage跳转页面
	 */
	public static void writeToPageMessage(HttpServletResponse response, String pageUrl) {
		MessageReturnResult messageReturnResult = new MessageReturnResult();
		messageReturnResult.setResult("toPage");
		messageReturnResult.setMessage(pageUrl);
		writeJson(response, messageReturnResult);
	}

	/**
	 * 回写登录
	 */
	public static void writeToLogin(HttpServletResponse response) {
		ToLoginResult result = new ToLoginResult();
		result.setResult("toLogin");
		writeJson(response, result);
	}

	/**
	 * 重定向
	 */
	public static void requestDispatcher(String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(path).forward(request, response);
	}
}