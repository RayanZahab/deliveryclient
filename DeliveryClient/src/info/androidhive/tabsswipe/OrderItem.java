package info.androidhive.tabsswipe;

public class OrderItem {
	private int id;
	private Product product;
	private int quantity;

	public OrderItem() {

	}

	public OrderItem(Product p, int q) {
		this.setProduct(p);
		this.setQuantity(q);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		double s = getQuantity() * getProduct().getPrice();
		return s;
	}

	public String toString() {
		return product.toString();
	}

	public double getUnitPrice() {
		return product.getPrice();
	}

	public boolean equals(OrderItem o) {
		if (o.getProduct().getId() == this.getProduct().getId()
				&& this.getQuantity() == o.getQuantity())
			return true;
		return false;
	}

}
