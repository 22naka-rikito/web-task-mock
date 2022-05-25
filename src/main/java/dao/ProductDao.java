package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Products;

public class ProductDao {
	private Connection connection;

	private static final String SQL_FIND_PRODUCT = "SELECT p.product_id, p.name AS product_name"
			+ ", p.price, p.category_id, c.name AS category_name "
			+ "FROM products AS p " + "JOIN categories AS c ON p.category_id = c.id "
			+ "WHERE p.name LIKE ? OR c.name LIKE ?" + "ORDER BY p.id";
	
	private static final String SQL_FIND_PRODUCT_ID = "SELECT p.product_id, p.name AS product_name"
			+ ", p.price, p.category_id, c.name AS category_name, p.description "
			+ "FROM products AS p " + "JOIN categories AS c ON p.category_id = c.id "
			+ "WHERE p.product_id = ?";

	private static final String SQL_FIND_ID = "SELECT product_id FROM products WHERE product_id = ?";
	private static final String INSERT_PRODUCTS = "INSERT INTO products (product_id, category_id, name, price, description) "
			+ "values(?, ?, ?, ?, ?)";
	private static final String DELETE_PRODUCTS ="DELETE FROM products WHERE product_id = ?";

	public ProductDao(Connection connection) {
		this.connection = connection;
	}

	public List<Products> find(String text) {
		List<Products> list = new ArrayList<>();

		try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_PRODUCT)) {
			stmt.setString(1, "%" + text + "%");
			stmt.setString(2, "%" + text + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Products product = new Products(rs.getInt("product_id"), rs.getString("product_name"),
						rs.getInt("price"), rs.getInt("category_id"), rs.getString("category_name"));
				list.add(product);
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Products findId(Integer id) {

		try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_PRODUCT_ID)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			Products product;
			if (rs.next()) {
				product = new Products(rs.getInt("product_id"), rs.getString("product_name"),
						rs.getInt("price"), rs.getInt("category_id"), rs.getString("category_name"), 
						rs.getString("description"));
				return product;
			}
			return null;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean checkID(String productId) {
		try (PreparedStatement stmt = connection.prepareStatement(SQL_FIND_ID)) {
			stmt.setInt(1, Integer.parseInt(productId));
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void insertProduct(Products product) {
		try (PreparedStatement stmt = connection.prepareStatement(INSERT_PRODUCTS)) {
			stmt.setInt(1, product.getId());
			stmt.setInt(2, product.getCategoryId());
			stmt.setString(3, product.getName());
			stmt.setInt(4, product.getPrice());
			stmt.setString(5,product.getDescription());
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deleteProduct(Integer id) {
		try (PreparedStatement stmt = connection.prepareStatement(DELETE_PRODUCTS)) {
			stmt.setInt(1, id);

			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}