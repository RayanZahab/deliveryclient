package com.mobilive.delivery.client.view.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.Customer;
import com.mobilive.delivery.client.model.User;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.ErrorHandlerManager;
import com.mobilive.delivery.client.utilities.GlobalM;
import com.mobilive.delivery.client.utilities.MyJs;
import com.mobilive.delivery.client.utilities.PhoneInfoManager;
import com.mobilive.delivery.client.utilities.PreferenecesManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.ValidationError;
import com.mobilive.delivery.client.utilities.myURL;
import com.mobilive.delivery.client.view.listview.SelectAdress;

public class ProfileFragment extends ParentFragment {
	Activity currentActivity;
	EditText nameTxt, usernameTxt, passTxt;
	EditText pass2;
	Spinner langSp;
	CheckBox keep;

	String name, phone, pass;
	String lang, lang_abv;
	int id;
	int branchId;
	User user;
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_profile, container, false);
		currentActivity = getActivity();
		super.onCreate(savedInstanceState);
		usernameTxt = (EditText) rootView.findViewById(R.id.user_name);
		nameTxt = (EditText) rootView.findViewById(R.id.name);
		passTxt = (EditText) rootView.findViewById(R.id.password);
		pass2 = (EditText) rootView.findViewById(R.id.password2);
		langSp = (Spinner) rootView.findViewById(R.id.languageSP);
		keep = (CheckBox) rootView.findViewById(R.id.keeploggedin);

		SharedPreferences settings1 = currentActivity.getSharedPreferences("PREFS_NAME", 0);
		boolean isChecked = settings1.getBoolean("isChecked", false);

		name = settings1.getString("name", "");
		pass = settings1.getString("pass", "");
		phone = settings1.getString("phone", "");
		lang = settings1.getString("lang", null);
		id = settings1.getInt("id", 0);
		branchId = settings1.getInt("branchId", 0);

		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.arabic));
		list.add(getString(R.string.english));
		if (lang.equals("en")) {
			lang = getString(R.string.english);
			lang_abv = "en";
		} else {
			lang = getString(R.string.arabic);
			lang_abv = "ar";
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(currentActivity,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		langSp.setAdapter(dataAdapter);

		nameTxt.setText(name);
		usernameTxt.setText(phone);
		passTxt.setText(pass);
		pass2.setText(pass);
		keep.setChecked(isChecked);
		new GlobalM().setSelected(langSp, dataAdapter, lang);
		Button buttonOne = (Button) rootView.findViewById(R.id.updateProfileBtn);
		buttonOne.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				updateInfo();
			}
		});
		Button addresses = (Button) rootView.findViewById(R.id.addresses);
		addresses.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				showAddresses();
			}
		});

		return rootView;
	}
	public void updateInfo() {
		if (!passTxt.getText().toString().equals(pass2.getText().toString())) {
			Toast.makeText(currentActivity, R.string.wrongcredentials,Toast.LENGTH_SHORT).show();
		}else {
			String serverURL = new myURL(null, "customers", id, 0).getURL();
			User user = new User(nameTxt.getText().toString(), phone, null, branchId, 0);
			user.setEncPassword(passTxt.getText().toString());
			user.setImei(PhoneInfoManager.getPhoneImei(currentActivity.getApplicationContext()));
			ValidationError valid = user.validate(true);
			if(valid.isValid(currentActivity))
				new MyJs("done", currentActivity, ((DeliveryClientApplication) currentActivity.getApplication()),"PUT", (Object) user, true, true).execute(serverURL);
		}
	}

	public void done(String s, String error) {
		if(error==null){
			String serverURL = new myURL(null, "customers", "login", 0).getURL();
			User user = new User("961"+phone, passTxt.getText().toString());
			user.setEncPassword(passTxt.getText().toString());
			RZHelper p = new RZHelper(serverURL, currentActivity, "getLoggedIn", true);
			p.post(user);
		}else{
			Toast.makeText(currentActivity.getApplicationContext(), ErrorHandlerManager.getInstance().getErrorString(getActivity(), error),Toast.LENGTH_SHORT).show();
		}
	}

	public void getLoggedIn(String s, String error) {
		if (error == null) {
			Customer user = new APIManager().getLogedInUser(s);
			CheckBox keeplog = (CheckBox) rootView.findViewById(R.id.keeploggedin);
			SharedPreferences settings = currentActivity.getSharedPreferences("PREFS_NAME", 0);
			SharedPreferences.Editor editor = settings.edit();

			editor.putBoolean("isChecked", keeplog.isChecked());
			editor.putString("token", user.getToken());
			editor.putString("name", user.getName());

			if (langSp.getSelectedItem().equals(getString(R.string.english))) {
				lang_abv = "en";
			} else {
				lang_abv = "ar";
			}

			editor.putString("lang", lang_abv);
			editor.putString("pass", pass);

			editor.commit();
			Log.d("ray","hereeeeeee");

			((DeliveryClientApplication) currentActivity.getApplication()).setGlobals();
			Locale locale = new Locale(lang_abv);
			Locale.setDefault(locale);
			Configuration config = new Configuration();
			config.locale = locale;
			currentActivity.getBaseContext().getResources().updateConfiguration(config,currentActivity.getBaseContext().getResources().getDisplayMetrics());
			new GlobalM().bkToNav(currentActivity, "Profile updated succ");
			
			
		} else {
			Toast.makeText(currentActivity.getApplicationContext(), R.string.wrongcredentials,Toast.LENGTH_SHORT).show();
		}
		Log.d("ray","hereeeeeee");
	}
	public void showAddresses()
	{
		Intent i = new Intent (currentActivity,SelectAdress.class);
		startActivity(i);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
	}
}
