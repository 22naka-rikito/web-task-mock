package service;

import java.sql.Connection;
import java.util.List;

import dao.ProductDao;
import entity.Products;
import util.DbUtil;

public class ProductsService {
	private Integer size;

	public ProductsService() {

	}

	public List<Products> findResult(String findText) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			List<Products> list;
			list = productsDao.find(findText);
			size = list.size();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Products findProduct(Integer id) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			Products product;
			product = productsDao.findId(id);
			return product;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteProduct(Integer id) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			productsDao.deleteProduct(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getSize() {
		return this.size;
	}

}