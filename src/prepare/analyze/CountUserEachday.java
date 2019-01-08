package prepare.analyze;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

/**
 * 统计每天用户量
 * 
 * @author Administrator
 *
 */
public class CountUserEachday {
	private static Connection connection = null;
	static {
		if (connection == null) {
			try {
				Class.forName(AnalyzeUtil.DRIVER);
				connection = (Connection) DriverManager.getConnection(AnalyzeUtil.URL, AnalyzeUtil.USERNAME,
						AnalyzeUtil.PASSWORD);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String getBrowserIdById(int id) {
		String sql = "select browserId from requestLog where id=?";
		String browserId = "";
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			browserId = resultSet.getString("browserId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return browserId;
	}

	public static int getMaxRequestId() {
		String sql = "select max(id) from requestLog";
		int id = 0;
		try {
			PreparedStatement preparedStatement;
			preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			id = resultSet.getInt("max(id)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static void main(String[] args) {
		long startMillis = System.currentTimeMillis();
		int start = 2279;
		int end = getMaxRequestId();
		int total = end - start;
		Set<String> browserSet = new HashSet<>();
		for (int i = start; i < end; i++) {
			String browserId = getBrowserIdById(i);
			System.out.print("size:" + browserSet.size());
			System.out.println(" (" + (i - start) * 100 / total + "%) id=" + i + ": " + browserId);
			if (browserId != null) {
				browserSet.add(browserId);
			}
		}
		System.out.println("result----------from " + start + " to " + end + " ----------");
		System.out.println(browserSet);
		System.out.println("totalSize:" + browserSet.size());
		System.out.println("Running coast: " + (System.currentTimeMillis() - startMillis) / 1000 + " s");
	}
}
