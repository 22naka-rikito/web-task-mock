package service;

import java.util.List;

import entity.Category;
import entity.Products;

public class MenuService {
	private String btn;
	private String msg;
	private List<Products> productsList;

	public MenuService(String btn) {
		this.btn = btn;
	}

	public List<Products> findProducts(String findWord) {
		ProductsService productsService = new ProductsService();
		productsList = productsService.findResult(findWord);
		this.msg = (this.productsList.size() == 0) ? "検索結果がありません" : null;
		return this.productsList;
	}

	public List<Category> categoryAll() {
		CategoryService categoryService = new CategoryService();
		return categoryService.categoryAll();
	}

	public Products findProduct(Integer productId) {
		ProductsService productsService = new ProductsService();
		return productsService.findProduct(productId);
	}

	public String getPath() {
		if ("find".equals(this.btn)) {
			return "menu.jsp";
		} else if ("insert".equals(this.btn)) {
			return "insert.jsp";
		} else if ("detail".equals(this.btn)) {
			return "detail.jsp";
		} else {
			return null;
		}
	}

	public String getMsg() {
		return this.msg;
	}
}