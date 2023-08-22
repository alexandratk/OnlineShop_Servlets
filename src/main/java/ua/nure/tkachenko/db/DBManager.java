package ua.nure.tkachenko.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.tkachenko.db.Entity.Team;
import ua.nure.tkachenko.db.Entity.User;

public class DBManager {
	
	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException ex) {
			throw new IllegalStateException("Cannot load a driver", ex);
		}
	}
	
	private static final String CONNECTION_URL = "jdbc:sqlserver://localhost;databaseName=lab2java;encrypt=false;user=test;password=123";
	private static final String FIND_ALL_USERS = "SELECT * FROM [dbo].[User] ORDER BY id";
	private static final String FIND_ALL_TEAMS = "SELECT * FROM [dbo].[Team] ORDER BY id";
	private static final String FIND_USER_BY_LOGIN = "SELECT * FROM [dbo].[User] WHERE login=? ORDER BY id";
	private static final String FIND_USER_BY_ID = "SELECT * FROM [dbo].[User] WHERE id=? ORDER BY id";
	private static final String FIND_USERS_BY_TEAM_ID = "SELECT * FROM [dbo].[User], [dbo].[User_Team] WHERE [dbo].[User].id=[dbo].[User_Team].id_user AND [dbo].[User_Team].id_team=? ORDER BY id_user";
	private static final String FIND_TEAMS_BY_USER_ID = "SELECT * FROM [dbo].[Team], [dbo].[User_Team] WHERE [dbo].[Team].id=[dbo].[User_Team].id_team AND [dbo].[User_Team].id_user=? ORDER BY title";
	private static final String FIND_TEAM_BY_ID = "SELECT * FROM [dbo].[Team] WHERE id=? ORDER BY id";
	private static final String DELETE_USER_BY_ID = "DELETE FROM [dbo].[User] WHERE id=?";
	private static final String DELETE_TEAM_BY_ID = "DELETE FROM [dbo].[Team] WHERE id=?";
	private static final String DELETE_USER_FROM_TEAM = "DELETE FROM [dbo].[User_Team] WHERE id_user=? AND id_team=?";
	private static final String INSERT_USER = "INSERT INTO [dbo].[User](login, password, role, full_name) VALUES (?, ?, ?, ?)";
	private static final String INSERT_TEAM = "INSERT INTO [dbo].[Team](title) VALUES (?)";
	private static final String ADD_USER_IN_TEAM = "INSERT INTO [dbo].[User_Team](id_user, id_team) VALUES (?, ?)";
	private static final String SELECT_USER_FROM_TEAM = "SELECT * FROM [dbo].[User_Team] WHERE id_user=? AND id_team=?";
	private static final String UPDATE_USER = "UPDATE [dbo].[User] SET full_name = ? WHERE Id = ?";
	private static final String UPDATE_TEAM = "UPDATE [dbo].[Team] SET title = ? WHERE Id = ?";


	private static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONNECTION_URL);
	}
	
	private static User extractUser(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setLogin(rs.getString("login"));
		user.setPassword(rs.getString("password"));
		user.setRole(rs.getString("role"));
		user.setFullname(rs.getString("full_name"));
		return user;
	}
	
	private static Team extractTeam(ResultSet rs) throws SQLException {
		Team team = new Team();
		team.setId(rs.getInt("id"));
		team.setTitle(rs.getString("title"));
		return team;
	}
		
	public static User getUser(String login) {
		User user = null;
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(FIND_USER_BY_LOGIN);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				user = extractUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return user;
	}
	
	public static User getUserById(String id) {
		User user = null;
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(FIND_USER_BY_ID);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				user = extractUser(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return user;
	}

	public static List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try (Connection con = getConnection()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(FIND_ALL_USERS);
			
			while (rs.next()) {
				users.add(extractUser(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

	public static void deleteUserById(String id) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(DELETE_USER_BY_ID);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}	
	}

	public static void insertUser(User newUser) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(INSERT_USER);
			pstmt.setString(1, newUser.getLogin());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getRole());
			pstmt.setString(4, newUser.getFullname());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void updateUser(User user) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_USER);
			pstmt.setString(1, user.getFullname());
			pstmt.setString(2, Integer.toString(user.getId()));
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static List<Team> getAllTeams() {
		List<Team> teams = new ArrayList<Team>();
		try (Connection con = getConnection()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(FIND_ALL_TEAMS);
			while (rs.next()) {
				Team team = extractTeam(rs);
				teams.add(extractTeam(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teams;
	}
	
	public static List<Team> getAllTeamsWithoutAdmin() {
		List<Team> teams = new ArrayList<Team>();
		try (Connection con = getConnection()){
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(FIND_ALL_TEAMS);
			while (rs.next()) {
				Team team = extractTeam(rs);
				if (team.getId() != 1) {
					teams.add(extractTeam(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teams;
	}

	public static void deleteTeamById(String id) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(DELETE_TEAM_BY_ID);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static void insertTeam(Team newTeam) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(INSERT_TEAM);
			pstmt.setString(1, newTeam.getTitle());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static Team getTeamById(String id) {
		Team team = null;
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(FIND_TEAM_BY_ID);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				team = extractTeam(rs);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return team;
	}

	public static void updateTeam(Team team) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(UPDATE_TEAM);
			pstmt.setString(1, team.getTitle());
			pstmt.setString(2, Integer.toString(team.getId()));
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public static List<User> getUserByTeamId(String id) {
		List<User> users = new ArrayList<User>();
		try (Connection con = getConnection()){
			PreparedStatement pstmt = con.prepareStatement(FIND_USERS_BY_TEAM_ID);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				users.add(extractUser(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public static List<Team> getTeamsByUserID(String id) {
		List<Team> teams = new ArrayList<Team>();
		try (Connection con = getConnection()){
			PreparedStatement pstmt = con.prepareStatement(FIND_TEAMS_BY_USER_ID);
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				teams.add(extractTeam(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return teams;
	}


	public static void excludeUserFromTeam(String idUser, String idTeam) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(DELETE_USER_FROM_TEAM);
			pstmt.setString(1, idUser);
			pstmt.setString(2, idTeam);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}

	public static boolean checkUserInTeam(String idUser, String idTeam) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(SELECT_USER_FROM_TEAM);
			pstmt.setString(1, idUser);
			pstmt.setString(2, idTeam);
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next()) {
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static void addUserInTeam(String idUser, String idTeam) {
		try (Connection con = getConnection()) {
			PreparedStatement pstmt = con.prepareStatement(ADD_USER_IN_TEAM);
			pstmt.setString(1, idUser);
			pstmt.setString(2, idTeam);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

}
