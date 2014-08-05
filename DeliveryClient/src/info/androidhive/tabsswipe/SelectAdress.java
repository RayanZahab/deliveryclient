package info.androidhive.tabsswipe;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SelectAdress extends Activity {

	ListView listView;

	ArrayAdapter<Item> arrayAdapter;
	ArrayList<Address> myAddresses;
	int myId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_address);

		listView = (ListView) findViewById(R.id.lstDemo);
		myId = ((deliveryclient) this.getApplication()).getUserId();
		getAddresses(myId);
	}

	public void getAddresses(int userId) {
		String serverURL = new myURL("addresses", "customers", ((deliveryclient) this.getApplication()).getUserId(), 0).getURL();
		MyJs mjs = new MyJs("setAdd", this,
				((deliveryclient) this.getApplication()), "GET");
		mjs.execute(serverURL);

	}

	public void callMethod(String m, String s, String error) {
		getAdd(s, error);
	}

	public void getAdd(String s, String error) {
		if (error == null) {
			myAddresses = new APIManager().getAddress(s);
			ArrayList<Item> mylist = new ArrayList<Item>();
			for (Address add : myAddresses) {
				Item it = new Item();
				it.setName(add.toString());
				it.setType("address");
				it.setId(add.getId());
				mylist.add(it);
			}

			arrayAdapter = new MyCustomAdapter(this, R.layout.row_radiobutton,
					mylist);

			listView.setAdapter(arrayAdapter);

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

				}
			});

		}

	}
	public void addAddress (View v){
		Intent i = new Intent(this, AddAddressActivity.class);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.select_adress, menu);
		return true;
	}

}
