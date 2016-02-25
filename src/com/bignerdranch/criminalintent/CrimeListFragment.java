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
		setHasOptionsMenu(true);// �÷����Ǹ�֪CrimeListFragment�����ϵͳѡ��˵������Ļص�
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();// �Ȼ�ȡCrimeLab�������ٻ�ȡ���е�crime�б�

		// ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),
		// android.R.layout.simple_list_item_1, mCrimes);
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);// setListAdapter()��һ��ListFragment��ı�����������Ϊ����ListView����adapter
		setRetainInstance(true);// ����CrimeListFragment��״̬��Ϣ,��activity����ʱ���Ա�֤fragment��״̬����
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
			registerForContextMenu(listView);// Ϊ�����Ĳ˵��Ǽ�listView��ͼ
		} else {
			listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE_MODAL);// �����б���ͼ��ѡ��ģʽ
			listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

				@Override
				// ��ͼ��ѡ�л���ʱ���ø÷���
				public void onItemCheckedStateChanged(ActionMode mode,
						int position, long id, boolean checked) {
					// TODO Auto-generated method stub

				}

				@Override
				// MultiChoiceModeListenerʵ������һ���ӿڣ���ActionMode.Callback
				// �û���Ļ���������Ĳ���ģʽʱ���ᴴ��һ��ActionMode���ʵ��
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					MenuInflater inflater = mode.getMenuInflater();// �Ӳ���ģʽ���MenuInflater������ģʽ���������Ĳ�������������
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
						mode.finish();// ���ٲ���ģʽ
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
		// getListAdapter()��һ��ListFragment��ı�������
		Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
		Log.d(TAG, c.getTitle() + " was clicked"); // ����������ListFragment�б���ͼ�ϵ�adapter��
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
			// Handle result,�����activity���ݸ�fragment������,��ͨ��fragment��ȡ���صĽ��
			// fragment�ܹ���activity�н��շ��ؽ��
		}
	}

	// �������Ƶ�adapter������
	private class CrimeAdapter extends ArrayAdapter<Crime> {

		public CrimeAdapter(ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);// ���ø���Ĺ��췽������Crime����������б�
		}

		// ����getView()���������ز����ڶ��Ʋ��ֵ���ͼ���󣬲�����Ӧ��Crime����
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);// �Ӷ��Ʋ��� ����һ���µ���ͼ����
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
			return convertView;// ����ͼ���󷵻ظ�listView
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

	// ���������Ĳ˵�
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
		int position = info.position;// ��ȡѡ���б������ݼ��е�λ����Ϣ
		// ͨ����ȡ�����������ѡ��λ�õ�crime��Ϣ
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
