package com.bignerdranch.criminalintent;

import android.app.Fragment;

//CrimeListActivity����̳г���SingleFragmentActivity��,��ʵ�ָ���ĳ��󷽷�
public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

}
