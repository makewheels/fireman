package com.eg.fireman.jdbc;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eg.fireman.bean.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class UserJdbc {
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

	public static User getUserByUsername(String username) {
		String sql = "select * from userTable where username=?";
		User user = new User();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean next = resultSet.next();
			if (next == false) {
				return null;
			}
			user.setId(resultSet.getInt("id"));
			user.setUserId(resultSet.getString("userId"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setLoginToken(resultSet.getString("loginToken"));
			user.setLoginTokenExpireAt(resultSet.getString("loginTokenExpireAt"));
			user.setPhone(resultSet.getString("phone"));
			user.setIsPhoneVerified(resultSet.getInt("isPhoneVerified"));
			user.setMail(resultSet.getString("mail"));
			user.setIsMailVerified(resultSet.getInt("isMailVerified"));
			user.setIsVip(resultSet.getInt("isVip"));
			user.setVipExpireAt(resultSet.getString("vipExpireAt"));
			user.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static void saveUser(String username, String password, String userId) {
		String sql = "insert into userTable (username,password,userId) values(?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, userId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登录日志
	 */
	public static void saveLoginLog(String userId, String userAgent, String ip, String browserId, String type,
			String loginToken) {
		String sql = "insert into loginLog (userId,userAgent,ip,browserId,type,loginToken) values(?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setString(2, userAgent);
			preparedStatement.setString(3, ip);
			preparedStatement.setString(4, browserId);
			preparedStatement.setString(5, type);
			preparedStatement.setString(6, loginToken);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据loginToken查user
	 */
	public static User getUserByLoginToken(String browserLoginToken) {
		if (browserLoginToken == null || browserLoginToken.equals("")) {
			return null;
		}
		String sql = "select * from userTable where loginToken=? and loginToken is not null and loginToken!=''";
		User user = new User();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, browserLoginToken);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean next = resultSet.next();
			if (next == false) {
				return null;
			}
			user.setId(resultSet.getInt("id"));
			user.setUserId(resultSet.getString("userId"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setLoginToken(resultSet.getString("loginToken"));
			user.setLoginTokenExpireAt(resultSet.getString("loginTokenExpireAt"));
			user.setPhone(resultSet.getString("phone"));
			user.setIsPhoneVerified(resultSet.getInt("isPhoneVerified"));
			user.setMail(resultSet.getString("mail"));
			user.setIsMailVerified(resultSet.getInt("isMailVerified"));
			user.setIsVip(resultSet.getInt("isVip"));
			user.setVipExpireAt(resultSet.getString("vipExpireAt"));
			user.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 根据userId查user
	 */
	public static User getUserByUserId(String userId) {
		String sql = "select * from userTable where userId=?";
		User user = new User();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean next = resultSet.next();
			if (next == false) {
				return null;
			}
			user.setId(resultSet.getInt("id"));
			user.setUserId(resultSet.getString("userId"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setLoginToken(resultSet.getString("loginToken"));
			user.setLoginTokenExpireAt(resultSet.getString("loginTokenExpireAt"));
			user.setPhone(resultSet.getString("phone"));
			user.setIsPhoneVerified(resultSet.getInt("isPhoneVerified"));
			user.setMail(resultSet.getString("mail"));
			user.setIsMailVerified(resultSet.getInt("isMailVerified"));
			user.setIsVip(resultSet.getInt("isVip"));
			user.setVipExpireAt(resultSet.getString("vipExpireAt"));
			user.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 根据loginToken查user
	 */
	public static void updateLoginTokenByUserId(String userId, String loginToken) {
		String sql = "update userTable set loginToken=? where userId=?";
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, loginToken);
			preparedStatement.setString(2, userId);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static User getUserByPhone(String phone) {
		String sql = "select * from userTable where phone=? and isPhoneVerified=1";
		User user = new User();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, phone);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean next = resultSet.next();
			if (next == false) {
				return null;
			}
			user.setId(resultSet.getInt("id"));
			user.setUserId(resultSet.getString("userId"));
			user.setUsername(resultSet.getString("username"));
			user.setPassword(resultSet.getString("password"));
			user.setLoginToken(resultSet.getString("loginToken"));
			user.setLoginTokenExpireAt(resultSet.getString("loginTokenExpireAt"));
			user.setPhone(resultSet.getString("phone"));
			user.setIsPhoneVerified(resultSet.getInt("isPhoneVerified"));
			user.setMail(resultSet.getString("mail"));
			user.setIsMailVerified(resultSet.getInt("isMailVerified"));
			user.setIsVip(resultSet.getInt("isVip"));
			user.setVipExpireAt(resultSet.getString("vipExpireAt"));
			user.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
}
