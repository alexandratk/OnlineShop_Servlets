package ua.nure.tkachenko;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ua.nure.tkachenko.db.DBManager;
import ua.nure.tkachenko.db.Entity.User;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/Login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final Logger logger = LogManager.getLogger(LoginServlet.class);

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    session.removeAttribute("currentUser");
	    logger.info("logout");
	    response.sendRedirect("index.jsp");
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		//System.out.println("login ==> " + login);
	    logger.info("login ==>" + login);
	    User user = DBManager.getUser(login);
	    
	    if (user != null && user.getPassword().equals(password)) {
	    	logger.info("successful authorization");
	    	HttpSession session = request.getSession();
	    	session.setAttribute("currentUser", user);
	    	
	    	response.sendRedirect("listUsers");
	    	return;
	    }
	    logger.info("failed authorization");
	    response.sendRedirect("index.jsp?errorMessage=No%20such%20login/passsword");
	}
}
