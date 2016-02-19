package com.bignerdranch.criminalintent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

//����activity�࣬�ó������а������󷽷�
public abstract class SingleFragmentActivity extends Activity {

	protected abstract Fragment createFragment();// ���󷽷������ཫ��ʵ�����÷���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fragment);
		FragmentManager fragmentManager = getFragmentManager();
		Fragment fragment = fragmentManager
				.findFragmentById(R.id.fragmentCotainer);
		if (fragment == null) {
			fragment = createFragment();
			fragmentManager.beginTransaction()
					.add(R.id.fragmentCotainer, fragment).commit();
		}
	}
}
