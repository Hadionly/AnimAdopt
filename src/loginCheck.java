import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Servlet implementation class loginCheck
 */
@WebServlet("/loginCheck")
public class loginCheck extends HttpServlet {
	 private static final long serialVersionUID = 1L;
	 private UserDAO userDAO;
	 final JPanel panel = new JPanel();
	 String username,password;
	 private HttpSession session =null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginCheck() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() {
        userDAO = new UserDAO();
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
		//get the parameters from the form
        username = request.getParameter("username");
        password = request.getParameter("password");
        
        //invalidate active session (if user is sent back to the login)
       // HttpSession session = request.getSession(false);
       // if (session !=null) 
        //		session.invalidate(); 
        
      //check if the fields are blank
        if(username.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(panel, "Fill all the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
			try {
				if(userDAO.validateLogin(username, password)) {
					session=request.getSession();
					session.setAttribute("currentUser", username);
				
					RequestDispatcher dispatcher = request.getRequestDispatcher("HomePage.jsp");
					dispatcher.forward(request, response);
				}else {
					RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
					dispatcher.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
	}

}