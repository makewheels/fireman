package com.eg.fireman.bean;

import java.sql.Timestamp;

public class User {
	private int id;
	private String userId;
	private String username;
	private String password;
	private String loginToken;
	private String loginTokenExpireAt;
	private String phone;
	private int isPhoneVerified;
	private String mail;
	private int isMailVerified;
	private int isVip;
	private String vipExpireAt;
	private Timestamp createTime;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginToken() {
		return loginToken;
	}

	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}

	public String getLoginTokenExpireAt() {
		return loginTokenExpireAt;
	}

	public void setLoginTokenExpireAt(String loginTokenExpireAt) {
		this.loginTokenExpireAt = loginTokenExpireAt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getIsPhoneVerified() {
		return isPhoneVerified;
	}

	public void setIsPhoneVerified(int isPhoneVerified) {
		this.isPhoneVerified = isPhoneVerified;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getIsMailVerified() {
		return isMailVerified;
	}

	public void setIsMailVerified(int isMailVerified) {
		this.isMailVerified = isMailVerified;
	}

	public int getIsVip() {
		return isVip;
	}

	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}

	public String getVipExpireAt() {
		return vipExpireAt;
	}

	public void setVipExpireAt(String vipExpireAt) {
		this.vipExpireAt = vipExpireAt;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", username=" + username + ", password=" + password
				+ ", loginToken=" + loginToken + ", loginTokenExpireAt=" + loginTokenExpireAt + ", phone=" + phone
				+ ", isPhoneVerified=" + isPhoneVerified + ", mail=" + mail + ", isMailVerified=" + isMailVerified
				+ ", isVip=" + isVip + ", vipExpireAt=" + vipExpireAt + ", createTime=" + createTime + "]";
	}

}
