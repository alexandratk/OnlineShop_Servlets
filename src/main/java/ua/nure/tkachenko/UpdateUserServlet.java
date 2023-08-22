package ua.nure.tkachenko;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.tkachenko.db.DBManager;
import ua.nure.tkachenko.db.Entity.Team;
import ua.nure.tkachenko.db.Entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@WebServlet("/editUser")
public class UpdateUserServlet extends HttpServlet {
 
	private static final Logger logger = LogManager.getLogger(UpdateUserServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null && !currentUser.getRole().equals("user")) {
	        try {
	            String id = request.getParameter("id");
	            User user = DBManager.getUserById(id);
	            user.setTeams(DBManager.getTeamsByUserID(id));
	            List<Team> teams = new ArrayList<Team>();
	            if(user != null) {
	            	if (!user.getRole().equals("admin")) {
	            		teams = DBManager.getAllTeamsWithoutAdmin();
	            	}
	            	request.setAttribute("allTeams", teams);
	                request.setAttribute("editUser", user);
	                getServletContext().getRequestDispatcher("/editUser.jsp").forward(request, response);
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
    		int idUser = Integer.parseInt(request.getParameter("id"));
    		logger.info("edit user by id ==>" + Integer.toString(idUser));
         	String fullname = request.getParameter("fullname");
         	String idTeam = request.getParameter("team");
         	if (!idTeam.equals("-1")) {
         		if (DBManager.checkUserInTeam(Integer.toString(idUser), idTeam)) {
         			DBManager.addUserInTeam(Integer.toString(idUser), idTeam);
         			logger.info("for user by id ==>" + Integer.toString(idUser) + " add new team by id ==>" + idTeam);
         		}
         	}
         	User user = new User(idUser, fullname);
         	DBManager.updateUser(user);
         	response.sendRedirect("listUsers");
    	}
    	catch(Exception ex) {
    		getServletContext().getRequestDispatcher("/editUser.jsp").forward(request, response); 
    	}
    }
}
