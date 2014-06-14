package info.androidhive.tabsswipe;

import android.app.Application;
import android.content.SharedPreferences;

public class DeliveryClient extends Application {
	private String token, orderStatus;
	private int shopId = 0, branchId = 0, categoryId = 0, productId = 0,
			orderId = 0, userId = 0;
	private boolean admin, prep, delivery, keepme;
	public MyJs.TransparentProgressDialog loader;
	
	public DeliveryClient() {
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getShopId() {
		return shopId;
	}

	public void setShopId(int shopId) {
		this.shopId = shopId;
	}

	public void setGlobals() {

		SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
		this.token = settings.getString("token", null);
		this.shopId = settings.getInt("shopId", 0);
		this.admin = settings.getBoolean("admin", false);
		this.prep = settings.getBoolean("preparer", false);
		this.delivery = settings.getBoolean("delivery", false);
		this.keepme = settings.getBoolean("isChecked", false);
	}

	public void clear(String current) {
		if (current.equals("listing")) {
			branchId = 0;
			categoryId = 0;
			productId = 0;
			orderId = 0;
			orderStatus = null;
			userId = 0;
		} else if (current.equals("categories") || current.equals("branch")) {
			categoryId = 0;
			productId = 0;
			orderId = 0;
			orderStatus = null;
			userId = 0;
		} else if (current.equals("products")) {
			productId = 0;
			orderId = 0;
			orderStatus = null;
			userId = 0;
		} else if (current.equals("product")) {
			orderId = 0;
			orderStatus = null;
			userId = 0;
		} else if (current.equals("branch")) {
			categoryId = 0;
			productId = 0;
			orderId = 0;
			orderStatus = null;
		}else if (current.equals("order")) {
			branchId = 0;
			categoryId = 0;
			productId = 0;
			userId = 0;
		}else if (current.equals("orders")) {
			branchId = 0;
			categoryId = 0;
			productId = 0;
			orderId = 0;
			userId = 0;
		}else if (current.equals("user")) {
			branchId = 0;
			categoryId = 0;
			productId = 0;
			orderId = 0;
			orderStatus = null;
		}
	}

	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isPrep() {
		return prep;
	}

	public void setPrep(boolean prep) {
		this.prep = prep;
	}

	public boolean isKeepme() {
		return keepme;
	}

	public void setKeepme(boolean keepme) {
		this.keepme = keepme;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public MyJs.TransparentProgressDialog getLoader() {
		return loader;
	}

	public void setLoader(MyJs.TransparentProgressDialog loader) {
		this.loader = loader;
	}
}
