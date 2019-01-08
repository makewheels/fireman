package com.eg.fireman.bean.result;

import java.sql.Timestamp;

/**
 * 在做选择题错题的时候要返回给前台的bean
 * 
 * @author Administrator
 *
 */
public class WrongChoiceQuestion {
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
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
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
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "WrongChoiceQuestion [id=" + id + ", questionId=" + questionId + ", pian=" + pian + ", chapter="
				+ chapter + ", questionType=" + questionType + ", no=" + no + ", wrongAnswer=" + wrongAnswer
				+ ", wrongInfo=" + wrongInfo + ", isDelete=" + isDelete + ", createTime=" + createTime + ", question="
				+ question + ", optionA=" + optionA + ", optionB=" + optionB + ", optionC=" + optionC + ", optionD="
				+ optionD + ", message=" + message + "]";
	}
}