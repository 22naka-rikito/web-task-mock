package service;

import java.sql.Connection;

import dao.UserDao;
import entity.User;
import util.DbUtil;

public class UserService{
	public UserService() {}
	
	public User login(String id, String pass) {
		try (Connection connection = DbUtil.getConnection()) {
			UserDao userDao = new UserDao(connection);
			User user = userDao.findUser(id);
			return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
	}
	
}