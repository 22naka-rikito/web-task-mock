package service;

import java.sql.Connection;
import java.util.List;

import dao.ProductDao;
import entity.Category;
import entity.Products;
import util.DbUtil;

public class InsertService {
	private String productId;
	private String name;
	private String price;
	private String description;
	private String categoryId;
	private List<Products> productsList;
	private String[] errorMsg = new String[4];

	public InsertService() {

	}

	public InsertService(String productId, String name, String price, String categoryId, String description) {
		this.productId = productId;
		this.name = name;
		this.price = price;
		this.description = description;
		this.categoryId = categoryId;
	}

	public void insertProduct() {
		try (Connection connection = DbUtil.getConnection()) {
			ProductDao productsDao = new ProductDao(connection);
			Products product = new Products(Integer.parseInt(productId), name, Integer.parseInt(price), description,
					Integer.parseInt(categoryId));
			productsDao.insertProduct(product);
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
		if ("register".equals(btn)) {
			if ((!"".equals(this.productId)) && (!"".equals(this.name)) && (!"".equals(this.price))) {
				ProductsService productsService = new ProductsService();
				Products product = productsService.findProduct(Integer.parseInt(productId));
				if (product != null) {
					this.errorMsg[3] = "商品IDが重複しています";
					return "insert.jsp";
				} else {
					return "menu.jsp";
				}
			} else {
				if (productId.equals("")) {
					this.errorMsg[0] = "商品IDは必須です";
				}
				if (name.equals("")) {
					this.errorMsg[1] = "商品名は必須です";
				}
				if (price.equals("")) {
					this.errorMsg[2] = "単価は必須です";
				}
				return "insert.jsp";
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