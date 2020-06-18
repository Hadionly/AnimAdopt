import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * ControllerServlet.java
 * This servlet acts as a page controller for the application, handling all
 * requests from the user.
 */
public class ControlServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdoptionSiteDAO adoptionSiteDAO;
    private UserDAO userDAO;
    private AnimalDAO animalDAO;
    private ReviewDAO reviewDAO;
    private FavoriteBreedersDAO favBreedersDAO;
    private FavoriteAnimalsDAO favAnimalsDAO;
    private CrateDAO crateDAO;
    final JPanel panel = new JPanel();
    String username,password,firstname,lastname,email;
    private HttpSession session =null;
	private static Connection connect = null;
	private static Statement statement = null;
	private static PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

    public void init() {
        adoptionSiteDAO = new AdoptionSiteDAO(); 
        userDAO = new UserDAO();
        animalDAO = new AnimalDAO();
        reviewDAO = new ReviewDAO();
        favBreedersDAO = new FavoriteBreedersDAO();
        favAnimalsDAO = new FavoriteAnimalsDAO(); 
        crateDAO = new CrateDAO();
    }
 
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	//get the parameters from the form
        username = request.getParameter("username");
        password = request.getParameter("password");
        firstname = request.getParameter("firstname");
        lastname = request.getParameter("lastname");
        email = request.getParameter("email");
        
        //check if username or email exists
