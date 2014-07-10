package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OrdersFragment extends ParentFragment {

	static int depth = 0;
	private static List<String> sequence = new ArrayList<String>();
	static ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	ArrayList<Business> businesses = new ArrayList<Business>();
	ArrayList<Shop> shops = new ArrayList<Shop>();
	ArrayList<Branch> branches = new ArrayList<Branch>();
	ArrayList<Category> categories = new ArrayList<Category>();
	ArrayList<Product> products = new ArrayList<Product>();
	static int countryId = 0, cityId = 0, areaId = 0, shopId = 0, branchId = 0,
			categoryId = 0, productId = 0;
	static ArrayList<Item> mylist = new ArrayList<Item>();
	static Activity currentActivity;
	static View view;
	static int fragmentId;
	static android.app.FragmentManager fragmentManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		sequence.add("business");
		sequence.add("country");
		sequence.add("city");
		sequence.add("area");
		sequence.add("shops");
		sequence.add("branches");
		sequence.add("categories");
		sequence.add("products");
		sequence.add("info");
		currentActivity = getActivity();

		view = inflater.inflate(R.layout.fragment_orders, container, false);
		mylist = new ArrayList<Item>();
		fragmentId = this.getId();
		fragmentManager = getFragmentManager();

		updateFooter();
		Button buttonOne = (Button) view.findViewById(R.id.back);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				goUp();
			}
		});
		Button submit = (Button) view.findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				move();
			}
		});
		mylist = null;
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((deliveryclient) currentActivity.getApplication()).setCurrentFragment(this);
		getList(sequence.get(0), 0);
		Log.d("ray", "onActivityCreated");
	}

	@Override
	public void onResume() {
		super.onResume();
		
	}

	public void goUp() {
		if (depth > 0) {
			depth--;
			int id = 0;
			switch (depth) {
			case 0:
				id = 0;
				break;
			case 1:
				id = 0;
				break;
			case 2:
				id = countryId;
				break;
			case 3:
				id = cityId;
				break;
			case 4:
				id = areaId;
				break;
			case 5:
				id = shopId;
				break;
			case 6:
				id = branchId;
				break;
			case 7:
				id = categoryId;
				break;
			}
			getList(sequence.get(depth), id);
		}

	}

	public void getList(String type, int id) {
		mylist = new ArrayList<Item>();
		Log.d("ray","type: "+type);
		if (type.equals("business")) {
			countryId = 0;
			cityId = 0;
			areaId = 0;
			shopId = 0;
			branchId = 0;
			categoryId = 0;
			productId = 0;
			depth =0;
			getBusinesses();
		} else if (type.equals("country")) {
			countryId = cityId = areaId = shopId = branchId = categoryId = 0;
			getCountries();
		} else if (type.equals("city")) {
			cityId = areaId = shopId = branchId = categoryId = 0;
			countryId = id;
			getCities(id);
		} else if (type.equals("area")) {
			areaId = shopId = branchId = categoryId = 0;
			cityId = id;
			getAreas(id);
		} else if (type.equals("shops")) {
			shopId = branchId = categoryId = 0;
			areaId = id;
			getShops(id);
		} else if (type.equals("branches")) {
			branchId = categoryId = 0;
			shopId = id;
			getBranches(id);
		} else if (type.equals("categories")) {
			categoryId = 0;
			branchId = id;
			getCategories(id);
		} else if (type.equals("products")) {
			categoryId = id;
			getProducts(branchId, id);
		}

	}

	public void move() {
		
		CartFragment fh = new CartFragment();
		android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.replace(fragmentId, fh);
		ft.commit();
	}

	private void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}

	public void getCountries() {
		countries = ((deliveryclient) currentActivity.getApplication()).getCountries();
		Log.d("rays","MYCOUNT"+countries.size());
		mylist = new ArrayList<Item>();
		for (Country myCountry : countries) {
			Item it = new Item();
			it.setName(myCountry.toString());
			it.setType("txt");
			it.setId(myCountry.getId());
			mylist.add(it);
		}
		updateList();		
	}


	public void getCities(int CountryId) {
		cities = countries.get(CountryId).getCities();
		for (City myCity : cities) {
			Item it = new Item();
			it.setName(myCity.toString());
			it.setType("txt");
			it.setId(myCity.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void getAreas(int CityId) {
		areas = cities.get(CityId).getAreas();
		for (Area myArea : areas) {
			Item it = new Item();
			it.setName(myArea.toString());
			it.setType("txt");
			it.setId(myArea.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void getShops(int areaId) {
		String serverURL = new myURL("shops", "areas", areaId, 30).getURL();
		MyJs mjs = new MyJs("setShops", currentActivity,
				((deliveryclient) currentActivity.getApplication()), "GET",
				true, true);
		mjs.execute(serverURL);
	}

	public void setShops(String s, String error) {
		shops = new APIManager().getShopsByArea(s);
		mylist = new ArrayList<Item>();
		for (Shop myShop : shops) {
			Item it = new Item();
			it.setName(myShop.toString());
			it.setType("txt");
			it.setId(myShop.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void getBranches(int shopId) {
		String serverURL = new myURL("branches", "shops", shopId, 30).getURL();
		MyJs mjs = new MyJs("setBranches", currentActivity,
				((deliveryclient) currentActivity.getApplication()), "GET");
		mjs.execute(serverURL);
	}

	public void setBranches(String s, String error) {
		branches = new APIManager().getBranchesByShop(s);
		mylist = new ArrayList<Item>();
		for (Branch myBranch : branches) {
			Item it = new Item();
			it.setName(myBranch.toString());
			it.setType("txt");
			it.setId(myBranch.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void getCategories(int branchId) {
		String serverURL = new myURL("categories", "branches", branchId, 30)
				.getURL();
		MyJs mjs = new MyJs("setCategories", currentActivity,
				((deliveryclient) currentActivity.getApplication()), "GET");
		mjs.execute(serverURL);
	}

	public void setCategories(String s, String error) {
		categories = new APIManager().getCategoriesByBranch(s);
		mylist = new ArrayList<Item>();
		for (Category myCat : categories) {
			Item it = new Item();
			it.setName(myCat.toString());
			it.setType("txt");
			it.setId(myCat.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void getProducts(int branchId, int categoryId) {
		String serverURL = new myURL("items", "branches/" + branchId
				+ "/categories", categoryId, 30).getURL();
		MyJs mjs = new MyJs("setProducts", currentActivity,
				((deliveryclient) currentActivity.getApplication()), "GET");
		mjs.execute(serverURL);
	}

	public void setProducts(String s, String error) {
		products = new APIManager().getItemsByCategoryAndBranch(s);
		mylist = new ArrayList<Item>();
		for (Product myProduct : products) {
			Item it = new Item();
			it.setName(myProduct.toString());
			it.setType("product");
			it.setPrice(myProduct.getPrice());
			it.setId(myProduct.getId());
			mylist.add(it);
		}
		updateList();
	}

	public static void getBusinesses() {
		android.app.Fragment myFragment = fragmentManager.findFragmentById(fragmentId);
		if (myFragment.isVisible())  {
			depth = 0;
			countryId = 0;
			cityId = 0;
			areaId = 0;
			shopId = 0;
			branchId = 0;
			categoryId = 0;
			productId = 0;
			String serverURL = new myURL("businesses", null, 0, 30).getURL();
			MyJs mjs = new MyJs("setBusinesses", currentActivity,
					((deliveryclient) currentActivity.getApplication()), "GET",
					true, true);
			mjs.execute(serverURL);
		}
	}

	public void setBusinesses(String s, String error) {
		businesses = new APIManager().getBusinesses(s);
		mylist = new ArrayList<Item>();
		for (Business myCountry : businesses) {
			Item it = new Item();
			it.setName(myCountry.toString());
			it.setType("txt");
			it.setId(myCountry.getId());
			mylist.add(it);
		}
		updateList();
	}

	

	public void updateList() {
		final ListView listView = (ListView) view.findViewById(R.id.orderslist);
		if (mylist.size() == 0) {
			Item i = new Item();
			i.setId(0);
			i.setName("Empty");
			i.setType("empty");
			mylist.add(i);
		}
		listView.setAdapter(new MyCustomAdapter(currentActivity,
				android.R.layout.simple_list_item_1, mylist));

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mylist.get(0).getType().equals("empty")) {
					depth++;
					int itemId = mylist.get(position).getId();
					if(sequence.get(depth).equals("country") ||
							sequence.get(depth).equals("city") ||sequence.get(depth).equals("area") )
					{
						getList(sequence.get(depth), position);
					}
					else
						getList(sequence.get(depth), itemId);
					listView.setAdapter(new MyCustomAdapter(currentActivity,
							android.R.layout.simple_list_item_1, mylist));
				}
			}
		});
	}

	public static void updateFooter() {
		Cart cart = ((deliveryclient) currentActivity.getApplication())
				.getMyCart();
		TextView quantity = (TextView) view.findViewById(R.id.totalQuantity);
		TextView price = (TextView) view.findViewById(R.id.totalprice);
		int totalPrice = 0;

		for (CartItem myP : cart.getCartItems()) {
			totalPrice += (myP.getCount() * myP.getProduct().getPrice());
		}
		price.setText(totalPrice + " L.L");
		quantity.setText("" + cart.getAllCount());
		MainActivity.updateCounter(cart.getAllCount());
	}
}