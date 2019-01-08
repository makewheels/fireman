package com.eg.fireman.vip;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class VipJdbc {
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

	public static ActiveCode getActiveCodeByCode(String code) {
		String sql = "select * from activeCode where code=?";
		ActiveCode activeCode = new ActiveCode();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean next = resultSet.next();
			if (next == false) {
				return null;
			}
			activeCode.setId(resultSet.getInt("id"));
			activeCode.setUserId(resultSet.getString("userId"));
			activeCode.setCode(resultSet.getString("code"));
			activeCode.setDays(resultSet.getInt("days"));
			activeCode.setIsActive(resultSet.getInt("isActive"));
			activeCode.setNote(resultSet.getString("note"));
			activeCode.setActiveTime(resultSet.getTimestamp("activeTime"));
			activeCode.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return activeCode;
	}

	public static void activeACodeByUserId(int id, String userId) {
		String sql = "update activeCode set isActive=1,userId=?,activeTime=? where id=?";
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, userId);
			preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(3, id);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void updateExpireAtByUserId(String userId, String updateExpireAt) {
		String sql = "update userTable set isVip=1,vipExpireAt=? where userId=?";
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, updateExpireAt);
			preparedStatement.setString(2, userId);
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存新订单
	 * 
	 * @param alipayOrder
	 */
	public static void saveAlipayOrder(AlipayOrder alipayOrder) {
		String sql = "insert into alipayOrder (userId,orderId,state,alipayOrderId,day,amountDouble,amountString,alipayBuyerPayAmount,alipayReceiptAmount,alipayBuyerId,alipayPayTime,alipayState,userAgent) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, alipayOrder.getUserId());
			preparedStatement.setString(2, alipayOrder.getOrderId());
			preparedStatement.setInt(3, alipayOrder.getState());
			preparedStatement.setString(4, alipayOrder.getAlipayOrderId());
			preparedStatement.setInt(5, alipayOrder.getDay());
			preparedStatement.setDouble(6, alipayOrder.getAmountDouble());
			preparedStatement.setString(7, alipayOrder.getAmountString());
			preparedStatement.setString(8, alipayOrder.getAlipayBuyerPayAmount());
			preparedStatement.setString(9, alipayOrder.getAlipayReceiptAmount());
			preparedStatement.setString(10, alipayOrder.getAlipayBuyerId());
			preparedStatement.setString(11, alipayOrder.getAlipayPayTime());
			preparedStatement.setString(12, alipayOrder.getAlipayState());
			preparedStatement.setString(13, alipayOrder.getUserAgent());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 已支付，更新订单
	 * 
	 * @param alipayOrder
	 */
	public static void updateAlipayOrderByOrderId(AlipayOrder alipayOrder) {
		String sql = "update alipayOrder set state=1,alipayOrderId=?,alipayBuyerPayAmount=?,alipayReceiptAmount=?,alipayBuyerId=?,alipayPaytime=?,alipayState=?,alipayBuyerAccount=?,alipayAmount=?,parameterMapJsonString=? where orderId=?";
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, alipayOrder.getAlipayOrderId());
			preparedStatement.setString(2, alipayOrder.getAlipayBuyerPayAmount());
			preparedStatement.setString(3, alipayOrder.getAlipayReceiptAmount());
			preparedStatement.setString(4, alipayOrder.getAlipayBuyerId());
			preparedStatement.setString(5, alipayOrder.getAlipayPayTime());
			preparedStatement.setString(6, alipayOrder.getAlipayState());
			preparedStatement.setString(7, alipayOrder.getAlipayBuyerAccount());
			preparedStatement.setString(8, alipayOrder.getAlipayAmount());
			preparedStatement.setString(9, alipayOrder.getParameterMapJsonString());
			preparedStatement.setString(10, alipayOrder.getOrderId());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static AlipayOrder getAlipayOrderByOrderId(String orderId) {
		String sql = "select * from alipayOrder where orderId=?";
		AlipayOrder alipayOrder = new AlipayOrder();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, orderId);
			ResultSet resultSet = preparedStatement.executeQuery();
			boolean next = resultSet.next();
			if (next == false) {
				return null;
			}
			alipayOrder.setOrderId(orderId);
			alipayOrder.setUserId(resultSet.getString("userId"));
			alipayOrder.setAmountString(resultSet.getString("amountString"));
			alipayOrder.setDay(resultSet.getInt("day"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alipayOrder;
	}
}
