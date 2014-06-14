package info.androidhive.tabsswipe;

import android.graphics.Bitmap;

public class Product {
	private int id, price,shop_id;
	private String description,  name;
	private Photo photo;
	private Category category;
	private Unit unit;
	private boolean available;
	private Bitmap bmpPhoto;
	

	public Product(int id, int price, String name, String description,
			Photo photo, Category category, Unit unit, boolean is_available,
			int shop_id) {
		this.id = id;
		this.price = price;
		this.name = name;
		this.description = description;
		this.photo = photo;
		this.category = category;
		this.unit = unit;
		this.setAvailable(is_available);
		this.setShop_id(shop_id);

	}
	public Product(int id)
	{
		this.id=id;
	}
	public ValidationError validate()
	{
		boolean valid = false;
		String msg="";
		if(this.price==0 || this.price<500)
		{
			msg="Invalid price";
		}
		else if(this.name.isEmpty() || this.name.length()<3)
		{
			msg="Invalid name";
		}
		else
		{
			valid =  true;
		}
		return new ValidationError(valid,msg);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Photo getPhoto() {
		return photo;
	}

	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String toString() {
		return this.name;
	}

	public int getShop_id() {
		return shop_id;
	}

	public void setShop_id(int shop_id) {
		this.shop_id = shop_id;
	}


	public Bitmap getBmpPhoto() {
		return bmpPhoto;
	}


	public void setBmpPhoto(Bitmap bmpPhoto) {
		this.bmpPhoto = bmpPhoto;
	}

}
