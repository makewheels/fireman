package prepare.create;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eg.fireman.bean.Answer;
import com.eg.fireman.bean.CheckQuestion;
import com.eg.fireman.util.AnswerUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class InsertCheck {
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

	private static void save(CheckQuestion checkQuestion) {
		String sql = "insert into checkQuestion (no,pian,chapter,question,answer) values(?,?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, checkQuestion.getNo());
			preparedStatement.setInt(2, checkQuestion.getPian());
			preparedStatement.setInt(3, checkQuestion.getChapter());
			preparedStatement.setString(4, checkQuestion.getQuestion());
			preparedStatement.setString(5, checkQuestion.getAnswer());
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		for (int i = 1; i <= 3; i++) {
			int chapter = i;
			int pian = 2;
			String folderPath = "D:\\SoftTopics\\2018.05.19消防员试题\\题\\判断\\第二篇\\";
			String fileNumber = chapter + "";
			FileReader fileReader = new FileReader(folderPath + fileNumber + ".txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			List<CheckQuestion> questionList = new ArrayList<>();
			while ((line = bufferedReader.readLine()) != null) {
				CheckQuestion checkQuestion = new CheckQuestion();
				checkQuestion.setPian(pian);
				checkQuestion.setChapter(chapter);
				int index = line.indexOf("、");
				checkQuestion.setNo(Integer.parseInt(line.substring(0, index)));
				checkQuestion.setQuestion(line.substring(index + 1));
				questionList.add(checkQuestion);
			}
			bufferedReader.close();
			fileReader.close();
			List<Answer> answerList = AnswerUtil.load(folderPath + fileNumber + "a.txt");
			for (CheckQuestion checkQuestion : questionList) {
				String answer = answerList.get(checkQuestion.getNo() - 1).getAnswer();
				checkQuestion.setAnswer(answer);
			}
			System.out.println("answerList-size: " + answerList.size());
			System.out.println("questionList-size: " + questionList.size());
			for (CheckQuestion checkQuestion : questionList) {
				save(checkQuestion);
			}
		}
	}

}
