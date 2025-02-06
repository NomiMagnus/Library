package register.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import register.model.Student;

public class StudentDao {

	public final String CONNECTION_STR = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public int registerStudent(Student student) throws ClassNotFoundException {

		String INSERT_STUDENT_SQL = "INSERT INTO Student(userName,password,address,phone)" + " VALUES (?,?,?,?);";

		int result = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_STUDENT_SQL);

			preparedStatement.setInt(1, 1);
			preparedStatement.setString(1, student.getUserName());
			preparedStatement.setString(2, student.getPassword());
			preparedStatement.setString(3, student.getAddress());
			preparedStatement.setString(4, student.getPhoneNum());

			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return result;

	}

	public List getListOfStudents() throws ClassNotFoundException {
		List dataList = new ArrayList();

		String SQL = "select * from Student";
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
				dataList.add(rs.getString("userName"));
				dataList.add(rs.getString("password"));

			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;
	}
	
	public boolean deleteStudent(int idUser) throws ClassNotFoundException {
		String SQL = "delete from Student where id ="+idUser;
		java.sql.Statement stmt;

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}
//			stmt = connection.createStatement();
//			stmt.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

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
}
