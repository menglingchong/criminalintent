package com.bignerdranch.criminalintent;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

//抽象activity类，该抽象类中包含抽象方法
public abstract class SingleFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();// 抽象方法，子类将会实例化该方法

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
