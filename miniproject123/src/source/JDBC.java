package source;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JDBC  {
    // Remaining code from the main class as before
	 private static final String DB_URL = "jdbc:mysql://localhost:3306/miniproject";
	    private static final String DB_USER = "root";
	    private static final String DB_PASSWORD = "Krishnaa@05102003";
	    static String displayname;
	    static String username;

	    public JDBC() {
	        // Constructor
	    }

	    private void insertOrderIntoDatabase(List<CartItem> cartItems, double totalPrice) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	      
	            String insertItemSql = "INSERT INTO orders (product_name, quantity, price) VALUES (?, ?, ?)";
	            PreparedStatement itemPstmt = conn.prepareStatement(insertItemSql);
	            for (CartItem cartItem : cartItems) {
	               
	                itemPstmt.setString(1, cartItem.getProductName());
	                itemPstmt.setInt(2, cartItem.getQuantity());
	                itemPstmt.setDouble(3, cartItem.getPrice());
	                itemPstmt.executeUpdate();
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public List<CartItem> GetCartFromDatabase() {
	        List<CartItem> cartItems = new ArrayList<>();

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            String selectSql = "SELECT * FROM cart_items";
	            PreparedStatement pstmt = conn.prepareStatement(selectSql);
	     
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                String productName = rs.getString("product_name");
	                double price = rs.getDouble("price");
	                int quantity = rs.getInt("quantity");

	                CartItem cartItem = new CartItem(productName, quantity, price);
	                cartItems.add(cartItem);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return cartItems;
	    }
	 
	    public void order() {
	        List<CartItem> cartItems = GetCartFromDatabase(); // Assuming this method retrieves cart items for the given customer
	        Scanner scanner = new Scanner(System.in);

	        System.out.println("Welcome,! Your order:");

	        System.out.println("-------------------------------------------------");
	        System.out.println("Cart Items:");
	        for (CartItem cartItem : cartItems) {
	            System.out.println(cartItem.getProductName() + " - Quantity: " + cartItem.getQuantity() + " - Price: $" + cartItem.getPrice());
	        }
	        System.out.println("-------------------------------------------------");

	        // Calculate total price
	        double totalPrice = 0;
	        for (CartItem cartItem : cartItems) {
	            totalPrice += cartItem.getPrice() * cartItem.getQuantity();
	        }

	        System.out.println("Total Price: $" + totalPrice);

	        System.out.print("Confirm your order (yes/no): ");
	        String confirmation = scanner.nextLine();

	        if (confirmation.equalsIgnoreCase("yes")) {
	            // Insert the cart items into the database as an order
	            insertOrderIntoDatabase(cartItems, totalPrice);

	            System.out.println("Order placed successfully. Thank you for shopping with us!");
	            deletecartitems();
	        } else {
	            System.out.println("Order not placed. Your cart items have not been processed.");
	        }

	        
	    }
	    public void updateproducts() {
	    Scanner scanner=new Scanner(System.in);
    	JDBC shoppingSystem = new JDBC();
    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
 	        String updatevalue = "Update products \n set product_price=?,product_availbility=? \n where product_name=?";
 	        PreparedStatement pstmt = conn.prepareStatement(updatevalue);
 	       System.out.println("");
 	        System.out.print("Enter product name: ");
 	       String product_name = scanner.nextLine();
 	        pstmt.setString(3, product_name);
 	        System.out.print("Enter product price: ");
 	        double productPrice = scanner.nextDouble();
 	         pstmt.setDouble(1, productPrice);
 	        System.out.print("Enter product availability: ");
 	        int productAvailability = scanner.nextInt();
 	       pstmt.setDouble(2, productAvailability);
 	        
 	        pstmt.executeUpdate(); 
 	        System.out.println("product update sucessfully");
 	       System.out.println("");
 	        System.out.println("What you like to do?");
 	        System.out.println("1. Add Product");
 	        System.out.println("2. Update Product");
 	        System.out.println("3. Exit....");
 	        System.out.print("Enter your choice: ");
 	        int choice = scanner.nextInt();
 	        System.out.println("-------------------------------------------------");
 	        scanner.nextLine();

 	         switch (choice) {
 	             case 1:
 	                 shoppingSystem.products();
 	                 break;
 	             case 2:

 	                 shoppingSystem.updateproducts();
 	                 break;
 	             case 3:
 	            	 System.exit(0);
 	            	 scanner.close();
 	            	 break;
 	             default:
 	                 System.out.println("Invalid choice. Exiting...");
 	         }
 	        
 	    } catch (SQLException e) {
 	        e.printStackTrace();
 	    }
	    }
	    
	    private void products() {
	    	JDBC shoppingSystem=new JDBC();
	        Scanner scanner = new Scanner(System.in);
	        System.out.println("");
	        System.out.println("Adding products as a seller: " + displayname);
	        System.out.println("");
	        System.out.print("Enter product name: ");
	        String productName = scanner.nextLine();
	        System.out.print("Enter product price: ");
	        double productPrice = scanner.nextDouble();
	        scanner.nextLine(); 
	        System.out.print("Enter product availability: ");
	        int productAvailability = scanner.nextInt();
	        scanner.nextLine(); 

	        Product product = new Product(productName, productPrice, productAvailability);
	        insertProductIntoDatabase(product);
	        System.out.println("");
	        System.out.println("1. Add Product");
	        System.out.println("2. Update Product");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	         switch (choice) {
	             case 1:
	                 shoppingSystem.products();
	                 break;
	             case 2:
	            	 shoppingSystem.updateproducts();

	                 
	                 break;
	             default:
	                 System.out.println("Invalid choice. Exiting...");
	         }
	        scanner.close();
	    }
	    
	    
	  

	    public void insertProductIntoDatabase(Product product) {

	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            String insertSql = "INSERT INTO products (product_name, product_price, product_availbility) VALUES (?, ?, ?)";
	            PreparedStatement pstmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
	            pstmt.setString(1, product.getProductName());
	            pstmt.setDouble(2, product.getProductPrice());
	            pstmt.setInt(3, product.getProductAvailability());
	            pstmt.executeUpdate();
	            System.out.println("product added successfully");

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        }
	   
	    public List<Product> GetProductFromDatabase() {
	        List<Product> productList = new ArrayList<>();
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            String selectSql = "SELECT * FROM products";
	            PreparedStatement pstmt = conn.prepareStatement(selectSql);
	            ResultSet rs = pstmt.executeQuery();

	            while (rs.next()) {
	                String productName = rs.getString("product_name");
	                double productPrice = rs.getDouble("product_price");
	                int productAvailability = rs.getInt("product_availbility");

	                Product product = new Product(productName, productPrice, productAvailability);
	                productList.add(product);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return productList;
	    }
	    
	    public void deletecartitems() {
	    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	    	        String deleteSql = "truncate table cart_items";
	    	        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
	    	        pstmt.executeUpdate(); 
	    	    } catch (SQLException e) {
	    	        e.printStackTrace();
	    	    }
	}


	    public void insertCartItemIntoDatabase( CartItem cartItem) {
	        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	            String insertSql = "INSERT INTO cart_items (product_name, quantity, price) VALUES ( ?, ?, ?)";
	            PreparedStatement pstmt = conn.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS);
	     
	            pstmt.setString(1, cartItem.getProductName());
	            pstmt.setInt(2, cartItem.getQuantity());
	            pstmt.setDouble(3, cartItem.getPrice());
	            pstmt.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void cart() {
	    	  JDBC shoppingSystem = new JDBC();
	        List<Product> productList = GetProductFromDatabase();
	        List<CartItem> cartItems = new ArrayList<>();
	        
	        Scanner scanner = new Scanner(System.in);
	        boolean yogi=true;
	        while (yogi) {
	        	System.out.println("");
	            System.out.println("-------------------------------------------------");
	            System.out.println("Available Products:");
	            for (int i = 0; i < productList.size(); i++) {
	                Product product = productList.get(i);
	                System.out.println((i + 1) + ". " + product.getProductName() + " - Price: $" + product.getProductPrice());
	            }
	            
	            System.out.println("-------------------------------------------------");
	            System.out.println("");
	             System.out.print("Enter the product number to add to the cart: ");
	             int productNumber = scanner.nextInt();
	            scanner.nextLine();
	            if (productNumber >= 1 && productNumber <= productList.size()) {
	             Product selectedProduct = productList.get(productNumber - 1);
	             
	             
	             System.out.print("Enter the count: ");
	             int productcount = scanner.nextInt();
	             double total=selectedProduct.getProductPrice()*productcount;
	             CartItem cartItem = new CartItem(selectedProduct.getProductName(),productcount,total);
	             cartItems.add(cartItem);
	             System.out.println("Product added to cart Sucessfully");
	             System.out.println("-------------------------------------------------");
	             insertCartItemIntoDatabase(cartItem);
	            yogi=false;
	               }
	           }
	 
	        System.out.println("What you like to do?");
	        System.out.println("1. Add Another Product");
	        System.out.println("2. View  Cart");
	        System.out.println("3. View  Orders");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	         switch (choice) {
	             case 1:
	                 shoppingSystem.cart();
	                 break;
	             case 2:
	                  
	                 shoppingSystem.order();
	                 break;
	             case 3:
getorders order=new getorders();
	                 order.getOrders();
	                 break;
	             default:
	                 System.out.println("Invalid choice. Exiting...");
	         }
	   
	        
	        
	        }
	    
	    private void sellerchoice() {
	    	Scanner scanner = new Scanner(System.in);
	    	JDBC shoppingSystem = new JDBC();
	    	
	    	System.out.println("");
	    	System.out.println("hello seller "+username);
	    	System.out.println("");
	    	
	        System.out.println("What you like to do?");
	        System.out.println("1. Add Product");
	        System.out.println("2. Update Product");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	         switch (choice) {
	             case 1:
	                 shoppingSystem.products();
	                 break;
	             case 2:

	                 System.out.println("come after");
	                 break;
	             default:
	                 System.out.println("Invalid choice. Exiting...");
	         }
	    }
//	    public void getOrders() {
//	    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
//	 	        String deleteSql = "Select * from orders";
//	 	        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
//	 	       ResultSet rs = pstmt.executeQuery();
//	 	      System.out.println("Welcome,! "+displayname+" Your orders:");
//	 	     System.out.println("-------------------------------------------------");
//	 	       while(rs.next()) {
//	 	    	   System.out.println(rs.getString(2)+" - Quantity: "+rs.getInt(3)+" - Price: $" +rs.getInt(4));
//	 	       }
//	 	      System.out.println("-------------------------------------------------");
//	 	    } catch (SQLException e) {
//	 	        e.printStackTrace();
//	 	    }
//	    }
	    
	    private void customerchoice() {
	    	Scanner scanner = new Scanner(System.in);
	    	JDBC shoppingSystem = new JDBC();
	    	
	    	System.out.println("");
	    	System.out.println("hello customer "+username);
	    	System.out.println("");
	        System.out.println("What you like to do?");
	        System.out.println("1. View Products");
	        System.out.println("2. View  Cart");
	        System.out.println("3. View  Orders");
	        System.out.print("Enter your choice: ");
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	         switch (choice) {
	             case 1:
	                 shoppingSystem.cart();
	                 break;
	             case 2:
	                  
	                 shoppingSystem.order();
	                 break;
	             case 3:
getorders order=new getorders();
	                 order.getOrders();
	                 break;
	             default:
	                 System.out.println("Invalid choice. Exiting...");
	         }
	    }

	    
	    private void connectToDatabase() {

	    	  try {
	              Class.forName("com.mysql.cj.jdbc.Driver");
	              Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	              System.out.println("Connected to the database successfully.");
	          } catch (ClassNotFoundException | SQLException e) {
	              e.printStackTrace();
	          }
	    }
	    private void insertCustomerIntoDatabase(Customer customer) {
	    	 try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	                 PreparedStatement pstmt = conn.prepareStatement(
	                         "INSERT INTO customers (username, password, name, email, mobile) VALUES (?, ?, ?, ?, ?)")) {
	                pstmt.setString(1, customer.getUsername());
	                pstmt.setString(2, customer.getPassword());
	                pstmt.setString(3, customer.getName());
	                
	                displayname=customer.getName();
	                pstmt.setString(4, customer.getEmail());
	                pstmt.setString(5, customer.getMobile());
	                pstmt.executeUpdate();
	                System.out.println("Customer Registered successfully.");
	                
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	    	 
	    }

	    public void insertSellerIntoDatabase(Seller seller) {
	    	try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
	                PreparedStatement pstmt = conn.prepareStatement(
	                        "INSERT INTO sellers (username, password, name, email, mobile) VALUES (?, ?, ?, ?, ?)")) {
	               pstmt.setString(1, seller.getUsername());
	               pstmt.setString(2, seller.getPassword());
	               pstmt.setString(3, seller.getName());
	               
	               displayname=seller.getName();
	               
	               
	               pstmt.setString(4, seller.getEmail());
	               pstmt.setString(5, seller.getMobile());
	               pstmt.executeUpdate();
	               System.out.println("");
	               System.out.println(" Seller Registered successfully.");
	               System.out.println("");
	           } catch (SQLException e) {
	               e.printStackTrace();
	           }
	    }

	    public static void main(String[] args) {
	        JDBC shoppingSystem = new JDBC();
	        shoppingSystem.connectToDatabase();

	        Scanner scanner = new Scanner(System.in);
	        System.out.println("");
	        System.out.println("Are you a Seller or Customer?");
	        System.out.println("1. Seller");
	        System.out.println("2. Customer");
	        System.out.print("Enter your choice: ");
	      
	        int choice = scanner.nextInt();
	        scanner.nextLine();

	        switch (choice) {
	            case 1:

	            	
	            	
	            	System.out.println("");
	            	System.out.println("Already a seller pls login or register");
	            	System.out.println("1. Login");
	            	System.out.println("2. Register");
	            	System.out.print("Enter your choice: ");
	            	 int choice1 = scanner.nextInt();
	                 scanner.nextLine();
	                 
	                 switch(choice1) {
	                 case 1:
	                	 System.out.println("");
	                	 
	                	 System.out.print("Enter your username: ");
	                     String name = scanner.nextLine();
	                     username=name;
	                     System.out.print("Enter your password: ");
	                     String Password = scanner.nextLine();
	                     
	                     LoginValidation login=new LoginValidation();
	                     if(login.validateSellerLogin(name,Password)) {
	                    	 System.out.println("Login Successfull..");
	                    	 shoppingSystem.sellerchoice();
	                     }else {
	                    	 System.out.println("Invalid username or password..");
	                     }
	                    
	                     
	                 case 2:
		            	 System.out.println("");
			            	System.out.print("Enter seller username: ");
			                String sellerUsername = scanner.nextLine();
			                System.out.print("Enter seller password: ");
			                String sellerPassword = scanner.nextLine();
			                System.out.print("Enter seller name: ");
			                String sellerName = scanner.nextLine();
			                System.out.print("Enter seller email: ");
			                String sellerEmail = scanner.nextLine();
			                System.out.print("Enter seller mobile: ");
			                String sellerMobile = scanner.nextLine();

			                Seller seller = new Seller(sellerUsername, sellerPassword, sellerName, sellerEmail, sellerMobile);
			                shoppingSystem.insertSellerIntoDatabase(seller);
			                shoppingSystem.sellerchoice();
			                break;
	                 }
	            case 2:
	            
	            	
	            	
	            	System.out.println("");
	            	System.out.println("Already a customer pls login or register");
	            	System.out.println("1. Login");
	            	System.out.println("2. Register");
	            	System.out.print("Enter your choice: ");
	            	 int choice2 = scanner.nextInt();
	                 scanner.nextLine();
	                 
	                 
	                 switch(choice2) {
	                 case 1:
	                	 System.out.println("");
	                	 
	                	 System.out.print("Enter your username: ");
	                     String name = scanner.nextLine();
	                     username=name;
	                     System.out.print("Enter your password: ");
	                     String Password = scanner.nextLine();
	                     
	                     LoginValidation login=new LoginValidation();
	                     if(login.validateCustomerLogin(name,Password)) {
	                    	 System.out.println("Login Successfull..");
	                    	 shoppingSystem.customerchoice();
	                     }else {
	                    	 System.out.println("Invalid username or password..");
	                     }
	                     
	                     
	                 case 2:
	                 	System.out.println("");
		            	System.out.println("--------Register to continue---------");
		        
		            	System.out.print("Enter your username: ");
		                String CustomerUsername = scanner.nextLine();
		                System.out.print("Enter your password: ");
		                String CustomerPassword = scanner.nextLine();
		                System.out.print("Enter customer name: ");
		                String CustomerName = scanner.nextLine();
		                System.out.print("Enter customer email: ");
		                String CustomerEmail = scanner.nextLine();
		                System.out.print("Enter customer mobile: ");
		                String CustomerMobile = scanner.nextLine();
		                Customer customer = new Customer(CustomerUsername, CustomerPassword,CustomerName,CustomerEmail,CustomerMobile);
		                shoppingSystem.insertCustomerIntoDatabase(customer);
		                System.out.println("----------------------------------------");
		                shoppingSystem.customerchoice();
		                break;
	                 }
	            	
	            default:
	                System.out.println("Invalid choice. Exiting...");
	        }

	        shoppingSystem.closeConnection();
	        scanner.close();
	    }

	    private void closeConnection() {
	    }

}
