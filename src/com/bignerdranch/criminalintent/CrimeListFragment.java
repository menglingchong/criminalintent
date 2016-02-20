package com.bignerdranch.criminalintent;

import java.util.ArrayList;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {

	private static final int REQUEST_CRIME = 1;
	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crimes_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();// �Ȼ�ȡCrimeLab�������ٻ�ȡ���е�crime�б�

		// ArrayAdapter<Crime> adapter = new ArrayAdapter<Crime>(getActivity(),
		// android.R.layout.simple_list_item_1, mCrimes);
		CrimeAdapter adapter = new CrimeAdapter(mCrimes);
		setListAdapter(adapter);// setListAdapter()��һ��ListFragment��ı�����������Ϊ����ListView����adapter
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
	    Log.d(TAG, c.getTitle() + " was clicked"); //����������ListFragment�б���ͼ�ϵ�adapter��
		Intent i = new Intent(getActivity(), CrimeActivity.class);
		i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
		startActivity(i);
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
}
