package ua.nure.tkachenko;

import java.io.IOException;
import java.util.ArrayList;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.tkachenko.db.DBManager;
import ua.nure.tkachenko.db.Entity.Team;
import ua.nure.tkachenko.db.Entity.User;
 
@WebServlet("/createTeam")
public class CreateTeamServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(CreateTeamServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null && !currentUser.getRole().equals("user")) {
	    	getServletContext().getRequestDispatcher("/createTeam.jsp").forward(request, response);
		} else {
			response.sendRedirect("index.jsp");
		}
	}
         
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
     
    	try {
         	String title = request.getParameter("title");
         	Team newTeam = new Team(title);
         	DBManager.insertTeam(newTeam);
         	logger.info("create new team login ==> " + newTeam.getTitle());
         	response.sendRedirect("listTeams");
    	}
    	catch(Exception ex) {
    		getServletContext().getRequestDispatcher("/createTeam.jsp").forward(request, response); 
    	}
	}
}
