package com.mobilive.delivery.client.view.fragment;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.adapter.OrdersAdapter;
import com.mobilive.delivery.client.model.Area;
import com.mobilive.delivery.client.model.Branch;
import com.mobilive.delivery.client.model.Business;
import com.mobilive.delivery.client.model.Category;
import com.mobilive.delivery.client.model.City;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Item;
import com.mobilive.delivery.client.model.Order;
import com.mobilive.delivery.client.model.Product;
import com.mobilive.delivery.client.model.Shop;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;
import com.mobilive.delivery.client.view.activity.OrderInfoActivity;
import com.mobilive.delivery.client.view.listview.PullToRefreshListView;
import com.mobilive.delivery.client.view.listview.PullToRefreshListView.OnRefreshListener;

public class PendingOrdersFragment extends ParentFragment {
	PullToRefreshListView lvTweets;
	static int depth = 0;
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
			mContent = getFragmentManager().getFragment(savedInstanceState,"mContent");
		}
		view = inflater.inflate(R.layout.pending_fragment_orders, container,false);
		mylist = new ArrayList<Item>();
		fragmentId = this.getId();
		fragmentManager = getFragmentManager();

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((DeliveryClientApplication) currentActivity.getApplication())
				.setCurrentFragment(this);
		getOrders();
	}

	@Override
	public void onResume() {
		super.onResume();

		getOrders();
	}

	public void getOrders() {
		String serverURL = new myURL(null, "customers", orderStatus + "_orders", 30).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "setOrders",true);
		p.get();
	}

	public void fetchTimelineAsync(int page) {
		Toast.makeText(currentActivity.getApplicationContext(), "Refreshed",Toast.LENGTH_SHORT).show();
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
			i.setTitle(currentActivity.getString(R.string.empty_list));
			orderItems.add(i);
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

		OrdersAdapter dataAdapter = new OrdersAdapter(currentActivity,
				R.layout.row_order, orderItems);
		dataAdapter.empty = empty;
		lvTweets.setAdapter(dataAdapter);

		lvTweets.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (morders.size() > 0) {
					Intent i = new Intent(currentActivity.getBaseContext(),OrderInfoActivity.class);
					((DeliveryClientApplication) currentActivity.getApplication()).setOrderId(orderItems.get(position).getId());
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

}