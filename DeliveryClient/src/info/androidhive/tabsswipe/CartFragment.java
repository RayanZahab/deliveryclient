package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CartFragment extends ParentFragment {
	Cart cart;
	static Activity currentActivity;
	FragmentManager fragmentManager;
	ArrayList<Item> mylist;
	public static View view;
	static View myview;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentActivity = getActivity();
		
		view = inflater.inflate(R.layout.fragment_cart, container, false);
		Button submit = (Button) view.findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				submitCart();
			}
		});
		
		return view;
	}
	
	
	public void submitCart()
	{
		Intent i = new Intent(this.getActivity(), PreviewActivity.class);
		startActivity(i);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((deliveryclient) currentActivity.getApplication()).setCurrentFragment(this);
		cart = ((deliveryclient) currentActivity.getApplication()).getMyCart();
		getProducts();
		updateFooter();
	}

	@Override
	public void onResume() {
		super.onResume();
		
	}

	public void getProducts() {
		mylist = new ArrayList<Item>();
		for (CartItem myP : cart.getCartItems()) {
			Product myProduct = myP.getProduct();
			Item it = new Item();
			it.setName(myProduct.toString());
			it.setType("product");
			it.setPrice(myProduct.getPrice());
			it.setId(myProduct.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void updateList() {
		ListView listView = (ListView) view.findViewById(R.id.cartList);
		listView.setAdapter(new MyCustomAdapter(currentActivity,
				R.layout.row_product, mylist));
	}

	public static void updateFooter() {
		Cart cart = ((deliveryclient) currentActivity.getApplication())
				.getMyCart();
		TextView price = (TextView) view.findViewById(R.id.carttotalprice);
		TextView quantity = (TextView) view.findViewById(R.id.totalQuantity);
		int totalPrice = 0;

		for (CartItem myP : cart.getCartItems()) {
			totalPrice += (myP.getCount() * myP.getProduct().getPrice());
		}
		price.setText( 	"" +totalPrice+currentActivity.getString(R.string.lira));
		quantity.setText("" + cart.getAllCount());
		MainActivity.updateCounter(cart.getAllCount());
	}
}
