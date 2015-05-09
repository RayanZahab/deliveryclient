package com.mobilife.delivery.client.view.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.mobilife.delivery.client.model.Customer;
import com.mobilife.delivery.client.model.Order;
import com.mobilife.delivery.client.utilities.APIManager;
import com.mobilife.delivery.client.utilities.myURL;
import com.mobilife.delivery.client.R;

public class BlockUserActivity extends Activity {

	Customer currentCustomer;
	int orderId;
	Order currentOrder;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getCurrentCustomer(orderId);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void getCurrentCustomer(int orderId) {
		String serverURL = new myURL(null, "orders", orderId, 0).getURL();
	}

	public void setCustomerInfo(String s, String error) {
		currentOrder = new APIManager().getOrder(s);
		currentCustomer = currentOrder.getCustomer();
		TextView customerName = (TextView) findViewById(R.id.customerName);
		customerName.setText(" " + currentCustomer.toString());
		TextView customerAdd = (TextView) findViewById(R.id.customerAdd);
		customerAdd.setText(currentOrder.getAddress().toString());
	}
	public void block(View v)
	{
		String serverURL = new myURL("deny", "customers", currentCustomer.getId(), 0).getURL();
	}

	public void back(String s, String error) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

}
