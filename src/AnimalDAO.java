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
@WebServlet("/AnimalDAO")
public class AnimalDAO extends HttpServlet {	
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	private UserDAO userDAO = new UserDAO();
	
	public AnimalDAO() {
		
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
    
    //insert a new animal
    protected boolean insertNewAnimal(Animal animal) throws SQLException {
    	//connect to the database
    	connect_func();
    
    	//set prepared statement contents for adding to database
    	//animalId is done with AUTO_INCREMENT
		String sql = "insert into animals(animalName, species, birthdate, price, owner, traitId1, traitId2, traitId3) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		
		preparedStatement.setString(1, animal.animalName);
		preparedStatement.setString(2, animal.species);
		preparedStatement.setString(3, animal.birthdate);
		preparedStatement.setFloat(4, animal.price);
		preparedStatement.setString(5, animal.owner);
		preparedStatement.setInt(6, animal.traitId1);
		preparedStatement.setInt(7, animal.traitId2);
		preparedStatement.setInt(8, animal.traitId3);
			
        boolean rowInserted = preparedStatement.executeUpdate() > 0;
        
        preparedStatement.close();
        connect.close();
        disconnect(); 

        return rowInserted;
    }
    
    //returns list of all a user's animals
    protected List<Animal> listUsersAnimals(String username) throws SQLException {
    	//create array list to hold the users
        List<Animal> listAnimals = new ArrayList<Animal>();  
        
        //prepared sql query statement
        String sql = "SELECT * FROM animals WHERE owner = ?"; //can edit this statement to remove duplicates with the traits...
        
      //connect to the database
        connect_func();
        
        //specify that animalId should be unique
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, username);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
        	int animalId = resultSet.getInt("animalId");
        	String animalName = resultSet.getString("animalName");
        	String species = resultSet.getString("species");
        	String birthdate = resultSet.getString("birthdate");
        	Float price = resultSet.getFloat("price");
        	int traitId1 = resultSet.getInt("traitId1");
        	int traitId2 = resultSet.getInt("traitId2");
        	int traitId3 = resultSet.getInt("traitId3");
        	
        	Animal animal = new Animal(animalId, animalName, species, birthdate, price, username, traitId1,
        			traitId2, traitId3);

        	listAnimals.add(animal);
        }    
        
        resultSet.close();     
        disconnect();     
        
        return listAnimals;
    }
    
    //Return animal for given ID
    protected Animal getAnimalFromId(int id) throws SQLException {
    	//create animal object to hold animal
        Animal animal = new Animal();
        
        //prepared sql query statement
        String sql = "SELECT * FROM animals WHERE animalId = ?"; //can edit this statement to remove duplicates with the traits...
        
        //connect to the database
        connect_func();
        
        //specify that animalId should be unique
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, id);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
         
        while (resultSet.next()) {
        	
        	String animalName = resultSet.getString("animalName");
        	String species = resultSet.getString("species");
        	String birthdate = resultSet.getString("birthdate");
        	Float price = resultSet.getFloat("price");
        	String owner = resultSet.getString("owner");
        	int traitId1 = resultSet.getInt("traitId1");
        	int traitId2 = resultSet.getInt("traitId2");
        	int traitId3 = resultSet.getInt("traitId3");
        	
        	animal = new Animal(id, animalName, species, birthdate, price, owner, traitId1,
        			traitId2, traitId3);
        }    
        
        resultSet.close();     
        disconnect();     
        
        return animal;
    }
    
  //returns a list of every Animal in the ANIMAL table that does not belong to current user
    protected List<Animal> listAllAnimals(String user) throws SQLException {   	
    	//create array list to hold the animals
        List<Animal> listAllAnimals = new ArrayList<Animal>();  
        
        //prepared sql query statement
        String sql = "SELECT * FROM animals WHERE owner <> ?";
        
        //connect to the database
        connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setString(1, user);
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = preparedStatement.executeQuery();
                  
        while (resultSet.next()) {
        	int animalId = resultSet.getInt("animalId");
        	String animalName = resultSet.getString("animalName");
        	String species = resultSet.getString("species");
        	String birthdate = resultSet.getString("birthdate");
        	Float price = resultSet.getFloat("price");
        	String username = resultSet.getString("owner");
        	int traitId1 = resultSet.getInt("traitId1");
        	int traitId2 = resultSet.getInt("traitId2");
        	int traitId3 = resultSet.getInt("traitId3");
        	
        	Animal animal = new Animal(animalId, animalName, species, birthdate, price, username, traitId1,
        			traitId2, traitId3);

        	listAllAnimals.add(animal);
        }    
                
        resultSet.close();
        statement.close();        
        disconnect();     
        
        return listAllAnimals;
    }
    
    //returns most expensive animal
    protected ArrayList<String> MostExpensiveAnimal() throws SQLException {
    	//create array list to hold the most expensive animal 
        ArrayList<String> MostExp = new ArrayList<String>();  
        
        //prepared sql query statement
        String sql = "SELECT * FROM animals WHERE price = (SELECT MAX(price) FROM animals)"; 
        
        //connect to the database
        connect_func();
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql); 
		resultSet.next();
		//get values from result Set
		int animalID = resultSet.getInt("animalId");
		Animal animal = getAnimalFromId(animalID);
		String traits = animal.getTraitList();
		String animalName = animal.getAnimalName();
		String species = animal.getSpecies();
		String owner = animal.getOwner();
		String price = String.valueOf(animal.getPrice());
		String BD = animal.getBirthdate();
		//Add the values to MostExp ArrayList
		MostExp.add(animalName);
		MostExp.add(species);
		MostExp.add(owner);
		MostExp.add(price);
		MostExp.add(BD);
		MostExp.add(traits);  
        
		resultSet.close();
        statement.close();        
        disconnect();     
        
        return MostExp;
    }
    
    //delete animal from id
    protected void deleteAnimal(int id) throws SQLException {        
        //prepared sql query statement
        String sql = "DELETE FROM animals WHERE animalId = ?"; //can edit this statement to remove duplicates with the traits...
        
        //connect to the database
        connect_func();
        
        //specify that animalId should be unique
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		preparedStatement.setInt(1, id);
        
        statement = (Statement) connect.createStatement();
        preparedStatement.executeUpdate();
             
        disconnect();     
    }
    
    
    //list the number of adopted animals per users
    protected int numberOfUserAnimals(String owner) throws SQLException {  	
    	//prepared sql query statement
        String sql = "SELECT `owner`, COUNT(*) AS `count` FROM animals WHERE `owner`='"+ owner +"'";
        
        //connect to the database
        connect_func();
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        
        int num = resultSet.getInt("count");
        
        return num;
    }
}

