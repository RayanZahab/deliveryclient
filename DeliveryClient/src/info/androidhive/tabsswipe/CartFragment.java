package info.androidhive.tabsswipe;

import java.util.ArrayList;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentActivity = getActivity();
		userId = ((deliveryclient) currentActivity.getApplication()).getUserId();
		view = inflater.inflate(R.layout.fragment_cart, container, false);
		Button submit = (Button) view.findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				submitCart();
			}
		});		
		return view;
	}
	
	public void getAddresses() {
		Log.d("ray","cart getting add");
		
		String serverURL = new myURL("addresses", "customers", userId, 0).getURL();
				
		RZHelper p = new RZHelper(serverURL, currentActivity, "getAdd", false,true);
		p.get();
	}

	public void getAdd(String s, String error) {
		if (error == null) {
			Log.d("ray","error add not null");
			Addresses = new APIManager().getAddress(s);
			Intent intent;
			int addressId = 0;
			if(Addresses.size()>0)
			{
				SharedPreferences settings = currentActivity.getSharedPreferences("PREFS_NAME", 0);
				SharedPreferences.Editor editor = settings.edit();
				for(int i =0;i<Addresses.size();i++) 
				{
					Log.d("ray","add: "+i);
					if (Addresses.get(i).isDefault()) {
						addressId = Addresses.get(i).getId();
						Log.d("ray","add found ");
						editor.putInt("addressId", Addresses.get(i).getId());
						editor.commit();
						break;
					}
				}
				intent = new Intent(this.getActivity(), PreviewActivity.class);
			}
			else
			{
				Toast.makeText(currentActivity.getApplicationContext(), "Please add an address",
						Toast.LENGTH_SHORT).show();
				intent = new Intent(this.getActivity(), AddAddressActivity.class);
				intent.putExtra("previous", "preview");
			}
			startActivity(intent);
		}else
			Log.d("ray","error:"+error);
			
	}
	

	
	public void callMethod(String m, String s, String error) {
		if (m.equals("getAdd"))
			getAdd(s, error);
	}
	public void submitCart()
	{		
		getAddresses();		
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
		//quantity.setText("" + cart.getAllCount());
		MainActivity.updateCounter(cart.getAllCount());
	}
}
