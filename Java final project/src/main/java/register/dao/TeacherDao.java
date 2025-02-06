package register.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

import register.model.Teacher;

public class TeacherDao {
	
	public final String CONNECTION_STR = "jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public int registerTeacher(Teacher teacher) throws ClassNotFoundException{
		
     String INSERT_TEACHER_SQL = "INSERT INTO Teacher(userName,password,address,phone)"+
     " VALUES (?,?,?,?);";
	
     

		int result = 0;
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		try {
		Connection connection = DriverManager.getConnection(CONNECTION_STR,"root","1234");
		
		if (connection != null) {
			
            System.out.println("Connected to the database library");
        }
		              
		PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TEACHER_SQL);
				
		preparedStatement.setInt(1,1);
		preparedStatement.setString(1,teacher.getUserName());
		preparedStatement.setString(2,teacher.getPassword());
		preparedStatement.setString(3,teacher.getAddress());
		preparedStatement.setString(4,teacher.getPhoneNum());
		
		System.out.println(preparedStatement);
		result = preparedStatement.executeUpdate();
				
		}
		catch(SQLException e) {
			
			e.printStackTrace();
			
		}
		return result;
				
	}
	 
	public List getListOfTeachers() throws ClassNotFoundException{
	List dataList = new ArrayList();
		
		String SQL = "select * from Teacher";
    	java.sql.Statement stmt;
							
		Class.forName("com.mysql.cj.jdbc.Driver");
			
			try {
			Connection connection = DriverManager.getConnection(CONNECTION_STR,"root","1234");
			
			if (connection != null) {
				
	            System.out.println("Connected to the database teacher");
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
		}
		 catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	public boolean deleteTeacher(int idUser) throws ClassNotFoundException {
		String SQL = "delete from Teacher where userName ="+ idUser;
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
}

