package info.androidhive.tabsswipe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	Fragment activeFragment;
	// private ViewPager viewPager;
	// private TabsPagerAdapter mAdapter;
	// private ActionBar actionBar;
	// Tab titles
	static FragmentManager fragmentManager;

	private static Context context;

	// new
	private DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	int areaId= 0;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private List navMenuTitles = new ArrayList<String>();

	public static ArrayList<NavDrawerItem> navDrawerItems;
	private static NavDrawerListAdapter adapter;
	public static List<Fragment> fragments = new ArrayList<Fragment>();

	int i, countryP, cityP, areaP;
	ArrayList<Country> countries = new ArrayList<Country>();
	ArrayList<City> cities = new ArrayList<City>();
	ArrayList<Area> areas = new ArrayList<Area>();
	int CityId;
	boolean last = false;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTitle = mDrawerTitle = getTitle();
		fragmentManager = MainActivity.this.getFragmentManager();
		context = this.getApplicationContext();
		navMenuTitles.add(getString(R.string.home));
		navMenuTitles.add(getString(R.string.cart));
		navMenuTitles.add(getString(R.string.profile));
		navMenuTitles.add(getString(R.string.pending_orders));
		navMenuTitles.add(getString(R.string.closed_orders));
		navMenuTitles.add(getString(R.string.home));

		addSlideMenu();
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		countries = ((deliveryclient) this.getApplication()).getCountries();
		Bundle extras = getIntent().getExtras();
		int fragmentIndex = 0;
		if(extras!=null)
		{
			fragmentIndex = extras.getInt("fragmentIndex");
			displayView(fragmentIndex);
		}
		// toggler
		toggler();

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
		if (item.getItemId() == R.id.action_settings)
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
		// navMenuTitles ;//=
		// getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		// navMenuIcons = getResources()
		// .obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(0).toString(),
				R.drawable.ic_home, true, "0"));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(1).toString(),
				R.drawable.ic_people));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(2).toString(),
				R.drawable.ic_communities));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(3).toString(),
				R.drawable.ic_communities));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(4).toString(),
				R.drawable.ic_communities));
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(context, navDrawerItems);
		mDrawerList.setAdapter(adapter);
		displayView(0);
	}

	public static void updateCounter(int count) {
		navDrawerItems.get(0).setCount("" + count);
		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(context, navDrawerItems);
		mDrawerList.setAdapter(adapter);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		ParentFragment fragment = null;
		Bundle args = new Bundle();
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			fragments.add(fragment);
			break;
		case 1:
			fragment = new CartFragment();
			fragments.add(fragment);
			break;
		case 2:
			fragment = new ProfileFragment();
			fragments.add(fragment);
			break;

		case 3:
			fragment = new PendingOrdersFragment();
			args.putString("orders", "pending");
			fragment.setArguments(args);
			fragments.add(fragment);
			break;

		case 4:
			fragment = new PendingOrdersFragment();
			args.putString("orders", "closed");
			fragment.setArguments(args);
			fragments.add(fragment);
			break;
		case 5:
			fragment = new HomeFragment();
			args.putString("orders", "closed");
			fragment.setArguments(args);
			fragments.add(fragment);
			break;

		default:
			fragment = new ProfileFragment();
			fragments.add(fragment);
			break;
		}
		Log.e("ray", "currentf" + position);

		if (fragment != null) {
			
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, (Fragment) fragment)
					.commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles.get(position).toString());
			mDrawerLayout.closeDrawer(mDrawerList);
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

	public void toggler() {

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
		Log.d("ray", "calling method: " + m);

		if (m.equals("setCountries"))
			setCountries(s, error);
		else if (m.equals("setCities"))
			setCities(s, error);
		else if (m.equals("setAreas"))
			setAreas(s, error);
		else if (m.equals("setOrders")) {
			Log.d("ray", "Here5: " + m);
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(PendingOrdersFragment.class)) {
					Method returnFunction;
					Log.d("ray", "Here52: " + m);
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(
								m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (m.equals("getAdd")) {
			Log.d("ray", "Here3: " + m);
			for (Fragment fragment : fragments) {
				Log.d("ray", "Here30: " + fragment.getClass() + "=="
						+ CartFragment.class);
				if (fragment.getClass().equals(OrdersFragment.class)) {
					Method returnFunction;
					Log.d("ray", "Here31: " + m);
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(
								m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		} else if (m.equals("done")) {
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(ProfileFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(
								m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		} else if (m.equals("setBusinesses")){			
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(OrdersFragment.class)) {
					Method returnFunction;
					Log.d("ray", "Here41: " + m);
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(
								m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		} else if(m.contains("Home"))
		{
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(HomeFragment.class)) {
					Method returnFunction;
					Log.d("ray", "Here51: " + m);
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(
								m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}else {
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(OrdersFragment.class)) {
					Method returnFunction;
					Log.d("ray", "Here41: " + m);
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(
								m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

	// @Override
	public void onAttachFragment(Fragment fragment) {
		activeFragment = fragment;
	}

	@Override
	public void onBackPressed() {
		new AlertDialog.Builder(this)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setTitle(R.string.exit)
				.setMessage(R.string.exitquest)
				.setPositiveButton(R.string.yes,
						new DialogInterface.OnClickListener() {
							@SuppressLint("NewApi")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								MainActivity.this.finishAffinity();
								SharedPreferences settings1 = getSharedPreferences(
										"PREFS_NAME", 0);
								settings1.edit().remove("PREFS_NAME").commit();
							}
						}).setNegativeButton(R.string.no, null).show();
	}

	public static Fragment getVisibleFragment() {

		for (Fragment fragment : fragments) {
			if (fragment != null && fragment.isVisible()) {
				return fragment;
			}
		}
		return null;
	}

	public void getCountries() {
		String serverURL = new myURL("countries", null, 0, 30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "setCountries", false);
		p.get();
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
		RZHelper p = new RZHelper(serverURL, this, "setCities", false);
		p.get();
	}

	public void setCities(String s, String error) {
		cities = new APIManager().getCitiesByCountry(s);
		for (int j = 0; j < cities.size(); j++) {
			if (j == cities.size() - 1)
				last = true;
			getAreas(j);
		}
	}

	public void getAreas(int position) {
		cityP = position;
		CityId = cities.get(position).getId();
		String serverURL = new myURL("areas", "cities", CityId, 30).getURL();
		RZHelper p = new RZHelper(serverURL, this, "setAreas",false,last);
		p.get();
	}

	public void setAreas(String s, String error) {
		areas = new APIManager().getAreasByCity(s);

		cities.get(cityP).setAreas(areas);
		countries.get(countryP).setCities(cities);	
		if (last) {
			((deliveryclient) this.getApplication()).setCountries(countries);			
			Log.d("ray", "setting countries: " + CityId + "->"+areas.size());
		}
	}
}
