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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.nure.tkachenko.db.DBManager;
import ua.nure.tkachenko.db.Entity.Team;
import ua.nure.tkachenko.db.Entity.User;
 
@WebServlet("/createUser")
public class CreateUserServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(CreateUserServlet.class);
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null && !currentUser.getRole().equals("user")) {
	        List<Team> teams = DBManager.getAllTeamsWithoutAdmin();
	    	request.setAttribute("allTeams", teams);
	    	getServletContext().getRequestDispatcher("/createUser.jsp").forward(request, response);
		} else {
			response.sendRedirect("index.jsp");
		}
	}
         
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
    	throws ServletException, IOException {
     
    	try {
         	String login = request.getParameter("login");
         	String password = request.getParameter("password");
         	String fullname = request.getParameter("fullname");
         	String role = request.getParameter("role");
         	User newUser = new User(login, password, role, fullname);
         	DBManager.insertUser(newUser);
         	String idTeam = request.getParameter("team");
         	if (role.equals("admin")) {
         		idTeam = "1";
         	}
         	User createdUser = DBManager.getUser(login);
         	logger.info("create new user id ==> " + createdUser.getId() + " login ==> " + createdUser.getLogin());
         	if (!idTeam.equals("-1")) {
         		DBManager.addUserInTeam(Integer.toString(createdUser.getId()), idTeam);
         	}
         	response.sendRedirect("listUsers");
    	}
    	catch(Exception ex) {
    		getServletContext().getRequestDispatcher("/createUser.jsp").forward(request, response); 
    	}
	}
}
