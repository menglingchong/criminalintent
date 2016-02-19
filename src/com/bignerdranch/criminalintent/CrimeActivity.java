package com.bignerdranch.criminalintent;

import android.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

//CrimeActivity类继承抽象SingleFragmentActivity类,并实现父类的抽象方法
public class CrimeActivity extends SingleFragmentActivity {

	/*
	 * private Fragment fragment;
	 * @Override protected void onCreate(Bundle savedInstanceState) {
	 * super.onCreate(savedInstanceState);
	 * setContentView(R.layout.activity_fragment);
	 * 
	 * FragmentManager fragmentManager = getFragmentManager();
	 * FragmentTransaction transaction = fragmentManager.beginTransaction();
	 * //从fragmentManger中请求fragment，若列中有该fragment，则直接返回。 Fragment fragment =
	 * fragmentManager .findFragmentById(R.id.fragmentCotainer); if (fragment ==
	 * null) { fragment = new CrimeFragment();
	 * transaction.add(R.id.fragmentCotainer, fragment); transaction.commit(); }
	 * }
	 */
	
	@Override
	protected Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeFragment();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.crime, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
