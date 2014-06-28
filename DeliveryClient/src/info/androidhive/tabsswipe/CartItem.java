package info.androidhive.tabsswipe;

public class CartItem {
	private Product product;
	private int count;
	public CartItem(Product p,int c)
	{
		this.product = p;
		this.count = c;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void addOne()
	{
		this.count = this.count +1;
	}
	public void rmvOne()
	{
		this.count = this.count -1;
	}

}
