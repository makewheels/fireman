package com.eg.fireman.bean.result;

import java.sql.Timestamp;

public class WrongCheckQuestion {
	private int id;
	private int questionId;
	private int pian;
	private int chapter;
	private String questionType;
	private int no;
	private String wrongAnswer;
	private String wrongInfo;
	private int isDelete;
	private Timestamp createTime;
	private String question;
	private String message;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getWrongAnswer() {
		return wrongAnswer;
	}

	public void setWrongAnswer(String wrongAnswer) {
		this.wrongAnswer = wrongAnswer;
	}

	public String getWrongInfo() {
		return wrongInfo;
	}

	public void setWrongInfo(String wrongInfo) {
		this.wrongInfo = wrongInfo;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "WrongCheckQuestion [id=" + id + ", questionId=" + questionId + ", pian=" + pian + ", chapter=" + chapter
				+ ", questionType=" + questionType + ", no=" + no + ", wrongAnswer=" + wrongAnswer + ", wrongInfo="
				+ wrongInfo + ", isDelete=" + isDelete + ", createTime=" + createTime + ", question=" + question
				+ ", message=" + message + "]";
	}

}
