package source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class getorders extends Abstract{
	
	
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/miniproject";
	 private static final String DB_USER = "root";
	 private static final String DB_PASSWORD = "Krishnaa@05102003";
	
	@Override
	  public void getOrders() {
	    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	 	        String deleteSql = "Select * from orders";
	 	        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
	 	       ResultSet rs = pstmt.executeQuery();
	 	      System.out.println("Welcome,! "+" Your orders:");
	 	     System.out.println("-------------------------------------------------");
	 	       while(rs.next()) {
	 	    	   System.out.println(rs.getString(2)+" - Quantity: "+rs.getInt(3)+" - Price: $" +rs.getInt(4));
	 	       }
	 	      System.out.println("-------------------------------------------------");
	 	    } catch (SQLException e) {
	 	        e.printStackTrace();
	 	    }
	    }
	@Override
	  public void getOrders(String customer_name) {
	    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	 	        String deleteSql = "Select * from orders";
	 	        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
	 	       ResultSet rs = pstmt.executeQuery();
	 	      System.out.println("Welcome,! "+" Your orders:");
	 	     System.out.println("-------------------------------------------------");
	 	       while(rs.next()) {
	 	    	   System.out.println(rs.getString(2)+" - Quantity: "+rs.getInt(3)+" - Price: $" +rs.getInt(4));
	 	       }
	 	      System.out.println("-------------------------------------------------");
	 	    } catch (SQLException e) {
	 	        e.printStackTrace();
	 	    }
	    }
}