package info.androidhive.tabsswipe;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_address);

		listView = getListView();
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setTextFilterEnabled(true);
		myId = ((deliveryclient) this.getApplication()).getUserId();
		Bundle extras = getIntent().getExtras();
		String previous = extras.getString("previous");
		Log.d("ray", "prev: " + previous);
		getAddresses(myId);
		current = this;
	}

	public void getAddresses(int userId) {
		String serverURL = new myURL("addresses", "customers",
				((deliveryclient) this.getApplication()).getUserId(), 0)
				.getURL();
		RZHelper p = new RZHelper(serverURL, this, "setAdd", true);
		p.get();
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setAdd"))
			getAdd(s, error);
	}

	public void getAdd(String s, String error) {
		int defaultPosition = 0, i = 0;
		ArrayList<Country> countries = ((deliveryclient) this.getApplication())
				.getCountries();
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
						defaultPosition = i;
					i++;
					addOut.add(add.toString(countries));
				}

				setListAdapter(new ArrayAdapter<String>(this,
						R.layout.row_radiobutton, addOut));
				listView.setItemChecked(defaultPosition, true);

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
						String serverURL = new myURL("set_default",
								"customers/addresses", mylist.get(position)
										.getId(), 0).getURL();

						RZHelper p = new RZHelper(serverURL, current,
								"nothing", true);
						p.put(null);
					}
				});
			}
		}

	}

	public void addAddress(View v) {
		Intent i = new Intent(this, AddAddressActivity.class);
		startActivity(i);
	}

	@Override
	public void onBackPressed() {
		Bundle extras = getIntent().getExtras();
		Log.d("ray", "In back: ");
		if (extras != null) {
			String previous = extras.getString("previous");
			Log.d("ray", "prev: " + previous);
			if (previous.equals("preview")) {
				Intent i = new Intent(this, PreviewActivity.class);
				startActivity(i);
			} else
				super.onBackPressed();

		} else
			super.onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.select_adress, menu);
		return true;
	}

}
