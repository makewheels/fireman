package prepare.create;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eg.fireman.bean.Answer;
import com.eg.fireman.bean.SingleQuestion;
import com.eg.fireman.util.AnswerUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class InsertSingle {
	private static Connection connection = null;
	static {
		if (connection == null) {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://qbserver.cn:3306/fireman";
			String username = "root";
			String password = "mysqlmima123";
			try {
				Class.forName(driver);
				connection = (Connection) DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void insert(SingleQuestion question) {
		String sql = "insert into singleQuestion (no,pian,chapter,question,optionA,optionB,optionC,optionD,answer) values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, question.getNo());
			preparedStatement.setInt(2, question.getPian());
			preparedStatement.setInt(3, question.getChapter());
			preparedStatement.setString(4, question.getQuestion());
			preparedStatement.setString(5, question.getOptionA());
			preparedStatement.setString(6, question.getOptionB());
			preparedStatement.setString(7, question.getOptionC());
			preparedStatement.setString(8, question.getOptionD());
			preparedStatement.setString(9, question.getAnswer());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		int chapter = 3;
		int pian = 2;
		String folderPath = "D:\\SoftTopics\\消防员试题\\题\\单选\\第二篇\\";
		String fileNumber = chapter + "";
		FileReader fileReader = new FileReader(folderPath + fileNumber + ".txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String line;
		List<SingleQuestion> questionList = new ArrayList<>();
		String questionIdString;
		SingleQuestion question = null;
		while ((line = bufferedReader.readLine()) != null) {
			char startChar = line.charAt(0);
			try {
				if (Character.isDigit(startChar)) {
					question = new SingleQuestion();
					questionIdString = startChar + "";
					int i = 1;
					while (Character.isDigit(line.charAt(i))) {
						questionIdString += line.charAt(i);
						i++;
					}
					question.setPian(pian);
					question.setChapter(chapter);
					question.setNo(Integer.parseInt(questionIdString));
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bufferedReader.close();
		fileReader.close();
		List<Answer> answerList = AnswerUtil.load(folderPath + fileNumber + "a.txt");
		for (SingleQuestion q : questionList) {
			String answer = answerList.get(q.getNo() - 1).getAnswer();
			q.setAnswer(answer);
			System.out.println(q);
			insert(q);
		}
		System.out.println("questionList-size: " + questionList.size());
		System.out.println("answerList-size: " + answerList.size());
	}

}
