package ua.nure.tkachenko.db.Entity;

public class Team implements java.io.Serializable{

	private int id;
	private String title;
	
	public Team() {}
	
	public Team(String title) {
		this.title = title;
	}
	
	public Team(int id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "Team [id=" + id + ", title=" + title + "]";
	}

}
