package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Category;

public class CategoryDao{
	private Connection connection;
	
	public CategoryDao(Connection connection) {
		this.connection = connection;
	}
	
	private static final String SQL_FIND_CATEGORY = "SELECT id, name FROM categories";
	
	public List<Category> findAll() {
		List<Category> list = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_CATEGORY)) {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Category category = new Category(rs.getInt("id"), rs.getString("name"));
				list.add(category);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}