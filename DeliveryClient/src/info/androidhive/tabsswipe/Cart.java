package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<CartItem> cartItems;
	private List<Integer> productIds;
	private double totalItems, totalPrice;

	public Cart() {
		cartItems =  new ArrayList<CartItem>();
		productIds = new ArrayList<Integer>();
		totalItems = 0;
		totalPrice = 0;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cI) {
		this.cartItems = cI;
	}

	public double getTotalItems() {
		totalItems = cartItems.size();
		return totalItems;
	}

	public double getTotalPrice() {
		double tp = 0;
		for (CartItem c : cartItems) {
			tp += c.getProduct().getPrice();
		}
		totalPrice = tp;
		return tp;
	}

	public List<Integer> getProductIds() {
		List<Integer> pIds = new ArrayList<Integer>();
		for (CartItem c : cartItems) {
			pIds.add(c.getProduct().getId());
		}
		productIds = pIds;
		return pIds;
	}

	public void addToCart(Product p) {
		productIds = getProductIds();
		
		int position = getProductPosition(p);
		if (position>=0) {
			cartItems.get(position).addOne();
		} else {
			CartItem ci = new CartItem(p, 1);
			cartItems.add(ci);
		}
		OrdersFragment.updateFooter();
	}
	public int getProductPosition(Product p)
	{
		int i = -1;
		for(CartItem ci:cartItems)
		{
			i++;
			if(ci.getProduct().getId() == p.getId())
				return i;
		}
		return -1;
	}
	public String toString()
	{
		String s = "";
		for(CartItem ci:cartItems)
		{
			s += ci.getProduct().getId() + " -> "+ci.getCount();
		}
		return s;
	}
	public int getProductCount(Product p)
	{
		for(CartItem ci:cartItems)
		{
			if(ci.getProduct().getId() == p.getId())
				return ci.getCount();
		}
		return 0;
	}

}
