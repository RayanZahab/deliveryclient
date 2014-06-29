package info.androidhive.tabsswipe;

import java.lang.reflect.Method;
import java.util.List;

import info.androidhive.tabsswipe.adapter.TabsPagerAdapter;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity implements
	TabListener  {
	
	Fragment activeFragment;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	// Tab titles
	static FragmentManager fragmentManager ;
	private String[] tabs = { "Orders", "Profile", "Cart" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayShowTitleEnabled(false);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fragmentManager = MainActivity.this
				.getSupportFragmentManager();
		
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new TabsPagerAdapter(fragmentManager);

		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		int i = 0;
		// Adding Tabs
		for (String tab_name : tabs) {
			Tab tab = actionBar.newTab();
			tab.setTag(tab_name);

			if (i == 1)
				tab.setIcon(R.drawable.admin);
			else if (i == 0)
				tab.setIcon(R.drawable.orders_tab);
			else
				tab.setIcon(R.drawable.carttabs);
			i++;

			tab.setText(tab_name);
			actionBar.addTab(tab.setTabListener(this));
			// actionBar.setC
		}

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				actionBar.setSelectedNavigationItem(position);
				if (position == 0) {
					//OrdersFragment.getBusinesses();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	public void callMethod(String m, String s, String error) {
		List<Fragment> fragments = fragmentManager.getFragments();
		for (Fragment fragment : fragments) {
			if (fragment.getClass().equals(OrdersFragment.class))
			{
				Method returnFunction;
				
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

	@Override
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
							@SuppressLint("NewApi")
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								MainActivity.this.finishAffinity();
							}
						}).setNegativeButton("No", null).show();
	}

	public static Fragment getVisibleFragment() {
		
		List<Fragment> fragments = fragmentManager.getFragments();
		for (Fragment fragment : fragments) {
			if (fragment != null && fragment.isVisible())
				return fragment;
		}
		return null;
	}

	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

}
