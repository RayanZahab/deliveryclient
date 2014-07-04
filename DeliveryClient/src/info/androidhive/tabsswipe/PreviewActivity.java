package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

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

		cart = ((deliveryclient) this.getApplication()).getMyCart();
		myOrder = new Order();

		preview();
	}

	public void getAddresses(int userId) {
		String serverURL = new myURL("addresses", "customers", 1, 0).getURL();
		MyJs mjs = new MyJs("setAdd", this,
				((deliveryclient) this.getApplication()), "GET");
		mjs.execute(serverURL);

	}

	public void getAdd(String s, String error) {
		if (error == null) {
			TextView customerName = (TextView) findViewById(R.id.customerName);
			TextView customerAdd = (TextView) findViewById(R.id.customerAdd);

			SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);

			String name = settings1.getString("name", "");
			customerName.setText(" " + name);
			myAddresses = new APIManager().getAddress(s);
			customerAdd.setText(myAddresses.get(0).toString());
		}
	}

	public void submit(View v) {
		String serverURL = new myURL("orders", null, 0, 0).getURL();
		new MyJs("afterCreation", this,
				((deliveryclient) this.getApplication()), "POST",
				(Object) myOrder).execute(serverURL);
	}

	public void callMethod(String m, String s, String error) {
		if(m.equals("setAdd"))
				getAdd(s, error);
		else
		{
			gotomain();
		}
	}
	public void gotomain()
	{
		((deliveryclient) this.getApplication()).emptyCart();
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

		int id = ((deliveryclient) this.getApplication()).getUserId();
		int addressId = ((deliveryclient) this.getApplication()).getAddressId();
		myOrder.setCustomer_id(id);
		myOrder.setAddress_id(addressId);
		myOrder.setCount(cart.getAllCount());
		myOrder.setTotal(cart.getTotalPrice());

		ListView listView = (ListView) findViewById(R.id.list);

		dataAdapter = new MyCustomAdapter(this, R.layout.row_preview, mylist);
		listView.setAdapter(dataAdapter);
		
		TextView total = (TextView) findViewById(R.id.allTotal);
		total.setText(""+((deliveryclient) this.getApplication()).getMyCart().getTotalPrice());
		getAddresses(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

}
