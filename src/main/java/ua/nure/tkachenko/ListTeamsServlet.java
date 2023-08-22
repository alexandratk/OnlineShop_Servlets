package ua.nure.tkachenko;

import java.io.IOException;
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

@WebServlet("/listTeams")
public class ListTeamsServlet extends HttpServlet{
	
	private static final Logger logger = LogManager.getLogger(ListTeamsServlet.class);
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	User currentUser = (User) session.getAttribute("currentUser");
    	if (currentUser != null) {
		//	System.out.println("ListTeamsServlet");
			List<Team> teams = DBManager.getAllTeams();
			logger.info("list teams");
			request.setAttribute("teams", teams);
			request.getRequestDispatcher("listTeams.jsp")
			.forward(request, response);
		} else {
			response.sendRedirect("index.jsp");
		}
	}
}