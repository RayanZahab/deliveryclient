package info.androidhive.tabsswipe;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.util.Log;

public class deliveryclient extends Application {
	private String token, orderStatus;
	private int shopId = 0, branchId = 0, categoryId = 0, productId = 0,
			orderId = 0, userId = 0 , addressId =0;
	private boolean admin, prep, delivery, keepme;
	public MyJs.TransparentProgressDialog loader;
	public Activity current;
	private UncaughtExceptionHandler defaultUEH;
	private Cart myCart;
	private List<Integer> myCartIds = new ArrayList<Integer>();
	private ArrayList<Country> countries ;
	public void emptyCart()
	{
		myCart =  new Cart();
		myCartIds = new ArrayList<Integer>();
	}
	public deliveryclient() {
		//defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		//Thread.setDefaultUncaughtExceptionHandler(_unCaughtExceptionHandler);
		myCart =  new Cart();
	}

	// handler listener
	/*private Thread.UncaughtExceptionHandler _unCaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// here I do logging of exception to a db
			PendingIntent myActivity = PendingIntent.getActivity(
					getBaseContext(), 192837, new Intent(getBaseContext(),
							LoginActivity.class), PendingIntent.FLAG_ONE_SHOT);

			AlarmManager alarmManager;
			alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 15000,
					myActivity);
			SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
			settings.edit().clear().commit();
			System.exit(2);

			defaultUEH.uncaughtException(thread, ex);

		}
	};
*/
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
		this.userId = settings.getInt("id", 0);
		this.setAddressId(settings.getInt("addressId", 0));
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
		} else if (current.equals("order")) {
			branchId = 0;
			categoryId = 0;
			productId = 0;
			userId = 0;
		} else if (current.equals("orders")) {
			branchId = 0;
			categoryId = 0;
			productId = 0;
			orderId = 0;
			userId = 0;
		} else if (current.equals("user")) {
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

	public Cart getMyCart() {
		return myCart;
	}

	public void setMyCart(Cart myCart) {
		this.myCart = myCart;
	}
	
	public List<Integer> getMyCartIds() {
		return myCartIds;
	}

	public void setMyCartIds(List<Integer> myIds) {
		this.myCartIds = myIds;
	}

	public Fragment getCurrentFragment() {
		return MainActivity.getVisibleFragment();
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public ArrayList<Country> getCountries() {
		return countries;
	}
	public void setCountries(ArrayList<Country> countries) {
		this.countries = countries;
	}

}
