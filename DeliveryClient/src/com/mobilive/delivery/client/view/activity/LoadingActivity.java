package com.mobilive.delivery.client.view.activity;


import java.util.ArrayList;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.Area;
import com.mobilive.delivery.client.model.City;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;

public class LoadingActivity extends Activity {

	int i, countryP, cityP, areaP;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	int CityId;
	boolean last = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();

		setContentView(R.layout.activity_loading);
		getCountries();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.loading, menu);
		return true;
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("finish"))
			finish(s, error);
	}

	public void getCountries() {
		String serverURL = new myURL("countries/get_all_cities_areas", null, 0,30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "finish", true, false);
		p.get();
	}

	
	public void finish(String s, String error) {
		countries = new APIManager().getAllCountries(s);

		((DeliveryClientApplication) this.getApplication()).setCountries(countries);
		Log.d("ray", "setting countries: " + CityId + "->" + areas.size());
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);

	}
}
