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
@WebServlet("/FavoriteBreedersDAO")
public class FavoriteBreedersDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public FavoriteBreedersDAO() {
		
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
    
    //add a new fav breeder
    protected static void insertFavBreeder(String username, String fav) throws SQLException {	
    	//connect to the database
    	connect_func();
     	
    	//set prepared statement contents for adding to database
    	//trait id is auto-incremented
		String sql = "insert into favoriteBreeders(user, favBreeder) "
				+ "values (?, ?)";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		
		preparedStatement.setString(1, username);
		preparedStatement.setString(2, fav);
			
		preparedStatement.executeUpdate();
        
      //  preparedStatement.close();
      //  connect.close();
        disconnect();    	
    }
    
    //remove a fav breeder
    protected static void removeFavBreeder(String u, String f) throws SQLException {
        String sql = "DELETE FROM favoriteBreeders WHERE user = ? AND favBreeder = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, u);
        preparedStatement.setString(2, f);
         
        preparedStatement.executeUpdate();
        preparedStatement.close();
//        disconnect();   
    }
    
    
    //return list of fav breeders for given user
    protected static List<FavoriteBreeders> listUsersFavBreeders(String username) throws SQLException {
    	//create array list to hold the users
        List<FavoriteBreeders> listFavBreeders = new ArrayList<FavoriteBreeders>();  
        
        //prepared sql query statement
        String sql = "SELECT * FROM favoriteBreeders WHERE user = ?"; 
        
       //connect to the database
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
        	String fav = resultSet.getString("favBreeder");

        	FavoriteBreeders favB = new FavoriteBreeders(username, fav);

        	listFavBreeders.add(favB);
        }    
        
        resultSet.close();     
        disconnect();     
        
        return listFavBreeders;
    }
    
}
