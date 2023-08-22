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
import ua.nure.tkachenko.db.Entity.Team;
import ua.nure.tkachenko.db.Entity.User;

@WebServlet("/editTeam")
public class UpdateTeamServlet extends HttpServlet {
 
	private static final Logger logger = LogManager.getLogger(UpdateTeamServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null && !currentUser.getRole().equals("user")) {
	        try {
	            String id = request.getParameter("id");
	            Team team = DBManager.getTeamById(id);
	            if (team != null) {
	                request.setAttribute("editTeam", team);
	                getServletContext().getRequestDispatcher("/editTeam.jsp").forward(request, response);
	            }
	            else {
	                getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
	            }
	        }
	        catch(Exception ex) {
	            getServletContext().getRequestDispatcher("/notfound.jsp").forward(request, response);
	        }
		} else {
			response.sendRedirect("index.jsp");
		}
    }
     
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	try {
    		int id = Integer.parseInt(request.getParameter("id"));
    		logger.info("edit team by id ==>" + Integer.toString(id));
         	String title = request.getParameter("title");
         	Team team = new Team(id, title);
         	DBManager.updateTeam(team);
         	response.sendRedirect("listTeams");
    	}
    	catch(Exception ex) {
    		getServletContext().getRequestDispatcher("/editTeam.jsp").forward(request, response); 
    	}
    }
}
