package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class PreviewActivity extends Activity {
	Cart cart;
	ArrayList<Item> mylist;
	MyCustomAdapter dataAdapter = null;
	Order myOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		cart = ((deliveryclient) this.getApplication()).getMyCart();
		myOrder = new Order();
		preview();
	}

	public void submit(View v) {
		String serverURL = new myURL("orders", null, 0, 0).getURL();
		new MyJs("afterCreation", this,
				((deliveryclient) this.getApplication()), "POST",
				(Object) myOrder).execute(serverURL);
	}

	public void callMethod(String m, String s, String error) {
		
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.preview, menu);
		return true;
	}

}
