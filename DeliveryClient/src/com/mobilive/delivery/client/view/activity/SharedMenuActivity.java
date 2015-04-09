package com.mobilive.delivery.client.view.activity;


import com.mobilive.delivery.client.model.Item;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SharedMenuActivity extends Activity {

	public static final int ABOUT = 1000;
	public static final int settings = 1001;
	public static final int LogOut = 1002;
	public static final int home = 1003;
	public static Context context;
	public static Menu menu;
	public static Activity activity;
	public static ArrayAdapter<Item> adapter;

	public SharedMenuActivity(Menu menu, Context ctx) {
		SharedMenuActivity.menu = menu;
		SharedMenuActivity.context = ctx;
	}

	public static void onCreateOptionsMenu(Activity a, Menu menu, Context ctx,
			ArrayAdapter<Item> adpt) {

		onCreateOptionsMenu(a, menu, ctx, adapter);
		context = ctx;
		activity = a;
		adapter = adpt;
	}

	

	public static boolean onOptionsItemSelected(MenuItem item, Activity caller) {
		Intent intent;
		switch (item.getItemId()) {
		case SharedMenuActivity.ABOUT:

			Toast msg = null;
			try {
				msg = Toast
						.makeText(
								context,
								"version"
										+ context
												.getPackageManager()
												.getPackageInfo(
														context.getPackageName(),
														0).versionName,
								Toast.LENGTH_LONG);
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			msg.show();
			return true;
		case SharedMenuActivity.home:
			intent = new Intent(caller, MainActivity.class);
			caller.startActivity(intent);
			return true;
		case SharedMenuActivity.LogOut:
			SharedPreferences sharedPref = context.getSharedPreferences(
					"PREFS_NAME", 0);
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.clear();
			editor.commit();
			intent = new Intent(caller, LoginActivity.class);
			caller.startActivity(intent);
			return true;
		default:
			return false;
		}
	}

}
