package com.mobilife.delivery.client.view.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.adapter.MyCustomAdapter;
import com.mobilife.delivery.client.model.Address;
import com.mobilife.delivery.client.model.Branch;
import com.mobilife.delivery.client.model.Business;
import com.mobilife.delivery.client.model.Cart;
import com.mobilife.delivery.client.model.Category;
import com.mobilife.delivery.client.model.Country;
import com.mobilife.delivery.client.model.Item;
import com.mobilife.delivery.client.model.Product;
import com.mobilife.delivery.client.model.Shop;
import com.mobilife.delivery.client.utilities.APIManager;
import com.mobilife.delivery.client.utilities.ErrorHandlerManager;
import com.mobilife.delivery.client.utilities.RZHelper;
import com.mobilife.delivery.client.utilities.myURL;
import com.mobilife.delivery.client.view.activity.AddAddressActivity;
import com.mobilife.delivery.client.view.activity.MainActivity;
import com.mobilife.delivery.client.R;

public class OrdersFragment extends ParentFragment {

	static int depth = 0;
	private static List<String> sequence = new ArrayList<String>();
	ArrayList<Business> businesses = new ArrayList<Business>();
	ArrayList<Shop> shops = new ArrayList<Shop>();
	ArrayList<Branch> branches = new ArrayList<Branch>();
	ArrayList<Category> categories = new ArrayList<Category>();
	ArrayList<Product> products = new ArrayList<Product>();
	static int areaId = 0, shopId = 0, branchId = 0,categoryId = 0, productId = 0;
	ArrayList<Item> mylist = new ArrayList<Item>();
	static Activity currentActivity;
	static View view;
	int fragmentId;
	android.app.FragmentManager fragmentManager;
	android.app.Fragment mContent;
	boolean passedByOnCreate = false;
	int call =-1;
	int userId = 0;
	ArrayList<Address> Addresses ;
	public RelativeLayout footer;
	ImageView buttonOne;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		currentActivity = getActivity();	
		sequence.add("business");		
		sequence.add("shops");
		sequence.add("branches");
		sequence.add("categories");
		sequence.add("products");
		sequence.add("info");
		userId = ((DeliveryClientApplication) currentActivity.getApplication()).getUserId();
		if(getArguments()!=null)
		{
			areaId = ((DeliveryClientApplication) currentActivity.getApplication()).getAreaId();
			categoryId = getArguments().getInt("categoryId");
			branchId = getArguments().getInt("branchId");
			mylist = null;
			depth=0;
		}

		if (savedInstanceState != null) {
			mContent = getFragmentManager().getFragment(savedInstanceState,
					"mContent");
		}
		
		view = inflater.inflate(R.layout.fragment_orders, container, false);
		mylist = new ArrayList<Item>();
		fragmentId = this.getId();
		fragmentManager = getFragmentManager();
		footer = (RelativeLayout) view.findViewById(R.id.mycart);

