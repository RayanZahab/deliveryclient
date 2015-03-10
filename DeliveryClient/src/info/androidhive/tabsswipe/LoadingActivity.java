package info.androidhive.tabsswipe;

import java.util.ArrayList;

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
		Log.d("ray", "calling method: " + m);

		if (m.equals("setCountries"))
			setCountries(s, error);
		else if (m.equals("setCities"))
			setCities(s, error);
		else if (m.equals("setAreas"))
			setAreas(s, error);
	}

	public void getCountries() {
		String serverURL = new myURL("countries", null, 0, 30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "setCountries", true,false);
		p.get();
	}

	public void setCountries(String s, String error) {
		countries = new APIManager().getCountries(s);
		for (int j = 0; j < countries.size(); j++) {
			getCities(j);
		}
	}

	public void getCities(int position) {
		countryP = position;
		int countryId = countries.get(position).getId();
		String serverURL = new myURL("cities", "countries", countryId, 30)
				.getURL();
		RZHelper p = new RZHelper(serverURL, this, "setCities", false);
		p.get();
	}

	public void setCities(String s, String error) {
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
		RZHelper p = new RZHelper(serverURL, this, "setAreas", false, last);
		p.get();
	}

	public void setAreas(String s, String error) {
		areas = new APIManager().getAreasByCity(s);

		cities.get(cityP).setAreas(areas);
		countries.get(countryP).setCities(cities);
		if (last) {
			((deliveryclient) this.getApplication()).setCountries(countries);
			Log.d("ray", "setting countries: " + CityId + "->" + areas.size());
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		}
	}
}
