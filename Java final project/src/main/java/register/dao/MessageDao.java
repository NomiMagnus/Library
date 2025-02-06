package register.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import register.model.Message;

public class MessageDao {
	private final String CONNECTION_STR = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public int createNewMessage(Message message) throws ClassNotFoundException {
//		String INSERT_USER_SQL = "INSERT INTO Message(userId,title,body,messageDate)" + " VALUES (?,?,?,?);";
		String INSERT_USER_SQL = "INSERT INTO Message(userId,title,body)" + " VALUES (?,?,?);";
		int result = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);

			preparedStatement.setInt(1, message.getUserId());
			preparedStatement.setString(2, message.getTitle());
			preparedStatement.setString(3, message.getBody());
//			preparedStatement.setDate(4, message.getMessageDate());

			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return result;
	}

	public boolean deleteMessage(int messageId) throws ClassNotFoundException {
		String SQL = "delete from message where id =" + messageId;
		java.sql.Statement stmt;
		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}
			stmt = connection.createStatement();
			stmt.execute(SQL);
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public List getAllMessages() throws ClassNotFoundException {
		List dataList = new ArrayList();

		String SQL = "select * from Message";
		java.sql.Statement stmt;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database library");
			}
			stmt = connection.createStatement();

			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(SQL);

			// Iterate through the data in the result set and display it.
			while (rs.next()) {
				dataList.add(rs.getInt("id"));
				dataList.add(rs.getString("title"));
				dataList.add(rs.getString("body"));
				dataList.add(rs.getInt("userId"));
//				dataList.add(rs.getString("messageDate"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;
	}

}
