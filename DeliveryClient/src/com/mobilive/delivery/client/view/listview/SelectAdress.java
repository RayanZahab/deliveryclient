package com.mobilive.delivery.client.view.listview;


import java.util.ArrayList;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.model.Address;
import com.mobilive.delivery.client.model.Country;
import com.mobilive.delivery.client.model.Item;
import com.mobilive.delivery.client.utilities.APIManager;
import com.mobilive.delivery.client.utilities.RZHelper;
import com.mobilive.delivery.client.utilities.myURL;
import com.mobilive.delivery.client.view.activity.AddAddressActivity;
import com.mobilive.delivery.client.view.activity.MainActivity;
import com.mobilive.delivery.client.view.activity.PreviewActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectAdress extends ListActivity {

	ListView listView;

	ArrayAdapter<Item> arrayAdapter;
	ArrayList<Address> myAddresses;
	ArrayList<String> addOut = new ArrayList<String>();
	Activity current;
	int myId;
	int defaultAddId = 0;
	String defaultAddName = "";
	ArrayList<Country> countries;
	String previous = "";
	ArrayAdapter<String> dataAdapter ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_address);
		current = this;

		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setTextFilterEnabled(true);
		myId = ((DeliveryClientApplication) this.getApplication()).getUserId();		
		countries = ((DeliveryClientApplication) this.getApplication())
				.getCountries();
		getAddresses(myId);
		Button submit = (Button) findViewById(R.id.submit);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			previous = extras.getString("previous");
			Log.d("ray","ray load prev: "+previous);
			if(previous!=null && previous.equals("preview"))
			{
				submit.setVisibility(View.VISIBLE);
			}
			else
			{
				submit.setVisibility(View.GONE);
			}
		}
		else
		{
			submit.setVisibility(View.GONE);
		}
	}

	public void getAddresses(int userId) {
		String serverURL = new myURL("addresses", "customers",
				((DeliveryClientApplication) this.getApplication()).getUserId(), 30)
				.getURL();
		RZHelper p = new RZHelper(serverURL, this, "setAdd", true);
		p.get();
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setAdd"))
			getAdd(s, error);
		else if(m.equals("afterDelete"))
		{
			afterDelete(s, error);
			/*addressId = currentAddress.getId();
			Log.d("ray","add found ");
			editor.putInt("addressId", currentAddress.getId());
			ArrayList<Country> countries = ((DeliveryClientApplication) currentActivity.getApplication())
					.getCountries();
			editor.putString("addressName", currentAddress.toString(countries));
			editor.commit();*/
		}
	}

	public void getAdd(String s, String error) {
		int defaultPosition = 0, i = 0;
		
		Log.d("ray", "adds: " + countries.size());

		if (error == null) {
			myAddresses = new APIManager().getAddress(s);
			if (myAddresses.size() > 0) {
				final ArrayList<Item> mylist = new ArrayList<Item>();
				for (Address add : myAddresses) {
					Item it = new Item();
					it.setName(add.toString(countries));
					it.setType("address");
					it.setId(add.getId());
					mylist.add(it);
					if (add.isDefault())
					{
						defaultPosition = i;						
						setDefault(add.getId(),it.getName());
					}
					i++;
					addOut.add(add.toString(countries));
				}
				dataAdapter = new ArrayAdapter<String>(this,
						R.layout.row_radiobutton, addOut);
				setListAdapter(dataAdapter);
				listView.setItemChecked(defaultPosition, true);

				registerForContextMenu(listView);
				
				
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long itemId) {
						CheckedTextView textView = (CheckedTextView) view;
						for (int i = 0; i < listView.getCount(); i++) {
							textView = (CheckedTextView) listView.getChildAt(i);
							if (textView != null) {
								textView.setTextColor(Color.BLACK);
							}

						}
						listView.invalidate();
						textView = (CheckedTextView) view;
						if (textView != null) {
							textView.setTextColor(Color.RED);
						}
						defaultAddId = mylist.get(position)
								.getId();
						String serverURL = new myURL("set_default",
								"customers/addresses", defaultAddId, 0).getURL();
						Log.d("ray","setting default: "+defaultAddId +" => "+serverURL);
						defaultAddName = mylist.get(position).toString();
						setDefault(defaultAddId,defaultAddName);
						RZHelper p = new RZHelper(serverURL, current,
								"nothing", true);
						p.put(null);
					}
				});
			}
		}

	}
	public void setDefault(int addId, String addName)
	{
		defaultAddId = addId;
		SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
		SharedPreferences.Editor editor = settings.edit();
		
		editor.putInt("addressId", addId);
		editor.putString("addressName", addName);
		Log.d("ray","pref saving default: "+defaultAddId );
		((DeliveryClientApplication) this.getApplication()).setAddressId(addId);
		editor.commit();
	}

	public void addAddress(View v) {
		Intent i = new Intent(this, AddAddressActivity.class);
		i.putExtra("previous", previous);
		startActivity(i);
	}
	public void submit(View v)
	{
		Intent intent = new Intent(this, PreviewActivity.class);
		intent.putExtra("previous", "preview");
		startActivity(intent);
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, MainActivity.class);
		if(!previous.equals("preview"))
		{
			i.putExtra("fragmentIndex", 2);
		}
		else
		{
			i.putExtra("fragmentIndex", 1);			
		}
		startActivity(i);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.cat_context_menu, menu);
	}
	public void Delete(final int position) {
		final int branchId = myAddresses.get(position).getId();
		new AlertDialog.Builder(this)
				.setTitle(
						"Delete this Add: "
								+ myAddresses.get(position).toString()
								+ " ?")
				.setIcon(R.drawable.branches)
				.setPositiveButton(android.R.string.yes,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int whichButton) {
								String serverURL = new myURL(null, "customers/addresses",
										branchId, 0).getURL();
								RZHelper p = new RZHelper(serverURL,
										SelectAdress.this, "afterDelete",
										false);
								addOut.remove(position);								
								p.delete();
							}
						}).setNegativeButton(android.R.string.no, null).show();
	}

	public void afterDelete(String s, String error) {
		/*Log.d("ray","in after delete");
		dataAdapter = new ArrayAdapter<String>(this,
				R.layout.row_radiobutton, addOut);
		dataAdapter.notifyDataSetChanged();
		*/
		Intent i = new Intent(SelectAdress.this, SelectAdress.class);
		i.putExtra("previous",previous);
		startActivity(i);
	}

	public void Edit(Address address) {
		Intent i = new Intent(SelectAdress.this, AddAddressActivity.class);
		 ((DeliveryClientApplication) this.getApplication()).setCurrentAddress(address);
		//DeliveryAdminApplication.setBranchId(item.getId());
			i.putExtra("address_id", address.getId());
			i.putExtra("previous",previous);
		startActivity(i);
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case R.id.edit:
			Edit(myAddresses.get((int) info.id));
			break;
		case R.id.delete:
			Delete((int) info.id);
			break;
		default:
			break;

		}
		return true;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.select_adress, menu);
		return true;
	}

}
