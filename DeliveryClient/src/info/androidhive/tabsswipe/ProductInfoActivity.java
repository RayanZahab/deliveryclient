package info.androidhive.tabsswipe;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ProductInfoActivity extends Activity {
	ArrayList<Unit> units;
	int categoryId, branchId, shopId, productId;
	Button upload;
	int RESULT_LOAD_IMAGE = 1;
	String picturePath, picName;
	Product currentProduct = null;
	Photo uploaded;
	TextView name, unitsTxt;
	TextView desc;
	TextView price;
	Spinner unitsSP;
	GlobalM glob = new GlobalM();
	ImageView plus,minus ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_info);
		name = (TextView) findViewById(R.id.productName);
		desc = (TextView) findViewById(R.id.description);
		price = (TextView) findViewById(R.id.price);
		unitsTxt = (TextView) findViewById(R.id.units);

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		((deliveryclient) this.getApplication()).clear("product");
		branchId = ((deliveryclient) this.getApplication()).getBranchId();
		categoryId = ((deliveryclient) this.getApplication()).getCategoryId();
		productId = ((deliveryclient) this.getApplication()).getProductId();
		shopId = ((deliveryclient) this.getApplication()).getShopId();
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

	public void addToCart(Item item) {

		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		p.setName(item.getName());
		Fragment f = ((deliveryclient) getApplication())
				.getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}

		((deliveryclient) getApplication()).getMyCart().addToCart(cn,
				p);

	}

	public void rmvFromCart(Item item) {
		Product p = new Product(item.getId());
		p.setPrice(item.getPrice());
		Fragment f = ((deliveryclient) getApplication())
				.getCurrentFragment();
		String cn = null;
		if (f != null) {
			cn = f.getClass().getName();
		}
		((deliveryclient) getApplication()).getMyCart().rmvFromCart(
				cn, p);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setProduct"))
			setProduct(s, error);
		else if (m.equals("setUnits"))
			setUnits(s, error);
	}

	public void getProduct(int id) {
		String serverURL = new myURL(null, "items", id, 1).getURL();
		new MyJs("setProduct", this, ((deliveryclient) this.getApplication()),
				"GET", true, false).execute(serverURL);
	}

	public void setProduct(String s, String error) {
		currentProduct = new APIManager().getItemsByCategoryAndBranch(s).get(0);
		String n;
		try {
			n = new String((currentProduct.getName()).getBytes("iso-8859-1"),
					"UTF-8");

			name.setText(n);
			desc.setText(currentProduct.getDescription());
			price.setText("" + currentProduct.getPrice());

			new ImageTask((ImageView) findViewById(R.id.preview),
					ProductInfoActivity.this).execute(currentProduct.getPhoto()
					.getUrl());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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

	public void getUnits(boolean first) {
		// getUnits
		String serverURL = new myURL("units", null, 0, 30).getURL();
		new MyJs("setUnits", this, ((deliveryclient) this.getApplication()),
				"GET", first, true).execute(serverURL);

	}

}
