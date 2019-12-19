package com.app.osgrim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

	private int numTabs;

	PageAdapter(FragmentManager fm, int numTabs) {
		super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		this.numTabs = numTabs;
	}

	@NonNull
	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return new ListFragment();
			case 1:
				return new InputFragment();
			case 2:
				return new ExportFragment();
		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return numTabs;
	}
}
