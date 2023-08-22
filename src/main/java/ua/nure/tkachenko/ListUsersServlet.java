package ua.nure.tkachenko;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

@WebServlet("/listUsers")
public class ListUsersServlet extends HttpServlet{
	
	private static final Logger logger = LogManager.getLogger(ListUsersServlet.class);
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null) {
		//	System.out.println("ListUsersServlet");
			List<User> users = DBManager.getAllUsers();
			for (int i = 0; i < users.size(); i++) {
				User curUser = users.get(i);
				List<Team> teamsCurUser = DBManager.getTeamsByUserID(Integer.toString(curUser.getId()));
				curUser.setTeams(teamsCurUser);
				users.set(i, curUser);
			}
			logger.info("list users");
			request.setAttribute("users", users);
			request.getRequestDispatcher("listUsers.jsp")
			.forward(request, response);
    	} else {
    		response.sendRedirect("index.jsp");
    	}
	}
	
//	@Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//		try {
//        	String id = request.getParameter("id");         
//        	System.out.println("delete user bu id ==> " + id);
//        	DBManager.deleteUserById(id);
//        	response.sendRedirect("listUsers");
//    	}
//    	catch(Exception ex) {
//        	ex.printStackTrace();
//     	}
//	}
}
