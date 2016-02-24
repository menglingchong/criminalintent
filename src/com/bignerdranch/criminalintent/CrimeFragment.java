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
	// 配置Fragment实例
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// UUID crimeId = (UUID) getActivity().getIntent().getSerializableExtra(
		// EXTRA_CRIME_ID);
		UUID crimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);// 获取fragment的argument
		mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
		setHasOptionsMenu(true);
	}

	// 更新日期
	public void updateDate() {
		mDateButton.setText(mCrime.getDate().toString());
	}

	@Override
	// 创建和配置Fragment视图,该方法将生成的View返回给托管的activity
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
				// 替代fragment的构造方法，调用fragment的newInstance()方法
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);// 设置目标fragment为CrimeFragment
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

	// 将argument附加给fragment
	public static CrimeFragment newInstance(UUID crimeId) {
		// TODO Auto-generated method stub
		Bundle args = new Bundle();// 创建bundle对象，并将argument放入bundle中
		args.putSerializable(EXTRA_CRIME_ID, crimeId);

		CrimeFragment fragment = new CrimeFragment();// fragment实例的创建，附加argument给fragment
		fragment.setArguments(args);
		return fragment;
	}

	private void returnResult() {
		// TODO Auto-generated method stub
		getActivity().setResult(Activity.RESULT_OK, null);// 只有activity拥有返回结果
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK)
			return;
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);// 获取DatePickerFragment的附加到intent上的extra数据
			mCrime.setDate(date);// 利用从fragment回传的数据更新模型层
			// mDateButton.setText(mCrime.getDate().toString());
			updateDate();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			// 实现向上导航,回退到crime列表界面
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());// 导航至父activity界面
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
		CrimeLab.get(getActivity()).saveCrimes();// 调用saveCriems()方法保存crime数据
	}

}
