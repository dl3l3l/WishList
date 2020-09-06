package model;

public class Category {
	private int categorynum;
	private String id;
	private String categoryname;
	public int getCategorynum() {
		return categorynum;
	}
	public void setCategorynum(int categorynum) {
		this.categorynum = categorynum;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	@Override
	public String toString() {
		return "Category [categorynum=" + categorynum + ", id=" + id + ", categoryname=" + categoryname + "]";
	}
	
}
