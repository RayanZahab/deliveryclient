package com.mobilife.delivery.client.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.mobilife.delivery.client.DeliveryClientApplication;
import com.mobilife.delivery.client.R;

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
		((DeliveryClientApplication) this.getApplication()).emptyCart();
		Intent i = new Intent(this, MainActivity.class);
		i.putExtra("fragmentIndex", 3);
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
