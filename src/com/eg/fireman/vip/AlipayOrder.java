package com.eg.fireman.vip;

import java.sql.Timestamp;

public class AlipayOrder {
	private int id;
	private String userId;
	private String orderId;
	private int state;
	private String alipayOrderId;
	private int day;
	private double amountDouble;
	private String amountString;
	private String alipayBuyerPayAmount;
	private String alipayReceiptAmount;
	private String alipayBuyerId;
	private Timestamp submitTime;
	private String alipayPayTime;
	private String alipayState;
	private String alipayBuyerAccount;
	private String alipayAmount;
	private String userAgent;
	private String parameterMapJsonString;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAlipayOrderId() {
		return alipayOrderId;
	}

	public void setAlipayOrderId(String alipayOrderId) {
		this.alipayOrderId = alipayOrderId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public double getAmountDouble() {
		return amountDouble;
	}

	public void setAmountDouble(double amountDouble) {
		this.amountDouble = amountDouble;
	}

	public String getAmountString() {
		return amountString;
	}

	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}

	public String getAlipayBuyerPayAmount() {
		return alipayBuyerPayAmount;
	}

	public void setAlipayBuyerPayAmount(String alipayBuyerPayAmount) {
		this.alipayBuyerPayAmount = alipayBuyerPayAmount;
	}

	public String getAlipayReceiptAmount() {
		return alipayReceiptAmount;
	}

	public void setAlipayReceiptAmount(String alipayReceiptAmount) {
		this.alipayReceiptAmount = alipayReceiptAmount;
	}

	public String getAlipayBuyerId() {
		return alipayBuyerId;
	}

	public void setAlipayBuyerId(String alipayBuyerId) {
		this.alipayBuyerId = alipayBuyerId;
	}

	public Timestamp getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}

	public String getAlipayPayTime() {
		return alipayPayTime;
	}

	public void setAlipayPayTime(String alipayPayTime) {
		this.alipayPayTime = alipayPayTime;
	}

	public String getAlipayState() {
		return alipayState;
	}

	public void setAlipayState(String alipayState) {
		this.alipayState = alipayState;
	}

	public String getAlipayBuyerAccount() {
		return alipayBuyerAccount;
	}

	public void setAlipayBuyerAccount(String alipayBuyerAccount) {
		this.alipayBuyerAccount = alipayBuyerAccount;
	}

	public String getAlipayAmount() {
		return alipayAmount;
	}

	public void setAlipayAmount(String alipayAmount) {
		this.alipayAmount = alipayAmount;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getParameterMapJsonString() {
		return parameterMapJsonString;
	}

	public void setParameterMapJsonString(String parameterMapJsonString) {
		this.parameterMapJsonString = parameterMapJsonString;
	}

	@Override
	public String toString() {
		return "AlipayOrder [id=" + id + ", userId=" + userId + ", orderId=" + orderId + ", state=" + state
				+ ", alipayOrderId=" + alipayOrderId + ", day=" + day + ", amountDouble=" + amountDouble
				+ ", amountString=" + amountString + ", alipayBuyerPayAmount=" + alipayBuyerPayAmount
				+ ", alipayReceiptAmount=" + alipayReceiptAmount + ", alipayBuyerId=" + alipayBuyerId + ", submitTime="
				+ submitTime + ", alipayPayTime=" + alipayPayTime + ", alipayState=" + alipayState
				+ ", alipayBuyerAccount=" + alipayBuyerAccount + ", alipayAmount=" + alipayAmount + ", userAgent="
				+ userAgent + ", parameterMapJsonString=" + parameterMapJsonString + "]";
	}

}