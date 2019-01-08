package com.eg.fireman.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eg.fireman.bean.Question;

public class QuestionUtil {
	public static List<Question> load() throws IOException {
		String path = AnswerUtil.class.getClassLoader().getResource("question.txt").getPath();
		FileReader fileReader = new FileReader(path);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		List<Question> questionList = new ArrayList<>();
		String questionIdString;
		Question question = null;
		while ((line = bufferedReader.readLine()) != null) {
			char startChar = line.charAt(0);
			if (Character.isDigit(startChar)) {
				question = new Question();
				questionIdString = startChar + "";
				int i = 1;
				while (Character.isDigit(line.charAt(i))) {
					questionIdString += line.charAt(i);
					i++;
				}
				question.setQuestionId(Integer.parseInt(questionIdString));
				question.setQuestion(line.substring(i + 1));
			} else {
				char optionChar = line.charAt(1);
				if (optionChar == 'D') {
					question.setOptionD(line.substring(3));
					questionList.add(question);
				} else {
					if (optionChar == 'A') {
						question.setOptionA(line.substring(3));
					} else if (optionChar == 'B') {
						question.setOptionB(line.substring(3));
					} else {
						question.setOptionC(line.substring(3));
					}
				}
			}
		}
		bufferedReader.close();
		fileReader.close();
		return questionList;
	}
}
