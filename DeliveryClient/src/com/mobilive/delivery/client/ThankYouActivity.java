package com.mobilive.delivery.client;

import com.mobilive.delivery.client.view.activity.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ThankYouActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thank_you);
		
		TextView nameTxt = (TextView) findViewById(R.id.name);
		SharedPreferences settings1 = getSharedPreferences("PREFS_NAME", 0);
		String name = settings1.getString("name", "");
		nameTxt.setText(" " + name);
	}

	public void gotomain(View v) {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thank_you, menu);
		return true;
	}

	public void onBackPressed() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