		updateFooter();
		buttonOne = (ImageView) view.findViewById(R.id.back);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				goUp();
			}
		});
		ImageView submit = (ImageView) view.findViewById(R.id.submit);
		submit.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				move();
			}
		});
		mylist = null;
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		return view;
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((DeliveryClientApplication) currentActivity.getApplication()).setCurrentFragment(this);
		if(call!=-1 ||categoryId!=0 ){
			depth = ((DeliveryClientApplication) currentActivity.getApplication()).getDepth();
			getList(sequence.get(((DeliveryClientApplication) currentActivity.getApplication()).getDepth()),
					((DeliveryClientApplication) currentActivity.getApplication()).getDepthVal());
		}
		else if (branchId != 0){
			depth = 3;
			((DeliveryClientApplication) currentActivity.getApplication()).setDepths(depth,branchId);
			getList(sequence.get(depth),branchId);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (sequence.size() < 1) {
			sequence.add("business");			
			sequence.add("shops");
			sequence.add("branches");
			sequence.add("categories");
			sequence.add("products");
			sequence.add("info");			
		}
		int hcall = ((DeliveryClientApplication) currentActivity.getApplication())
				.getDepth() * ((DeliveryClientApplication) currentActivity.getApplication())
				.getDepthVal();

		if(call!=hcall || call==-1)
		{
			getList(sequence.get(
					((DeliveryClientApplication) currentActivity.getApplication())
					.getDepth()
					),
					((DeliveryClientApplication) currentActivity.getApplication())
							.getDepthVal());	
		}
	}

	public int goUp() {
		if(depth==3 && ((DeliveryClientApplication) currentActivity.getApplication()).getMyCart().getAllCount()>0)
		{
			new AlertDialog.Builder(currentActivity).setTitle(R.string.empty_cart).setIcon(R.drawable.categories).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int whichButton) {
					goingUp();
				}
			} ).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int whichButton) {
					return;
				}
			}).show();
		}
		else
		{
			goingUp();
		}
		return depth;

	}
	
	public void goingUp()
	{
		if (depth > 0) {
			depth--;
			int id = 0;
			switch (depth) {
			case 0:
				id = 0;
				break;
			case 1:
				id = 0;
				break;			
			case 2:
				id = shopId;
				break;
			case 3:
				id = branchId;
				break;
			case 4:
				id = categoryId;
				break;
			}
			getList(sequence.get(depth), id);
		}
	}

	public void getList(String type, int id) {
		mylist = new ArrayList<Item>();
		call = ((DeliveryClientApplication) currentActivity.getApplication())
				.getDepth() * ((DeliveryClientApplication) currentActivity.getApplication())
				.getDepthVal();
		((DeliveryClientApplication) currentActivity.getApplication()).setDepths(sequence.indexOf(type),id);
		if (type.equals("business")) {			
			shopId = 0;
			branchId = 0;
			categoryId = 0;
			productId = 0;
			depth = 0;
			currentActivity.getActionBar().setTitle(getString(R.string.business_categories));
			getBusinesses();
		}  else if (type.equals("shops")) {
			shopId = branchId = categoryId = 0;
			currentActivity.getActionBar().setTitle(getString(R.string.shops));
			getShops(areaId);
		} else if (type.equals("branches")) {
			((DeliveryClientApplication) currentActivity.getApplication()).emptyCart();
			updateFooter();
			branchId = categoryId = 0;
			shopId = id;
			currentActivity.getActionBar().setTitle(getString(R.string.branches));
			getBranches(id,areaId);
		} else if (type.equals("categories")) {
			categoryId = 0;
			branchId = id;
			categoryId = 0;
			((DeliveryClientApplication) currentActivity.getApplication()).setCategoryId(id);
			currentActivity.getActionBar().setTitle(getString(R.string.categories));
			((DeliveryClientApplication) currentActivity.getApplication()).setBranchId(id);
			getCategories(id);
		} else if (type.equals("products")) {
			categoryId = id;
			((DeliveryClientApplication) currentActivity.getApplication()).setCategoryId(id);
			currentActivity.getActionBar().setTitle(getString(R.string.products));
			getProducts(branchId, id);
		}

	}

	public void move() {
		CartFragment fh = new CartFragment();
		android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
		MainActivity.fragments.add(fh);
		ft.replace(fragmentId, fh);
		ft.commit();
	}
	public void getAddresses() {
		String serverURL = new myURL("addresses", "customers", userId, 0).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "getAdd", false,true);
		p.get();
	}

	public void getAdd(String s, String error) {
		if (error == null) {
			Addresses = new APIManager().getAddress(s);
			Intent intent;
			int addCount = Addresses.size();
			if(addCount>0)
			{
				SharedPreferences settings = currentActivity.getSharedPreferences("PREFS_NAME", 0);
				SharedPreferences.Editor editor = settings.edit();
				
				if(addCount==1)
					Addresses.get(0).setDefault(true);
				Address currentAddress = null;
				for(int i =0;i<addCount;i++) 
				{
					currentAddress = Addresses.get(i);
					if (currentAddress.isDefault() ) {
						int addressId = currentAddress.getId();
						editor.putInt("addressId", addressId);
						ArrayList<Country> countries = ((DeliveryClientApplication) currentActivity.getApplication()).getCountries();
						editor.putString("addressName", currentAddress.toString(countries));
						editor.commit();
						break;
					}
				}
				move();
			}
			else
			{
				Toast.makeText(currentActivity.getApplicationContext(), R.string.add_address,
						Toast.LENGTH_SHORT).show();
				intent = new Intent(this.getActivity(), AddAddressActivity.class);
				intent.putExtra("previous", "preview");
				startActivity(intent);
			}
		}
			
	}

	public void getShops(int areaId) {
		MainActivity.setShowHomeFragment(false);
		String serverURL = new myURL("shops?business_id="+((DeliveryClientApplication) currentActivity.getApplication()).getBusinessId(), "areas", areaId, 0).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "setShops", true,true);
		p.get();
	}

	public void setShops(String s, String error) {
		shops = new APIManager().getShopsByArea(s);
		mylist = new ArrayList<Item>();
		for (Shop myShop : shops) {
			Item it = new Item();
			it.setName(myShop.toString());
			it.setType("shop");
			it.setId(myShop.getId());
			it.setPhotoName(myShop.getPhotoName());
			mylist.add(it);
		}
		updateList();
	}

	public void getBranches(int shopId, int areaId) {
		TimeZone timzone = TimeZone.getDefault();
		//offset in hours
		int offset = (int) (timzone.getOffset(System.currentTimeMillis())/(1000*60*60));
		String offsetStr = (offset>=0?"+":"-")+offset;
		String serverURL = new myURL("branches?area_id="+areaId+"&timezone="+offsetStr, "shops", shopId, 30).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "setBranches", true);
		p.get();
	}
	public void setBranches(String s, String error) {
		branches = new APIManager().getBranchesByShop(s);
		mylist = new ArrayList<Item>();
		for (Branch myBranch : branches) {
			Item it = new Item();
			it.setName(myBranch.toString());
			it.setType("branch");
			it.setId(myBranch.getId());
			it.setTime(myBranch.getEstimation_time());
			it.setCharge(myBranch.getDelivery_charge());
			it.setMinimum(myBranch.getMin_amount());
			it.setBranchIsOpened(myBranch.isOpened());
			mylist.add(it);
		}
		updateList();
	}

	public void getCategories(int branchId) {
		String serverURL = new myURL("categories", "branches", branchId, 30)
				.getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "setCategories", true);
		p.get();
	}

	public void setCategories(String s, String error) {
		categories = new APIManager().getCategoriesByBranch(s);
		mylist = new ArrayList<Item>();
		for (Category myCat : categories) {
			Item it = new Item();
			it.setName(myCat.toString());
			it.setType("txtImg");
			it.setId(myCat.getId());
			it.setPhotoName(myCat.getPhotoName());
			mylist.add(it);
		}
		updateList();
	}

	public void getProducts(int branchId, int categoryId) {
		String serverURL = new myURL("items", "branches/" + branchId
				+ "/categories", categoryId, 30).getURL();
		
		RZHelper p = new RZHelper(serverURL, currentActivity, "setProducts", true,true);
		p.get();
	}

	public void setProducts(String s, String error) {
		products = new APIManager().getItemsByCategoryAndBranch(s);
		mylist = new ArrayList<Item>();
		for (Product myProduct : products) {
			Item it = new Item();
			it.setName(myProduct.toString());
			it.setType("product");
			it.setPrice(myProduct.getPrice());
			it.setId(myProduct.getId());
			mylist.add(it);
		}
		updateList();
	}

	public static void getBusinesses() {
		depth = 0;		
		shopId = 0;
		branchId = 0;
		categoryId = 0;
		productId = 0;
		String serverURL = new myURL("businesses", null, 0, 30).getURL();
		
		RZHelper p = new RZHelper(serverURL, currentActivity, "setBusinesses", true);
		p.get();
	}

	public void setBusinesses(String s, String error) {
		businesses = new APIManager().getBusinesses(s);
		mylist = new ArrayList<Item>();
		for (Business myBusin : businesses) {
			Item it = new Item();
			it.setName(myBusin.toString());
			it.setType("businesses");
			it.setPhotoName(myBusin.getPhoto());
			it.setId(myBusin.getId());
			mylist.add(it);
		}
		updateList();
	}

	public void updateList() {
		if(depth ==0)
		{
			buttonOne.setVisibility(View.GONE);
		}
		else
		{
			buttonOne.setVisibility(View.VISIBLE);
		}
		if(depth<=2)
		{
			footer.setVisibility(View.GONE);
		}
		else
		{
			footer.setVisibility(View.VISIBLE);
		}
		final ListView listView = (ListView) view.findViewById(R.id.orderslist);
		if (mylist.size() == 0) {
			Item i = new Item();
			i.setId(0);
			i.setName(currentActivity.getString(R.string.empty_list));
			i.setType("empty");
			mylist.add(i);
		}
		listView.setAdapter(new MyCustomAdapter(currentActivity,
				R.layout.row_txtimg, mylist));

		listView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (!mylist.get(0).getType().equals("empty")) {
					if(mylist.get(position).getType().equalsIgnoreCase("branch")&& !mylist.get(position).isBranchIsOpened()){
						Toast.makeText(currentActivity, ErrorHandlerManager.getInstance().getErrorString(currentActivity, "Sorry the Shop is closed now.."),Toast.LENGTH_SHORT).show();
						return;
					}
					int itemId = mylist.get(position).getId();
					if(depth==0)
					{
						((DeliveryClientApplication) currentActivity.getApplication()).setBusinessId(itemId);
					}
					depth++;
					getList(sequence.get(depth), itemId);
					listView.setAdapter(new MyCustomAdapter(currentActivity,R.layout.row_txtimg, mylist));
				}
			}
		});
	}

	public static void updateFooter() {
		Cart cart = ((DeliveryClientApplication) currentActivity.getApplication()).getMyCart();
		TextView quantity = (TextView) view.findViewById(R.id.totalQuantity);
		//TextView price = (TextView) view.findViewById(R.id.totalprice);
		//int totalPrice = 0;
		/*for (CartItem myP : cart.getCartItems()) {
			totalPrice += (myP.getCount() * myP.getProduct().getPrice());
		}*/
		quantity.setText("" + cart.getAllCount());
		MainActivity.updateCounter(cart.getAllCount());
	}
}