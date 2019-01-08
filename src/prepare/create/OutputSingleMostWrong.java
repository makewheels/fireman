package prepare.create;

import java.io.FileWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eg.fireman.bean.SingleQuestion;
import com.eg.fireman.easteregg.mostwrong.MostWrongChooseQuestion;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 从数据库读数据，输出到文本中
 * 
 * @author Administrator
 *
 */
public class OutputSingleMostWrong {
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

	public static Connection getConnection() {
		return connection;
	}

	/**
	 * 返回随机指定章节单选
	 */
	public static SingleQuestion getSingleQuestionById(int questionId) {
		String sql = "select * from singleQuestion where id=?";
		SingleQuestion singleQuestion = new SingleQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, questionId);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			singleQuestion.setId(questionId);
			singleQuestion.setNo(resultSet.getInt("no"));
			singleQuestion.setPian(resultSet.getInt("pian"));
			singleQuestion.setChapter(resultSet.getInt("chapter"));
			singleQuestion.setQuestion(resultSet.getString("question"));
			singleQuestion.setOptionA(resultSet.getString("optionA"));
			singleQuestion.setOptionB(resultSet.getString("optionB"));
			singleQuestion.setOptionC(resultSet.getString("optionC"));
			singleQuestion.setOptionD(resultSet.getString("optionD"));
			singleQuestion.setAnswer(resultSet.getString("answer"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return singleQuestion;
	}

	/***
	 * 从最高错误率表中提取bean
	 * 
	 * @param id
	 * @return
	 */
	public static MostWrongChooseQuestion getMostWrongSingleQuestionById(int id) {
		String sql = "select * from singleMostWrong where id=?";
		MostWrongChooseQuestion question = new MostWrongChooseQuestion();
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			question.setId(id);
			question.setPian(resultSet.getInt("pian"));
			question.setChapter(resultSet.getInt("chapter"));
			question.setNo(resultSet.getInt("no"));
			question.setQuestionType(resultSet.getString("questionType"));
			question.setQuestionId(resultSet.getInt("questionId"));
			question.setWrongTimes(resultSet.getInt("wrongTimes"));
			question.setCorrectTimes(resultSet.getInt("correctTimes"));
			question.setTotalTimes(resultSet.getInt("totalTimes"));
			question.setCorrectRate(resultSet.getDouble("correctRate"));
			question.setWrongRate(resultSet.getDouble("wrongRate"));
			question.setMostWrongAnswer(resultSet.getString("mostWrongAnswer"));
			question.setCreateTime(resultSet.getTimestamp("createTime"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return question;
	}

	/**
	 * 彩蛋：最高错误率单选错题总数
	 * 
	 * @return
	 */
	public static int getTotalMostWrongSingle() {
		String sql = "SELECT COUNT(*) FROM singleMostWrong";
		PreparedStatement preparedStatement;
		int total = 0;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			total = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}

	public static void main(String[] args) throws Exception {
		int total = getTotalMostWrongSingle();
		List<MostWrongChooseQuestion> mostWrongSingleQuestionList = new ArrayList<>();
		for (int i = 1; i <= total; i++) {
			mostWrongSingleQuestionList.add(getMostWrongSingleQuestionById(i));
		}
		List<SingleQuestion> singleQuestionList = new ArrayList<>();
		for (MostWrongChooseQuestion mostWrongChooseQuestion : mostWrongSingleQuestionList) {
			SingleQuestion singleQuestion = getSingleQuestionById(mostWrongChooseQuestion.getQuestionId());
			singleQuestionList.add(singleQuestion);
		}
		FileWriter fileWritterQuestion = new FileWriter("D:\\singleMostWrong_question.txt");
		FileWriter fileWritterAnswer = new FileWriter("D:\\singleMostWrong_answer.txt");
		int count = 1;
		for (int i = 0; i < mostWrongSingleQuestionList.size(); i++) {
			MostWrongChooseQuestion mostWrongChooseQuestion = mostWrongSingleQuestionList.get(i);
			fileWritterQuestion.write(mostWrongChooseQuestion.getId() + "、"
					+ String.format("%.2f%%", mostWrongChooseQuestion.getCorrectRate() * 100) + "  ");
			SingleQuestion singleQuestion = singleQuestionList.get(i);
			fileWritterQuestion.write(singleQuestion.getQuestion() + "\n");
			fileWritterQuestion.write("A、" + singleQuestion.getOptionA() + "  ");
			fileWritterQuestion.write("B、" + singleQuestion.getOptionB() + "\n");
			fileWritterQuestion.write("C、" + singleQuestion.getOptionC() + "  ");
			fileWritterQuestion.write("D、" + singleQuestion.getOptionD() + "\n\n");
			fileWritterAnswer.write(mostWrongChooseQuestion.getId() + "、" + singleQuestion.getAnswer() + "   ");
			if (count % 5 == 0) {
				fileWritterAnswer.write("\n");
			}
			count++;
		}
		fileWritterQuestion.close();
		fileWritterAnswer.close();
		System.out.println("done");
	}

}
