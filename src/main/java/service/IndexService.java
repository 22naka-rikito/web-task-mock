package service;

import java.util.List;

import entity.Products;
import entity.User;

public class IndexService {
	private String id;
	private String pass;
	private User loginUser;
	private String[] errorMsg = new String[3];
	private List<Products> list;

	public IndexService(String id, String pass) {
		this.id = id;
		this.pass = pass;
	}

	public String getPath() {
		UserService userservice = new UserService();
		User user = userservice.login(id, pass);
		
		if ("".equals(id) || "".equals(pass)) {
			if ("".equals(id)) {
				errorMsg[1] = "IDは必須です";
			}
			if ("".equals(pass)) {
				errorMsg[2] = "PASSは必須です";
			}
			return "index.jsp";
		} else {
			if (user != null) {
				this.loginUser = user;
				ProductsService productsService = new ProductsService();
				this.list = productsService.findResult("");
				return "menu.jsp";
			} else {
				errorMsg[0] = "IDまたはパスワードが不正です";
				return "index.jsp";
			}
		}
	}
	
	public String[] getErrorMsg() {
		return this.errorMsg;
	}
	
	public User getUser() {
		return this.loginUser;
	}
	
	public List<Products> getList(){
		return this.list;
	}
}