import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JPanel;

/**
 * Servlet implementation class AdoptionServlet
 */
@WebServlet("/AdoptionServlet")
public class AdoptionServlet extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private AdoptionSiteDAO adoptionSiteDAO;
	 private UserDAO userDAO;
	 private AnimalDAO animalDAO;
	 private ReviewDAO reviewDAO;
	 private TraitDAO traitDAO;
	 final JPanel panel = new JPanel();
	 private HttpSession session =null;

	 public void init() {
	        adoptionSiteDAO = new AdoptionSiteDAO(); 
	        userDAO = new UserDAO();
	        animalDAO = new AnimalDAO();
	        reviewDAO = new ReviewDAO();
	        traitDAO = new TraitDAO();
	    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdoptionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
  		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		session = request.getSession();

    	//get username used for the owner
    	String owner = (String) session.getAttribute("currentUser");
    	
    	//check if username have 5 animals registered
    	int userlimit = 0;
		try {
			userlimit = animalDAO.numberOfUserAnimals(owner);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
    	if(userlimit == 5) {
    		//step 1: set the content type
      		response.setContentType("text/html");
      		
      		//step 2: get the printwriter
      		PrintWriter outt = response.getWriter();
      		
      		//step 3: generate HTML content
      		outt.println("<html><body>");
      		
      		outt.print("<head>\r\n" + 
      					"    <title>Pet Adoption Site</title>\r\n" + 
      					"</head>");
      		
      		outt.println("<div align=\"center\">");
      		
      		outt.println("<h2>Sorry you have reached your limit for adoptions!</h2>");
      		outt.println("<a href=\"HomePage.jsp\">Homepage</a>");     		
      		
      		outt.println("</center>");
      		outt.println("</body></html>");
    	}else {
    		//get the parameters from the form
            String name = request.getParameter("animalName");
            String species = request.getParameter("species");
            String birthday = request.getParameter("birthday");
            String traits = request.getParameter("traits");
            String price = request.getParameter("price");
            //parse to float
            float fprice = Float.parseFloat(price);
            
            Animal animal = new Animal(name, species, birthday, fprice, owner, 200 , 200, 200);
            
            
            //get the traits separated by the comma
            StringTokenizer tokenizer = new StringTokenizer(traits, ",");
            int traitNum = 0;
            
            while(tokenizer.hasMoreTokens()) {
            	traitNum++; //increase the trait number
            	
            	//get each string and make a trait out of it
            	String tempTrait = tokenizer.nextToken();
            	Trait animalTrait = new Trait(tempTrait);
            	
            	//get trait id
            	int traitId = 0;
            	
    			try {
    				traitId = traitDAO.findTraitId(animalTrait);
    				
    			} catch (SQLException e1) {
    				// TODO Auto-generated catch block
    				e1.printStackTrace();
    			}
    			
            	try {
            		//get trait ID if it does not exist 
    				if( traitId == -1) {
    					traitDAO.insertNewTrait(animalTrait); //add the trait
    					traitId = traitDAO.findTraitId(animalTrait); //get the traitID	
    				}	
    				
    				switch(traitNum) {
    				case 1: 
    					animal.setTraitId1(traitId);
    					break;
    				case 2:
    					animal.setTraitId2(traitId);
    					break;
    				case 3:
    					animal.setTraitId3(traitId);
    					break;			
    				}
    							
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}        	
            } //end tokenizer of traits
            
            //now add the animal
            try {
    			animalDAO.insertNewAnimal(animal);
    		} catch (SQLException e2) {
    			// TODO Auto-generated catch block
    			e2.printStackTrace();
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
      		
      		out.println("<h2>Adoption Listed Successfully!</h2>");
      		out.println("<a href=\"HomePage.jsp\">Homepage</a>");     		
      		
      		out.println("</center>");
      		out.println("</body></html>");
    	}
    	
        
	}

}

