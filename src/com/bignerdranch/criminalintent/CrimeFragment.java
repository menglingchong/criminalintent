package com.bignerdranch.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {

	public static final String EXTRA_CRIME_ID = "com.bignerdranch.criminalintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final int REQUEST_DATE = 0;
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;

	@Override
	// ����Fragmentʵ��
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(
		// EXTRA_CRIME_ID);
		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);// ��ȡfragment��argument
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		setHasOptionsMenu(true);
	}

	// ��������
	public void updateDate() {
		mDateButton.setText(mCrime.getDate().toString());
	}

	@Override
	// ����������Fragment��ͼ,�÷��������ɵ�View���ظ��йܵ�activity
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime, container, false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		mTitleField = (EditText) view.findViewById(R.id.crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mCrime.setTitle(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mDateButton = (Button) view.findViewById(R.id.crime_date);
		updateDate();
		// mDateButton.setText(mCrime.getDate().toString());
		// mDateButton.setEnabled(false);
		mDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fm = getActivity().getSupportFragmentManager();
				// DatePickerFragment dialog = new DatePickerFragment();
				// ���fragment�Ĺ��췽��������fragment��newInstance()����
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);// ����Ŀ��fragmentΪCrimeFragment
				dialog.show(fm, DIALOG_DATE);
			}
		});

		mSolvedCheckBox = (CheckBox) view.findViewById(R.id.crime_solved);
		mSolvedCheckBox.setChecked(mCrime.isSolved());
		mSolvedCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						mCrime.setSolved(isChecked);
					}
				});
		return view;
	}

	// ��argument���Ӹ�fragment
	public static CrimeFragment newInstance(UUID crimeId) {
		// TODO Auto-generated method stub
		Bundle args = new Bundle();// ����bundle���󣬲���argument����bundle��
		args.putSerializable(EXTRA_CRIME_ID, crimeId);

		CrimeFragment fragment = new CrimeFragment();// fragmentʵ���Ĵ���������argument��fragment
		fragment.setArguments(args);
		return fragment;
	}

	private void returnResult() {
		// TODO Auto-generated method stub
		getActivity().setResult(Activity.RESULT_OK, null);// ֻ��activityӵ�з��ؽ��
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);// ��ȡDatePickerFragment�ĸ��ӵ�intent�ϵ�extra����
			mCrime.setDate(date);// ���ô�fragment�ش������ݸ���ģ�Ͳ�
			// mDateButton.setText(mCrime.getDate().toString());
			updateDate();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			// ʵ�����ϵ���,���˵�crime�б����
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());// ��������activity����
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();// ����saveCriems()��������crime����
	}

}
