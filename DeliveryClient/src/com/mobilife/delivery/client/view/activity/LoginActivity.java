package com.mobilife.delivery.client.view.activity;


import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.model.Area;
import com.mobilife.delivery.client.model.City;
import com.mobilife.delivery.client.model.Country;
import com.mobilife.delivery.client.model.Customer;
import com.mobilife.delivery.client.model.User;
import com.mobilife.delivery.client.utilities.APIManager;
import com.mobilife.delivery.client.utilities.ErrorHandlerManager;
import com.mobilife.delivery.client.utilities.PhoneInfoManager;
import com.mobilife.delivery.client.utilities.RZHelper;
import com.mobilife.delivery.client.utilities.myURL;
import com.mobilife.delivery.client.R;

public class LoginActivity extends Activity {

	private EditText username;
	private EditText password;
	boolean isChecked = false;
	int i, countryP, cityP, areaP;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	int CityId;
	boolean last = false;

	Customer user;
	private TextView forgotpassword, loginTxt;
	private CheckBox keeploggedin;
	private Button submit;
	String lang ;
	private TextView register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		register = (TextView) findViewById(R.id.register);
		
		SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
		isChecked = settings1.getBoolean("isChecked", false);

		lang = settings1.getString("lang", null);
		if (lang != null) {
			if (lang.equals("en")) {
				changeLangById(R.id.english);
			} else {
				changeLangById(R.id.arabic);
			}
		}else
			lang = "en";
		i++;

		if (isChecked) {
			((DeliveryClientApplication) this.getApplication()).setGlobals();
			Intent i = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(i);
		} else {
			settings1.edit().remove("PREFS_NAME").commit();
		}
		TextView forgetPassword = (TextView) findViewById(R.id.forgotpassword);
		forgetPassword.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
				startActivity(i);
			}
		});
		
		
		register.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(i);
			}
		});

	}
	public void changeLang(View view) {
		int viewId = view.getId();
		changeLangById(viewId);
	}

	@SuppressLint("NewApi")
	public void changeLangById(int viewId) {
		String lang_ab = "en";
		switch (viewId) {
		case R.id.english:
			lang_ab = "en";
			break;
		case R.id.arabic:
			lang_ab = "ar";
			break;
		}
		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);
		forgotpassword = (TextView) findViewById(R.id.forgotpassword);
		loginTxt = (TextView) findViewById(R.id.login);
		keeploggedin = (CheckBox) findViewById(R.id.keeploggedin);
		submit = (Button) findViewById(R.id.submit);

		Locale locale = new Locale(lang_ab);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

		SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("lang", lang_ab);
		editor.commit();

		int bgId = 0;
		ImageView img = (ImageView) findViewById(viewId);
		switch (viewId) {
		case R.id.english:
			img = (ImageView) findViewById(R.id.english);
			if (img != null) {
				img.setImageResource(R.drawable.arlanguage);
				img.setId(R.id.arabic);
			}
			bgId = R.drawable.phonebg;
			break;
		case R.id.arabic:
			img = (ImageView) findViewById(R.id.arabic);
			img.setImageResource(R.drawable.enlanguage);
			img.setId(R.id.english);
			bgId = R.drawable.phonebgar;
			break;
		}
		int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			username.setBackground(getResources().getDrawable(bgId));
		} else {
			username.setBackground(getResources().getDrawable(bgId));
		}
		username.setText(null);
		username.setHint(getString(R.string.phonenum));
		password.setHint(getString(R.string.password));
		forgotpassword.setText(getString(R.string.forgotpass));
		keeploggedin.setText(getString(R.string.keeploggedin));
		loginTxt.setText(getString(R.string.login));
		register.setText(getString(R.string.register));
		submit.setText(getString(R.string.enter));
	}

	public void login(View view) {
		
		if(_validateLogIn(username,password)){
			if(PhoneInfoManager.containZeroAsPrefix(username.getText().toString())){
				Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, "Phone Number can not start with zero.."),Toast.LENGTH_SHORT).show();
				return;
			}
			String countryCode= "961";
			String serverURL = new myURL(null, "customers", "login", 0).getURL();
			User user = new User(countryCode+username.getText().toString(), null);
			user.setEncPassword(password.getText().toString());
			RZHelper p = new RZHelper(serverURL, this, "getLoggedIn", true);
			p.post(user);
		}else{
			Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, "please enter User Name and Password"),Toast.LENGTH_SHORT).show();
		}
	}

	public void getLoggedIn(String s, String error) {
		if (error == null) {
			if (s != null) {
				user = new APIManager().getLogedInUser(s);
				if (user != null) {
					CheckBox keeplog = (CheckBox) findViewById(R.id.keeploggedin);
					SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
					SharedPreferences.Editor editor = settings.edit();

					editor.putInt("id", user.getId());
					editor.putString("lang", lang);
					editor.commit();

					editor.putBoolean("isChecked", keeplog.isChecked());
					editor.putString("token", user.getToken());
					editor.putString("name", user.getName());
					editor.putString("pass", password.getText().toString());
					editor.putString("phone", username.getText().toString());
					editor.putString("gender", user.getGender().toString());
					editor.commit();
					((DeliveryClientApplication) this.getApplication()).setGlobals();
					Intent i = new Intent(this, LoadingActivity.class);
					startActivity(i);
				}else {
					Toast.makeText(getApplicationContext(), R.string.no_net, Toast.LENGTH_LONG).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), R.string.no_net, Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(this, error),Toast.LENGTH_SHORT).show();
		}
	}

	public void changeLangs(View view) {
		String lang_ab = "en";

		switch (view.getId()) {
		case R.id.english:
			lang_ab = "en";
			break;
		case R.id.arabic:
			lang_ab = "ar";
			break;
		}

		Locale locale = new Locale(lang_ab);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());

		SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("lang", lang_ab);
		editor.commit();
		Intent i = new Intent(LoginActivity.this, LoginActivity.class);
		startActivity(i);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("getLoggedIn"))
			getLoggedIn(s, error);

	}
	
	private boolean _validateLogIn(EditText userName, EditText pass) {
		return  userName.getText()!=null && userName.getText().length()>0 
				&& pass.getText()!=null && pass.getText().length()>0;
	}

	public void forgotpassword(View view) {
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exit)
				.setMessage(R.string.exitquest)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							@SuppressLint("NewApi")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								LoginActivity.this.finishAffinity();
							}
						}).setNegativeButton(R.string.no, null).show();
	}
}
