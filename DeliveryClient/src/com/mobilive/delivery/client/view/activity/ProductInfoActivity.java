package com.mobilive.delivery.client.view.activity;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.Item;
import com.mobilive.delivery.client.model.Photo;
import com.mobilive.delivery.client.model.Product;
import com.mobilive.delivery.client.model.Unit;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.GlobalM;
import com.mobilive.delivery.client.utilities.ImageTask;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;

public class ProductInfoActivity extends Activity {
	ArrayList<Unit> units;
	int categoryId, branchId, shopId, productId;
	Button upload;
	int RESULT_LOAD_IMAGE = 1;
	String picturePath, picName;
	Product currentProduct = null;
	Photo uploaded;
	TextView name, unitsTxt;
	TextView desc, qtTxt;
	TextView price;
	Spinner unitsSP;
	GlobalM glob = new GlobalM();
	ImageView plus, minus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_info);
		name = (TextView) findViewById(R.id.productname);
		desc = (TextView) findViewById(R.id.description);
		price = (TextView) findViewById(R.id.price);
		unitsTxt = (TextView) findViewById(R.id.units);
		qtTxt = (TextView) findViewById(R.id.qtTxt);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		((DeliveryClientApplication) this.getApplication()).clear("product");
		branchId = ((DeliveryClientApplication) this.getApplication()).getBranchId();
		categoryId = ((DeliveryClientApplication) this.getApplication()).getCategoryId();
		productId = ((DeliveryClientApplication) this.getApplication()).getProductId();
		shopId = ((DeliveryClientApplication) this.getApplication()).getShopId();
		if (productId != 0) {
			getProduct(productId);
		} else {
			getUnits(true);
		}
		final Item item = new Item();
		item.setId(productId);

		plus = (ImageView) findViewById(R.id.plus);
		minus = (ImageView) findViewById(R.id.minus);
		final TextView qtTxt = (TextView) findViewById(R.id.qtTxt);

		plus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int qt = Integer.parseInt(qtTxt.getText().toString());
				if (qt < 9) {
					qt++;
					qtTxt.setText("" + qt);
					addToCart(item);
				}

			}
		});
		minus.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				int qt = Integer.parseInt(qtTxt.getText().toString());
				if (qt > 0) {
					qt--;
					qtTxt.setText("" + qt);
					rmvFromCart(item);
				}
			}
		});

	}

	public void submit(View view) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	public void addToCart(Item item) {

		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		p.setName(item.getName());
		Fragment f = ((DeliveryClientApplication) getApplication()).getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}

		((DeliveryClientApplication) getApplication()).getMyCart().addToCart(cn, p);

	}

	public void rmvFromCart(Item item) {
		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		Fragment f = ((DeliveryClientApplication) getApplication()).getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}
		((DeliveryClientApplication) getApplication()).getMyCart().rmvFromCart(cn, p);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setProduct"))
			setProduct(s, error);
		else if (m.equals("setUnits"))
			setUnits(s, error);
	}

	public void getProduct(int id) {
		String serverURL = new myURL(null, "items", id, 1).getURL();
		RZHelper p = new RZHelper(serverURL, this, "setProduct", true,false);
		p.get();
	}

	public void setProduct(String s, String error) {
		currentProduct = new APIManager().getItemsByCategoryAndBranch(s).get(0);

		name.setText(currentProduct.getName());
		desc.setText(currentProduct.getDescription());
		price.setText("" + currentProduct.getPrice());

		new ImageTask((ImageView) findViewById(R.id.productimg),
				ProductInfoActivity.this).execute(currentProduct.getPhoto()
				.getUrl());
		int i = ((DeliveryClientApplication) getApplication()).getMyCart()
				.getProductCount(currentProduct);
		qtTxt.setText(i + "");

		getUnits(false);
	}

	public void setUnits(String s, String error) {
		units = new APIManager().getUnits(s);
		ArrayAdapter<Unit> dataAdapter = new ArrayAdapter<Unit>(this,
				android.R.layout.simple_spinner_item, units);

		if (currentProduct != null) {
			unitsTxt.setText(glob.getSelected(dataAdapter,
					currentProduct.getUnit()));
		}
	}
	public void back(View v)
	{
		onBackPressed();
	}

	public void getUnits(boolean first) {
		// getUnits
		String serverURL = new myURL("units", null, 0, 30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "setUnits", first,true);
		p.get();
	}

}
