package service;

import java.sql.Connection;

import dao.ProductDao;
import entity.Products;
import util.DbUtil;

public class UpdateService{
	public UpdateService() {
		
	}
	
	public boolean productIdCheck(String id, String idOld) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			Products product = productsDao.checkID(id);
			
			if(product != null) {
				if(product.getId() == Integer.parseInt(idOld)) {
					return false;
				}else {
					return true;
				}
			}else { 
				return false;
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
		return true;
	}
	
	public void updateProduct(Products product, String productIdOld) {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			
			productsDao.updateProduct(product, productIdOld);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
}