package service;

import java.sql.Connection;
import java.util.List;

import dao.ProductDao;
import entity.Category;
import entity.Products;
import util.DbUtil;

public class UpdateService{
	private String productIdOld;
	private String productId;
	private String name;
	private String price;
	private String description;
	private String categoryId;
	private List<Products> productsList;
	private String[] errorMsg = new String[4];
	
	public UpdateService(String productId, String name, String price, String categoryId, String description, String productIdOld) {
		this.productIdOld = productIdOld;
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.categoryId = categoryId;
	}
	
	public Products findProduct(Integer productId) {
		ProductsService productsService = new ProductsService();
		return productsService.findProduct(productId);
	}
	
	public void updateProduct() {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			Products product = new Products(Integer.parseInt(productId), name, Integer.parseInt(price), description,
					Integer.parseInt(categoryId));
			productsDao.updateProduct(product, productIdOld);
		} catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public List<Products> findProducts() {
		ProductsService productsService = new ProductsService();
		this.productsList = productsService.findResult("");
		return this.productsList;
	}
	
	public String getPath(String btn) {
		if ("update".equals(btn)) {
			if ((!"".equals(this.productId)) && (!"".equals(this.name)) && (!"".equals(this.price))) {
				ProductsService productsService = new ProductsService();
				Products product = productsService.findProduct(Integer.parseInt(productId));
				
				if (product != null) {
					if(product.getId() == Integer.parseInt(productIdOld)) {
						return "menu.jsp";
					}else {
						errorMsg[3] = "商品IDが重複しています";
						return "update.jsp";
					}
				} else {
					return "menu.jsp";
				}
			} else {
				if (productId.equals("")) {
					errorMsg[0] = "商品IDは必須です";
				}
				if (name.equals("")) {
					errorMsg[1] = "商品名は必須です";
				}
				if (price.equals("")) {
					errorMsg[2] = "単価は必須です";
				}
				return "update.jsp";
			}
		}else if("back".equals(btn)) {
			return "menu.jsp";
		}else {
			return null;
		}
	}
	
	public List<Category> categoryAll() {
		CategoryService categoryService = new CategoryService();
		return categoryService.categoryAll();
	}
	
	public String[] getErrorMsg() {
		return this.errorMsg;
	}
}