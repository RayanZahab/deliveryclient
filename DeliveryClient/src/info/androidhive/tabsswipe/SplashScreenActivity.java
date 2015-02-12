package info.androidhive.tabsswipe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashScreenActivity extends Activity {

	// Splash screen timer
	private static int SPLASH_TIME_OUT = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();

		setContentView(R.layout.activity_splash_screen);

		new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

			@Override
			public void run() {
				// This method will be executed once the timer is over
				// Start your app main activity
				SharedPreferences settings1 = getSharedPreferences(
						"PREFS_NAME", 0);
				String lang = settings1.getString("lang", null);
				Intent i;
				
				boolean isChecked = settings1
						.getBoolean("isChecked", false);
				if (isChecked) {
					i = new Intent(SplashScreenActivity.this,
							MainActivity.class);
				} else {
					i = new Intent(SplashScreenActivity.this,
							LoginActivity.class);
				}
				
				startActivity(i);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}

}