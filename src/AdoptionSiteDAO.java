import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Servlet implementation class Connect
@WebServlet("/AdoptionSiteDAO")

public class AdoptionSiteDAO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	
	private UserDAO userDAO = new UserDAO();		//to access methods for users table
	private AnimalDAO animalDAO = new AnimalDAO();	//to access methods for animals table
	private TraitDAO traitDAO = new TraitDAO();		//to access methods for traits table
	private ReviewDAO reviewDAO = new ReviewDAO();	//to access methods for traits table
	private FavoriteBreedersDAO fbDAO = new FavoriteBreedersDAO();
	private FavoriteAnimalsDAO faDAO = new FavoriteAnimalsDAO();
	private CrateDAO crateDAO = new CrateDAO();
	
	public AdoptionSiteDAO() {
		
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
    
    //remove all tables and data from the database
    protected void removeAllTables() throws SQLException{
    	connect_func();
    	String sql;
    	
    	//to drop tables with foreign keys easily, disable checking for FK before dropping
    	sql = "SET FOREIGN_KEY_CHECKS = 0";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove users table
    	sql = "DROP TABLE IF EXISTS users";	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove animals table  	
    	sql = "DROP TABLE IF EXISTS animals";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove traits table
    	sql = "DROP TABLE IF EXISTS traits";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove reviews table
    	sql = "DROP TABLE IF EXISTS reviews";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove favoriteBreeders table
    	sql = "DROP TABLE IF EXISTS favoriteBreeders";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove favoriteAnimals table
    	sql = "DROP TABLE IF EXISTS favoriteAnimals";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//remove crate table
    	sql = "DROP TABLE IF EXISTS crate";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//reset checking back to true
    	sql = "SET FOREIGN_KEY_CHECKS = 1";
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	preparedStatement.close();
    	connect.close();  
    	disconnect();
    }
    
    //create schema
    protected void createTables() throws SQLException {
    	connect_func();
    	String sql;
    	
    	//create users table
    	sql = "CREATE TABLE users ("
    			+ "userId		int NOT NULL AUTO_INCREMENT, "
    			+ "username		varchar(20), "
    			+ "password		varchar(20), "
    			+ "firstname	varchar(20), "
    			+ "lastname		varchar(20), "
    			+ "email		varchar(30), "
    			+ "numReviews	int, "
    			+ "numPostings	int, "
    			+ "PRIMARY KEY (userId) ) ";
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();

    	//create traits table  	
    	sql = "CREATE TABLE traits ("
    			+ "traitId		int NOT NULL AUTO_INCREMENT, "
    			+ "trait		varchar(20), "
    			+ "PRIMARY KEY (traitId) ) ";
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    		
    	sql = "ALTER TABLE traits AUTO_INCREMENT=200"; //starts the auto-increment of traitIds at 200
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//create animals table
    	sql = "CREATE TABLE animals ("
    			+ "animalId		int NOT NULL AUTO_INCREMENT, "
    			+ "animalName	varchar(20), "
    			+ "species		varchar(20), "
    			+ "birthdate	char(10), "
    			+ "price		float(53), "
    			+ "owner		varchar(20), "
    			+ "traitId1		int, "
    			+ "traitId2		int, "
    			+ "traitId3		int, "
    			+ "PRIMARY KEY (animalId), "
    			+ "FOREIGN KEY (traitId1) REFERENCES traits (traitId), "
    			+ "FOREIGN KEY (traitId2) REFERENCES traits (traitId), "
    			+ "FOREIGN KEY (traitId3) REFERENCES traits (traitId) ) ";
 
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	sql = "ALTER TABLE animals AUTO_INCREMENT=100"; //starts the auto-increment of animalIds at 100
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//create reviews table
    	sql = "CREATE TABLE reviews ("
    			+ "reviewId		int NOT NULL AUTO_INCREMENT, "
    			+ "animalId		int, "
    			+ "userId       int, "
    			+ "postingUser	varchar(20), "
    			+ "contents		varchar(160), "
    			+ "category		varchar(20), "
    			+ "PRIMARY KEY (reviewId), "
    			+ "FOREIGN KEY (animalId) REFERENCES animals (animalId)"
    			+ "ON DELETE CASCADE ) ";
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    
    	sql = "ALTER TABLE reviews AUTO_INCREMENT=300"; //starts the auto-increment of reviewIds at 300
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//create favoriteBreeders table
    	sql = "CREATE TABLE favoriteBreeders ("
    			+ "user			varchar(20), "
    			+ "favBreeder	varchar(20), "
    			+ "PRIMARY KEY (user, favBreeder) )";
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//create favoriteAnimals table
    	sql = "CREATE TABLE favoriteAnimals ("
    			+ "user			varchar(20), "
    			+ "favAnimalId	int, "
    			+ "PRIMARY KEY (user, favAnimalId), "
    			+ "FOREIGN KEY	(favAnimalId) REFERENCES animals (animalId)"
    			+ "ON DELETE CASCADE )";
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	//create crate table
    	sql = "CREATE TABLE crate ("
    			+ "username		varchar(20), "
    			+ "animalId		int, "
    			+ "PRIMARY KEY (username, animalId), "
    			+ "FOREIGN KEY (animalId) REFERENCES animals (animalId)"
    			+ "ON DELETE CASCADE )";
    	
    	preparedStatement = (PreparedStatement)connect.prepareStatement(sql);
    	preparedStatement.execute();
    	
    	preparedStatement.close();
    	connect.close();
    	disconnect();
    }
    
    //add john user
    protected void addjohnUser() throws SQLException{
    	connect_func();
    	
    	User johnUser = new User("john", "pass1234", "john", "User", "johnuser@gmail.com", 0, 0);
    	
    	userDAO.insertUser(johnUser);
    	
    	connect.close();
    	disconnect();
    }
    
    //populate tables with data
    protected void populateTablesWithData() throws SQLException {
    	int num = 20; //number of users and animals
    	
    	connect_func();
    	
    	//populate users table - - - -
    	User user;
    	String username;
    	String password;
    	String first = "firstname";
    	String last = "lastname";
    	String email;
    	
    	for(int i = 1; i <= num; i++) {
    		username = "username" + Integer.toString(i);
    		password = "pass" + Integer.toString(i);
    		email = "email" + Integer.toString(i) + "@email.com";
    		
    		user = new User(username, password, first, last, email, 0, 0);
    		userDAO.insertUser(user);
    	}
    	
    	userDAO.insertUser(new User("demo", "pass", "demo", "user", "email@email.com", 0, 0));
    	
    	//populate traits table - - - -    	
    	String[] traits = {"N/A", "Energetic", "Goofy", "Weird", "Silly", "Smart",
    				"Cute", "Friendly", "Funny", "Annoying", "Awesome", "Smelly",
    				"Loud", "Soft", "Cute"};
    	
    	for(int i = 0; i < traits.length; i++) {
    		traitDAO.insertNewTrait(new Trait(traits[i]));
    	}
    
    	//populate animals table - - - -
     	String name;
    	String[] allSpecies = {"Dog", "Cat", "Bird", "Turtle", "Bat", "Rat", "Snake"};
    	String species;
    	String birthday;
    	int month;
    	String m;
    	int day;
    	String d;
    	DecimalFormat df = new DecimalFormat("0.00");
    	double rand;
    	float price;
    	String owner;
    	int tid1 = 200;
    	int tid2 = 200;
    	int tid3 = 200;
        	
    	for(int i = 1; i <= num; i++) {
    		name = "PetName" + Integer.toString(i);
    		species = allSpecies[(int)(Math.random() * allSpecies.length)];
    		
    		month = 1 + (int)(Math.random() * 13);
    		if(month < 10) {
    			m = "0" + Integer.toString(month);
    		} else {
    			m = Integer.toString(month);
    		}
    		
    		day = 1 + (int)(Math.random() * 29);
    		if(day < 10) {
    			d = "0" + Integer.toString(day);
    		} else {
    			d = Integer.toString(day);
    		}
    		
    		birthday = Integer.toString(2010 + (int)(Math.random() * 10)) + "/" 
    				+ m + "/" + d;
    		
    		rand = 20 + (int)(Math.random() * 300);
    		price = (float)Double.parseDouble(df.format(rand));
    	
    		owner = "username" + Integer.toString(1 + (int)(Math.random() * 10));
    		
    		tid1 = 201 + (int)(Math.random() * (traits.length - 1));
    		tid2 = 201 + (int)(Math.random() * (traits.length - 1));
    		tid3 = 201 + (int)(Math.random() * (traits.length - 1));
    		
    		if(tid1 == tid2) {
    			tid2 = 200;
    			tid3 = 200;
    		} else if(tid3 == tid1 || tid3 == tid2) {
    			tid3 = 200;
    		}
    		
    		animalDAO.insertNewAnimal(new Animal(name, species, birthday, price, owner, tid1, tid2, tid3)); 		
    	}
        	
    	//populate reviews table - - - - 
    	int anId;
    	String contents;
    	String[] category = {"Cray", "Cray-Cray", "Adorbs", "Totes Adorbs"};
    	String cat;
    	
    	//leave 3 reviews for each user on random animals (except last 3 users)
    	int a1, a2, a3;
    	
    	//no cray-cray reviews (from user)
    	//only adorbs and totes adorbs (for animal)
    	//no cray-cray reviews (for animal)
    	username = "username11";
    	contents = "Review Contents";
    	
    	a1 = 100;
    	a2 = 101;
    	a3 = 102;
    	   	
    	cat = "Adorbs";
    	
    	reviewDAO.insertNewReview(new Review (a1, 12, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 12, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 12, username, contents, cat));
    	
    	username = "username12";
    	cat = "Totes Adorbs";
    	
    	reviewDAO.insertNewReview(new Review (a1, 13, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 13, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 13, username, contents, cat));
    	
    	username = "username13";
    	cat = "Totes Adorbs";
    	
    	reviewDAO.insertNewReview(new Review (a1, 14, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 14, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 14, username, contents, cat));
    	
    	username = "username14";
    	cat = "Adorbs";
    	
    	reviewDAO.insertNewReview(new Review (a1, 15, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 15, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 15, username, contents, cat));
    	
    	a1 = 104;
    	a2 = 105;
    	a3 = 106;
    	
    	username = "username15";
    	cat = "Totes Adorbs";
    	
    	reviewDAO.insertNewReview(new Review (a1, 16, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 16, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 16, username, contents, cat));
    	
    	username = "username16";
    	cat = "Adorbs";
    	
    	reviewDAO.insertNewReview(new Review (a1, 17, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 17, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 17, username, contents, cat));
    	   	
    	//only cray-cray reviews (from user)
    	a1 = 109;
    	a2 = 110;
    	a3 = 111;
    	
    	username = "username17";
    	cat = "Cray-Cray";
    	
    	reviewDAO.insertNewReview(new Review (a1, 18, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 18, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 18, username, contents, cat));
    	
    	username = "username18";
    	
    	reviewDAO.insertNewReview(new Review (a1, 19, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a2, 19, username, contents, cat));
    	reviewDAO.insertNewReview(new Review (a3, 19, username, contents, cat));
    	    
    	//populate favoriteBreeders table - - - -
    	//give each user three favorite breeders
    	String favUser;
    	int u1, u2, u3;
    	
    	for(int i = 1; i <= num; i++) {
    		username = "username" + Integer.toString(i); 
    			
    		u1 = 1 + (int)(Math.random() * (num - 10));
    		u2 = 1 + (int)(Math.random() * (num - 10));
    		u3 = 1 + (int)(Math.random() * (num - 10));

    		while(u1 == i) {
        		u1 = 1 + (int)(Math.random() * (num - 10));
    		}
    		
    		while(u2 == i || u2 == u1) {
        		u2 = 1 + (int)(Math.random() * (num - 10));
    		}
    		
    		while(u3 == i || u3 == u2 || u3 == u1) {
    	   		u3 = 1 + (int)(Math.random() * (num - 10));
    		}
		
    		favUser = "username" + Integer.toString(u1);
    		fbDAO.insertFavBreeder(username, favUser);
    		
    		favUser = "username" + Integer.toString(u2);
    		fbDAO.insertFavBreeder(username, favUser);
    		
    		favUser = "username" + Integer.toString(u3);
    		fbDAO.insertFavBreeder(username, favUser);
    	}
    	
    	//populate favoriteAnimals table - - - -
    	//give each user three favorite animals
    	for(int i = 11; i <= 19; i++) {
    		username = "username" + Integer.toString(i); 
    		
    		a1 = 100 + (int)(Math.random() * (num - 10));
    		a2 = 100 + (int)(Math.random() * (num - 10));
    		a3 = 100 + (int)(Math.random() * (num - 10));
    		int a4 = 100 + (int)(Math.random() * (num - 10));

    		while(a1 == a2) {
        		a2 = 100 + (int)(Math.random() * (num - 10));
    		}
    		
    		while(a3 == a1 || a3 == a2) {
        		a3 = 100 + (int)(Math.random() * (num - 10));
    		}
    		
    		while(a4 == a1 || a4 == a2 || a4 == a3) {
        		a4 = 100 + (int)(Math.random() * (num - 10));
    		}	
    		  		
    		faDAO.insertFavAnimal(username, a1);
    		faDAO.insertFavAnimal(username, a2);
    		faDAO.insertFavAnimal(username, a3); 	
    		faDAO.insertFavAnimal(username, a4);
 	
    	}
    	
    	//user 20 has all animals marked as favorites
    	for(int i = 1; i <= 19; i++) {
    		faDAO.insertFavAnimal("username20", (100 + i));   		
    	}
        	
    	//populate crate table - - - -
    	//add 5 animals to each users crate
    	int a4 = 100;
    	int a5 = 100;
    	
    	for(int i = 11; i <= 20; i++) {
    		username = "username" + Integer.toString(i);
    		
    		a1 = 100 + (int)(Math.random() * num);
    		a2 = 100 + (int)(Math.random() * num);
    		a3 = 100 + (int)(Math.random() * num);
    		a4 = 100 + (int)(Math.random() * num);
    		a5 = 100 + (int)(Math.random() * num);
    		

    		while(a1 == a2) {
        		a2 = 100 + (int)(Math.random() * num);
    		}
    		
    		while(a3 == a1 || a3 == a2) {
        		a3 = 100 + (int)(Math.random() * num);
    		}
    		
    		while(a4 == a1 || a4 == a2 || a4 == a3) {
        		a4 = 100 + (int)(Math.random() * num);
    		}
    		
    		while(a5 == a1 || a5 == a2 || a5 == a3 || a5 == a4) {
        		a5 = 100 + (int)(Math.random() * num);
    		}
    		
    		crateDAO.insertToCrate(username, a1);
    		crateDAO.insertToCrate(username, a2);
    		crateDAO.insertToCrate(username, a3);
    		crateDAO.insertToCrate(username, a4);  		
    		crateDAO.insertToCrate(username, a5);    		
    	} 
    	
    	//for demo account
		crateDAO.insertToCrate("demo", a1);
		crateDAO.insertToCrate("demo", a2);
		crateDAO.insertToCrate("demo", a3);
		crateDAO.insertToCrate("demo", a4);  		
		crateDAO.insertToCrate("demo", a5);  
		
		//leave 3 reviews
    	reviewDAO.insertNewReview(new Review (107, 22, "demo", contents, "Adorbs"));
    	reviewDAO.insertNewReview(new Review (108, 22, "demo", contents, "Adorbs"));
    	reviewDAO.insertNewReview(new Review (112, 22, "demo", contents, "Adorbs"));
    	
    	connect.close();
    	disconnect();
    }
	  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}