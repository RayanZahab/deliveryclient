package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText username;
	private EditText password;
	boolean isChecked = false;
	int i, countryP, cityP, areaP;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);

		SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
		isChecked = settings1.getBoolean("isChecked", false);
		i++;

		if (isChecked) {
			((deliveryclient) this.getApplication()).setGlobals();
			Intent i = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(i);
		}
		RelativeLayout register = (RelativeLayout) findViewById(R.id.register);
		register.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(i);
			}
		});

	}

	public void login(View view) {
		String serverURL = new myURL(null, "users", "login", 0).getURL();
		User user = new User(username.getText().toString(), null);
		user.setEncPassword(password.getText().toString());
		MyJs mjs = new MyJs("getLoggedIn", this,
				((deliveryclient) this.getApplication()), "POST", (Object) user,true,false);
		mjs.execute(serverURL);
	}
	
	public void getLoggedIn(String s, String error) {
		if (error == null) {
			user = new APIManager().getLogedInUser(s);
			CheckBox keeplog = (CheckBox) findViewById(R.id.keeploggedin);
			SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
			SharedPreferences.Editor editor = settings.edit();

			editor.putInt("id", user.getId());
			
			editor.putBoolean("isChecked", keeplog.isChecked());
			editor.putString("token", user.getToken());
			editor.putString("name", user.getName());
			editor.putString("pass", password.getText().toString());
			editor.putString("phone", username.getText().toString());
			editor.putBoolean("admin", user.isIs_admin());
			editor.putBoolean("preparer", user.isIs_preparer());
			editor.putBoolean("delivery", user.isIs_delivery());
			editor.putInt("shopId", 6);
			editor.putInt("branchId", user.getBranch_id());
			editor.commit();
			((deliveryclient) this.getApplication()).setGlobals();
			getAddresses(user.getId());
		} else {
			Toast.makeText(getApplicationContext(), R.string.wrongcredentials,
					Toast.LENGTH_SHORT).show();
		}
	}
	
	public void getAddresses(int userId) {
		String serverURL = new myURL("addresses", "customers", 1, 0).getURL();
		MyJs mjs = new MyJs("getAdd", this,
				((deliveryclient) this.getApplication()), "GET", false, true);
		mjs.execute(serverURL);

	}

	public void getAdd(String s, String error) {
		if (error == null) {
			ArrayList<Address> address = new APIManager().getAddress(s);
			SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
			SharedPreferences.Editor editor = settings.edit();
			if (address.size() > 0) {
				editor.putInt("addressId", address.get(0).getId());
				editor.commit();
			}
			((deliveryclient) this.getApplication()).setGlobals();
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		}
	}
	

	
	public void callMethod(String m, String s, String error) {
		if (m.equals("getLoggedIn"))
			getLoggedIn(s, error);
		else if (m.equals("getAdd"))
			getAdd(s, error);
	}
	public void forgotpassword(View view) {
		// Intent i = new Intent(LoginActivity.this,
		// ForgotPasswordActivity.class);
		// startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exit)
				.setMessage(R.string.exitquest)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@SuppressLint("NewApi")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								LoginActivity.this.finishAffinity();
							}
						}).setNegativeButton("No", null).show();
	}

}
