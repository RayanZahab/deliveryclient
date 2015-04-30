package com.mobilive.delivery.client.view.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.ForgetPasswordRequest;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.ErrorHandlerManager;
import com.mobilive.delivery.client.utilities.PhoneInfoManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;

public class ForgetPasswordActivity extends Activity implements OnItemSelectedListener {

	private EditText mobileNumberEditText;
	private ArrayList<Country> countries;
	private Spinner countrySpinner;
	private EditText countrycode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_initActivityView();
	}

	private void _initActivityView() {
		_getCountries();		
	}
	
	private void _getCountries() {
		String serverURL = new myURL("countries", null, 0,30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "getCountries", true);
		p.get();
	}

	public void getNewPass(View view){
		if(mobileNumberEditText.getText()!=null && !mobileNumberEditText.getText().toString().equals("")){
				ForgetPasswordRequest forgetPasswordRequest = new ForgetPasswordRequest();
				forgetPasswordRequest.setMobileNumber(countrycode.getText().toString()+mobileNumberEditText.getText().toString());
				forgetPasswordRequest.setMobileImei(PhoneInfoManager.getPhoneImei(getApplicationContext()));
				_checkCodeRequest(forgetPasswordRequest);
		}else{
			Toast.makeText(getApplicationContext(), getString(R.string.userAddNumber),Toast.LENGTH_SHORT).show();
		}
	}
	private void _checkCodeRequest(ForgetPasswordRequest forgetPasswordRequest) {	
		String serverURL = new myURL("customers/forget_password", null, 0,30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "finish", true);
		p.post(forgetPasswordRequest);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("finish"))
			finish(s, error);
		else if(m.equals("getCountries")){
				countries = new APIManager().getCountries(s);
				_initView();
				_initCountrySpinner();
				_initActionBar();
		}
	}

	private void _initView() {
		setContentView(R.layout.activity_forget_password);		
	}
	private void _initCountrySpinner(){
		countrySpinner = (Spinner) findViewById(R.id.countriesSP);
		ArrayAdapter<Country> countryAdapter = new ArrayAdapter<Country>(this, android.R.layout.simple_spinner_item,countries);
		countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		countrySpinner.setAdapter(countryAdapter);
		countryAdapter.notifyDataSetChanged();
		countrySpinner.setOnItemSelectedListener(this);
	}
	
	private void finish(String s, String error) {
		if(error==null || !(error.length()>0)){
			Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, "A new Password was sent by SMS."),Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(this,LoginActivity.class);
			startActivity(intent);
		}
	}
	private void _initActionBar() {
		mobileNumberEditText = (EditText) findViewById(R.id.name);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.forget_password, menu);
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
		Object selObject = arg0.getSelectedItem();
		if (selObject !=null && selObject instanceof Country) {
			String countryCode = ((Country)selObject).getCountrCode();
			countrycode = (EditText) findViewById(R.id.countrycode);
			countrycode.setText(countryCode);
			
		} 	
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
