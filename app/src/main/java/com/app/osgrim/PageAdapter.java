package com.app.osgrim;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * PageAdapter class allows us to associate the three fragments (list, input and export) to the
 * three tabs. <br>
 * La classe PageAdapter nous permet d'associer les trois fragments (listFragment, inputFragment
 * et exportFragment) aux trois onglets.
 *
 * @author Morgane Cadeau
 * @version 1.0
 */
class PageAdapter extends FragmentStatePagerAdapter {

	/**
	 * The number of tabs. <br>
	 * Le nombre d'onglets.
	 */
	private int numTabs;

	/**
	 * PageAdapter constructor. Initialize the number of tabs. <br>
	 * Constructeur du PageAdapter. Initialise le nombre d'onglets.
	 * @param fm The FragmentManager. <br> Le FragmentManager.
	 * @param numTabs The number of tabs. <br> Le nombre d'onglets.
	 */
	PageAdapter(FragmentManager fm, int numTabs) {
		super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		this.numTabs = numTabs;
	}

	/**
	 * Get the Fragment at the position tab. <br>
	 * Récupère le Fragment à l'onglet position.
	 * @param position The position of the tab (0,1,2). <br> La position de l'onglet (0,1,2).
	 * @return The Fragment corresponding to the position given in parameter. <br> Le Fragment
	 * correspondant à la position donnée en paramètre.
	 */
	@NonNull
	@Override
	public Fragment getItem(int position) {
		switch (position) {
			case 0:
				return new ListFragment();
			case 1:
				return new InputFragment();
			case 2:
				return new ListBilanFragment();
			case 3:
				return new ExportFragment();
		}
		return new ExportFragment();
	}

	/**
	 * Get the number of tabs. <br>
	 * Récupère le nombre d'onglets.
	 * @return The number of tabs. <br> Le nombre d'onglets.
	 */
	@Override
	public int getCount() {
		return numTabs;
	}
}
