package com.eg.fireman.bean;

public class Answer {
	private int questionId;
	private String answer;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Override
	public String toString() {
		return "Answer [questionId=" + questionId + ", answer=" + answer + "]";
	}

}
