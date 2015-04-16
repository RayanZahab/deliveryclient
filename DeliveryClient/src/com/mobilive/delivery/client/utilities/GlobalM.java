package com.mobilive.delivery.client.utilities;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.view.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GlobalM {

	public GlobalM() {

	}

	public void setSelected(Spinner sp, ArrayAdapter<?> list, Object o) {

		for (int position = list.getCount()-1; position >=0; position--) {
			if (list.getItem(position).equals(o)) {
				sp.setSelection(position);
				return;
			}
		}
	}
	public String getSelected(ArrayAdapter<?> list, Object o) {

		for (int position = list.getCount()-1; position >=0; position--) {
			if (list.getItem(position).equals(o)) {
				
				return list.getItem(position).toString();
			}
		}
		return null;
	}

	public void bkToNav(Activity a, String msg) {

		Intent i;
		Log.d("raya", "backkkkkkkkkk2: "+msg);
		
		i = new Intent(a, MainActivity.class);
		
			i.putExtra("fragmentIndex", 0);			
		
		if (msg != null && !msg.isEmpty()) {
			Toast t = Toast.makeText(a.getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
			t.setGravity(Gravity.TOP, 0, 0);
			Log.d("raya", "backkkkkkkkkk3: ");
			
			t.show();
		}/*
		if (((DeliveryClient) a.getApplication()).getToken() != null)
			a.startActivity(i);*/

		a.startActivity(i); 
			
	}
	public void showError(Activity from, String msg)
	{
		Toast t = Toast.makeText(from.getApplicationContext(), msg,
				Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP, 0, 0);
		t.show();
	}

	public void goTo(Activity from,Class to, String msg) {

		Intent i;
		i = new Intent(from, to.getClass());
		
		if (msg != null && !msg.isEmpty()) {
			Toast t = Toast.makeText(from.getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
			t.setGravity(Gravity.TOP, 0, 0);
			t.show();
		}
		if (((DeliveryClientApplication) from.getApplication()).getToken() != null)
			from.startActivity(i);
	}

	public String getago(String date) {
		try {
			long now = System.currentTimeMillis();
			long time = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			TimeZone tz = TimeZone.getTimeZone("GMT");
			sdf.setTimeZone(tz);
			time = sdf.parse(date).getTime();

			return ""
					+ DateUtils.getRelativeTimeSpanString(time, now,
							DateUtils.SECOND_IN_MILLIS,
							DateUtils.FORMAT_ABBREV_ALL);
		} catch (Exception e) {
			return date;
		}
	}
}
