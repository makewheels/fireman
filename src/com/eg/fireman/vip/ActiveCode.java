package com.eg.fireman.vip;

import java.sql.Timestamp;

public class ActiveCode {
	private int id;
	private String userId;
	private String code;
	private int days;
	private int isActive;
	private String note;
	private Timestamp activeTime;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Timestamp getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Timestamp activeTime) {
		this.activeTime = activeTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "ActiveCode [id=" + id + ", userId=" + userId + ", code=" + code + ", days=" + days + ", isActive="
				+ isActive + ", note=" + note + ", activeTime=" + activeTime + ", createTime=" + createTime + "]";
	}

}
