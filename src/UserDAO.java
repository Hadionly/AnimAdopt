import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.tomcat.jni.User;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Servlet implementation class Connect
@WebServlet("/UserDAO")
public class UserDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public String currentUser;
	
	public UserDAO() {
		
	}
	
	//connect to the database
    protected static void connect_func() throws SQLException {
        if (connect == null || connect.isClosed()) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            connect = (Connection) DriverManager
  			      .getConnection("jdbc:mysql://127.0.0.1:3306/adoptionsitedb?"
  			          + "user=john&password=pass1234");
            System.out.println(connect);
        }
    }
    
    //disconnect from the database
    protected static void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    //insert a user
    protected boolean insertUser(User user) throws SQLException {
    	//connect to the database
    	connect_func();
    
    	//set prepared statement contents for adding to database
    	//userId is done with AUTO_INCREMENT
		String sql = "insert into users(username, password, firstname, lastname, email, numReviews, numPostings) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user.username);
		preparedStatement.setString(2, user.password);
		preparedStatement.setString(3, user.firstname);
		preparedStatement.setString(4, user.lastname);
		preparedStatement.setString(5, user.email);
		preparedStatement.setInt(6, user.numReviews);
		preparedStatement.setInt(7, user.numPostings);
			
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        
        preparedStatement.close();
        connect.close();
        disconnect(); 

        return rowInserted;
    }  
    
    //Update a user
    protected void updateUser(User user) throws SQLException {
    	//connect to the database
    	connect_func();
    
    	//set prepared statement contents for adding to database
		String sql = "update users set username=?,password=?,firstname=?,lastname=?,email=?,numReviews=?,numpostings=? where userId=?";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user.username);
		preparedStatement.setString(2, user.password);
		preparedStatement.setString(3, user.firstname);
		preparedStatement.setString(4, user.lastname);
		preparedStatement.setString(5, user.email);
		preparedStatement.setInt(6, user.numReviews);
		preparedStatement.setInt(7, user.numPostings);
		preparedStatement.setInt(8, user.userId);
		
		preparedStatement.executeUpdate();	
        
        preparedStatement.close();
        connect.close();
        disconnect(); 

        
    } 
    
    //returns a list of every User in the USERS table
    protected List<User> listAllUsers() throws SQLException {
    	//create array list to hold the users
        List<User> listUsers = new ArrayList<User>();  
        
        //prepared sql query statement
        String sql = "SELECT * FROM users";      
        
        //connect to the database
        connect_func();
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
        	int id = resultSet.getInt("userId");
        	String username = resultSet.getString("username");
        	String password = resultSet.getString("password");
        	String firstname = resultSet.getString("firstname");
        	String lastname = resultSet.getString("lastname");
        	String email = resultSet.getString("email");
        	int numReviews = resultSet.getInt("numReviews");
        	int numPostings = resultSet.getInt("numPostings");
        	
        	User user = new User(id, username, password, firstname, lastname, email, numReviews, numPostings); 
        	
        	listUsers.add(user);
        }    
        
        resultSet.close();
        statement.close();        
        disconnect();     
        
        return listUsers;
    }
    
    //returns a user matching id from USERS table
    protected static User getUser(int id) throws SQLException {
    	User user = null;
        String sql = "SELECT * FROM users WHERE userId = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
        	String username = resultSet.getString("username");
        	String password = resultSet.getString("password");
        	String firstname = resultSet.getString("firstname");
        	String lastname = resultSet.getString("lastname");
        	String email = resultSet.getString("email");
        	int numReviews = resultSet.getInt("numReviews");
        	int numPostings = resultSet.getInt("numPostings");
        	
        	user = new User(id, username, password, firstname, lastname, email, numReviews, numPostings);         
        }
         
        resultSet.close();
      //  statement.close();
        disconnect();
         
        return user;
    }
    
    public static User getUserWithUsername(String Username) throws SQLException {
    	User user = null;
        String sql = "SELECT * FROM users WHERE username = ?";
         
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, Username);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
        	int id = resultSet.getInt("userId");
        	String password = resultSet.getString("password");
        	String firstname = resultSet.getString("firstname");
        	String lastname = resultSet.getString("lastname");
        	String email = resultSet.getString("email");
        	int numReviews = resultSet.getInt("numReviews");
        	int numPostings = resultSet.getInt("numPostings");
        	
        	user = new User(id, Username, password, firstname, lastname, email, numReviews, numPostings);         
        }
         
        resultSet.close();
       // statement.close();
        disconnect();
         
        return user;
    }
    
    //validate login
    public boolean validateLogin(String user, String pass) throws SQLException{
    	boolean success = false;
    	
    	String sql ="SELECT * FROM users WHERE username = ? AND password = ?";
    	
    	connect_func();
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.setString(1, user);
    	preparedStatement.setString(2, pass);
    	
    	resultSet = preparedStatement.executeQuery();
    	
    	if(resultSet.next()) {
    		currentUser = resultSet.getString("username"); //save the username of the current user
    		success = true;
    	}
    	
    	resultSet.close();
    	disconnect();
    	
    	return success;
    }
}