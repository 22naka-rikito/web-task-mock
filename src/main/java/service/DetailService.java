package service;

import java.util.List;

import entity.Category;
import entity.Products;

public class DetailService {
	private String path;

	public List<Products> delete(String productId) {
		ProductsService productsService = new ProductsService();
		productsService.deleteProduct(Integer.parseInt(productId));
		return productsService.findResult("");
	}

	public Products findProduct(Integer productId) {
		ProductsService productsService = new ProductsService();
		this.path = "update.jsp";
		return productsService.findProduct(productId);
	}

	public List<Category> categoryAll() {
		CategoryService categoryService = new CategoryService();
		return categoryService.categoryAll();
	}

	public String getPath(String btn, String productId) {
		if("delete".equals(btn)) {
			ProductsService productsService = new ProductsService();
			Products product = productsService.findProduct(Integer.parseInt(productId));
			if (product != null) {
				return "menu.jsp";
			}
			return "detail.jsp";
		}else if("edit".equals(btn)) {
			return "update.jsp";
		}
		return null;
	}
}