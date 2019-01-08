package com.eg.fireman.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eg.fireman.bean.CheckQuestion;
import com.eg.fireman.bean.MysqlQuestion;
import com.eg.fireman.bean.SingleQuestion;
import com.eg.fireman.easteregg.mostwrong.MostWrongCheckQuestion;
import com.eg.fireman.easteregg.mostwrong.MostWrongChooseQuestion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class QuestionJdbc {
	private static Connection connection = null;
	static {
		if (connection == null) {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/fireman";
			String username = "root";
			String password = "mysqlmima123";
			try {
				Class.forName(driver);
				connection = (Connection) DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Connection getConnection() {
		return connection;
	}

	/**
	 * 插入数据库
	 * 
	 * @param questionId
	 *            id
	 * @param question
	 *            题干
	 * @param answer
	 *            答案
	 * @param optionA
	 * @param optionB
	 * @param optionC
	 * @param optionD
	 */
	public static void insertQuestion(int questionId, String question, String answer, String optionA, String optionB,
			String optionC, String optionD) {
		String sql = "insert into multipleQuestion (questionId,question,answer,optionA,optionB,optionC,optionD) values(?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			preparedStatement.setString(2, question);
			preparedStatement.setString(3, answer);
			preparedStatement.setString(4, optionA);
			preparedStatement.setString(5, optionB);
			preparedStatement.setString(6, optionC);
			preparedStatement.setString(7, optionD);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据id查MysqlQuestion，这个是跟mysql对应的，有answer<br>
	 * 应该是一个老接口，是多选题的
	 * 
	 * @param questionId
	 * @return
	 */
	public static MysqlQuestion getMysqlQuestionById(int questionId) {
		String sql = "select * from multipleQuestion where questionId=?";
		MysqlQuestion mysqlQuestion = new MysqlQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			mysqlQuestion.setQuestionId(questionId);
			mysqlQuestion.setQuestion(resultSet.getString("question"));
			mysqlQuestion.setAnswer(resultSet.getString("answer"));
			mysqlQuestion.setOptionA(resultSet.getString("optionA"));
			mysqlQuestion.setOptionB(resultSet.getString("optionB"));
			mysqlQuestion.setOptionC(resultSet.getString("optionC"));
			mysqlQuestion.setOptionD(resultSet.getString("optionD"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mysqlQuestion;
	}

	/**
	 * 保存请求题目记录
	 */
	public static void insertRequestRecord(String userAgent, String ip, String browserId, String jsessionid,
			int questionId, int lastQuestionId, String isRandom, int pian, int chapter, int no, String questionType,
			String userId) {
		String sql = "insert into requestLog (userAgent,ip,time,browserId,jsessionid,questionId,lastQuestionId,isRandom,pian,chapter,no,questionType,userId) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, userAgent);
			preparedStatement.setString(2, ip);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			preparedStatement.setString(3, simpleDateFormat.format(new Date()));
			preparedStatement.setString(4, browserId);
			preparedStatement.setString(5, jsessionid);
			preparedStatement.setInt(6, questionId);
			preparedStatement.setInt(7, lastQuestionId);
			preparedStatement.setString(8, isRandom + "");
			preparedStatement.setInt(9, pian);
			preparedStatement.setInt(10, chapter);
			preparedStatement.setInt(11, no);
			preparedStatement.setString(12, questionType);
			preparedStatement.setString(13, userId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存提交答案记录，老版本
	 */
	public static void insertSubmitRecord(String userAgent, String ip, String browserId, String jsessionid,
			int questionId, String submitAnswer, String standardAnswer, String result, String isRandom, String type,
			int pian, int chapter, int no, String userId) {
		String sql = "insert into submitLog (userAgent,ip,time,browserId,jsessionid,questionId,submitAnswer,standardAnswer,result,isRandom,questionType,pian,chapter,no,userId) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, userAgent);
			preparedStatement.setString(2, ip);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			preparedStatement.setString(3, simpleDateFormat.format(new Date()));
			preparedStatement.setString(4, browserId);
			preparedStatement.setString(5, jsessionid);
			preparedStatement.setInt(6, questionId);
			preparedStatement.setString(7, submitAnswer);
			preparedStatement.setString(8, standardAnswer);
			preparedStatement.setString(9, result);
			preparedStatement.setString(10, isRandom);
			preparedStatement.setString(11, type);
			preparedStatement.setInt(12, pian);
			preparedStatement.setInt(13, chapter);
			preparedStatement.setInt(14, no);
			preparedStatement.setString(15, userId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 总请求
	 * 
	 * @return
	 */
	public static int getTotalRequest() {
		String sql = "SELECT COUNT(*) FROM requestLog";
		PreparedStatement preparedStatement;
		int totalRequest = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			totalRequest = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalRequest;
	}

	/**
	 * 总提交
	 * 
	 * @return
	 */
	public static int getTotalSubmit() {
		String sql = "SELECT COUNT(*) FROM submitLog";
		PreparedStatement preparedStatement;
		int totalSubmit = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			totalSubmit = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalSubmit;
	}

	/**
	 * 总正确
	 * 
	 * @return
	 */
	public static int getTotalSubmitCorrect() {
		String sql = "SELECT COUNT(*) FROM submitLog where result='true'";
		PreparedStatement preparedStatement;
		int totalCorrect = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			totalCorrect = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalCorrect;
	}

	/**
	 * 统计数据访问日志
	 */
	public static void insertStatisticsLog(String userAgent, String ip, String browserId, String jsessionid,
			String userId) {
		String sql = "insert into statisticsLog (userAgent,ip,time,browserId,jsessionid,userId) values(?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, userAgent);
			preparedStatement.setString(2, ip);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			preparedStatement.setString(3, simpleDateFormat.format(new Date()));
			preparedStatement.setString(4, browserId);
			preparedStatement.setString(5, jsessionid);
			preparedStatement.setString(6, userId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回随机指定章节单选
	 */
	public static SingleQuestion getSingleQuestionById(int questionId) {
		String sql = "select * from singleQuestion where id=?";
		SingleQuestion singleQuestion = new SingleQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			singleQuestion.setId(questionId);
			singleQuestion.setNo(resultSet.getInt("no"));
			singleQuestion.setPian(resultSet.getInt("pian"));
			singleQuestion.setChapter(resultSet.getInt("chapter"));
			singleQuestion.setQuestion(resultSet.getString("question"));
			singleQuestion.setOptionA(resultSet.getString("optionA"));
			singleQuestion.setOptionB(resultSet.getString("optionB"));
			singleQuestion.setOptionC(resultSet.getString("optionC"));
			singleQuestion.setOptionD(resultSet.getString("optionD"));
			singleQuestion.setAnswer(resultSet.getString("answer"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return singleQuestion;
	}

	/**
	 * 保存反馈
	 */
	public static void saveFeedback(String question, String contact, String userAgent, String ip, String browserId,
			String jsessionid, String userId) {
		String sql = "insert into feedback (question,contact,userAgent,ip,browserId,jsessionid,userId) values(?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, question);
			preparedStatement.setString(2, contact);
			preparedStatement.setString(3, userAgent);
			preparedStatement.setString(4, ip);
			preparedStatement.setString(5, browserId);
			preparedStatement.setString(6, jsessionid);
			preparedStatement.setString(7, userId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static CheckQuestion getCheckQuestionById(int questionId) {
		String sql = "select * from checkQuestion where id=?";
		CheckQuestion checkQuestion = new CheckQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			checkQuestion.setId(questionId);
			checkQuestion.setNo(resultSet.getInt("no"));
			checkQuestion.setPian(resultSet.getInt("pian"));
			checkQuestion.setChapter(resultSet.getInt("chapter"));
			checkQuestion.setQuestion(resultSet.getString("question"));
			checkQuestion.setAnswer(resultSet.getString("answer"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return checkQuestion;
	}

	/**
	 * 彩蛋：最高错误率多选错题总数
	 * 
	 * @return
	 */
	public static int getTotalMostWrongMultiple() {
		String sql = "SELECT COUNT(*) FROM multipleMostWrong";
		PreparedStatement preparedStatement;
		int total = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			total = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 彩蛋：最高错误率单选错题总数
	 * 
	 * @return
	 */
	public static int getTotalMostWrongSingle() {
		String sql = "SELECT COUNT(*) FROM singleMostWrong";
		PreparedStatement preparedStatement;
		int total = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			total = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 彩蛋：最高错误率判断错题总数
	 * 
	 * @return
	 */
	public static int getTotalMostWrongCheck() {
		String sql = "SELECT COUNT(*) FROM checkMostWrong";
		PreparedStatement preparedStatement;
		int total = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			total = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * 从最高错误率表中提取bean
	 * 
	 * @param id
	 * @return
	 */
	public static MostWrongChooseQuestion getMostWrongMultipleQuestionById(int id) {
		String sql = "select * from multipleMostWrong where id=?";
		MostWrongChooseQuestion question = new MostWrongChooseQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			question.setId(id);
			question.setPian(resultSet.getInt("pian"));
			question.setChapter(resultSet.getInt("chapter"));
			question.setNo(resultSet.getInt("no"));
			question.setQuestionType(resultSet.getString("questionType"));
			question.setQuestionId(resultSet.getInt("questionId"));
			question.setWrongTimes(resultSet.getInt("wrongTimes"));
			question.setCorrectTimes(resultSet.getInt("correctTimes"));
			question.setTotalTimes(resultSet.getInt("totalTimes"));
			question.setCorrectRate(resultSet.getDouble("correctRate"));
			question.setWrongRate(resultSet.getDouble("wrongRate"));
			question.setMostWrongAnswer(resultSet.getString("mostWrongAnswer"));
			question.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}

	/**
	 * 从最高错误率表中提取bean
	 * 
	 * @param id
	 * @return
	 */
	public static MostWrongChooseQuestion getMostWrongSingleQuestionById(int id) {
		String sql = "select * from singleMostWrong where id=?";
		MostWrongChooseQuestion question = new MostWrongChooseQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			question.setId(id);
			question.setPian(resultSet.getInt("pian"));
			question.setChapter(resultSet.getInt("chapter"));
			question.setNo(resultSet.getInt("no"));
			question.setQuestionType(resultSet.getString("questionType"));
			question.setQuestionId(resultSet.getInt("questionId"));
			question.setWrongTimes(resultSet.getInt("wrongTimes"));
			question.setCorrectTimes(resultSet.getInt("correctTimes"));
			question.setTotalTimes(resultSet.getInt("totalTimes"));
			question.setCorrectRate(resultSet.getDouble("correctRate"));
			question.setWrongRate(resultSet.getDouble("wrongRate"));
			question.setMostWrongAnswer(resultSet.getString("mostWrongAnswer"));
			question.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}

	/**
	 * 从最高错误率表中提取bean
	 * 
	 * @param id
	 * @return
	 */
	public static MostWrongCheckQuestion getMostWrongCheckQuestionById(int id) {
		String sql = "select * from checkMostWrong where id=?";
		MostWrongCheckQuestion question = new MostWrongCheckQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			question.setId(id);
			question.setPian(resultSet.getInt("pian"));
			question.setChapter(resultSet.getInt("chapter"));
			question.setNo(resultSet.getInt("no"));
			question.setQuestionType(resultSet.getString("questionType"));
			question.setQuestionId(resultSet.getInt("questionId"));
			question.setWrongTimes(resultSet.getInt("wrongTimes"));
			question.setCorrectTimes(resultSet.getInt("correctTimes"));
			question.setTotalTimes(resultSet.getInt("totalTimes"));
			question.setCorrectRate(resultSet.getDouble("correctRate"));
			question.setWrongRate(resultSet.getDouble("wrongRate"));
			question.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}
}
