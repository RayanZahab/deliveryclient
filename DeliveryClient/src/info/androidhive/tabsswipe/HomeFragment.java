package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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
		((deliveryclient) currentActivity.getApplication()).emptyCart();
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
		((deliveryclient) currentActivity.getApplication()).setDepths(0,0);
		return view;
	}

	public void callMethod(String m, String s, String error) {
		

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
		
		updateList("country");
		
			
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
