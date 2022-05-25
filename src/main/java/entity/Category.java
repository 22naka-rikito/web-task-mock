package entity;

public class Category{
	private Integer id;
	private String name;
	
	public Category(Integer id,String name){
		this.id = id;
		this.name = name;
	}
	
	public Category(Integer id){
		this.id = id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}