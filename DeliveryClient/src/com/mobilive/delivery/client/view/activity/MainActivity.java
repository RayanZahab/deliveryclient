package com.mobilive.delivery.client.view.activity;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.mobilive.delivery.client.DeliveryClientApplication;
import com.mobilive.delivery.client.R;
import com.mobilive.delivery.client.adapter.NavDrawerListAdapter;
import com.mobilive.delivery.client.utilities.NavDrawerItem;
import com.mobilive.delivery.client.view.fragment.CartFragment;
import com.mobilive.delivery.client.view.fragment.HomeFragment;
import com.mobilive.delivery.client.view.fragment.OrdersFragment;
import com.mobilive.delivery.client.view.fragment.ParentFragment;
import com.mobilive.delivery.client.view.fragment.PendingOrdersFragment;
import com.mobilive.delivery.client.view.fragment.ProfileFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	private Fragment activeFragment;
	static FragmentManager fragmentManager;

	private static Context context;

	// new
	private DrawerLayout mDrawerLayout;
	private static ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	int areaId= 0;

	private static boolean showHomeFragment = true;
	
	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private List<String> navMenuTitles = new ArrayList<String>();

	public static ArrayList<NavDrawerItem> navDrawerItems;
	private static NavDrawerListAdapter adapter;
	public static List<Fragment> fragments = new ArrayList<Fragment>();	
	public int categoryId = 0, branchId = 0;
	int fragmentIndex = 0;

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
		navMenuTitles.add(getString(R.string.info));
		navMenuTitles.add(getString(R.string.Logout));
		navMenuTitles.add(getString(R.string.home));
		Bundle extras = getIntent().getExtras();
		
		if(extras!=null)
		{
			fragmentIndex = extras.getInt("fragmentIndex");
			categoryId = extras.getInt("categoryId");			
		}
		
		addSlideMenu();
		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		
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
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			displayView(position);
		}
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	public void addSlideMenu() {
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();
		
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(0).toString(),R.drawable.ic_home, true, "0"));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(1).toString(),R.drawable.ic_people));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(2).toString(),R.drawable.ic_communities));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(3).toString(),R.drawable.ic_communities));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(4).toString(),R.drawable.ic_communities));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(5).toString(),R.drawable.ic_communities));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles.get(6).toString(),R.drawable.ic_communities));

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		adapter = new NavDrawerListAdapter(context, navDrawerItems);
		mDrawerList.setAdapter(adapter);
		displayView(fragmentIndex);
	}

	public static void updateCounter(int count) {
		navDrawerItems.get(0).setCount("" + count);
		adapter = new NavDrawerListAdapter(context, navDrawerItems);
		mDrawerList.setAdapter(adapter);
	}

	private void displayView(int position) {
		ParentFragment fragment = null;
		Bundle args = new Bundle();
		boolean cartEmpty = true;
		if( ((DeliveryClientApplication) getApplication()).getMyCart().getAllCount()>0)
		{
			branchId = ((DeliveryClientApplication) getApplication()).getBranchId();	
			position = 7;
			cartEmpty = false;
		}
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			fragments.add(fragment);
			break;
		case 1:
			if(!cartEmpty)
			{
				fragment = new CartFragment();
				fragments.add(fragment);
			}else{
				Toast.makeText(getApplicationContext(),R.string.cart_is_empty,Toast.LENGTH_SHORT).show();
			}
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
			try {
				Toast.makeText(getApplicationContext(),"version" + getPackageManager().getPackageInfo(getPackageName(), 0).versionName,Toast.LENGTH_SHORT).show();
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case 6:
			logout(true);
			break;
		case 7:
			fragment = new OrdersFragment();
			args.putInt("categoryId", categoryId);
			args.putInt("branchId", branchId);
			fragment.setArguments(args);
			fragments.add(fragment);
			break;

		default:
			fragment = new ProfileFragment();
			fragments.add(fragment);
			break;
		}
		if (fragment != null) {
			
			FragmentTransaction tx = fragmentManager.beginTransaction();
			tx.addToBackStack(null);
			tx.replace(R.id.frame_container, (Fragment) fragment).commit();
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

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public void toggler() {

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
				R.string.app_name // nav drawer close - description for
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	public void callMethod(String m, String s, String error) {
		if (m.equals("setOrders")) {
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(PendingOrdersFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} else if (m.equals("getAdd")) {
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(OrdersFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		} else if (m.equals("done") || m.equals("getLoggedIn")) {
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(ProfileFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

		} else if (m.equals("setBusinesses")){			
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(OrdersFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		} else if(m.contains("Home")){
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(HomeFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}else {
			for (Fragment fragment : fragments) {
				if (fragment.getClass().equals(OrdersFragment.class)) {
					Method returnFunction;
					try {
						returnFunction = fragment.getClass().getDeclaredMethod(m, s.getClass(), s.getClass());
						if (returnFunction != null)
							returnFunction.invoke(fragment, s, error);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	// @Override
	public void onAttachFragment(Fragment fragment) {
		setActiveFragment(fragment);
	}

	@Override
	public void onBackPressed() {
		Fragment fragment = getVisibleFragment();
		int goUpReturnIntValue = -1;
		if (fragment.getClass().equals(OrdersFragment.class)) { 
			Method returnFunction;
			try {
				returnFunction = fragment.getClass().getDeclaredMethod("goUp");
				if (returnFunction != null){
					Object goUpReturnValue = returnFunction.invoke(fragment);
					goUpReturnIntValue = (goUpReturnValue!=null)?(Integer) goUpReturnValue:-1;
					if(goUpReturnIntValue!=0)
						setShowHomeFragment(false);
					
					if(isShowHomeFragment()){
						if(fragment instanceof HomeFragment){
							_exitApp();
						}else{
							HomeFragment homeFragment = new HomeFragment();
							FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
							fragments.clear();
							fragments.add(homeFragment);
							fragmentTransaction.replace(fragment.getId(), homeFragment);
							fragmentTransaction.commit();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(goUpReturnIntValue==0)
				setShowHomeFragment(true);
		}
		else{
			_exitApp();
		}		
	}
	
	public void logout(boolean login)
	{
		if(!login)
		{
			_exitApp();
		}
		else
		{
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
							
							SharedPreferences settings1 = getSharedPreferences(
									"PREFS_NAME", 0);
							settings1.edit().remove("PREFS_NAME").commit();
							Intent i = new Intent(MainActivity.this, LoginActivity.class);
							startActivity(i);
						}
					}).setNegativeButton(R.string.no, null).show();
		}
		
	}

	public static boolean isShowHomeFragment() {
		return showHomeFragment;
	}

	public static void setShowHomeFragment(boolean showHomeFragment) {
		MainActivity.showHomeFragment = showHomeFragment;
	}
	private void _exitApp() {
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

	public Fragment getActiveFragment() {
		return activeFragment;
	}

	public void setActiveFragment(Fragment activeFragment) {
		this.activeFragment = activeFragment;
	}

	
}
