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


@WebServlet("/excludeUserFromTeamServlet")
public class ExcludeUserFromTeamServlet extends HttpServlet {
	
	private static final Logger logger = LogManager.getLogger(ExcludeUserFromTeamServlet.class);
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
		try {
			String idUser = request.getParameter("user_id");
			String idTeam = request.getParameter("team_id");
			if (idTeam.equals("1")) {
				logger.error("user tried to exclude admin fgom team Administrator");    
				response.sendRedirect("editUser?id=" + idUser);
				return;
			}
		//	System.out.println("excludeUserFromTeamServlet team id ==> " + idTeam);
			logger.info("from team by id ==>" + idTeam + " exclude user by id ==>" + idUser);
			DBManager.excludeUserFromTeam(idUser, idTeam);      
			response.sendRedirect("editUser?id=" + idUser);
//            User user = DBManager.getUserById(idUser);
//            user.setTeams(DBManager.getTeamsByUserID(idUser));
//            if(user != null) {
//                request.setAttribute("editUser", user);
//                getServletContext().getRequestDispatcher("/editUser.jsp").forward(request, response);
//            }
    	}
    	catch(Exception ex) {
        	ex.printStackTrace();
     	}
	}
}