package com.mobilive.delivery.client.model;

public class Category {
	private int id, shopId;
	private String name;
	private boolean active;

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

}
