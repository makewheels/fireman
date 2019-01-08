package com.eg.fireman.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 错题
 * 
 * @author Administrator
 *
 */
public class WrongQuestion implements Serializable {
	private static final long serialVersionUID = 6960839797874762956L;

	private int id;
	private String userId;
	private int questionId;
	private int pian;
	private int chapter;
	private String questionType;
	private int no;
	private String answer;
	private String isRandom;
	private int wrongTimeCount;
	private int correctTimeCount;
	private int isDelete;
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

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getPian() {
		return pian;
	}

	public void setPian(int pian) {
		this.pian = pian;
	}

	public int getChapter() {
		return chapter;
	}

	public void setChapter(int chapter) {
		this.chapter = chapter;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getIsRandom() {
		return isRandom;
	}

	public void setIsRandom(String isRandom) {
		this.isRandom = isRandom;
	}

	public int getWrongTimeCount() {
		return wrongTimeCount;
	}

	public void setWrongTimeCount(int wrongTimeCount) {
		this.wrongTimeCount = wrongTimeCount;
	}

	public int getCorrectTimeCount() {
		return correctTimeCount;
	}

	public void setCorrectTimeCount(int correctTimeCount) {
		this.correctTimeCount = correctTimeCount;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "WrongQuestion [id=" + id + ", userId=" + userId + ", questionId=" + questionId + ", pian=" + pian
				+ ", chapter=" + chapter + ", questionType=" + questionType + ", no=" + no + ", answer=" + answer
				+ ", isRandom=" + isRandom + ", wrongTimeCount=" + wrongTimeCount + ", correctTimeCount="
				+ correctTimeCount + ", isDelete=" + isDelete + ", createTime=" + createTime + "]";
	}

}