try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/adoptionsitedb?"
			          + "user=john&password=pass1234");
			preparedStatement = connect.prepareStatement("SELECT * FROM users WHERE username=? OR email=?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, email);
			resultSet = preparedStatement.executeQuery();
			
			if(resultSet.next()) {
				// check if user exists
				try {
					
					Class.forName("com.mysql.jdbc.Driver");
					connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/adoptionsitedb?"
					          + "user=john&password=pass1234");
					preparedStatement = connect.prepareStatement("SELECT * FROM users WHERE username=?");
					preparedStatement.setString(1, username);
					resultSet = preparedStatement.executeQuery();
					
					if(resultSet.next()) {
						//step 1: set the content type
			      		response.setContentType("text/html");
			      		
			      		//step 2: get the printwriter
			      		PrintWriter out = response.getWriter();
			      		
			      		//step 3: generate HTML content
			      		out.println("<html><body>");
			      		
			      		out.print("<head>\r\n" + 
			      					"    <title>Pet Adoption Site</title>\r\n" + 
			      					"</head>");
			      		
			      		out.println("<div align=\"center\">");
			      		
			      		out.println("<h2>Username already exists!</h2>");
			      		out.println("<a href=\"RegistrationForm.jsp\">Try Again</a>");     		
			      		
			      		out.println("</center>");
			      		out.println("</body></html>");
					}else {
						//step 1: set the content type
			      		response.setContentType("text/html");
			      		
			      		//step 2: get the printwriter
			      		PrintWriter out = response.getWriter();
			      		
			      		//step 3: generate HTML content
			      		out.println("<html><body>");
			      		
			      		out.print("<head>\r\n" + 
			      					"    <title>Pet Adoption Site</title>\r\n" + 
			      					"</head>");
			      		
			      		out.println("<div align=\"center\">");
			      		
			      		out.println("<h2>Email already exists!</h2>");
			      		out.println("<a href=\"RegistrationForm.jsp\">Try Again</a>");     		
			      		
			      		out.println("</center>");
			      		out.println("</body></html>");
					}
					
				} catch (SQLException | ClassNotFoundException e) {
					e.getMessage();
				}
			} else {

		        User newUser = new User(username, password, firstname, lastname, email, 0, 0);
		        
		        try {
					if(!userDAO.insertUser(newUser)){
						throw new SQLException(); //error adding to table
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        	//step 1: set the content type
		      		response.setContentType("text/html");
		      		
		      		//step 2: get the printwriter
		      		PrintWriter out = response.getWriter();
		      		
		      		//step 3: generate HTML content
		      		out.println("<html><body>");
		      		
		      		out.print("<head>\r\n" + 
		      					"    <title>Pet Adoption Site</title>\r\n" + 
		      					"</head>");
		      		
		      		out.println("<div align=\"center\">");
		      		
		      		out.println("<h2>User Registered Successfully!</h2>");
		      		out.println("<a href=\"LoginForm.jsp\">Login</a>");     		
		      		
		      		out.println("</center>");
		      		out.println("</body></html>");
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			e.getMessage();
		} 
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
        String action = request.getServletPath();
        System.out.println(action);
        try {
            switch (action) {
            case "/initialize":
            	initializeDatabase(request, response);
            	break;
            case "/loginorregister":
            	showLoginOrRegisterPage(request, response);
            	break;
            case "/register":
            	showRegistrationForm(request, response);
            	break;
            case "/registerNow":
            	showLoginOrRegisterPage(request, response);
            	break;
            case "/login":
            	showLoginForm(request, response);
            	break;
            case "/home":
            	showHomePage(request, response);
            	break;
            case "/searchform":
            	showSearchForm(request, response);
            	break;
            case "/listmyadoptions":
            	showListMyAdoptions(request, response);
            	break;
            case "/listalladoptions":
            	showListAllAdoptions(request, response);
            	break;
            case "/adoptionform":
            	showAdoptionForm(request, response);
            	break;
            case "/reviewform":
            	showReviewForm(request, response);
            	break;
            case "/listUsers":
            	listUsers(request, response);
            	break;
            case "/adoptionCrate":
            	showAdoptionCrate(request, response);
            	break;
            case "/removeFromCrate":
            	removeFromCrate(request, response);
            	break;
            case "/addAnimalToCrate":
            	addAnimalToCrate(request, response);
            	break;
            case "/adoptAnimal":
            	showAdoptAnimal(request, response);
            	break;
            case "/addToCrate":
            	showAddToCrate(request, response);
            	break;
            case "/congrats":
            	showCongrats(request, response);
            	break;
            case "/favBreeders":
            	showFavBreeders(request, response);
            	break;
            case "/favAnimals":
            	showFavAnimals(request, response);
            	break;
            case "/seeUsersWho":
            	showSeeUsersWho(request, response);
            	break;
            case "/onlyAdorbs":
            	showOnlyAdorbs(request, response);
            	break;
            case "/mostExpensive":
            	showMostExpensive(request, response);
            	break;
            case "/logout":
            	logoutUser(request, response);
            	break;
            default: 
            	showHomePage(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
 
    private void initializeDatabase(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	//remove all tables and data from the database
    	adoptionSiteDAO.removeAllTables();
    	
    	//create the tables/schema for database
    	adoptionSiteDAO.createTables();
    	
    	//add  user
    	adoptionSiteDAO.addRootUser();
    	
    	//populate table(s) with data (make a new method for every table)
    	adoptionSiteDAO.populateTablesWithData();	
    	
    	//then take the user to the login or register page
        RequestDispatcher dispatcher = request.getRequestDispatcher("LoginOrRegisterPage.jsp");
        dispatcher.forward(request, response);
    }

    private void showLoginOrRegisterPage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("LoginOrRegisterPage.jsp");
        dispatcher.forward(request, response);
    }
    
    
    private void showRegistrationForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("RegistrationForm.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("LoginForm.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showHomePage(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("HomePage.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showReviewForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException { 	
        RequestDispatcher dispatcher = request.getRequestDispatcher("ReviewForm.jsp");
        dispatcher.forward(request, response);
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUsers = userDAO.listAllUsers();

        request.setAttribute("listUsers", listUsers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("UsersList.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showSearchForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("SearchForm.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showListMyAdoptions(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	 session=request.getSession();
    	 String currentUser	= (String) session.getAttribute("currentUser");
    	 System.out.println(currentUser);
    	 List<Animal> listAllAnimals = animalDAO.listUsersAnimals(currentUser);

         request.setAttribute("listAllAnimals", listAllAnimals);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListMyAdoptions.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showListAllAdoptions(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
		 session=request.getSession();
		 String currentUser	= (String) session.getAttribute("currentUser");
    	
        List<Animal> listAllAnimals = animalDAO.listAllAnimals(currentUser);

        request.setAttribute("listAllAnimals", listAllAnimals);

        RequestDispatcher dispatcher = request.getRequestDispatcher("ListAllAdoptions.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showAdoptionForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdoptionForm.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showAdoptionCrate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
	   	session=request.getSession();
        
	   	String currentUser	= (String) session.getAttribute("currentUser");
	   	System.out.println(currentUser);
	   	List<Animal> listCrateAnimals = crateDAO.listCrateAnimals(currentUser);

        request.setAttribute("listCrateAnimals", listCrateAnimals);
   	    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdoptionCrate.jsp");
        dispatcher.forward(request, response);
    }
    
    private void removeFromCrate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
	   	session=request.getSession();
		String currentUser	= (String) session.getAttribute("currentUser");
        String removeId = (String)request.getParameter("animalId");

		int anId = Integer.parseInt(removeId);	
		crateDAO.removeFromCrate(currentUser, anId);

   	    response.sendRedirect("adoptionCrate");
    }
    
    private void addAnimalToCrate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
	   	session=request.getSession();
		String currentUser	= (String) session.getAttribute("currentUser");
        String addId = (String)request.getParameter("animalId");

		int anId = Integer.parseInt(addId);	
		crateDAO.insertToCrate(currentUser, anId);

   	    response.sendRedirect("adoptionCrate");
    }
    
    private void showAdoptAnimal(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	session = request.getSession();
		String anIdString = request.getParameter("animalId");
		
		int anId = Integer.parseInt(anIdString);
		
		Animal animal = animalDAO.getAnimalFromId(anId);
		
		String name = animal.getAnimalName();
		String species = animal.getSpecies();
		float price = animal.getPrice();
	
		request.setAttribute("name", name);
		request.setAttribute("species", species);
		request.setAttribute("price", price);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("AdoptAnimal.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showAddToCrate(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
		session=request.getSession();
		String currentUser	= (String) session.getAttribute("currentUser");
   	
        List<Animal> listAllAnimals = crateDAO.listAnimalsNotInCrate(currentUser);

        request.setAttribute("listAllAnimals", listAllAnimals);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("AddToCrate.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showCongrats(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	session = request.getSession();
		String anIdString = request.getParameter("anId");
		
		int anId = Integer.parseInt(anIdString);	
		animalDAO.deleteAnimal(anId);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("Congrats.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showFavBreeders(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	session=request.getSession();
   	 	String currentUser	= (String) session.getAttribute("currentUser");
   	 	System.out.println(currentUser);
    	
        List<FavoriteBreeders> listFavBreeders = favBreedersDAO.listUsersFavBreeders(currentUser);

        request.setAttribute("listFavBreeders", listFavBreeders);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("FavBreeders.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showFavAnimals(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	session=request.getSession();
   	 	String currentUser	= (String) session.getAttribute("currentUser");
   	 	System.out.println(currentUser);
    	
        List<Animal> listFavAnimals = favAnimalsDAO.listUsersFavAnimals(currentUser);

        request.setAttribute("listFavAnimals", listFavAnimals);
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("FavAnimals.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showSeeUsersWho(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("SeeUsersWho.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showOnlyAdorbs(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
   	 	session=request.getSession();
   	 	String currentUser	= (String) session.getAttribute("currentUser");
    	
        RequestDispatcher dispatcher = request.getRequestDispatcher("OnlyAdorbs.jsp");
        dispatcher.forward(request, response);
    }
    
    private void showMostExpensive(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	ArrayList<String> MostExp = animalDAO.MostExpensiveAnimal();
		
		request.setAttribute("MostExp", MostExp );
        RequestDispatcher dispatcher = request.getRequestDispatcher("MostExpensive.jsp");
        dispatcher.forward(request, response);
    }
    
    private void logoutUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
    	session=request.getSession();
    	session.invalidate();
        RequestDispatcher dispatcher = request.getRequestDispatcher("LoginOrRegisterPage.jsp");
        dispatcher.forward(request, response);
    }
}