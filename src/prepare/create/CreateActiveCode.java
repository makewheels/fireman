package prepare.create;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 创建新的激活码并保存在数据库里
 * 
 * @author Administrator
 *
 */
public class CreateActiveCode {
	private static Connection connection = null;
	static {
		if (connection == null) {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://qbserver.cn:3306/fireman";
			// String url = "jdbc:mysql://localhost:3306/fireman";
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

	/**
	 * 保存激活码到数据库
	 * 
	 * @param code
	 */
	public static void saveActiveCode(String code, int days, String note) {
		String sql = "insert into activeCode (code,days,note) values(?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setString(1, code);
			preparedStatement.setInt(2, days);
			preparedStatement.setString(3, note);
			preparedStatement.execute();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		// 配置开始-----------------
		// 要几个激活码
		int amount = 1;
		// 会员时长
		int days = 20;
		// 备注
		String note = "我自己啊";
		// 配置结束-----------------

		for (int i = 0; i < amount; i++) {
			String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
			String number = "0123456789";
			int numberGroup = new Random().nextInt(3);
			for (int j = 0; j < numberGroup; j++) {
				letter += number;
			}
			String code = "";
			// 长度是32-64随机
			int codeLength = new Random().nextInt(33) + 32;
			int letterLength = letter.length();
			for (int j = 0; j < codeLength; j++) {
				int randomIndex = new Random().nextInt(letterLength);
				code += letter.charAt(randomIndex);
			}
			saveActiveCode(code, days, note);
			System.out.println(code);
		}
	}
}
