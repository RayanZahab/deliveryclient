package com.mobilive.delivery.client.model;

public class Category {
	private int id, shopId;
	private String name;
	private boolean active;
	private String photoName;

	public Category(int id, String name, boolean is_active, int shopId) {
		this.setId(id);
		this.setName(name);
		this.setActive(is_active);
		this.setShopId(shopId);
	}
	public Category(int id) {
		this.setId(id);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	
    public void setPhotoWithParse(String photo) {
		if(photo!=null)
			this.photoName = parsePhoto(photo);
	}
	
	private String parsePhoto(String error){
		String res = "";
		int index1= error.indexOf("{\"url\":\"");
		int index2= error.indexOf("\"}");
		if(index1!=-1 && index2!=-1){
			res = error.substring(index1+"{\"url\":\"".length(),index2);
			res = res.replaceAll("\\\\", "");
		}else
			res = null;
		return res;
	}

}
