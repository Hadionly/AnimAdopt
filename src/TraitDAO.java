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
@WebServlet("/TraitDAO")
public class TraitDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public TraitDAO() {
		
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
    
    protected int findTraitId(Trait t) throws SQLException {
    	//returns the id of an existing trait from the trait table
    	int traitId = -1;
    	
    	String sql = "SELECT * FROM traits WHERE trait = ?";
    	
    	connect_func();
        
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setString(1, t.trait);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
        	traitId = resultSet.getInt("traitId");  	
        }
         
        resultSet.close();
        disconnect();
    	
    	return traitId; //returns -1 if trait does not exist in table
    }
    
    //adds a new trait to the traits table
    protected void insertNewTrait(Trait t) throws SQLException {	
    	//this method will:
    		//add a new trait and should only be called AFTER making sure it does not exist (can use findTraitId method to do this)
    		//VERY important to avoid duplicates!!
    	//connect to the database
    	connect_func();
     	
    	//set prepared statement contents for adding to database
    	//trait id is auto-incremented
		String sql = "insert into traits(trait) "
				+ "values (?)";
		
		preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
		
		preparedStatement.setString(1, t.trait);
			
		preparedStatement.executeUpdate();
        
        preparedStatement.close();
        connect.close();
        disconnect();    	
    }
    
    public String getTrait(int id) throws SQLException {
    	String name = "N/A";
    	
    	connect_func();
    	String sql = "SELECT * FROM traits WHERE traitId = ?";
    	
        preparedStatement = (PreparedStatement) connect.prepareStatement(sql);
        preparedStatement.setInt(1, id);
         
        ResultSet resultSet = preparedStatement.executeQuery();
         
        if (resultSet.next()) {
        	name = resultSet.getString("trait");  	
        }
         
        resultSet.close();
        disconnect();
        
        return name;   	
    }
    
    //returns a list of every Trait in the TRAIT table
    protected List<Trait> listAllTraits() throws SQLException {
    	//create array list to hold the animals
        List<Trait> listAllTraits = new ArrayList<Trait>();  
        
        //prepared sql query statement
        String sql = "SELECT * FROM traits";
        
        //connect to the database
        connect_func();
        
        statement = (Statement) connect.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
                  
        while (resultSet.next()) {
        	int traitId = resultSet.getInt("traitId");
        	
        	if(traitId != 200) {
        		String trait = resultSet.getString("trait");
        		Trait newTrait = new Trait(traitId, trait);
        		listAllTraits.add(newTrait);
        	}
        }    
                
        resultSet.close();
        statement.close();        
        disconnect();     
        
        return listAllTraits;
    }
}