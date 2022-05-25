package entity;

public class User{
	private String id;
	private String pass;
	private String name;
	
	public User(String id, String pass, String name) {
		this.id = id;
		this.pass = pass;
		this.name = name;
	}
	
	//UserID
	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return this.id;
	}
	
	//password
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	public String getPass() {
		return this.pass;
	}
	
	//UserName
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}