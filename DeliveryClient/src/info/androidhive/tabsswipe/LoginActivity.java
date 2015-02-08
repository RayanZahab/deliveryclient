package info.androidhive.tabsswipe;

import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.user_name);
		password = (EditText) findViewById(R.id.password);

		SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
		isChecked = settings1.getBoolean("isChecked", false);

		String lang = settings1.getString("lang", null);
		if (lang != null) {
			if (lang.equals("en")) {
				changeLangById(R.id.english);
			} else {
				changeLangById(R.id.arabic);
			}
		}
		i++;

		if (isChecked) {
			((deliveryclient) this.getApplication()).setGlobals();
			Intent i = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(i);
		} else {
			settings1.edit().remove("PREFS_NAME").commit();
		}
		TextView register = (TextView) findViewById(R.id.register);
		register.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(LoginActivity.this,
						RegisterActivity.class);
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

		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

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
			username.setBackgroundDrawable(getResources().getDrawable(bgId));

			// password.setBackgroundDrawable(getResources().getDrawable(R.drawable.passwordbgar));
		} else {
			username.setBackground(getResources().getDrawable(bgId));
			// password.setBackground(getResources().getDrawable(R.drawable.passwordbgar));
		}
		username.setText(null);
		username.setHint(getString(R.string.username));
		password.setHint(getString(R.string.password));
		forgotpassword.setText(getString(R.string.forgotpass));
		keeploggedin.setText(getString(R.string.keeploggedin));
		loginTxt.setText(getString(R.string.login));
		submit.setText(getString(R.string.enter));
	}

	public void login(View view) {
		String serverURL = new myURL(null, "customers", "login", 0).getURL();

		User user = new User(username.getText().toString(), null);
		user.setEncPassword(password.getText().toString());

		RZHelper p = new RZHelper(serverURL, this, "getLoggedIn", true);
		p.post(user);
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
			Log.d("ray", "token: " + user.getToken());
			editor.putString("name", user.getName());
			editor.putString("pass", password.getText().toString());
			editor.putString("phone", username.getText().toString());
			editor.commit();
			((deliveryclient) this.getApplication()).setGlobals();
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		} else {
			Toast.makeText(getApplicationContext(), R.string.wrongcredentials,
					Toast.LENGTH_SHORT).show();
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

		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

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
