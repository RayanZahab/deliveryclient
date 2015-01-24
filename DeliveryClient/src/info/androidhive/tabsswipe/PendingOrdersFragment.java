package info.androidhive.tabsswipe;

import info.androidhive.tabsswipe.PullToRefreshListView.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class PendingOrdersFragment extends ParentFragment {
	PullToRefreshListView lvTweets;
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
	android.app.Fragment mContent;
	ArrayList<Item> orderItems = new ArrayList<Item>();
	ArrayList<Order> morders;
	OrdersAdapter dataAdapter;
	String orderStatus = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		currentActivity = getActivity();
		Bundle args = getArguments();
		orderStatus = args.getString("orders");
		if (savedInstanceState != null) {
			mContent = getFragmentManager().getFragment(savedInstanceState,
					"mContent");
		}
		view = inflater.inflate(R.layout.pending_fragment_orders, container,
				false);
		mylist = new ArrayList<Item>();
		fragmentId = this.getId();
		fragmentManager = getFragmentManager();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((deliveryclient) currentActivity.getApplication())
				.setCurrentFragment(this);
		getOrders();
	}

	@Override
	public void onResume() {
		super.onResume();

		getOrders();
	}

	public void getOrders() {
		String serverURL;
		serverURL = new myURL(null, "customers", orderStatus + "_orders", 30)
				.getURL();
		MyJs mjs = new MyJs("setOrders", currentActivity,
				((deliveryclient) currentActivity.getApplication()), "GET",
				true, true);
		mjs.execute(serverURL);
	}

	public void fetchTimelineAsync(int page) {
		Toast.makeText(currentActivity.getApplicationContext(), "Refreshed",
				Toast.LENGTH_SHORT).show();
		getOrders();
	}

	public void setOrders(String s, String error) {
		orderItems = new ArrayList<Item>();
		morders = new APIManager().getOrders(s);
		lvTweets = (PullToRefreshListView) view.findViewById(R.id.orderslist);

		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				lvTweets.setAdapter(null);
				fetchTimelineAsync(0);
			}
		});
		boolean empty = false;
		if (morders.size() == 0) {
			Item i = new Item();
			i.setId(0);
			i.setName(currentActivity.getString(R.string.empty_list));
			i.setType("empty");
			orderItems.add(i);
			Log.d("ray", "empty");
			empty = true;
		} else {
			for (int i = 0; i < morders.size(); i++) {
				Item itm = new Item(morders.get(i).getId(), morders.get(i)
						.toString(), morders.get(i).getCount(), morders.get(i)
						.getTotal(), morders.get(i).isNewCustomer());
				itm.setDate(morders.get(i).getDate());
				orderItems.add(itm);
			}
		}
		Log.d("ray", "ray orders: " + orderItems.size());

		OrdersAdapter dataAdapter = new OrdersAdapter(currentActivity,
				R.layout.row_order, orderItems);
		dataAdapter.empty = empty;
		lvTweets.setAdapter(dataAdapter);

		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (morders.size() > 0) {
					Intent i;
					if (orderItems.get(position).isNew()) {
						i = new Intent(currentActivity.getBaseContext(),
								BlockUser.class);
					} else {
						i = new Intent(currentActivity.getBaseContext(),
								OrderInfoActivity.class);
					}
					((deliveryclient) currentActivity.getApplication())
							.setOrderId(orderItems.get(position).getId());
					startActivity(i);
				}
			}
		});
		lvTweets.onRefreshComplete();
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
}