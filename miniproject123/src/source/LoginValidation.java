package source;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LoginValidation extends JDBC {
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/miniproject";
	 private static final String DB_USER = "root";
	 private static final String DB_PASSWORD = "Krishnaa@05102003";
	 
	 static String username;

    public LoginValidation() {
        super(); // Call the constructor of the parent class (JDBC)
    }

    // Method to validate the login credentials for customers
    public boolean validateCustomerLogin(String username, String password) {
      
    	boolean isValidUser = false;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectSql = "SELECT password FROM customers WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(selectSql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // The username is found in the database
                String storedPassword = rs.getString("password");
                isValidUser = storedPassword.equals(password);
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isValidUser;
    }

    // Method to validate the login credentials for sellers
    public boolean validateSellerLogin(String username, String password) {
    	
    	 boolean isValidUser = false;

         try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
             String selectSql = "SELECT password FROM sellers WHERE username = ?";
             PreparedStatement pstmt = conn.prepareStatement(selectSql);
             pstmt.setString(1, username);
             ResultSet rs = pstmt.executeQuery();

             if (rs.next()) {
                 // The username is found in the database
                 String storedPassword = rs.getString("password");
                 isValidUser = storedPassword.equals(password);
             }
             
             
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return isValidUser;
     }
 


}