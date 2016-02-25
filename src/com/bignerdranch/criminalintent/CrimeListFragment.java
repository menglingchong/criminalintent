package com.bignerdranch.criminalintent;

import java.util.ArrayList;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

	private static final int REQUEST_CRIME = 1;
	private boolean mSubtitleVisible;
	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);// 该方法是告知CrimeListFragment需接收系统选项菜单方法的回调
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();// 先获取CrimeLab单例，再获取其中的crime列表

		// ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),
		// android.R.layout.simple_list_item_1, mCrimes);
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);// setListAdapter()是一个ListFragment类的便利方法。可为内置ListView设置adapter
		setRetainInstance(true);// 保存CrimeListFragment的状态信息,当activity销毁时可以保证fragment的状态不变
		mSubtitleVisible = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = super.onCreateView(inflater, container, savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (mSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}
		}

		ListView listView = (ListView) v.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			registerForContextMenu(listView);// 为上下文菜单登记listView视图
		} else {
			listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);// 设置列表视图的选择模式
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

				@Override
				// 视图在选中或撤销时调用该方法
				public void onItemCheckedStateChanged(ActionMode mode,
						int position, long id, boolean checked) {
					// TODO Auto-generated method stub

				}

				@Override
				// MultiChoiceModeListener实现了另一个接口，即ActionMode.Callback
				// 用户屏幕进入上下文操作模式时，会创建一个ActionMode类的实例
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					MenuInflater inflater = mode.getMenuInflater();// 从操作模式获得MenuInflater，操作模式负责上下文操作栏进行配置
					inflater.inflate(R.menu.crime_list_item_context, menu);
					return true;
				}

				@Override
				public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode,
						MenuItem item) {
					// TODO Auto-generated method stub
					switch (item.getItemId()) {
					case R.id.menu_item_delete_crime:
						CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
						CrimeLab crimeLab = CrimeLab.get(getActivity());
						for (int i = adapter.getCount() - 1; i >= 0; i--) {
							if (getListView().isItemChecked(i)) {
								crimeLab.deleteCrime(adapter.getItem(i));
							}
						}
						mode.finish();// 销毁操作模式
						adapter.notifyDataSetChanged();
						return true;

					default:
						return false;
					}
				}

				@Override
				public void onDestroyActionMode(ActionMode mode) {
					// TODO Auto-generated method stub

				}
			});
		}
		return v;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((CrimeAdapter) getListAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		// Crime c = (Crime) (getListAdapter()).getItem(position);//
		// getListAdapter()是一个ListFragment类的便利方法
		Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
		Log.d(TAG, c.getTitle() + " was clicked"); // 返回设置在ListFragment列表视图上的adapter。
		// Intent i = new Intent(getActivity(), CrimeActivity.class);
		Intent i = new Intent(getActivity(), CrimePagerActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		// startActivity(i);
		startActivityForResult(i, REQUEST_CRIME);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CRIME) {
			// Handle result,处理从activity传递给fragment的数据,即通过fragment获取返回的结果
			// fragment能够从activity中接收返回结果
		}
	}

	// 创建定制的adapter适配器
	private class CrimeAdapter extends ArrayAdapter<Crime> {

		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);// 调用父类的构造方法来绑定Crime对象的数组列表
		}

		// 覆盖getView()方法，返回产生于定制布局的视图对象，并填充对应的Crime数据
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);// 从定制布局 产生一个新的视图对象
			}
			Crime c = getItem(position);
			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_titleTextView);
			titleTextView.setText(c.getTitle());
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.crime_list_item_dateTextView);
			dateTextView.setText(c.getDate().toString());
			CheckBox solvedCheckBox = (CheckBox) convertView
					.findViewById(R.id.crime_list_item_solvedCheckBox);
			solvedCheckBox.setChecked(c.isSolved());
			return convertView;// 将视图对象返回给listView
		}

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitle != null) {
			showSubtitle.setTitle(R.string.hide_subtitle);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent i = new Intent(getActivity(), CrimePagerActivity.class);
			i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivityForResult(i, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				mSubtitleVisible = true;
				item.setTitle(R.string.hide_subtitle);
			} else {
				getActivity().getActionBar().setSubtitle(null);
				mSubtitleVisible = false;
				item.setTitle(R.string.show_subtitle);
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	// 创建上下文菜单
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context,
				menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;// 获取选中列表在数据集中的位置信息
		// 通过获取适配器来获得选中位置的crime信息
		CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
		Crime crime = adapter.getItem(position);

		switch (item.getItemId()) {
		case R.id.menu_item_delete_crime:
			CrimeLab.get(getActivity()).deleteCrime(crime);
			adapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}
}
