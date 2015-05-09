package com.mobilife.delivery.client.view.fragment;


import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.model.Address;
import com.mobilife.delivery.client.model.Area;
import com.mobilife.delivery.client.model.Business;
import com.mobilife.delivery.client.model.Cart;
import com.mobilife.delivery.client.model.City;
import com.mobilife.delivery.client.model.Country;
import com.mobilife.delivery.client.model.Item;
import com.mobilife.delivery.client.utilities.ErrorHandlerManager;
import com.mobilife.delivery.client.view.activity.MainActivity;
import com.mobilife.delivery.client.R;

public class HomeFragment extends ParentFragment implements OnItemSelectedListener {
	
	View view;
	
	Cart cart;
	
	View myview;
	Activity currentActivity;
	ArrayList<Item> mylist = new ArrayList<Item>();
	
	FragmentManager fragmentManager;
	
	ArrayList<Business> businesses = new ArrayList<Business>();
	ArrayList<Address> Addresses;

	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	
	Fragment mContent;
	Spinner countrySpinner, citySpinner, areaSpinner;
	
	int fragmentId;
	int i, countryP, cityP, areaP;
	boolean last = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		
		currentActivity = getActivity();
		if (savedInstanceState != null) {
			mContent = getFragmentManager().getFragment(savedInstanceState,"mContent");
		}
		((DeliveryClientApplication) currentActivity.getApplication()).emptyCart();
		fragmentId = this.getId();
		fragmentManager = getFragmentManager();
		view = inflater.inflate(R.layout.my_main, container, false);
		countrySpinner = (Spinner) view.findViewById(R.id.countriesSP);
		citySpinner = (Spinner) view.findViewById(R.id.citiesSP);
		areaSpinner = (Spinner) view.findViewById(R.id.areasSP);

		citySpinner.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (cities!=null && cities.size() <= 0) {
					citySpinner.setClickable(false);
					Toast.makeText(v.getContext(), ErrorHandlerManager.getInstance().getErrorString(currentActivity, "No Cities exist in this Country"),Toast.LENGTH_SHORT).show();
				}else{
					citySpinner.setClickable(true);
				}
				return false;
			}
		});

		areaSpinner.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (areas!=null && areas.size() <= 0) {
					areaSpinner.setClickable(false);
					Toast.makeText(v.getContext(), ErrorHandlerManager.getInstance().getErrorString(currentActivity, "No Areas exist in this City"),Toast.LENGTH_SHORT).show();
				}else{
					areaSpinner.setClickable(true);
				}
				return false;
			}
		});

		Button buttonOne = (Button) view.findViewById(R.id.search);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				search();
			}
		});
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((DeliveryClientApplication) currentActivity.getApplication()).setCurrentFragment(this);
		cart = ((DeliveryClientApplication) currentActivity.getApplication()).getMyCart();
		countries = ((DeliveryClientApplication) currentActivity.getApplication()).getCountries();
		updateList("country");
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		Object sp1 = arg0.getSelectedItem();
		if (sp1 instanceof Country) {
			getCachedCities(position);
		} else if (sp1 instanceof City) {			
			getCachedAreas(position);
		}
	}

	public void getCachedCities(int CountryId) {
		cities = countries.get(CountryId).getCities();
		updateList("city");
	}

	public void getCachedAreas(int CityId) {
		areas = cities.get(CityId).getAreas();
		updateList("area");
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	public void updateList(String type) {

		((DeliveryClientApplication) currentActivity.getApplication()).setDepths(0,0);
		Log.d("ray","empty");
		countrySpinner = (Spinner) view.findViewById(R.id.countriesSP);
		citySpinner = (Spinner) view.findViewById(R.id.citiesSP);
		areaSpinner = (Spinner) view.findViewById(R.id.areasSP);
		if (type.equals("country")) {
			ArrayAdapter<Country> counrytAdapter = new ArrayAdapter<Country>(
					currentActivity, android.R.layout.simple_spinner_item,
					countries);
			counrytAdapter
			.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			counrytAdapter.notifyDataSetChanged();
			ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(currentActivity, android.R.layout.simple_spinner_item,new ArrayList<Area>());
			areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaSpinner.setAdapter(areaAdapter);
			areaAdapter.notifyDataSetChanged();
			if (countrySpinner != null) {
				countrySpinner.setAdapter(counrytAdapter);
				countrySpinner.setOnItemSelectedListener(this);
			}
		} else if (type.equals("city")) {
			ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(currentActivity, android.R.layout.simple_spinner_item,cities);
			cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cityAdapter.notifyDataSetChanged();
			ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(currentActivity, android.R.layout.simple_spinner_item,new ArrayList<Area>());
			areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaSpinner.setAdapter(areaAdapter);
			areaAdapter.notifyDataSetChanged();
			citySpinner.setAdapter(cityAdapter);
			citySpinner.setOnItemSelectedListener(this);
		} else if (type.equals("area")) {
			ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(currentActivity, android.R.layout.simple_spinner_item,areas);
			areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaSpinner.setAdapter(areaAdapter);
			areaAdapter.notifyDataSetChanged();
			areaSpinner.setOnItemSelectedListener(this);
		}

	}

	public void search() {
		if (areas!=null && areas.size() >0 && areaSpinner.getSelectedItem()!=null){
			OrdersFragment ordersFragment = new OrdersFragment();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			MainActivity.fragments.add(ordersFragment);
			fragmentTransaction.replace(fragmentId, ordersFragment);	
			Bundle arguments = new Bundle();
			int areaId = ((Area)areaSpinner.getSelectedItem()).getId();
			arguments.putInt("areaId", areaId);
			((DeliveryClientApplication) currentActivity.getApplication()).setAreaId(areaId);
			ordersFragment.setArguments(arguments);
			fragmentTransaction.commit();
		}else
			Toast.makeText(currentActivity.getApplicationContext(),"No Area Selected..", Toast.LENGTH_SHORT).show();
	}
}
