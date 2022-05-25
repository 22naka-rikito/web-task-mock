package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.User;

public class UserDao{
	private Connection connection;
	private static final String SQL_SELECT_USER = "SELECT * FROM users WHERE login_id = ?";
	
	public UserDao(Connection connection) {
	    this.connection = connection;
	}
	
	public User selectUser(String id){
		try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_USER)) {
			stmt.setString(1, id);
	        ResultSet rs = stmt.executeQuery();
	        User user;

	        if (rs.next()) {
	        	user = new User(rs.getString("login_id"), rs.getString("password"), rs.getString("name"));
	        	return user;
	        }
	        return null;
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
	
	public User findUser(String id) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_SELECT_USER)) {
			stmt.setString(1, id);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	User user = new User(rs.getString("login_id"), rs.getString("password"), rs.getString("name"));
	        	return user;
	        }
	        return null;
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
}