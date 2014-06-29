package info.androidhive.tabsswipe.adapter;

import info.androidhive.tabsswipe.ProfileFragment;
import info.androidhive.tabsswipe.CartFragment;
import info.androidhive.tabsswipe.OrdersFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new OrdersFragment();
		case 1:
			// Games fragment activity
			return new ProfileFragment();
		case 2:
			return new CartFragment();
			// Movies fragment activity
		default:
			return new OrdersFragment();
		}
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
