package com.eg.fireman.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 日志
 * 
 * @author Administrator
 *
 */
public class LogJdbc {
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

	/**
	 * 保存请求题目记录：新版本，简化了。还加入了请求类型requestType
	 */
	public static void insertRequestRecord(String userAgent, String ip, String browserId, String jsessionid,
			int questionId, String isRandom, String questionType, String userId, String requestType) {
		String sql = "insert into requestLog (userAgent,ip,time,browserId,jsessionid,questionId,isRandom,userId,requestType,questionType) values(?,?,?,?,?,?,?,?,?,?)";
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
			preparedStatement.setString(7, isRandom + "");
			preparedStatement.setString(8, userId);
			preparedStatement.setString(9, requestType);
			preparedStatement.setString(10, questionType);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
