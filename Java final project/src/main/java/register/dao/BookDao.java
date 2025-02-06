package register.dao;

import java.net.http.HttpRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.cj.Session;
import com.mysql.cj.xdevapi.Statement;

import register.model.Book;
import register.model.Loan;
import register.model.Student;

public class BookDao {

	public final static String CONNECTION_STR = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	public int addBook(Book book) throws ClassNotFoundException {

		String INSERT_BOOK_SQL = "INSERT INTO Book(name,author,description,isLoan)" + " VALUES (?,?,?,?);";

		int result = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {

				System.out.println("Connected to the database library");
			}

			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_SQL);

			// preparedStatement.setInt(1,1);
			preparedStatement.setString(1, book.getName());
			preparedStatement.setString(2, book.getAuthor());
			preparedStatement.setString(3, book.getDescription());
			preparedStatement.setBoolean(4, book.isLoan());

			System.out.println(preparedStatement);
			result = preparedStatement.executeUpdate();
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return result;

	}

	public List getListOfBooks() throws ClassNotFoundException {
		List dataList = new ArrayList();

		String SQL = "select * from Book";
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
				int isLoan = Integer.parseInt(rs.getString("isLoan"));
				Boolean b = isLoan == 0 ? false : true;
				
				System.out.println(rs.getInt("id") + " - " + rs.getString("name") + " - " + rs.getString("author")
						+ " - " + rs.getString("description") + " - " + b);
				
				dataList.add(rs.getInt("id"));
				dataList.add(rs.getString("name"));
				dataList.add(rs.getString("author"));
				dataList.add(rs.getString("description"));
				dataList.add(rs.getInt("loanUserId"));
				dataList.add(b);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataList;
	}

	public boolean deleteBook(int idBook) throws ClassNotFoundException {

		String SQL = "delete from book where id =" + idBook;

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

	public static void takeBook(int bookId, int userId, String type) throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");
		LocalDate today = LocalDate.now();
		java.sql.Date date = java.sql.Date.valueOf(today);
		int addWeeks = 2;
		if(type != "student")
			addWeeks = 4;
		java.sql.Date returnDate = java.sql.Date.valueOf(today.plusWeeks(addWeeks));

		String SQL = "UPDATE Book SET loanUserId = ?, isLoan = true WHERE id = ?";
		String SQL3 = "INSERT INTO loans(loadDate, returnDate, userId, bookId, isReturned, extendPeriodTimes) VALUES (?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = connection.prepareStatement(SQL);
			stmt.setInt(1, userId);
			stmt.setInt(2, bookId);
			stmt.executeUpdate();
			stmt.close();

			PreparedStatement preparedStatement = connection.prepareStatement(SQL3);
			preparedStatement.setDate(1, date);
			preparedStatement.setDate(2, returnDate);
			preparedStatement.setInt(3, userId);
			preparedStatement.setInt(4, bookId);
			preparedStatement.setBoolean(5, false);
			preparedStatement.setInt(6, 0);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static List getMyBooks(int userId) throws ClassNotFoundException {
		List b = new ArrayList();

		String find = "select * from book where loanUserId=" + userId;

		java.sql.Statement stmt;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database books");
			}

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(find);
			while (rs.next()) {
				b.add(rs.getInt("id"));
				b.add(rs.getString("name"));
				b.add(rs.getString("aouther"));
				b.add(rs.getString("isBorrow"));

				System.out.println(b);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public static void returnBook(int idBook, int idUser) throws ClassNotFoundException, SQLException {
		Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");
		java.sql.Statement stmt = null;

		try {
			// יצירת חיבור לבסיס הנתונים
			connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			// ביצוע העדכון של הספר שהושאל
			String updateBookSQL1 = "UPDATE Book SET loanUserId = 0, isLoan = false WHERE id = " + idBook;

			String updateSafeModeSQL = "SET SQL_SAFE_UPDATES = 0;";
			String updateBookSQL2 = "UPDATE Loans SET isReturned = true WHERE userId = " + idUser + " and bookId="
					+ idBook;

			stmt = connection.createStatement();
			stmt.executeUpdate(updateBookSQL1);
			stmt.executeUpdate(updateSafeModeSQL);
			stmt.executeUpdate(updateBookSQL2);

			// הדפסת ה-SQL לבדיקת תקינות
			System.out.println("Update Book SQL: " + updateBookSQL1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// השחרור של משאבי המערכת
			if (stmt != null) {
				stmt.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static Book findBook(int bookId) throws ClassNotFoundException {
		String SQL = "select * from Book where id = " + bookId + "";

		Book b = new Book();

		java.sql.Statement stmt;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database books");
			}

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				b.setName(rs.getString("name"));
				b.setAuthor(rs.getString("author"));
				b.setLoan(rs.getBoolean("isLoan"));
				b.setDescription(rs.getString("description"));
				b.setLoanUserId(rs.getInt("loanUserId"));
				System.out.println(b);
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public List getAllUsersLoans() throws ClassNotFoundException {
		List historyList = new ArrayList();

		String find = "select * from loans where isReturned=false";

		java.sql.Statement stmt;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database books");
			}

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(find);
			while (rs.next()) {
				historyList.add(rs.getDate("loadDate"));
				historyList.add(rs.getDate("returnDate"));
				historyList.add(rs.getInt("userId"));
				historyList.add(rs.getInt("bookId"));
				historyList.add(rs.getBoolean("isReturned"));
				historyList.add(rs.getInt("extendPeriodTimes"));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return historyList;
	}

	public List getUserHistory(int userId) throws ClassNotFoundException {
		List b = new ArrayList();

		String find = "select * from loans where userId=" + userId;
//		find="SELECT bookId, MAX(loadDate) AS loadDate, MAX(returnDate) AS returnDate, MAX(userId) AS userId, MAX(isReturned) AS isReturned, MAX(extendPeriodTimes) AS extendPeriodTimes "
//				+ "FROM loans "
//				+ "WHERE userId ="+userId+ " GROUP BY bookId;";
		java.sql.Statement stmt;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database books");
			}

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(find);
			// loadDate,returnDate,userId,bookId,isReturned,extendPeriodTimes
			while (rs.next()) {
				b.add(rs.getDate("loadDate"));
				b.add(rs.getDate("returnDate"));
				b.add(rs.getInt("userId"));
				b.add(rs.getInt("bookId"));
				b.add(rs.getBoolean("isReturned"));
				b.add(rs.getInt("extendPeriodTimes"));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public List getAllLoans() throws ClassNotFoundException {
		String SQL = "select * from loans";

		List b = new ArrayList();

		java.sql.Statement stmt;
		Class.forName("com.mysql.cj.jdbc.Driver");
		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database books");
			}

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				b.add(rs.getInt("id"));
				b.add(rs.getString("name"));
				b.add(rs.getString("author"));
				b.add(rs.getString("loanUserId"));
			}
			rs.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public boolean extension(int bookId, int userId, String type) throws ClassNotFoundException {
		String SQL = "SELECT * FROM Loans WHERE userId=" + userId + " AND bookId=" + bookId;
		java.sql.Statement stmt;
		boolean success = false; // לצורך החזרת תוצאה

		try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR, "root", "1234");

			if (connection != null) {
				System.out.println("Connected to the database books");
			}

			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);

			if (rs.next()) { // העברת הקורסור לרשומה הראשונה בתוצאות
				int times = rs.getInt("extendPeriodTimes");
				int id = rs.getInt("id");
				if (times == 2) {
					return false;
				}

				times++;
				int plus = 7;
				if (!type.equalsIgnoreCase("student")) {
					plus *= 2;
				}

				java.sql.Date returnDate = rs.getDate("returnDate");

				LocalDate localReturnDate = returnDate.toLocalDate();
				java.sql.Date newReturnDate = java.sql.Date.valueOf(localReturnDate.plusDays(plus));

				// הוספת SQL_SAFE_UPDATES
				String updateSafeModeSQL = "SET SQL_SAFE_UPDATES = 0;";
				stmt.executeUpdate(updateSafeModeSQL);

				// עדכון הנתונים בטבלה
				String updateSQL = "UPDATE Loans SET extendPeriodTimes=?, returnDate =? WHERE id =? and isReturned=false";
				
			    PreparedStatement pstmt = connection.prepareStatement(updateSQL);

			    // הגדרת ערכים עבור השדות extendPeriodTimes ו-returnDate
			    pstmt.setInt(1, times); // הגדרת הערך החדש של extendPeriodTimes
			    pstmt.setDate(2, newReturnDate); // הגדרת הערך החדש של returnDate
			    System.out.println("id ========= " +id);
			    pstmt.setInt(3, id);

			    System.out.println(updateSQL);
			    // ביצוע העדכון
			    int rowsUpdated = pstmt.executeUpdate();
			    
			    pstmt.close();
			    connection.close();
			    
			    
//				stmt.executeUpdate(updateSQL);
//
//					PreparedStatement stmt1 = connection.prepareStatement(updateSQL);
//					stmt1.setInt(1, times);
//					stmt1.setDate(2, newReturnDate);
//					stmt1.setInt(3, rs.getInt("id"));
//					int rowsUpdated = stmt1.executeUpdate();
//					stmt1.close();
					
				
				if (rowsUpdated > 0) {
					success = true;
				}
			} else {
				System.out.println("No record found for the given bookId and userId.");
			}

			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return success;
	}

}
