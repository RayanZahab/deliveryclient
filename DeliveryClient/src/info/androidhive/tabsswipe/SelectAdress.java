package info.androidhive.tabsswipe;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
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
	
	ArrayList< String>arrayList; // list of the strings that should appear in ListView
	ArrayAdapter<String> arrayAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_adress);
		
		listView = (ListView) findViewById(R.id.lstDemo);
        
        arrayList = new ArrayList<String>();
        arrayList.add("India");
        arrayList.add("USA");
        arrayList.add("England");
        arrayList.add("Singapur");
        arrayList.add("China");
        arrayList.add("Canada");
        arrayList.add("Srilanka");
        arrayList.add("SouthAfrica");
        
        
        
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_single_choice,arrayList);
        listView.setAdapter(arrayAdapter);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long itemId) {
				CheckedTextView textView = (CheckedTextView) view;
				for (int i = 0; i < listView.getCount(); i++) {
					textView= (CheckedTextView) listView.getChildAt(i);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_adress, menu);
		return true;
	}

}
