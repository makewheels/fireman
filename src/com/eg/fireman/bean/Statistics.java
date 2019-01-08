package com.eg.fireman.bean;

public class Statistics {
	private int totalRequest;
	private int totalSubmit;
	private int totalCorrect;
	private String correctRate;

	public int getTotalRequest() {
		return totalRequest;
	}

	public void setTotalRequest(int totalRequest) {
		this.totalRequest = totalRequest;
	}

	public int getTotalSubmit() {
		return totalSubmit;
	}

	public void setTotalSubmit(int totalSubmit) {
		this.totalSubmit = totalSubmit;
	}

	public int getTotalCorrect() {
		return totalCorrect;
	}

	public void setTotalCorrect(int totalCorrect) {
		this.totalCorrect = totalCorrect;
	}

	public String getCorrectRate() {
		return correctRate;
	}

	public void setCorrectRate(String correctRate) {
		this.correctRate = correctRate;
	}

	@Override
	public String toString() {
		return "Statistics [totalRequest=" + totalRequest + ", totalSubmit=" + totalSubmit + ", totalCorrect="
				+ totalCorrect + ", correctRate=" + correctRate + "]";
	}

}
