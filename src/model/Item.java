package model;


public class Item {
	private int itemnum;
	private int categorynum;
	private String id;
	private String itemname;
	private int price;
	private String url;
	private String memo;
	private int purchase;
	
	public int getItemnum() {
		return itemnum;
	}
	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}
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
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public int getPurchase() {
		return purchase;
	}
	public void setPurchase(int purchase) {
		this.purchase = purchase;
	}
	@Override
	public String toString() {
		return "Item [itemnum=" + itemnum + ", categorynum=" + categorynum + ", id=" + id + ", itemname=" + itemname
				+ ", price=" + price + ", url=" + url + ", memo=" + memo + ", purchase=" + purchase + "]";
	}
	

}
