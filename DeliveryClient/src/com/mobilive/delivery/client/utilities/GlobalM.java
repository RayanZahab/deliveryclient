package com.mobilive.delivery.client.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.model.Area;
import com.mobilive.delivery.client.model.City;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Order;
import com.mobilive.delivery.client.view.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mobilive.delivery.client.view.activity.MainActivity;

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
			
			Log.d("ray","check : "+((Country)mylist.get(position)).getName()+ " -- "+name);
			
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
				Log.d("ray","check Co: "+((Country) o).getName()+ " -- "+name);
				if ( ((Country)o).getName().equals(name) )
				{
					sp.setSelection(position);
					return;
				}
					
			}
			else if (o instanceof City)
			{
				Log.d("ray","check C: "+((City)o).getName()+ " -- "+name);
				if (((City)o).getName().equals(name) )
				{
					sp.setSelection(position);
					return;
				}
					
			}
			else if (o instanceof Area)
			{
				Log.d("ray","check A: "+((Area)o).getName()+ " -- "+name);
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
