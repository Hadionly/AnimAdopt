import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@WebServlet("/ViewReview")
public class ViewReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;
    final JPanel panel = new JPanel();
    String category,contents,postingUsername, resultMessage;
    int postingUserID,animalID;
    private HttpSession session =null;

    public void init() {
        reviewDAO = new ReviewDAO();
    }
	    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//get animal id selected from the ListAllAdoptions.jsp and show all the reviews in a new page
    	try { 
    		animalID = Integer.parseInt(request.getParameter("ViewAnimalID"));
    	} catch (NumberFormatException e)
    	{
    		request.setAttribute("ViewAnimalID", request.getParameter("ViewAnimalID"));
    		request.getRequestDispatcher("ReviewList.jsp").forward(request, response);
    		return;
    	}
    	
    	try {
    		List<Review> review = ReviewDAO.listAnimalsReviews(animalID);
    		if (review != null)
    		{
    			//create a list containing all the reviews for a selected animal
    			List<Review> reviewList = ReviewDAO.listAnimalsReviews(animalID);
    			
    			//create a list for the people who posted the reviews
    			List<String> reviewPosterList = new ArrayList<String>();
    			for (int i = 0; i < reviewList.size(); i++) {
    				String postingUser = reviewList.get(i).getPostingUser();
    				reviewPosterList.add(postingUser);
    			}
    			
    			List<String> reviewContentsList = new ArrayList<String>();
    			for (int i = 0; i < reviewList.size(); i++) {
    				String reviewContents = reviewList.get(i).getContents();
    				reviewContentsList.add(reviewContents);
    			}
    			
    			List<String> reviewCategoryList = new ArrayList<String>();
    			for (int i = 0; i < reviewList.size(); i++) {
    				String reviewCategory = reviewList.get(i).getCategory();
    				reviewCategoryList.add(reviewCategory);
    			}
    			
    			//set attributes to be used in the reviewList page
    			request.setAttribute("review", review);
    			request.setAttribute("reviewList", reviewList);
    			request.setAttribute("reviewContentsList", reviewContentsList);
    			request.setAttribute("reviewPosterList", reviewPosterList);
    			request.setAttribute("reviewCategoryList", reviewCategoryList);

    			
    		}
    	} catch (NumberFormatException | SQLException e) {
    		System.out.println(e);
    		//could forward to an error page here
    	}
    	
    	request.setAttribute("animalID", request.getParameter("AnimalID"));
    	request.getRequestDispatcher("ReviewList.jsp").forward(request, response);
    	return;
    }
	 
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	doGet(request,response);
	       }
}
