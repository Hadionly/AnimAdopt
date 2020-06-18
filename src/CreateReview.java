import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

@WebServlet("/CreateReview")
public class CreateReview extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;
    final JPanel panel = new JPanel();
    String category,contents,postingUsername,animalID, resultMessage;
    int postingUserID;
    private HttpSession session =null;

    public void init() {
        reviewDAO = new ReviewDAO();
    }
	     
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	session = request.getSession();

	    	//use the posting username to get the users id to be used in creation of the new review
	    	String postingUser = (String) session.getAttribute("currentUser");
	    	
	    	//check if username have 5 animals registered
	    	int userlimit = 0;
			try {
				userlimit = reviewDAO.numberOfUserReviews(postingUser);
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
	      		
	      		outt.println("<h2>Sorry you have reached your limit for reviews!</h2>");
	      		outt.println("<a href=\"HomePage.jsp\">Homepage</a>");     		
	      		
	      		outt.println("</center>");
	      		outt.println("</body></html>");
			}else {
				
				int animalId = Integer.parseInt(request.getParameter("AnimalID"));
				
				try {
					if(reviewDAO.checkReview(postingUser, animalId)) {
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
						
						outt.println("<h2>Sorry you can't review the same animal twice!</h2>");
						outt.println("<a href=\"HomePage.jsp\">BACK</a>");     		
						
						outt.println("</center>");
						outt.println("</body></html>");
					}else {
						User user = null;
						try {
							user = UserDAO.getUserWithUsername(postingUser);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						int userId = (Integer) user.getUserId();
						System.out.println(userId);
						try {
							//need to have the animalId displayed on the review form in a hidden button or maybe inside the submit button
							
							String content= request.getParameter("content");
							String category= request.getParameter("category");
							Review newReview = new Review(animalId, userId, postingUser, content,category);
							ReviewDAO.insertNewReview(newReview);
						}
						catch (NumberFormatException | SQLException e)
						{
							System.out.println(e);
						}
						request.setAttribute("message", resultMessage);
						request.getRequestDispatcher("ReviewForm.jsp").forward(request,response);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
	   }
}
