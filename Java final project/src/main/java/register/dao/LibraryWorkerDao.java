package register.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import register.model.LibraryWorker;


public class LibraryWorkerDao {
	private UserDao userDao=new UserDao();	
	public final String CONNECTION_STR = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public int registerLibraryWorker(LibraryWorker libraryWorker) throws ClassNotFoundException{
		userDao.registerUser(libraryWorker);
		
     String INSERT_LIBRARY_WORKER_SQL = "INSERT INTO Library_worker(userId)"+" VALUES (?);";

		int result = 0;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
		Connection connection = DriverManager.getConnection(CONNECTION_STR,"root","1234");
		
		if (connection != null) {
			
            System.out.println("Connected to the database library");
        }
		              
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LIBRARY_WORKER_SQL);
				
		preparedStatement.setInt(1,1);
		preparedStatement.setString(1,libraryWorker.getUserName());
		preparedStatement.setString(2,libraryWorker.getPassword());
		preparedStatement.setString(3,libraryWorker.getAddress());
		preparedStatement.setString(4,libraryWorker.getPhoneNum());
		
		System.out.println(preparedStatement);
		result = preparedStatement.executeUpdate();
				
		}
		catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		return result;
				
	}
	 
}

