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


@WebServlet("/deleteTeam")
public class DeleteTeamServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(DeleteTeamServlet.class);
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        	throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null && !currentUser.getRole().equals("user")) {
	        getServletContext().getRequestDispatcher("/deleteTeam.jsp").forward(request, response);
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
				if (!id.equals("1")) {
					logger.info("delete team by id ==> " + id);
					DBManager.deleteTeamById(id);       
				} else {
					logger.error("user tried to delete team administrators");
				}
				
			}
        	response.sendRedirect("listTeams");
    	}
    	catch(Exception ex) {
        	ex.printStackTrace();
     	}
	}
}