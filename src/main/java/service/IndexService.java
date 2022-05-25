package service;

import entity.User;

public class IndexService {
	private String id;
	private String pass;
	private User loginUser;
	private String[] errorMsg = new String[3];

	public IndexService(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}

	public boolean login() {
		UserService userservice = new UserService();
		User user = userservice.login(id, pass);
		if ("".equals(id) || "".endsWith(pass)) {
			if ("".equals(id)) {
				errorMsg[1] = "IDは必須です";
			}
			if ("".equals(pass)) {
				errorMsg[2] = "PASSは必須です";
			}
			return false;
			
		} else {
			if (user != null) {
				this.loginUser = user;
				return true;
			} else {
				errorMsg[0] = "IDまたはパスワードが不正です";
				return false;
			}
		}
	}
	
	public String[] getErrorMsg() {
		return errorMsg;
	}
	
	public User getUser() {
		return loginUser;
	}
}