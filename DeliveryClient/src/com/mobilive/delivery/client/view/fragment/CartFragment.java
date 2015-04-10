package com.mobilive.delivery.client.view.fragment;


import java.util.ArrayList;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.adapter.MyCustomAdapter;
import com.mobilive.delivery.client.model.Address;
import com.mobilive.delivery.client.model.Cart;
import com.mobilive.delivery.client.model.CartItem;
import com.mobilive.delivery.client.model.Item;
import com.mobilive.delivery.client.model.Product;
import com.mobilive.delivery.client.view.activity.AddAddressActivity;
import com.mobilive.delivery.client.view.activity.MainActivity;
import com.mobilive.delivery.client.view.activity.PreviewActivity;
import com.mobilive.delivery.client.view.listview.SelectAdress;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CartFragment extends ParentFragment {
	Cart cart;
	static Activity currentActivity;
	FragmentManager fragmentManager;
	ArrayList<Item> mylist;
	public static View view;
	static View myview;
	int userId = 0;
	ArrayList<Address> Addresses ;
	TextView name,phone;
	int addressId = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentActivity = getActivity();
		userId = ((DeliveryClientApplication) currentActivity.getApplication()).getUserId();
		view = inflater.inflate(R.layout.fragment_cart, container, false);
		Button submit = (Button) view.findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				submitCart();
			}
		});		
		name = (TextView) view.findViewById(R.id.customerName);
		//address = (TextView) view.findViewById(R.id.customerAdd);
		phone = (TextView) view.findViewById(R.id.customerphone);
		return view;
	}
	
	
	public void callMethod(String m, String s, String error) {
		
	}
	public void submitCart()
	{		
		//Intent intent = new Intent(this.getActivity(), PreviewActivity.class);
		Intent intent = new Intent(this.getActivity(), SelectAdress.class);
		intent.putExtra("previous", "preview");
		startActivity(intent);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((DeliveryClientApplication) currentActivity.getApplication()).setCurrentFragment(this);
		cart = ((DeliveryClientApplication) currentActivity.getApplication()).getMyCart();
		SharedPreferences settings1 = currentActivity.getSharedPreferences(
				"PREFS_NAME", 0);
		String nameVal = settings1.getString("name", "");
		String phoneVal = settings1.getString("phone", "");
		String addVal = settings1.getString("addressName", "");
		addressId = settings1.getInt("addressId", 0);
		name.setText(nameVal);
		phone.setText(phoneVal);
		//address.setText(addVal);		
		
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
		Cart cart = ((DeliveryClientApplication) currentActivity.getApplication())
				.getMyCart();
		TextView price = (TextView) view.findViewById(R.id.carttotalprice);
		TextView quantity = (TextView) view.findViewById(R.id.totalQuantity);
		int totalPrice = 0;

		for (CartItem myP : cart.getCartItems()) {
			totalPrice += (myP.getCount() * myP.getProduct().getPrice());
		}
		price.setText( 	"" +totalPrice+currentActivity.getString(R.string.lira));
		//quantity.setText("" + cart.getAllCount());
		MainActivity.updateCounter(cart.getAllCount());
	}
}
