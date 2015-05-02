package com.mobilive.delivery.client.view.activity;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.adapter.OrderInfoAdapter;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Item;
import com.mobilive.delivery.client.model.Order;
import com.mobilive.delivery.client.model.OrderItem;
import com.mobilive.delivery.client.model.OrderStatus;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.GlobalM;
import com.mobilive.delivery.client.utilities.Helper;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;

public class OrderInfoActivity extends Activity {
	TextView status;
	OrderInfoAdapter dataAdapter;
	int orderId;
	AlertDialog alertDialog;
	Order currentOrder;
	GlobalM glob = new GlobalM();
	ArrayList<OrderItem> orderitem;
	ArrayList<Item> SPitems;
	ListView listView;
	EditText notes;
	Boolean isAdmin = true, isPreparer = true, disabled = false;
	ArrayList<String> stat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_info);
		status = (TextView) findViewById(R.id.order_status);
		listView = (ListView) findViewById(R.id.listView);
		notes = (EditText) findViewById(R.id.noteinput);
		stat = new ArrayList<String>();
		stat.add(0, "Opened");
		stat.add(1, "Prepared");
		stat.add(2, "Closed");
		isAdmin = ((DeliveryClientApplication) this.getApplication()).isAdmin();
		isPreparer = ((DeliveryClientApplication) this.getApplication()).isPrep();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		((DeliveryClientApplication) this.getApplication()).clear("order");
		this.orderId = ((DeliveryClientApplication) this.getApplication()).getOrderId();

		if (orderId != 0) {
			getCurrentOrder(orderId);
		}

		this.setTitle(R.string.preview_order_activity);
		disable(true);

	}

	public void addItemsOnStatus() {
		if(currentOrder.getStatus()!=null){
			OrderStatus orderStatusTxt = OrderStatus.valueOf(currentOrder.getStatus());
			status.setText(getString(orderStatusTxt.getId()));
		}
	}

	public void getCurrentOrder(int orderId) {
		String serverURL = new myURL(null, "orders", orderId, 0).getURL();		
		RZHelper p = new RZHelper(serverURL, this, "setOrderInfo", true,true);
		p.get();
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setOrderInfo"))
			setOrderInfo(s, error);
	}

	public void setOrderInfo(String s, String error) {
		currentOrder = new APIManager().getOrder(s);
		addItemsOnStatus();
		orderitem = currentOrder.getOrderItems();
		SPitems = new ArrayList<Item>();
		Item _Item;
		double total = 0;
		TextView totalTxt = (TextView) findViewById(R.id.allTotal);

		for (int i = 0; i < orderitem.size(); i++) {
			_Item = new Item(orderitem.get(i).getId(), orderitem.get(i).toString(), orderitem.get(i).getQuantity(), orderitem.get(i).getUnitPrice());
			SPitems.add(_Item);
			total = total + orderitem.get(i).getTotalPrice();
		}
		dataAdapter = new OrderInfoAdapter(OrderInfoActivity.this,R.layout.row_order_info, SPitems, disabled);
		dataAdapter.setTotal(totalTxt);

		listView.setAdapter(dataAdapter);
		new Helper().getListViewSize(listView);
		totalTxt.setText(total + "");
		TextView customerName = (TextView) findViewById(R.id.customerName);
		TextView customerphone = (TextView) findViewById(R.id.customerphone);
		customerName.setText(" " + currentOrder.getCustomer().toString());
		TextView customerAdd = (TextView) findViewById(R.id.customerAdd);
		ArrayList<Country> countries = ((DeliveryClientApplication) this.getApplication()).getCountries();
		customerAdd.setText(currentOrder.getAddress().toString(countries));
		
		SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
		String phoneVal = settings.getString("phone", "");
		customerphone.setText(phoneVal);
		notes.setText(currentOrder.getNote());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.order_info, menu);
		// SharedMenu.onCreateOptionsMenu(this, menu, getApplicationContext());
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// if (SharedMenu.onOptionsItemSelected(item, this) == false) {
		// // handle local menu items here or leave blank
		// }
		return super.onOptionsItemSelected(item);
	}

	public void disable(boolean closed) {
		status.setEnabled(false);
		status.setClickable(false);
		listView.setEnabled(false);
		listView.setClickable(false);
		notes.setEnabled(false);
		notes.setClickable(false);

		disabled = true;
	}
}
