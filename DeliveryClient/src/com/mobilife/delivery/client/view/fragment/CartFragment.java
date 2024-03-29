package com.mobilife.delivery.client.view.fragment;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.adapter.MyCustomAdapter;
import com.mobilife.delivery.client.model.Address;
import com.mobilife.delivery.client.model.Cart;
import com.mobilife.delivery.client.model.CartItem;
import com.mobilife.delivery.client.model.Gender;
import com.mobilife.delivery.client.model.Item;
import com.mobilife.delivery.client.model.Product;
import com.mobilife.delivery.client.view.activity.MainActivity;
import com.mobilife.delivery.client.view.listview.SelectAdress;
import com.mobilife.delivery.client.R;

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
	ImageView genderImg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		currentActivity = getActivity();
		userId = ((DeliveryClientApplication) currentActivity.getApplication()).getUserId();
		view = inflater.inflate(R.layout.fragment_cart, container, false);
		Button submit = (Button) view.findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				submitCart();
			}
		});		
		Button back = (Button) view.findViewById(R.id.back);
		back.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				back();
			}
		});		
		name = (TextView) view.findViewById(R.id.customerName);
		phone = (TextView) view.findViewById(R.id.customerphone);
		genderImg = (ImageView)  view.findViewById(R.id.gender);
		currentActivity.setTitle(R.string.cart);
		return view;
	}
	
	
	public void callMethod(String m, String s, String error) {
		
	}
	public void submitCart()
	{		
		Intent intent = new Intent(this.getActivity(), SelectAdress.class);
		intent.putExtra("previous", "preview");
		startActivity(intent);
	}
	public void back()
	{		
		Intent i = new Intent(currentActivity, MainActivity.class);
		i.putExtra("fragmentIndex", 7);
		int branchId = ((DeliveryClientApplication) currentActivity.getApplication()).getBranchId();
		i.putExtra("branchId", branchId);
		startActivity(i);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((DeliveryClientApplication) currentActivity.getApplication()).setCurrentFragment(this);
		cart = ((DeliveryClientApplication) currentActivity.getApplication()).getMyCart();
		SharedPreferences settings1 = currentActivity.getSharedPreferences("PREFS_NAME", 0);
		String nameVal = settings1.getString("name", "");
		String phoneVal = settings1.getString("phone", "");
		name.setText(nameVal);
		phone.setText(phoneVal);
		
		String genderVal = settings1.getString("gender", "");
		if(Gender.Male.toString().equals(genderVal))
		{
			genderImg.setImageResource(R.drawable.malepicto);
		}
		else
		{
			genderImg.setImageResource(R.drawable.femalepicto);			
		}
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
		Cart cart = ((DeliveryClientApplication) currentActivity.getApplication()).getMyCart();
		TextView price = (TextView) view.findViewById(R.id.carttotalprice);
		int totalPrice = 0;
		for (CartItem myP : cart.getCartItems()) {
			totalPrice += (myP.getCount() * myP.getProduct().getPrice());
		}
		price.setText( 	"" +totalPrice+currentActivity.getString(R.string.lira));
		MainActivity.updateCounter(cart.getAllCount());
	}
}
