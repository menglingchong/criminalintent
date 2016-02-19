package com.bignerdranch.criminalintent;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CrimeListFragment extends ListFragment {

	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();// 先获取CrimeLab单例，再获取其中的crime列表

		ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),
				android.R.layout.simple_list_item_1, mCrimes);
		setListAdapter(adapter);// setListAdapter()是一个ListFragment类的便利方法。可为内置ListView设置adapter
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Crime c = (Crime) (getListAdapter()).getItem(position);// getListAdapter()是一个ListFragment类的便利方法
		Log.d(TAG, c.getTitle() + " was clicked"); 				// 返回设置在ListFragment列表视图上的adapter。
	}
}
