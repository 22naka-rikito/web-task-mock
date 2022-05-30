package entity;

public class Products {
	private Integer id;
	private String name;
	private Integer price;
	private Category category;
	private String description;

	public Products(Integer id) {
		this.id = id;
	}

	public Products(Integer id, String name, Integer price, Integer categoryID, String categoryName) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = new Category(categoryID, categoryName);
	}

	public Products(Integer id, String name, Integer price, Integer categoryID, String categoryName,
			String description) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = new Category(categoryID, categoryName);
		this.description = description;
	}

	
	//インサートで使うやつ
	public Products(Integer id, String name, Integer price, String description, Integer categoryID) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = new Category(categoryID);

	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPrice() {
		return this.price;
	}

	public void setDscription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategory() {
		return this.category;
	}

	public void setCategoryNeme(String categoryName) {
		this.category.setName(categoryName);
	}

	public String getCategoryName() {
		return this.category.getName();
	}

	public void setCategoryId(Integer categoryId) {
		this.category.setId(categoryId);
	}

	public Integer getCategoryId() {
		return this.category.getId();
	}

}