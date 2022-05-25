package service;

import java.sql.Connection;

import dao.ProductDao;
import entity.Products;
import util.DbUtil;

public class InsertService{
	public InsertService() {
		
	}
	
	public boolean productIdCheck(String id) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			
			return productsDao.checkID(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public void insertProduct(Products product) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			
			productsDao.insertProduct(product);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
}