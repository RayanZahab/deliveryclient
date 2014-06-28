package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CartFragment extends Fragment {
	Cart cart;
	static Activity currentActivity;
	FragmentManager fragmentManager;
	ArrayList<Item> mylist;
	static View view ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentActivity = getActivity();

		view = inflater.inflate(R.layout.fragment_orders, container, false);
		cart = ((deliveryclient) currentActivity.getApplication())
				.getMyCart();
		
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getProducts();
		updateFooter();
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
			Log.d("rays","Pro: "+myProduct.getId());
		}
		updateList();
	}
	public void updateList() {
		final ListView listView = (ListView) currentActivity
				.findViewById(R.id.list);
		listView.setAdapter(new MyCustomAdapter(currentActivity,
				R.layout.row_product, mylist));		
	}
	public static void updateFooter() {
		Cart cart = ((deliveryclient) currentActivity.getApplication())
				.getMyCart();
		TextView quantity = (TextView) view.findViewById(R.id.totalQuantity);
		TextView price = (TextView) view.findViewById(R.id.totalprice);
		int totalPrice = 0;

		for (CartItem myP : cart.getCartItems()) {
			totalPrice += (myP.getCount() * myP.getProduct().getPrice());
		}
		price.setText(totalPrice + " L.L");
		quantity.setText("" + cart.getAllCount());
	}
}
