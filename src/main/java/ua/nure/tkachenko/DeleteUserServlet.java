package ua.nure.tkachenko;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.tkachenko.db.DBManager;
import ua.nure.tkachenko.db.Entity.User;


@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(DeleteUserServlet.class);
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        	throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null && !currentUser.getRole().equals("user")) {
	        getServletContext().getRequestDispatcher("/deleteUser.jsp").forward(request, response);
		} else {
			response.sendRedirect("index.jsp");
		}
    }
            
     
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		try {
			String button = request.getParameter("button");
			if (button.equals("yes")) {
				String id = request.getParameter("id");  
				logger.info("delete user by id ==> " + id);
				DBManager.deleteUserById(id);
			}
        	response.sendRedirect("listUsers");
    	}
    	catch(Exception ex) {
        	ex.printStackTrace();
     	}
	}
}