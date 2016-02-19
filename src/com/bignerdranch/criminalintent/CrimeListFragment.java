package com.bignerdranch.criminalintent;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;

public class CrimeListFragment extends ListFragment {

	private ArrayList<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();// 先获取CrimeLab单例，再获取其中的crime列表
	}
}
