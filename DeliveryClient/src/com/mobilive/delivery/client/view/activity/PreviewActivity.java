package com.mobilive.delivery.client.view.activity;


import java.util.ArrayList;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.adapter.MyCustomAdapter;
import com.mobilive.delivery.client.model.Address;
import com.mobilive.delivery.client.model.Cart;
import com.mobilive.delivery.client.model.CartItem;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Item;
import com.mobilive.delivery.client.model.Order;
import com.mobilive.delivery.client.model.OrderItem;
import com.mobilive.delivery.client.model.Product;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;
import com.mobilive.delivery.client.view.listview.SelectAdress;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends Activity {
	Cart cart;
	ArrayList<Item> mylist;
	MyCustomAdapter dataAdapter = null;
	Order myOrder;
	ArrayList<Address> myAddresses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		cart = ((DeliveryClientApplication) this.getApplication()).getMyCart();
		myOrder = new Order();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		preview();
	}

	public void getAddresses(int userId) {
		String serverURL = new myURL("addresses", "customers", ((DeliveryClientApplication) this.getApplication()).getUserId(), 0).getURL();
		
		RZHelper p = new RZHelper(serverURL, this, "setAdd", true);
		p.get();
	}
	public void selectAdd(View v)
	{
		Intent i = new Intent(this, SelectAdress.class);
		i.putExtra("previous", "preview");
		 startActivity(i);
	}

	public void getAdd(String s, String error) {
		if (error == null) {
			TextView customerName = (TextView) findViewById(R.id.customerName);
			TextView customerAdd = (TextView) findViewById(R.id.customerAdd);

			SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);

			String name = settings1.getString("name", "");
			customerName.setText(" " + name);
			myAddresses = new APIManager().getAddress(s);
			ArrayList<Country> countries = ((DeliveryClientApplication) this.getApplication())
					.getCountries();
			for(int i =0;i<myAddresses.size();i++)
			{
				if(myAddresses.get(i).isDefault())
				{
					customerAdd.setText(myAddresses.get(i).toString(countries));
					break;
				}
			}
			
		}
	}

	public void submit(View v) {
		TextView noteTxt = (TextView) findViewById(R.id.note);
		myOrder.setNote(noteTxt.getText().toString());
		String serverURL = new myURL("orders", null, 0, 0).getURL();
		
		RZHelper p = new RZHelper(serverURL, this, "afterCreation", true);
		p.post(myOrder);
	}

	public void callMethod(String m, String s, String error) {
		if(m.equals("setAdd"))
			getAdd(s, error);
		else
		{
			Toast.makeText(getApplicationContext(), R.string.success_order,
					Toast.LENGTH_SHORT).show();
			gotomain();
		}
	}
	public void gotomain()
	{
		((DeliveryClientApplication) this.getApplication()).emptyCart();
		 Intent i = new Intent(this, MainActivity.class);
		 startActivity(i);
	}

	public void preview() {
		mylist = new ArrayList<Item>();
		ArrayList<OrderItem> ois = new ArrayList<OrderItem>();
		for (CartItem myP : cart.getCartItems()) {
			Product myProduct = myP.getProduct();
			Item it = new Item();
			it.setName(myProduct.toString());
			it.setType("preview");
			it.setPrice(myProduct.getPrice());
			it.setId(myProduct.getId());
			mylist.add(it);

			// set Order Info
			OrderItem oi = new OrderItem();
			oi.setId(it.getId());
			oi.setQuantity(myP.getCount());
			ois.add(oi);
		}
		myOrder.setOrderItems(ois);

		int id = ((DeliveryClientApplication) this.getApplication()).getUserId();
		int addressId = ((DeliveryClientApplication) this.getApplication()).getAddressId();
		myOrder.setCustomer_id(id);
		myOrder.setAddress_id(addressId);
		myOrder.setCount(cart.getAllCount());
		myOrder.setTotal(cart.getTotalPrice());

		ListView listView = (ListView) findViewById(R.id.list);

		dataAdapter = new MyCustomAdapter(this, R.layout.row_preview, mylist);
		listView.setAdapter(dataAdapter);
		
		TextView total = (TextView) findViewById(R.id.allTotal);
		total.setText(""+((DeliveryClientApplication) this.getApplication()).getMyCart().getTotalPrice());
		getAddresses(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

}
