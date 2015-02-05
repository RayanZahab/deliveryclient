package info.androidhive.tabsswipe;

import java.util.ArrayList;





import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class AddAddressActivity extends Activity implements
		OnItemSelectedListener {

	Spinner countrySpinner, citySpinner, areaSpinner;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	Activity currentActivity;
	static ArrayList<Item> mylist = new ArrayList<Item>();
	String previous ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_address);
		currentActivity = this;
		countrySpinner = (Spinner) findViewById(R.id.countriesSP);
		citySpinner = (Spinner) findViewById(R.id.citiesSP);
		areaSpinner = (Spinner) findViewById(R.id.areasSP);
		Bundle extras = getIntent().getExtras();
		if(extras!=null)
			previous = extras.getString("previous");
		getCountries();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void submit(View v) {
		//street building floor details
		EditText street = ((EditText) findViewById(R.id.street));
		EditText building = ((EditText) findViewById(R.id.building));
		EditText floor = ((EditText) findViewById(R.id.floor));
		EditText details = ((EditText) findViewById(R.id.details));
		
		Address newAdd = new Address();
		newAdd.setStreet(street.getText().toString());
		newAdd.setBuilding(building.getText().toString());
		newAdd.setFloor(floor.getText().toString());
		newAdd.setDetails(details.getText().toString());
		newAdd.setCountry(""+((Country)countrySpinner.getSelectedItem()).getId());
		newAdd.setCity(""+((City)citySpinner.getSelectedItem()).getId());
		newAdd.setArea(""+((Area)areaSpinner.getSelectedItem()).getId());
		newAdd.setDefault(true);

		String serverURL = new myURL(null, "customers", "addresses", 0).getURL();
		RZHelper p = new RZHelper(serverURL, currentActivity, "afterCreation", true);
		p.post(newAdd);
		
	}
	public void callMethod(String m, String s, String error) {
		Intent i = new Intent(this, SelectAdress.class);
		i.putExtra("previous", previous);
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
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
		if (type.equals("country")) {
			ArrayAdapter<Country> counrytAdapter = new ArrayAdapter<Country>(
					this, android.R.layout.simple_spinner_item, countries);
			counrytAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			counrytAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(null);
			countrySpinner.setAdapter(counrytAdapter);
			countrySpinner.setOnItemSelectedListener(this);
		} else if (type.equals("city")) {
			ArrayAdapter<City> cityAdapter = new ArrayAdapter<City>(this,
					android.R.layout.simple_spinner_item, cities);
			cityAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			cityAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(null);
			citySpinner.setAdapter(cityAdapter);
			citySpinner.setOnItemSelectedListener(this);
		} else if (type.equals("area")) {
			ArrayAdapter<Area> areaAdapter = new ArrayAdapter<Area>(this,
					android.R.layout.simple_spinner_item, areas);
			areaAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			areaAdapter.notifyDataSetChanged();
			areaSpinner.setAdapter(areaAdapter);
			areaSpinner.setOnItemSelectedListener(this);
		}

	}

}
