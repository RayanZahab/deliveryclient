package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderInfoActivity extends Activity {
	Spinner status;
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
		status = (Spinner) findViewById(R.id.order_status);
		listView = (ListView) findViewById(R.id.listView);
		notes = (EditText) findViewById(R.id.noteinput);
		stat = new ArrayList<String>();
		stat.add(0, "Opened");
		stat.add(1, "Prepared");
		stat.add(2, "Closed");
		isAdmin = ((deliveryclient) this.getApplication()).isAdmin();
		isPreparer = ((deliveryclient) this.getApplication()).isPrep();

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		((deliveryclient) this.getApplication()).clear("order");
		this.orderId = ((deliveryclient) this.getApplication()).getOrderId();

		if (orderId != 0) {
			getCurrentOrder(orderId);
		}

		String orderStatus = ((deliveryclient) OrderInfoActivity.this
				.getApplication()).getOrderStatus();
		actionBar.setTitle(orderStatus);
		disable(true);

	}

	public void addItemsOnStatus() {
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.open_status));
		list.add(getString(R.string.prepared_status));
		list.add(getString(R.string.delivered_status));

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		status.setAdapter(dataAdapter);
		if (list.indexOf(currentOrder.getStatus()) > -1)
			status.setSelection(list.indexOf(currentOrder.getStatus()));
	}

	public void getCurrentOrder(int orderId) {
		String serverURL = new myURL(null, "orders", orderId, 0).getURL();
		MyJs mjs = new MyJs("setOrderInfo", this,
				((deliveryclient) this.getApplication()), "GET", true, false);
		mjs.execute(serverURL);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setOrderInfo"))
			setOrderInfo(s, error);
	}

	public void setOrderInfo(String s, String error) {
		Log.d("ray","order info: "+s);
		currentOrder = new APIManager().getOrder(s);
		addItemsOnStatus();
		orderitem = currentOrder.getOrderItems();
		SPitems = new ArrayList<Item>();
		Item _Item;
		double total = 0;
		TextView totalTxt = (TextView) findViewById(R.id.allTotal);

		for (int i = 0; i < orderitem.size(); i++) {
			_Item = new Item(orderitem.get(i).getId(), orderitem.get(i)
					.toString(), orderitem.get(i).getQuantity(), orderitem.get(
					i).getUnitPrice());
			SPitems.add(_Item);
			total = total + orderitem.get(i).getTotalPrice();
		}
		dataAdapter = new OrderInfoAdapter(OrderInfoActivity.this,
				R.layout.row_order_info, SPitems, disabled);
		dataAdapter.setTotal(totalTxt);

		listView.setAdapter(dataAdapter);
		new Helper().getListViewSize(listView);
		totalTxt.setText(total + "");
		TextView customerName = (TextView) findViewById(R.id.customerName);
		customerName.setText(" " + currentOrder.getCustomer().toString());
		TextView customerAdd = (TextView) findViewById(R.id.customerAdd);
		ArrayList<Country> countries = ((deliveryclient) this.getApplication())
				.getCountries();
		customerAdd.setText(currentOrder.getAddress().toString(countries));

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