package register.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import register.model.User;

public class UserDao {

	private final String CONNECTION_STR = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public int registerUser(User user) throws ClassNotFoundException {

		String INSERT_USER_SQL = "INSERT INTO User(userName,password,address,phone,type)" + " VALUES (?,?,?,?,?);";

		int result = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);

			//preparedStatement.setInt(1, 1);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getAddress());
			preparedStatement.setString(4,user.getPhoneNum());
			preparedStatement.setString(5,user.getType());

			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return result;

	}

	public List getListOfUsers() throws ClassNotFoundException {
		List dataList = new ArrayList();

		String SQL = "select * from User";
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
				System.out.println(rs.getString("userName") + " - " + rs.getString("password"));
				dataList.add(rs.getString("id"));
				dataList.add(rs.getString("userName"));
				dataList.add(rs.getString("password"));
				dataList.add(rs.getString("type"));
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;
	}
	
	public boolean deleteUser(int idUser) throws ClassNotFoundException {
		
		String SQL = "delete from user where id ="+idUser;
					 
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
	
	//not good...
	public List getListOfLoanedBooks() throws ClassNotFoundException {
		List dataList = new ArrayList();

		String SQL = "select * from Loans";
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

				System.out.println(rs.getString("userName") + " - " + rs.getString("book_name") + " - " + rs.getString("returnDate"));
				dataList.add(rs.getString("userName"));
				dataList.add(rs.getString("book_name"));
				dataList.add(rs.getString("returnDate"));

			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;
	}
	
	public Object[] login(String userName, String password) throws ClassNotFoundException {
		User user=new User();
		Object[] myUser = new Object[2];

		String SQL = "select * from user where userName = '"+userName+"' and password = '"+password+"';";
		
		java.sql.Statement stmt;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}
			
			stmt = connection.createStatement();

			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(SQL);
			if (rs.next()) {
			    if (rs.getString(1) == null && rs.getString(1).isEmpty()) {
			        return null;
			    }
			} else {
			    System.out.println("ResultSet is empty");
			    return null;
			}

			user.setAddress(rs.getString("address"));
			user.setPassword(rs.getString("password"));
			user.setPhoneNum(rs.getString("phone"));
			user.setType(rs.getString("type"));
			user.setUserName(rs.getString("userName"));
			
			int userId = rs.getInt("id");
			myUser[0] = user;
			myUser[1]=userId;
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myUser;
	}
}
