package service;

import java.sql.Connection;
import java.util.List;

import dao.CategoryDao;
import entity.Category;
import util.DbUtil;

public class CategoryService {
	public CategoryService() {

	}

	public List<Category> categoryAll() {
		try (Connection connection = DbUtil.getConnection()) {
			CategoryDao categoryDao = new CategoryDao(connection);
			List<Category> list;
			list = categoryDao.findAll();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}