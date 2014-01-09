package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLtest {

	private static Statement stmt;
	private static ResultSet rs;
	
	public static void main(String[] args) {
		try {
		    System.out.println("Loading driver...");
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new RuntimeException("Cannot find the driver in the classpath!", e);
		}
	
		String url = "jdbc:mysql://sql.ewi.tudelft.nl/minorprojectSOT";
		String username = "admin";
		String password = "fietskopen";
		Connection connection = null;
		try {
		    System.out.println("Connecting database...");
		    connection = DriverManager.getConnection(url, username, password);
		    System.out.println("Database connected!");
		    stmt = connection.createStatement();
		    try{
		    	 stmt.executeUpdate("DROP TABLE myTable");
		    	 }catch(Exception e){
		    	 System.out.print(e);
		    	 System.out.println(
		    	 "No existing table to delete");
		    	 }
		    stmt.executeUpdate(
		    		 "CREATE TABLE myTable(test_id int," +
		    		 "test_val char(15) not null)");
		    stmt.executeUpdate(
		    		 "INSERT INTO myTable(test_id, " +
		    		 "test_val) VALUES(1,'One')");
		    stmt.executeUpdate(
		    		 "INSERT INTO myTable(test_id, " +
		    		 "test_val) VALUES(2,'Two')");
		    		 stmt.executeUpdate(
		    		 "INSERT INTO myTable(test_id, " +
		    		 "test_val) VALUES(3,'Three')");
		    		 stmt.executeUpdate(
		    		 "INSERT INTO myTable(test_id, " +
		    		 "test_val) VALUES(4,'Four')");
		    		 stmt.executeUpdate(
		    		 "INSERT INTO myTable(test_id, " +
		    		 "test_val) VALUES(5,'Five')");
		    		 stmt = connection.createStatement(
		    				 ResultSet.TYPE_SCROLL_INSENSITIVE,
		    				 ResultSet.CONCUR_READ_ONLY);
		    		 rs = stmt.executeQuery("SELECT * " +
		    				 "from myTable ORDER BY test_id");
		    		 
		    		 System.out.println("Display all results:");
		    		 while(rs.next()){
		    		 int theInt= rs.getInt("test_id");
		    		 String str = rs.getString("test_val");
		    		 System.out.println("\ttest_id= " + theInt
		    		 + "\tstr = " + str);
		    		 }
		} catch (SQLException e) {
		    throw new RuntimeException("Cannot connect the database!", e);
				
		} finally {
		    System.out.println("Closing the connection.");
		    if (connection != null) try { connection.close(); } catch (SQLException ignore) {}
		}
	}

}
