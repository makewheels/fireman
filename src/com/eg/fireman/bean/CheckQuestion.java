package com.eg.fireman.bean;

/**
 * 判断题
 * 
 * @author Administrator
 *
 */
public class CheckQuestion {
	private int id;
	private int no;
	private int pian;
	private int chapter;
	private String question;
	private String answer;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "CheckQuestion [id=" + id + ", no=" + no + ", pian=" + pian + ", chapter=" + chapter + ", question="
				+ question + ", answer=" + answer + "]";
	}

}
