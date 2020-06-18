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
@WebServlet("/FavoriteAnimalsDAO")
public class FavoriteAnimalsDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public FavoriteAnimalsDAO() {
		
	}
	
	//connect to the database
    protected void connect_func() throws SQLException {
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
    protected void disconnect() throws SQLException {
        if (connect != null && !connect.isClosed()) {
        	connect.close();
        }
    }
    
    //add a new fav animal
    protected void insertFavAnimal(String username, int anId) throws SQLException {	
    	//connect to the database
    	connect_func();
     	
    	//set prepared statement contents for adding to database
    	//trait id is auto-incremented
		String sql = "insert into favoriteAnimals(user, favAnimalId) "
				+ "values (?, ?)";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		
		preparedStatement.setString(1, username);
		preparedStatement.setInt(2, anId);
			
		preparedStatement.executeUpdate();
        
     //   preparedStatement.close();
     //   connect.close();
        disconnect();    	
    }
    
    //remove a fav animal
    
    //return a list of all a user's fav animals
    protected List<Animal> listUsersFavAnimals(String username) throws SQLException {
    	//create array list to hold the users
        List<Animal> listFavAnimals = new ArrayList<Animal>();  
        
        AnimalDAO animalDAO = new AnimalDAO();
        
        //prepared sql query statement
        String sql = "SELECT * FROM favoriteAnimals WHERE user = ?"; 
        
       //connect to the database
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
        	int favAnId = resultSet.getInt("favAnimalId");
        	
        	Animal animal = animalDAO.getAnimalFromId(favAnId);
        	
        	listFavAnimals.add(animal);       	       	
        }    
        
        resultSet.close();     
        disconnect();     
        
        return listFavAnimals;
    }
    
    //remove a fav Animal
    protected void removeFavAnimal(String u, int f) throws SQLException {
        String sql = "DELETE FROM favoriteanimals WHERE user = ? AND favAnimalId = ?";        
        connect_func();
         
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, u);
        preparedStatement.setInt(2, f);
         
        preparedStatement.executeUpdate();
        preparedStatement.close();
//        disconnect();   
    }

}
