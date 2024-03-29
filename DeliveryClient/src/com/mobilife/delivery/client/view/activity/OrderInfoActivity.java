package com.mobilife.delivery.client.view.activity;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.adapter.OrderInfoAdapter;
import com.mobilife.delivery.client.model.Country;
import com.mobilife.delivery.client.model.Gender;
import com.mobilife.delivery.client.model.Item;
import com.mobilife.delivery.client.model.Order;
import com.mobilife.delivery.client.model.OrderItem;
import com.mobilife.delivery.client.model.OrderStatus;
import com.mobilife.delivery.client.utilities.APIManager;
import com.mobilife.delivery.client.utilities.GlobalM;
import com.mobilife.delivery.client.utilities.Helper;
import com.mobilife.delivery.client.utilities.RZHelper;
import com.mobilife.delivery.client.utilities.myURL;
import com.mobilife.delivery.client.R;

public class OrderInfoActivity extends Activity {
	TextView status;
	OrderInfoAdapter dataAdapter;
	int orderId;
	AlertDialog alertDialog;
	Order currentOrder;
	GlobalM glob = new GlobalM();
	ArrayList<OrderItem> orderitem;
	ArrayList<Item> spItems;
	ListView listView;
	TextView notes;
	Boolean isAdmin = true, isPreparer = true, disabled = false;
	ArrayList<String> stat;
	private ImageView genderImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_info);
		status = (TextView) findViewById(R.id.order_status);
		listView = (ListView) findViewById(R.id.listView);
		notes = (TextView) findViewById(R.id.noteinput);
		stat = new ArrayList<String>();
		stat.add(0, "Opened");
		stat.add(1, "Prepared");
		stat.add(2, "Closed");
		isAdmin = ((DeliveryClientApplication) this.getApplication()).isAdmin();
		isPreparer = ((DeliveryClientApplication) this.getApplication()).isPrep();
		genderImg = (ImageView)  findViewById(R.id.gender);
		SharedPreferences preferences = getSharedPreferences("PREFS_NAME", 0);
		String genderVal = preferences.getString("gender", "");
		if(Gender.Male.toString().equals(genderVal))
		{
			genderImg.setImageResource(R.drawable.malepicto);
		}
		else
		{
			genderImg.setImageResource(R.drawable.femalepicto);			
		}
		
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
			String cancelReason = currentOrder.getCancelReason();
			if(cancelReason!=null && !"null".equals(cancelReason) && !cancelReason.isEmpty())
				cancelReason = getString(R.string.ordercanceled)+":" + currentOrder.getCancelReason();
			else
				cancelReason="";
			notes.setText(notes.getText().toString()+ (notes.getText().toString().isEmpty()?"":"\n") + cancelReason);
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
		spItems = new ArrayList<Item>();
		Item _Item;
		double total = 0;
		TextView totalTxt = (TextView) findViewById(R.id.allTotal);

		for (int i = 0; i < orderitem.size(); i++) {
			_Item = new Item(orderitem.get(i).getId(), orderitem.get(i).toString(), orderitem.get(i).getQuantity(), orderitem.get(i).getUnitPrice());
			spItems.add(_Item);
			total = total + orderitem.get(i).getTotalPrice();
		}
		dataAdapter = new OrderInfoAdapter(OrderInfoActivity.this,R.layout.row_order_info, spItems, disabled);
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
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
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
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MainActivity.class);
		if(currentOrder.getStatus()!=null && (currentOrder.getStatus().equalsIgnoreCase("Opened")||currentOrder.getStatus().equalsIgnoreCase("Assigned")||currentOrder.getStatus().equalsIgnoreCase("Prepared")))
			i.putExtra("fragmentIndex", 3);
		else
			i.putExtra("fragmentIndex", 4);
		startActivity(i);
	}
	
	@Override 
	public Intent getParentActivityIntent() {
		Intent i = new Intent(this, MainActivity.class);
		if(currentOrder.getStatus()!=null && (currentOrder.getStatus().equalsIgnoreCase("Opened")||currentOrder.getStatus().equalsIgnoreCase("Assigned")||currentOrder.getStatus().equalsIgnoreCase("Prepared")))
			i.putExtra("fragmentIndex", 3);
		else
			i.putExtra("fragmentIndex", 4);

		return i;
	};
}
