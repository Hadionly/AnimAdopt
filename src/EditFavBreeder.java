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

@WebServlet("/editFavBreeder")
public class EditFavBreeder extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private FavoriteBreedersDAO favBreedersDAO;
    String favBreeder;
    private HttpSession session =null;
    
    public void init() {
        favBreedersDAO = new FavoriteBreedersDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//get animal id selected from the ListAllAdoptions.jsp and show all the reviews in a new page
    	try { 
    		favBreeder = request.getParameter("RemoveFavBreeder");
    	} catch (NumberFormatException e)
    	{
    		request.setAttribute("RemoveFavBreeder", request.getParameter("RemoveFavBreeder"));
    		//request.getRequestDispatcher("ReviewList.jsp").forward(request, response);
    		return;
    	}
    	
    	session=request.getSession();
   	 	String currentUser	= (String) session.getAttribute("currentUser");
    	
		try {
			favBreedersDAO.removeFavBreeder(currentUser ,favBreeder);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	/*	
	 * attempt to refresh the list of breeders after removing one, would need restructure of 
	 * jsp page for displaying it probably not worth the effort.
	 * Refresh button works well enough
		try {
		List<FavoriteBreeders> favoriteBreeders = favBreedersDAO.listUsersFavBreeders(currentUser);
		if (favoriteBreeders != null) { 
			List<FavoriteBreeders> favoriteBreedersList = favBreedersDAO.listUsersFavBreeders(currentUser);

			List<String> listFavBreeders = new ArrayList<String>();
			for (int i = 0; i < favoriteBreedersList.size(); i++) {
				String favBreeder = favoriteBreedersList.get(i).getFavBreeder();
				listFavBreeders.add(favBreeder);
			}
			request.setAttribute("listFavBreeders", listFavBreeders);
		}
    	
    	request.getRequestDispatcher("FavBreeders.jsp").forward(request, response);
    	return;
		} catch (NumberFormatException | SQLException e) {
		System.out.println(e);} 
	*/
		request.getRequestDispatcher("FavBreeders.jsp").forward(request, response);
    	return;
    }
	 
	     
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
	    	session = request.getSession();

	    	//use the posting username to get the users id to be used in creation of the new review
	    	String postingUser = (String) session.getAttribute("currentUser");

    		String addBreeder = request.getParameter("item");
	    
			try {
				favBreedersDAO.insertFavBreeder(postingUser, addBreeder);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			
		    	request.getRequestDispatcher("FavBreeders.jsp").forward(request,response);
			
	       } 
	  
}

