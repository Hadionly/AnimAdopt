import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
@WebServlet("/ReviewDAO")
public class ReviewDAO  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private static UserDAO userDAO = new UserDAO();		//to access methods for users table

	public ReviewDAO() {
		
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
    
    //insert new review
    protected static void insertNewReview(Review review) throws SQLException {
    	//connect to the database
    	connect_func();
    	
    	//get user and increment the reviews number
    	User user = UserDAO.getUser(review.userId);
    	int reviewNo = user.getNumReviews();
    	reviewNo = reviewNo + 1;
    	//update user
    	user.setNumReviews(reviewNo);
    	userDAO.updateUser(user);
    	//set prepared statement contents for adding to database
    	//reviewId is done with AUTO_INCREMENT
		String sql = "insert into reviews(animalId, userId, postingUser, contents, category) "
				+ "values (?, ?, ?, ?, ?)";
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, review.animalId);
		preparedStatement.setInt(2, review.userId);
		preparedStatement.setString(3, review.postingUser);
		preparedStatement.setString(4, review.contents);
		preparedStatement.setString(5, review.category);	
		
        preparedStatement.executeUpdate();
        
        preparedStatement.close();
        connect.close();
        disconnect(); 
        
    }
    
    //check if user already reviewed this animal
    protected boolean checkReview(String user, int animalId) throws SQLException{
    	//connect to the database
    	connect_func();
    	
    	
    	String sql ="SELECT * FROM reviews where postingUser=? and animalId=?";
    	preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user);
		preparedStatement.setInt(2, animalId);
		
		ResultSet rs = preparedStatement.executeQuery();
		
		boolean AlreadyReviewed;
		if(rs.next()) {
			AlreadyReviewed = true;
		} else {
			AlreadyReviewed = false;
		}
    	
		return AlreadyReviewed;
    }
    
    //returns list of reviews for a particular animal
    protected static List<Review> listAnimalsReviews(int animalId) throws SQLException {
    	
        //prepared sql query statement
        String sql = "SELECT * FROM reviews WHERE animalId = ?"; 
        
        //connect to the database
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, animalId); 
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        //create array list to hold the users
        List<Review> listReviews = new ArrayList<Review>();
        while (resultSet.next()) {
        	int reviewId = resultSet.getInt("reviewId");
        	int userId = resultSet.getInt("userId");
        	String postingUser = resultSet.getString("postingUser");
        	String contents = resultSet.getString("contents");
        	String category = resultSet.getString("category");

        	Review review = new Review(reviewId, animalId, userId, postingUser, contents, category);

        	listReviews.add(review);
        }    
        
        resultSet.close();
      //  statement.close();        
        disconnect();     
      
        return listReviews;
    }
    
  //list the number of adopted animals per users
    protected int numberOfUserReviews(String postingUser) throws SQLException {
    	
    	//prepared sql query statement
        String sql = "SELECT `postingUser`, COUNT(*) AS `count` FROM reviews WHERE `postingUser`='"+ postingUser +"'";
        
        //connect to the database
        connect_func();
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        
        int num = resultSet.getInt("count");
        
        return num;
    }   
}