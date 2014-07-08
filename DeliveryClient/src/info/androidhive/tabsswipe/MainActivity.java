package info.androidhive.tabsswipe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity  {
	
	Fragment activeFragment;
	//private ViewPager viewPager;
	//private TabsPagerAdapter mAdapter;
	//private ActionBar actionBar;
	// Tab titles
	static FragmentManager fragmentManager ;
	private String[] tabs = { "Home", "find", "photos", "Home2", "find2", "photos2" };
	private static Context context;
	
	//new
	private DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles = {  "Cart","Orders", "Profile","Home", "find", "photos" };
	private TypedArray navMenuIcons;

	public static ArrayList<NavDrawerItem> navDrawerItems;
	private static NavDrawerListAdapter adapter;
	static List<Fragment> fragments = new ArrayList<Fragment>();
	
	int i, countryP, cityP, areaP;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		fragmentManager = MainActivity.this
				.getFragmentManager();
		context = this.getApplicationContext();
		addSlideMenu();
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		//toggler
		toggler();
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		if(item.getItemId()== R.id.action_settings)
			return true;
		else
			return super.onOptionsItemSelected(item);
	}
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	

	public void addSlideMenu() {
		// load slide menu items
		//navMenuTitles ;//= getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		//navMenuIcons = getResources()
		//		.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], R.drawable.ic_home, true, "22"));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], R.drawable.ic_people));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], R.drawable.ic_communities));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], R.drawable.ic_photos));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], R.drawable.ic_pages));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], R.drawable.ic_whats_hot, true, "50+"));
		
		// Recycle the typed array
		//navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(context,
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
		getCountries();
	}
	public static void updateCounter(int count)
	{
		navDrawerItems.get(0).setCount(""+count);
		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(context,
				navDrawerItems);
		mDrawerList.setAdapter(adapter);
	}
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		ParentFragment fragment = null;
		switch (position) {
		case 0:
			fragment = new CartFragment();
			fragments.add(fragment);
			break;
		case 1:
			fragment = new OrdersFragment();
			fragments.add(fragment);
			break;
		case 2:
			fragment = new ProfileFragment();
			fragments.add(fragment);
			break;/*
		case 3:
			fragment = new CommunityFragment();
			break;
		case 4:
			fragment = new PagesFragment();
			break;
		case 5:
			fragment = new WhatsHotFragment();
			break;
*/
		default:
			break;
		}

		if (fragment != null) {
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, (Fragment) fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void toggler()
	{

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		
	}
	public void callMethod(String m, String s, String error) {
		if (m.equals("setCountries"))
			setCountries(s, error);
		else if (m.equals("setCities"))
			setCities(s, error);
		else if (m.equals("setAreas"))
			setAreas(s, error);
		else
		{
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(OrdersFragment.class))
				{
					Method returnFunction;
					Log.d("ray","method: "+m);
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(),
								s.getClass());
						if(returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
			}
		}
	}

	//@Override
	public void onAttachFragment(Fragment fragment) {
		activeFragment = fragment;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exit)
				.setMessage(R.string.exitquest)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								MainActivity.this.finishAffinity();
							}
						}).setNegativeButton("No", null).show();
	}

	public static Fragment getVisibleFragment() {
		
		for (Fragment fragment : fragments) {
			if (fragment != null && fragment.isVisible())
			{
				return fragment;
			}
		}
		return null;
	}
	
	public void getCountries() {
		String serverURL = new myURL("countries", null, 0, 30).getURL();
		MyJs mjs = new MyJs("setCountries", this,
				((deliveryclient) getApplication()), "GET", true, false);
		mjs.execute(serverURL);
	}

	public void setCountries(String s, String error) {
		countries = new APIManager().getCountries(s);
		for (int j = 0; j < countries.size(); j++) {
			getCities(j);
		}
	}

	public void getCities(int position) {
		countryP = position;
		int countryId = countries.get(position).getId();
		String serverURL = new myURL("cities", "countries", countryId, 30)
				.getURL();
		new MyJs("setCities", this, ((deliveryclient) getApplication()), "GET",
				false, false).execute(serverURL);
	}

	public void setCities(String s, String error) {
		cities = new APIManager().getCitiesByCountry(s);
		for (int j = 0; j < cities.size(); j++) {
			getAreas(j);
		}
	}

	public void getAreas(int position) {
		cityP = position;
		int CityId = cities.get(position).getId();
		Log.d("ray", "City: " + CityId);
		String serverURL = new myURL("areas", "cities", CityId, 30).getURL();
		MyJs mjs = new MyJs("setAreas", this,
				((deliveryclient) this.getApplication()), "GET", false, false);
		mjs.execute(serverURL);
	}

	public void setAreas(String s, String error) {
		areas = new APIManager().getAreasByCity(s);
		for (int j = 0; j < cities.size(); j++) {
			Log.d("ray", "City: " + cities.get(j).getId());
		}
		cities.get(cityP).setAreas(areas);
		countries.get(countryP).setCities(cities);
		if (countryP == countries.size() - 1 && cityP == countries.get(countryP).getCities().size()-1) {
			((deliveryclient) this.getApplication()).setCountries(countries);
			((deliveryclient) this.getApplication()).loader.dismiss();
		}
	}
}
