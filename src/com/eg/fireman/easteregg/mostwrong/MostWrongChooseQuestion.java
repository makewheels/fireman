package com.eg.fireman.easteregg.mostwrong;

import java.sql.Timestamp;

/**
 * 错误率最高的选择题bean
 * 
 * @author Administrator
 *
 */
public class MostWrongChooseQuestion {
	private int id;
	private int pian;
	private int chapter;
	private int no;
	private String questionType;
	private int questionId;
	private int wrongTimes;
	private int correctTimes;
	private int totalTimes;
	private double correctRate;
	private double wrongRate;
	private String mostWrongAnswer;
	private Timestamp createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public int getWrongTimes() {
		return wrongTimes;
	}

	public void setWrongTimes(int wrongTimes) {
		this.wrongTimes = wrongTimes;
	}

	public int getCorrectTimes() {
		return correctTimes;
	}

	public void setCorrectTimes(int correctTimes) {
		this.correctTimes = correctTimes;
	}

	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public double getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(double correctRate) {
		this.correctRate = correctRate;
	}

	public double getWrongRate() {
		return wrongRate;
	}

	public void setWrongRate(double wrongRate) {
		this.wrongRate = wrongRate;
	}

	public String getMostWrongAnswer() {
		return mostWrongAnswer;
	}

	public void setMostWrongAnswer(String mostWrongAnswer) {
		this.mostWrongAnswer = mostWrongAnswer;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "MostWrongMultipleQuestion [id=" + id + ", pian=" + pian + ", chapter=" + chapter + ", no=" + no
				+ ", questionType=" + questionType + ", questionId=" + questionId + ", wrongTimes=" + wrongTimes
				+ ", correctTimes=" + correctTimes + ", totalTimes=" + totalTimes + ", correctRate=" + correctRate
				+ ", wrongRate=" + wrongRate + ", mostWrongAnswer=" + mostWrongAnswer + ", createTime=" + createTime
				+ "]";
	}

}
