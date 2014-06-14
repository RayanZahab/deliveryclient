package info.androidhive.tabsswipe;

import java.util.Locale;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SelectLanguageActivity extends Activity {
	MyCustomAdapter dataAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActionBar actionBar = getActionBar();
		actionBar.hide();
		setContentView(R.layout.activity_select_language);

		SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
		String lang = settings1.getString("lang", null);
		if (lang != null) {
			Intent i = new Intent(SelectLanguageActivity.this,
					LoginActivity.class);
			startActivity(i);
		}
	}

	public void select(View view) {
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
		Intent i = new Intent(SelectLanguageActivity.this, LoginActivity.class);
		startActivity(i);
	}

	public void gotobutton(View view) {
		String method = null;
		switch (view.getId()) {
		case R.id.about:
			method = null;
			Toast.makeText(getApplicationContext(),
					"Developed By Array Fusion", Toast.LENGTH_SHORT)
					.show();
			break;
		}
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
								SelectLanguageActivity.this.finishAffinity();
							}
						}).setNegativeButton("No", null).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.navigation, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		//if (SharedMenu.onOptionsItemSelected(item, this) == false) {
			// handle local menu items here or leave blank
		//}
		return super.onOptionsItemSelected(item);
	}
}
