package model;
public class user{
	private String id;
	private String username;
	private String pwd;
	public user(String id,String name,String pwd){
		this.id = id;
		this.username = name;
		this.pwd = pwd;
	}
	public user(String id,String name){
		this.id = id;
		this.username = name;
		this.pwd ="";
	}	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String toString(){
		return id+","+username+(pwd.equals("")?"":",")+pwd;
	}
}