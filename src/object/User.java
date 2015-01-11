package object;

public class User {
	private String username, pass;
	private boolean admin;

	public User(String username, String pass, boolean admin) {
		this.username = username;
		this.pass = pass;
		this.admin = admin;
	}

	public User(User usr) {
		this.username = usr.getUsername();
		this.pass = usr.getPass();
		this.admin = usr.admin();
	}

	public String getUsername() {
		return username;
	}

	public boolean admin() {
		return admin;
	}

	public String getPass() {
		return pass;
	}
}
