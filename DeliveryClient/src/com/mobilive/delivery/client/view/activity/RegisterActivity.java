package com.mobilive.delivery.client.view.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.mobilive.delivery.client.utilities.ErrorHandlerManager;
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
	private Button addUserBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		_initActivityView();
	}

	public void addUser() {

		EditText username = (EditText) findViewById(R.id.name);
		EditText inputphone = (EditText) findViewById(R.id.phone);
		EditText pass = (EditText) findViewById(R.id.password);
		EditText pass2 = (EditText) findViewById(R.id.password2);

		String serverURL = new myURL(null, "customers", "register", 0).getURL();
		String method = "Upload";

		if(PhoneInfoManager.containZeroAsPrefix(inputphone.getText().toString())){
			Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, "Phone Number can not start with zero.."),Toast.LENGTH_SHORT).show();
			return;
		}
		User user = new User();
		user.setName(username.getText().toString());
		user.setPhone(countrycode.getText()+""+inputphone.getText().toString());
		user.setImei(PhoneInfoManager.getPhoneImei(getApplicationContext()));
		user.setEncPassword(pass.getText().toString());
		user.setGender(_getSelectedGender());
		ValidationError valid = user.validate(false);
        if(valid!=null && valid.getErrorMsgId()!=0 && getString(new Integer(valid.getErrorMsgId()))!=null){
        	Toast.makeText(this, getString(new Integer(valid.getErrorMsgId())),Toast.LENGTH_SHORT).show();
        	return;
        }
        
		if(_validatePass(pass,pass2))
		{
			if (valid.isValid(this)) {
				this.user = user;
				new MyJs("returnErrorMsg", this, ((DeliveryClientApplication) this.getApplication()),method, (Object) user, true,true).execute(serverURL);
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, "Pass is empty or do not match"),Toast.LENGTH_SHORT).show();
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
		addUserBtn = (Button)findViewById(R.id.addUserBtn);
		addUserBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				addUser();
				
			}
		});
		
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
			}else
				Toast.makeText(this, ErrorHandlerManager.getInstance().getErrorString(this, error),Toast.LENGTH_SHORT).show();
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