package com.bignerdranch.criminalintent;

import java.util.UUID;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {

	public static final String EXTRA_CRIME_ID = "com.bignerdranch.criminalintent.crime_id";
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
	}

	@Override
	// 创建和配置Fragment视图,该方法将生成的View返回给托管的activity
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_crime, container, false);
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
		mDateButton.setText(mCrime.getDate().toString());
		mDateButton.setEnabled(false);

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

}
