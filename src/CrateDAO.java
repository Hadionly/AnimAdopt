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
@WebServlet("/CrateDAO")
public class CrateDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public CrateDAO() {
		
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
    protected void insertToCrate(String username, int anId) throws SQLException {	
    	//connect to the database
    	connect_func();
     	
    	//set prepared statement contents for adding to database
    	//trait id is auto-incremented
		String sql = "insert into crate(username, animalId) "
				+ "values (?, ?)";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		
		preparedStatement.setString(1, username);
		preparedStatement.setInt(2, anId);
			
		preparedStatement.executeUpdate();
        
        preparedStatement.close();
        connect.close();
        disconnect();    	
    }
    
    //remove an entry from crate
    protected void removeFromCrate(String user, int anId) throws SQLException {        
        //prepared sql query statement
        String sql = "DELETE FROM crate WHERE username = ? AND animalId = ?"; 
        //connect to the database
        connect_func();
        
        //specify that animalId should be unique
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user);
		preparedStatement.setInt(2, anId);
        
        statement = (Statement) connect.createStatement();
        preparedStatement.executeUpdate();
             
        disconnect();     
    }
    
    //return a list of all animals in user's crate
    protected List<Animal> listCrateAnimals(String username) throws SQLException {
    	//create array list to hold the users
        List<Animal> listCrateAnimals = new ArrayList<Animal>();  
        
        AnimalDAO animalDAO = new AnimalDAO();
        
        //prepared sql query statement
        String sql = "SELECT * FROM crate WHERE username = ?"; 
        
       //connect to the database
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
        	int anId = resultSet.getInt("animalId");
        	
        	Animal animal = animalDAO.getAnimalFromId(anId);
        	
        	listCrateAnimals.add(animal);       	       	
        }    
        
        resultSet.close();     
        disconnect();     
        
        return listCrateAnimals;
    }
    
    //return a list of all animals in not in user's crate
    protected List<Animal> listAnimalsNotInCrate(String username) throws SQLException {
    	//create array list to hold the users
        List<Animal> listCrateAnimals = new ArrayList<Animal>();  
        
        AnimalDAO animalDAO = new AnimalDAO();
        
        //prepared sql query statement
        /* String sql = "SELECT a.animalId "
        		+ "FROM animals a"
        		+ "LEFT JOIN crate c ON c.username = animals.owner "
        		+ "c.username IS NULL"; */
        
        String sql = "SELECT animalId FROM animals "
        		+ "WHERE owner <> ? "
        		+ "AND "
        		+ "animalId NOT IN (SELECT animalId FROM crate WHERE username = ?)";
        
        
        //want animal ids of all the animals
        //not owned by the current user
        //or in the current user's crate 
              
       //connect to the database
        connect_func();
        
       preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
       preparedStatement.setString(1, username);
       preparedStatement.setString(2,  username);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
        	int anId = resultSet.getInt("animalId");
        	
        	Animal animal = animalDAO.getAnimalFromId(anId);
        	
        	listCrateAnimals.add(animal);       	       	
        }    
        
        resultSet.close();     
        disconnect();     
        
        return listCrateAnimals;
    }
}