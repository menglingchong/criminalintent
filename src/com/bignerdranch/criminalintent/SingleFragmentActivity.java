package com.bignerdranch.criminalintent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

//����activity�࣬�ó������а������󷽷�
public abstract class SingleFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();// ���󷽷������ཫ��ʵ�����÷���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		FragmentManager fragmentManager = getSupportFragmentManager();
		Fragment fragment = fragmentManager
				.findFragmentById(R.id.fragmentCotainer);
		if (fragment == null) {
			fragment = createFragment();
			fragmentManager.beginTransaction()
					.add(R.id.fragmentCotainer, fragment).commit();
		}
	}
}
