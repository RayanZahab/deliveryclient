package com.mobilive.delivery.client.view.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.ConfirmationMode;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Gender;
import com.mobilive.delivery.client.model.User;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.MyJs;
import com.mobilive.delivery.client.utilities.PhoneInfoManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.ValidationError;
import com.mobilive.delivery.client.utilities.myURL;

public class RegisterActivity extends Activity implements OnItemSelectedListener {

	private Spinner countrySpinner, genderSpinner;
	private ArrayList<Country> countries = new ArrayList<Country>();
	private EditText countrycode;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_initActivityView();
	}

	public void addUser(View view) {

		EditText username = (EditText) findViewById(R.id.name);
		EditText inputphone = (EditText) findViewById(R.id.phone);
		EditText pass = (EditText) findViewById(R.id.password);
		EditText pass2 = (EditText) findViewById(R.id.password2);

		String serverURL = new myURL(null, "customers", "register", 0).getURL();
		String method = "Upload";

		if(_validatePass(pass,pass2))
		{
			User user = new User();
			user.setName(username.getText().toString());
			user.setPhone(countrycode.getText()+""+inputphone.getText().toString());
			user.setImei(PhoneInfoManager.getPhoneImei(getApplicationContext()));
			user.setEncPassword(pass.getText().toString());
			user.setGender(_getSelectedGender());
			ValidationError valid = user.validate(false);

			if (valid.isValid(this)) {
				this.user = user;
				new MyJs("returnErrorMsg", this, ((DeliveryClientApplication) this.getApplication()),method, (Object) user, true,true).execute(serverURL);
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Pass is empty or do not match",Toast.LENGTH_SHORT).show();
		}
	}

	
	private boolean _validatePass(EditText pass, EditText pass2) {
		return  pass.getText()!=null && pass.getText().length()>0 
				&& pass2.getText()!=null && pass2.getText().length()>0 
				&& pass.getText().toString().equals(pass2.getText().toString());
	}

	private Gender _getSelectedGender() {
		Object selectedItem = genderSpinner.getSelectedItem();
		if(selectedItem!=null)
			return (Gender)selectedItem;
		return Gender.Male;
	}

	private void _initActivityView() {
		_getCountries();		
	}

	private void _initCountrySpinner(){
		countrySpinner = (Spinner) findViewById(R.id.countriesSP);
		ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(this, android.R.layout.simple_spinner_item,countries);
		countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		countrySpinner.setAdapter(countryAdapter);
		countryAdapter.notifyDataSetChanged();
		countrySpinner.setOnItemSelectedListener(this);
	}

	private void _initView() {
		setContentView(R.layout.activity_register);		
	}

	private void _initGenderSpinner() {
		genderSpinner = (Spinner) findViewById(R.id.genderSP);
		ArrayAdapter<Gender> genderAdapter = new ArrayAdapter<Gender>(this, android.R.layout.simple_spinner_item,Gender.values());
		genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		genderSpinner.setAdapter(genderAdapter);
		genderAdapter.notifyDataSetChanged();
	}

	private void _initActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void _getCountries() {
		String serverURL = new myURL("countries", null, 0,30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "finish", true);
		p.get();
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("finish"))
			finish(s, error);
		else if (m.equals("returnErrorMsg")){
			if(error==null || !(error.length()>0)){
				Intent intent = new Intent(RegisterActivity.this,RegisterConfirmationActivity.class);
				intent.putExtra("registerUser", user);
				intent.putExtra("confirmationMode", ConfirmationMode.NewUser);
				startActivity(intent);
			}
		}
	}

	private void finish(String s, String error) {
		countries = new APIManager().getCountries(s);
		_initView();
		_initCountrySpinner();
		_initGenderSpinner();
		_initActionBar();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long arg3) {
		Object selObject = arg0.getSelectedItem();
		if (selObject !=null && selObject instanceof Country) {
			String countryCode = ((Country)selObject).getCountrCode();
			countrycode = (EditText) findViewById(R.id.countrycode);
			countrycode.setText(countryCode);
			
		} 	
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}