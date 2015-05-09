package com.mobilife.delivery.client.model;

public class Item {
	// text, text+image, product(text+input number+price),
	// order(text+number+number), address(radiogroupbutton),
	// cart(text+inputnumb+int total+intprice)

	private String type, name, date;
	// txt, txtImg, product,order,cart,address
	private int img, id,price;
	private boolean isDefault;
	private String title;
	private boolean isNew;
	private Integer quantity;
	private double totalPrice;
	private String time,charge, minimum;
	private String photoName;
	private boolean branchIsOpened;
	
	public Item(){}
	
	public Item(int id, String title, Integer quantity, double price, boolean status) {
		super();
		this.setTitle(title);
		this.setNew(status);
		this.id = id;
		this.setQuantity(quantity);
		this.setTotalPrice(price);
	}
	public Item(int id,String title, Integer quantity, double price) {
		super();
		this.id= id;
		this.title = title;
		this.setQuantity(quantity);
		this.setTotalPrice(price);
	}
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	public String toString()
	{
		return getName();
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public void setDate(String date) {
		this.date = date;
	}
	public String getDate() {
		return this.date;
	}

	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public boolean isBranchIsOpened() {
		return branchIsOpened;
	}

	public void setBranchIsOpened(boolean branchIsOpened) {
		this.branchIsOpened = branchIsOpened;
	}
}
