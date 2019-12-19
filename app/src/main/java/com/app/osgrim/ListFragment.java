package com.app.osgrim;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

	private View listView;
	private MainActivity mainAct;

	public ListFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		listView = inflater.inflate(R.layout.fragment_list, container, false);
		mainAct = (MainActivity) getActivity();
		return listView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mainAct.listReports = listView.findViewById(R.id.listReports);
		mainAct.listReports.setLayoutManager(new LinearLayoutManager(mainAct));
		mainAct.listReports.setAdapter(mainAct.reportAdapter);
	}

}
