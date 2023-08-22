package ua.nure.tkachenko.db.Entity;

import java.util.List;

public class User implements java.io.Serializable{

	private int id;
	private String login;
	private String password;
	private String role;
	private String fullname;
	private List<Team> teams;

	public User() {}
	
	public User(int id, String login, String password, String role, String fullname, List<Team> teams) {
		super();
		this.id = id;
		this.login = login;
		this.password = password;
		this.role = role;
		this.fullname = fullname;
		this.teams = teams;
	}

	public User(String login, String password, String role, String fullname) {
		this.login = login;
		this.password = password;
		this.role = role;
		this.fullname = fullname;
	}
	
	public User(int id, String fullname) {
		super();
		this.id = id;
		this.fullname = fullname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	public String teamsToString() {
		String listTeams = "";
		for (int i = 0; i < teams.size(); i++) {
			listTeams += teams.get(i).getTitle() + "\n";
		}
		return listTeams;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", role=" + role + ", fullname="
				+ fullname + "]";
	}
}
