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
	static int fragmentId;
	static android.app.FragmentManager fragmentManager;
	android.app.Fragment mContent;
	int i, countryP, cityP, areaP;
	int CityId;
	boolean last = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		currentActivity = getActivity();
		if (savedInstanceState != null) {
			mContent = getFragmentManager().getFragment(savedInstanceState,
					"mContent");
		}
		fragmentId = this.getId();
		fragmentManager = getFragmentManager();
		view = inflater.inflate(R.layout.my_main, container, false);
		countrySpinner = (Spinner) view.findViewById(R.id.countriesSP);
		citySpinner = (Spinner) view.findViewById(R.id.citiesSP);
		areaSpinner = (Spinner) view.findViewById(R.id.areasSP);
		
		Button buttonOne = (Button) view.findViewById(R.id.search);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				search();
			}
		});
		return view;
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setHomeCountries"))
			setHomeCountries(s, error);
		else if (m.equals("setHomeCities"))
			setHomeCities(s, error);
		else if (m.equals("setHomeAreas"))
			setHomeAreas(s, error);

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
		countries = ((deliveryclient) currentActivity.getApplication()).getCountries();
		if(countries==null)
			getCountries();
		else
		{
			for (int j = 0; j < countries.size(); j++) {
				getCities(j);
			}
			updateList("country");
		}
			
		// ((MainActivity) currentActivity).getCountries();
		// updateFooter();
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
		Log.d("ray", "ray nothing");
	}

	public void getCountries() {
		String serverURL = new myURL("countries", null, 0, 30).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity,
				"setHomeCountries", true, false);
		p.get();
	}

	public void setHomeCountries(String s, String error) {
		countries = new APIManager().getCountries(s);
		for (int j = 0; j < countries.size(); j++) {
			getCities(j);
		}
		updateList("country");
	}

	public void getCities(int position) {
		countryP = position;
		int countryId = countries.get(position).getId();
		String serverURL = new myURL("cities", "countries", countryId, 30)
				.getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "setHomeCities",
				false);
		p.get();
	}

	public void setHomeCities(String s, String error) {
		cities = new APIManager().getCitiesByCountry(s);
		for (int j = 0; j < cities.size(); j++) {
			if (j == cities.size() - 1)
				last = true;
			getAreas(j);
		}
	}

	public void getAreas(int position) {
		cityP = position;
		CityId = cities.get(position).getId();
		String serverURL = new myURL("areas", "cities", CityId, 30).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "setHomeAreas",
				false, last);
		p.get();
	}

	public void setHomeAreas(String s, String error) {
		areas = new APIManager().getAreasByCity(s);

		cities.get(cityP).setAreas(areas);
		countries.get(countryP).setCities(cities);
		if (last) {
			((deliveryclient) currentActivity.getApplication())
					.setCountries(countries);
			Log.d("ray", "setting countries: " + CityId + "->" + areas.size());

		}
	}

	public void updateList(String type) {
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
			areaSpinner.setAdapter(null);
			Log.d("ray", "countr: " + countries.size());
			if (countrySpinner != null) {
				countrySpinner.setAdapter(counrytAdapter);
				countrySpinner.setOnItemSelectedListener(this);
			}
		} else if (type.equals("city")) {
			ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(
					currentActivity, android.R.layout.simple_spinner_item,
					cities);
			cityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cityAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(null);
			citySpinner.setAdapter(cityAdapter);
			citySpinner.setOnItemSelectedListener(this);
		} else if (type.equals("area")) {
			ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(
					currentActivity, android.R.layout.simple_spinner_item,
					areas);
			areaAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(areaAdapter);
			areaSpinner.setOnItemSelectedListener(this);
		}

	}

	public void search() {
		OrdersFragment fh = new OrdersFragment();
		android.app.FragmentTransaction ft = fragmentManager.beginTransaction();
		MainActivity.fragments.add(fh);
		ft.replace(fragmentId, fh);	
		Bundle arguments = new Bundle();
		Log.d("ray","sending myarea: "+((Area)areaSpinner.getSelectedItem()).getId());
        arguments.putInt("areaId", ((Area)areaSpinner.getSelectedItem()).getId());

		fh.setArguments(arguments);
		ft.commit();
	}
}