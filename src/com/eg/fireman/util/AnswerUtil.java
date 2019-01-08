package com.eg.fireman.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eg.fireman.bean.Answer;

public class AnswerUtil {
	public static List<Answer> load(String filepath) throws IOException {
		FileReader fileReader = new FileReader(filepath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		List<Answer> answerList = new ArrayList<>();
		while ((line = bufferedReader.readLine()) != null) {
			String questionIdString = "";
			String answerString = "";
			boolean isNumber = true;
			Answer answer = null;
			for (int i = 0; i < line.length(); i++) {
				char c = line.charAt(i);
				if (isNumber == true) {
					// 如果上一个是数字
					if (Character.isDigit(c)) {
						questionIdString += c;
					} else {
						// 这个是字母，说明题号读取完成了
						isNumber = false;
						answerString = c + "";
						answer = new Answer();
						int questionId = Integer.parseInt(questionIdString);
						answer.setQuestionId(questionId);
					}
				} else {
					// 如果上一个是字母
					if (Character.isLetter(c)) {
						answerString += c;
					} else {
						// 这个是数字，说明刚才的题读完了，现在开始了新的题
						isNumber = true;
						answer.setAnswer(answerString);
						answerList.add(answer);
						questionIdString = c + "";
						answerString = "";
					}
				}
			}
		}
		bufferedReader.close();
		fileReader.close();
		return answerList;
	}
}
