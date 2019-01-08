package com.eg.fireman.util;

public class Result {
	private boolean result;
	private String correctAnswer;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public String toString() {
		return "Result [result=" + result + ", correctAnswer=" + correctAnswer + "]";
	}

}
