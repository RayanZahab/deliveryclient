package com.mobilife.delivery.client.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobilife.delivery.client.model.Area;
import com.mobilife.delivery.client.model.City;
import com.mobilife.delivery.client.model.Country;
import com.mobilife.delivery.client.view.activity.MainActivity;

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
	public void setSelectedString(Spinner sp, ArrayList<Country> mylist, String name) {
		for (int position = 0; position <mylist.size(); position++) {
		//	Log.d("ray","mylist : "+mylist.get(position).get+ " -- "+name);
			
		}
		ArrayAdapter<?> list = (ArrayAdapter<?>) sp.getAdapter();
		for (int position = list.getCount()-1; position >=0; position--) {
			
			
			if (list.getItem(position).toString().equals(name)) {
				sp.setSelection(position);
				return;
			}
		}
	}
	public void setSelectedStrings(Spinner sp, ArrayAdapter<?> list, String name) {

		for (int position = list.getCount()-1; position >=0; position--) {
			Object o = list.getItem(position);
			
			if (o instanceof Country)
			{
				if ( ((Country)o).getName().equals(name) )
				{
					sp.setSelection(position);
					return;
				}
					
			}
			else if (o instanceof City)
			{
				if (((City)o).getName().equals(name) )
				{
					sp.setSelection(position);
					return;
				}
					
			}
			else if (o instanceof Area)
			{
				if (((Area)o).getName().equals(name) )
				{
					sp.setSelection(position);
					return;
				}
					
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
		
		i = new Intent(a, MainActivity.class);
		
			i.putExtra("fragmentIndex", 0);			
		
		if (msg != null && !msg.isEmpty()) {
			Toast t = Toast.makeText(a.getApplicationContext(), msg,
					Toast.LENGTH_SHORT);
			t.setGravity(Gravity.TOP, 0, 0);
			
			t.show();
		}
		a.startActivity(i); 
			
	}
	public void showError(Activity from, String msg)
	{
		Toast t = Toast.makeText(from.getApplicationContext(), msg,
				Toast.LENGTH_SHORT);
		t.setGravity(Gravity.TOP, 0, 0);
		t.show();
	}

	public String getago(String date) {
		try {
			long now = System.currentTimeMillis();
			long time = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.US);
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
