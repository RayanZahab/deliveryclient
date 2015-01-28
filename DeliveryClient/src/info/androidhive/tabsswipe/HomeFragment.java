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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class HomeFragment extends ParentFragment implements
		OnItemSelectedListener {
	Cart cart;
	public static View view;
	static View myview;
	ArrayList<Address> Addresses;
	Spinner countrySpinner, citySpinner, areaSpinner;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	static Activity currentActivity;
	static ArrayList<Item> mylist = new ArrayList<Item>();
	String previous;
	ArrayList<Business> businesses = new ArrayList<Business>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentActivity = getActivity();
		view = inflater.inflate(R.layout.my_main, container, false);
		countrySpinner = (Spinner) view.findViewById(R.id.countriesSP);
		citySpinner = (Spinner) view.findViewById(R.id.citiesSP);
		areaSpinner = (Spinner) view.findViewById(R.id.areasSP);

		return view;
	}

	public void callMethod(String m, String s, String error) {
		// if (m.equals("getAdd"))
		// getAdd(s, error);
	}

	public void submitCart() {
		// getAddresses();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((deliveryclient) currentActivity.getApplication())
				.setCurrentFragment(this);
		cart = ((deliveryclient) currentActivity.getApplication()).getMyCart();
		getBusinesses();
		//((MainActivity) currentActivity).getCountries();
		//updateFooter();
		

	}
	
	public static void getBusinesses() {		
		String serverURL = new myURL("businesses", null, 0, 30).getURL();
		
		RZHelper p = new RZHelper(serverURL, currentActivity, "setBusinesses", true);
		p.get();
	}

	public void setBusinesses(String s, String error) {
		/*
		businesses = new APIManager().getBusinesses(s);
		mylist = new ArrayList<Item>();
		for (Business myCountry : businesses) {
			Item it = new Item();
			it.setName(myCountry.toString());
			it.setType("txt");
			it.setId(myCountry.getId());
			mylist.add(it);
		}
		updateList();
		*/
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {

		Object sp1 = arg0.getSelectedItem();
		if (sp1 instanceof Country) {
			getCities(position);
		} else if (sp1 instanceof City) {
			getAreas(position);
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		Log.d("ray", "ray nothing");
	}

	public void getCountries() {
		countries = ((deliveryclient) currentActivity.getApplication())
				.getCountries();
		updateList("country");
	}

	public void getCities(int CountryId) {
		cities = countries.get(CountryId).getCities();
		updateList("city");
	}

	public void getAreas(int CityId) {
		areas = cities.get(CityId).getAreas();
		updateList("area");
	}

	public void updateList(String type) {
		countrySpinner = (Spinner) view.findViewById(R.id.countriesSP);
		citySpinner = (Spinner) view.findViewById(R.id.citiesSP);
		areaSpinner = (Spinner) view.findViewById(R.id.areasSP);
		if (type.equals("country")) {
			ArrayAdapter<Country> counrytAdapter = new ArrayAdapter<Country>(
					currentActivity, android.R.layout.simple_spinner_item, countries);
			counrytAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			counrytAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(null);
			Log.d("ray","countr: "+countries.size());
			if(countrySpinner!=null)
			{
			//countrySpinner.setAdapter(counrytAdapter);
			//countrySpinner.setOnItemSelectedListener(this);
				}
		} else if (type.equals("city")) {
			ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(currentActivity,
					android.R.layout.simple_spinner_item, cities);
			cityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cityAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(null);
			citySpinner.setAdapter(cityAdapter);
			citySpinner.setOnItemSelectedListener(this);
		} else if (type.equals("area")) {
			ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(currentActivity,
					android.R.layout.simple_spinner_item, areas);
			areaAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(areaAdapter);
			areaSpinner.setOnItemSelectedListener(this);
		}

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
		price.setText("" + totalPrice
				+ currentActivity.getString(R.string.lira));
		quantity.setText("" + cart.getAllCount());
		MainActivity.updateCounter(cart.getAllCount());
	}
}
