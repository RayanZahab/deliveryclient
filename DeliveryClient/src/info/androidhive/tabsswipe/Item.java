package info.androidhive.tabsswipe;

public class Item {
	// text, text+image, product(text+input number+price),
	// order(text+number+number), address(radiogroupbutton),
	// cart(text+inputnumb+int total+intprice)

	private String type, name;
	// txt, txtImg, product,order,cart,address
	private int img, id,price;

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
}
