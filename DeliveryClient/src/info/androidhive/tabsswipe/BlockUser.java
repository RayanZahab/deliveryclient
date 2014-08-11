package info.androidhive.tabsswipe;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class BlockUser extends Activity {

	Customer currentCustomer;
	int orderId;
	Order currentOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	//	setContentView(R.layout.activity_block_user);
	//	this.orderId = ((deliverclient) this.getApplication()).getOrderId();
		getCurrentCustomer(orderId);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void getCurrentCustomer(int orderId) {
		String serverURL = new myURL(null, "orders", orderId, 0).getURL();
	//	MyJs mjs = new MyJs("setCustomerInfo", this,
	//			((deliverclient) this.getApplication()), "GET", true, true);
	//	mjs.execute(serverURL);
	}

	public void setCustomerInfo(String s, String error) {
		currentOrder = new APIManager().getOrder(s);
		currentCustomer = currentOrder.getCustomer();
		TextView customerName = (TextView) findViewById(R.id.customerName);
		customerName.setText(" " + currentCustomer.toString());
	//	TextView customerPhone = (TextView) findViewById(R.id.customerPhone);
	//	customerPhone.setText(" " + currentCustomer.getMobile());
		TextView customerAdd = (TextView) findViewById(R.id.customerAdd);
		customerAdd.setText(currentOrder.getAddress().toString());
	}
	public void block(View v)
	{
		String serverURL;
		serverURL = new myURL("deny", "customers", currentCustomer.getId(), 0).getURL();
		//new MyJs("back", this,
		//		((deliverclient) this.getApplication()), "POST")
		//		.execute(serverURL);
	}

	public void back(String s, String error) {
	//	Intent i = new Intent(getBaseContext(), OrdersActivity.class);
	//	startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
	//	getMenuInflater().inflate(R.menu.block_user, menu);
	//	SharedMenu.onCreateOptionsMenu(this, menu, getApplicationContext());
		return true;
	}

}
