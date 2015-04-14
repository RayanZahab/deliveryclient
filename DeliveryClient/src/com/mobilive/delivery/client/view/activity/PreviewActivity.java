package com.mobilive.delivery.client.view.activity;

import java.util.ArrayList;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.adapter.MyCustomAdapter;
import com.mobilive.delivery.client.model.Address;
import com.mobilive.delivery.client.model.Cart;
import com.mobilive.delivery.client.model.CartItem;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Gender;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends Activity {
	Cart cart;
	ArrayList<Item> mylist;
	MyCustomAdapter dataAdapter = null;
	Order myOrder;
	ArrayList<Address> myAddresses;
	String name, addName, phoneVal;
	int addId;
	ImageView genderImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		cart = ((DeliveryClientApplication) this.getApplication()).getMyCart();
		myOrder = new Order();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);

		name = settings1.getString("name", "");
		addName = settings1.getString("addressName", "");
		addId = settings1.getInt("addressId", 0);
		phoneVal = settings1.getString("phone", "");
		TextView customerName = (TextView) findViewById(R.id.customerName);
		TextView customerAdd = (TextView) findViewById(R.id.customerAdd);
		TextView customerPhone = (TextView) findViewById(R.id.customerphone);
		customerName.setText("" + name);
		customerPhone.setText("" + phoneVal);
		customerAdd.setText(addName.replace(",", "\n"));
		String genderVal = settings1.getString("gender", "");
		if(genderVal.equals(Gender.Male.toString()))
		{
			genderImg.setImageResource(R.drawable.malepicto);
		}
		else
		{
			genderImg.setImageResource(R.drawable.femalepicto);			
		}
		preview();
	}

	public void submit(View v) {
		TextView noteTxt = (TextView) findViewById(R.id.note);
		myOrder.setNote(noteTxt.getText().toString());
		String serverURL = new myURL("orders", null, 0, 0).getURL();

		RZHelper p = new RZHelper(serverURL, this, "afterCreation", true);
		p.post(myOrder);
	}

	public void callMethod(String m, String s, String error) {

		{
			Toast.makeText(getApplicationContext(), R.string.success_order,
					Toast.LENGTH_SHORT).show();
			thankyou();
		}
	}

	public void thankyou() {
		Intent i = new Intent(this, ThankYouActivity.class);
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

		int id = ((DeliveryClientApplication) this.getApplication())
				.getUserId();

		myOrder.setCustomer_id(id);
		myOrder.setAddress_id(addId);
		myOrder.setCount(cart.getAllCount());
		myOrder.setTotal(cart.getTotalPrice());

		ListView listView = (ListView) findViewById(R.id.list);

		dataAdapter = new MyCustomAdapter(this, R.layout.row_preview, mylist);
		listView.setAdapter(dataAdapter);

		TextView total = (TextView) findViewById(R.id.allTotal);
		total.setText("" + cart.getTotalPrice());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

}
