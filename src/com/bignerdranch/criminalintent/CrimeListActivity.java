package com.bignerdranch.criminalintent;

import android.app.Fragment;

//CrimeListActivity该类继承抽象SingleFragmentActivity类,并实现父类的抽象方法
public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

}
