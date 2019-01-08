package com.eg.fireman.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.eg.fireman.bean.CheckQuestion;
import com.eg.fireman.bean.MysqlQuestion;
import com.eg.fireman.bean.SingleQuestion;
import com.eg.fireman.bean.User;
import com.eg.fireman.jdbc.QuestionJdbc;
import com.eg.fireman.util.CheckConstants;
import com.eg.fireman.util.SingleConstants;

public class RequestQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = -572219604793626196L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		String isRandom = request.getParameter("isRandom");
		HttpSession session = request.getSession();
		boolean hasBrowserId = false;
		String browserId = null;
		String jsessionid = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if (name.equals("browserId")) {
					hasBrowserId = true;
					browserId = cookie.getValue();
				} else if (name.equals("JSESSIONID")) {
					jsessionid = cookie.getValue();
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
		String userAgent = request.getHeader("user-agent");
		String ip = request.getRemoteAddr();
		String questionType = request.getParameter("questionType");
		int questionId = 0;
		int lastQuestionId = 0;
		// 如果是老题
		if (questionType == null) {
			questionType = "multiple";
			if (isRandom.equals("true")) {
				Random random = new Random();
				questionId = random.nextInt(160) + 1;
			} else {
				// 遍历cookie，看有没有进度id
				boolean hasLastQuestionId = false;
				Cookie[] cookies1 = request.getCookies();
				if (cookies1 != null) {
					for (Cookie cookie : cookies1) {
						String name = cookie.getName();
						if (name.equals("lastQuestionId")) {
							hasLastQuestionId = true;
							lastQuestionId = Integer.parseInt(cookie.getValue());
						}
					}
				}
				// 如果没有questionId
				if (hasLastQuestionId == false) {
					Cookie cookie = new Cookie("lastQuestionId", 1 + "");
					cookie.setMaxAge(31104000);
					response.addCookie(cookie);
					questionId = 1;
				} else {
					// 如果有questionId
					if (lastQuestionId == 160) {
						questionId = 1;
					} else {
						questionId = lastQuestionId + 1;
					}
					Cookie cookie = new Cookie("lastQuestionId", questionId + "");
					cookie.setMaxAge(31104000);
					response.addCookie(cookie);
				}
			}
		}
		session.setAttribute("questionId", questionId);
		String jsonString = null;
		int pian = 0;
		int chapter = 0;
		int no = 0;
		Random random = new Random();
		// 单选题
		if (questionType != null && questionType.equals("single")) {
			pian = Integer.parseInt(request.getParameter("pian"));
			chapter = Integer.parseInt(request.getParameter("chapter"));
			SingleQuestion singleQuestion = null;
			// 随机
			if (isRandom.equals("true")) {
				if (pian == 0 && chapter == 0) {
					questionId = random.nextInt(SingleConstants.TOTAL_AMOUNT) + 1;
				} else if (pian == 1 && chapter == 1) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_1) + SingleConstants.START_INDEX_1_1;
				} else if (pian == 1 && chapter == 2) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_2) + SingleConstants.START_INDEX_1_2;
				} else if (pian == 1 && chapter == 3) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_3) + SingleConstants.START_INDEX_1_3;
				} else if (pian == 1 && chapter == 4) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_4) + SingleConstants.START_INDEX_1_4;
				} else if (pian == 1 && chapter == 5) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_5) + SingleConstants.START_INDEX_1_5;
				} else if (pian == 1 && chapter == 6) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_6) + SingleConstants.START_INDEX_1_6;
				} else if (pian == 1 && chapter == 7) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_7) + SingleConstants.START_INDEX_1_7;
				} else if (pian == 1 && chapter == 8) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_8) + SingleConstants.START_INDEX_1_8;
				} else if (pian == 1 && chapter == 9) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_9) + SingleConstants.START_INDEX_1_9;
				} else if (pian == 1 && chapter == 10) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_10) + SingleConstants.START_INDEX_1_10;
				} else if (pian == 1 && chapter == 11) {
					questionId = random.nextInt(SingleConstants.AMOUNT_1_11) + SingleConstants.START_INDEX_1_11;
				} else if (pian == 2 && chapter == 1) {
					questionId = random.nextInt(SingleConstants.AMOUNT_2_1) + SingleConstants.START_INDEX_2_1;
				} else if (pian == 2 && chapter == 2) {
					questionId = random.nextInt(SingleConstants.AMOUNT_2_2) + SingleConstants.START_INDEX_2_2;
				} else if (pian == 2 && chapter == 3) {
					questionId = random.nextInt(SingleConstants.AMOUNT_2_3) + SingleConstants.START_INDEX_2_3;
				}
			} else {
				// 顺序
				// 查看进度
				Integer progressPian = (Integer) session.getAttribute("progressPian");
				Integer progressChapter = (Integer) session.getAttribute("progressChapter");
				Integer progressNo = (Integer) session.getAttribute("progressNo");
				// 如果没有进度，或session中存的pian和chapter与session中的不一致，说明他换章节了，那就重置
				if (progressPian == null || progressChapter == null || progressNo == null || pian != progressPian
						|| chapter != progressChapter) {
					progressNo = 1;
					if (pian == 0 && chapter == 0) {
						questionId = 1;
					} else if (pian == 1 && chapter == 1) {
						questionId = SingleConstants.START_INDEX_1_1;
					} else if (pian == 1 && chapter == 2) {
						questionId = SingleConstants.START_INDEX_1_2;
					} else if (pian == 1 && chapter == 3) {
						questionId = SingleConstants.START_INDEX_1_3;
					} else if (pian == 1 && chapter == 4) {
						questionId = SingleConstants.START_INDEX_1_4;
					} else if (pian == 1 && chapter == 5) {
						questionId = SingleConstants.START_INDEX_1_5;
					} else if (pian == 1 && chapter == 6) {
						questionId = SingleConstants.START_INDEX_1_6;
					} else if (pian == 1 && chapter == 7) {
						questionId = SingleConstants.START_INDEX_1_7;
					} else if (pian == 1 && chapter == 8) {
						questionId = SingleConstants.START_INDEX_1_8;
					} else if (pian == 1 && chapter == 9) {
						questionId = SingleConstants.START_INDEX_1_9;
					} else if (pian == 1 && chapter == 10) {
						questionId = SingleConstants.START_INDEX_1_10;
					} else if (pian == 1 && chapter == 11) {
						questionId = SingleConstants.START_INDEX_1_11;
					} else if (pian == 2 && chapter == 1) {
						questionId = SingleConstants.START_INDEX_2_1;
					} else if (pian == 2 && chapter == 2) {
						questionId = SingleConstants.START_INDEX_2_2;
					} else if (pian == 2 && chapter == 3) {
						questionId = SingleConstants.START_INDEX_2_3;
					}
				} else {
					// 如果有进度，并且提交的pian和chapter与session中的一致，那么是继续本章的下一道题
					if (pian == 1 && chapter == 1) {
						if (progressNo == SingleConstants.AMOUNT_1_1) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_1 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 2) {
						if (progressNo == SingleConstants.AMOUNT_1_2) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_2 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 3) {
						if (progressNo == SingleConstants.AMOUNT_1_3) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_3 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 4) {
						if (progressNo == SingleConstants.AMOUNT_1_4) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_4 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 5) {
						if (progressNo == SingleConstants.AMOUNT_1_5) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_5 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 6) {
						if (progressNo == SingleConstants.AMOUNT_1_6) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_6 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 7) {
						if (progressNo == SingleConstants.AMOUNT_1_7) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_7 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 8) {
						if (progressNo == SingleConstants.AMOUNT_1_8) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_8 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 9) {
						if (progressNo == SingleConstants.AMOUNT_1_9) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_9 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 10) {
						if (progressNo == SingleConstants.AMOUNT_1_10) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_10 + progressNo;
							progressNo++;
						}
					} else if (pian == 1 && chapter == 11) {
						if (progressNo == SingleConstants.AMOUNT_1_11) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_1_11 + progressNo;
							progressNo++;
						}
					} else if (pian == 2 && chapter == 1) {
						if (progressNo == SingleConstants.AMOUNT_2_1) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_2_1 + progressNo;
							progressNo++;
						}
					} else if (pian == 2 && chapter == 2) {
						if (progressNo == SingleConstants.AMOUNT_2_2) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_2_2 + progressNo;
							progressNo++;
						}
					} else if (pian == 2 && chapter == 3) {
						if (progressNo == SingleConstants.AMOUNT_2_3) {
							progressNo = 1;
						} else {
							questionId = SingleConstants.START_INDEX_2_3 + progressNo;
							progressNo++;
						}
					}
				}
				session.setAttribute("progressPian", pian);
				session.setAttribute("progressChapter", chapter);
				session.setAttribute("progressNo", progressNo);
			}
			singleQuestion = QuestionJdbc.getSingleQuestionById(questionId);
			pian = singleQuestion.getPian();
			chapter = singleQuestion.getChapter();
			no = singleQuestion.getNo();
			singleQuestion.setAnswer("");
			jsonString = JSON.toJSONString(singleQuestion);
		} else if (questionType.equals("multiple")) {
			// 多选题
			MysqlQuestion mysqlQuestion = QuestionJdbc.getMysqlQuestionById(questionId);
			jsonString = JSON.toJSONString(mysqlQuestion);
		} else if (questionType.equals("check")) {
			// 判断题
			pian = Integer.parseInt(request.getParameter("pian"));
			chapter = Integer.parseInt(request.getParameter("chapter"));
			if (isRandom.equals("true")) {
				if (pian == 0 && chapter == 0) {
					questionId = random.nextInt(CheckConstants.TOTAL_AMOUNT) + 1;
				} else if (pian == 1 && chapter == 1) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_1) + CheckConstants.START_INDEX_1_1;
				} else if (pian == 1 && chapter == 2) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_2) + CheckConstants.START_INDEX_1_2;
				} else if (pian == 1 && chapter == 3) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_3) + CheckConstants.START_INDEX_1_3;
				} else if (pian == 1 && chapter == 4) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_4) + CheckConstants.START_INDEX_1_4;
				} else if (pian == 1 && chapter == 5) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_5) + CheckConstants.START_INDEX_1_5;
				} else if (pian == 1 && chapter == 6) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_6) + CheckConstants.START_INDEX_1_6;
				} else if (pian == 1 && chapter == 7) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_7) + CheckConstants.START_INDEX_1_7;
				} else if (pian == 1 && chapter == 8) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_8) + CheckConstants.START_INDEX_1_8;
				} else if (pian == 1 && chapter == 9) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_9) + CheckConstants.START_INDEX_1_9;
				} else if (pian == 1 && chapter == 10) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_10) + CheckConstants.START_INDEX_1_10;
				} else if (pian == 1 && chapter == 11) {
					questionId = random.nextInt(CheckConstants.AMOUNT_1_11) + CheckConstants.START_INDEX_1_11;
				} else if (pian == 2 && chapter == 1) {
					questionId = random.nextInt(CheckConstants.AMOUNT_2_1) + CheckConstants.START_INDEX_2_1;
				} else if (pian == 2 && chapter == 2) {
					questionId = random.nextInt(CheckConstants.AMOUNT_2_2) + CheckConstants.START_INDEX_2_2;
				} else if (pian == 2 && chapter == 3) {
					questionId = random.nextInt(CheckConstants.AMOUNT_2_3) + CheckConstants.START_INDEX_2_3;
				}
			}
			CheckQuestion checkQuestion = QuestionJdbc.getCheckQuestionById(questionId);
			checkQuestion.setAnswer("");
			jsonString = JSON.toJSONString(checkQuestion);
		}
		out.print(jsonString);
		out.flush();
		out.close();
		User user = (User) session.getAttribute("user");
		String userId = null;
		if (user != null) {
			userId = user.getUserId();
		}
		QuestionJdbc.insertRequestRecord(userAgent, ip, browserId, jsessionid, questionId, lastQuestionId, isRandom,
				pian, chapter, no, questionType, userId);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
