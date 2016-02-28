package com.bignerdranch.criminalintent;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class CrimeFragment extends Fragment {

	private static final String TAG = "CrimeFragment";
	public static final String EXTRA_CRIME_ID = "com.bignerdranch.criminalintent.crime_id";
	private static final String DIALOG_DATE = "date";
	private static final int REQUEST_DATE = 0;
	private static final int REQUEST_PHOTO = 1;
	private static final int REQUEST_CONTACT = 2;
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSolvedCheckBox;
	private ImageButton mPhotoButton;
	private ImageView mPhotoView;
	private Button mSuspectButton;
	private static final String DIALOG_IMAGE = "image";

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

		mPhotoButton = (ImageButton) view.findViewById(R.id.crime_imageButton);
		mPhotoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), CrimeCameraActivity.class);
				// startActivity(i);
				startActivityForResult(i, REQUEST_PHOTO);
			}
		});

		mPhotoView = (ImageView) view.findViewById(R.id.crime_imageView);
		mPhotoView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Photo p = mCrime.getPhoto();
				if (p == null) {
					return;
				}

				FragmentManager fm = getActivity().getSupportFragmentManager();
				String path = getActivity().getFileStreamPath(p.getFilename())
						.getAbsolutePath();
				ImageFragment.newInstance(path).show(fm, DIALOG_IMAGE);
			}
		});

		// ��ѯPackageManagerȷ���豸�Ƿ�������
		PackageManager pm = getActivity().getPackageManager();
		boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
				|| pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
				|| Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
				|| Camera.getNumberOfCameras() > 0;
		if (!hasACamera) {
			mPhotoButton.setEnabled(false);
		}

		Button reportButton = (Button) view
				.findViewById(R.id.crime_reportButton);
		reportButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("text/plain");
				i.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
				i.putExtra(Intent.EXTRA_SUBJECT,
						getString(R.string.crime_report_subject));
				i = Intent.createChooser(i, getString(R.string.send_report));// ����һ��ÿ�ζ���ʾ��activity��ѡ����
				startActivity(i);
			}
		});

		mSuspectButton = (Button) view.findViewById(R.id.crime_suspectButton);
		mSuspectButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��ʽintent�ɲ����Լ����ݻ�ȡλ�����
				Intent i = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(i, REQUEST_CONTACT);
			}
		});

		if (mCrime.getSuspect() != null) {
			mSuspectButton.setText(mCrime.getSuspect());
		}
		return view;
	}

	// �����ź��ͼƬ���ø�ImageView��ͼ
	private void showPhoto() {
		Photo p = mCrime.getPhoto();
		BitmapDrawable b = null;
		if (p != null) {
			String path = getActivity().getFileStreamPath(p.getFilename())
					.getAbsolutePath();
			b = PictureUtils.getScaledDrawable(getActivity(), path);
		}
		mPhotoView.setImageDrawable(b);
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showPhoto();// ����ͼƬ
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		PictureUtils.cleanImageView(mPhotoView);// ж��ͼƬ
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
		} else if (requestCode == REQUEST_PHOTO) {
			String filename = data
					.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
			if (filename != null) {
				// Log.i(TAG, "filename:" + filename);// �����Ƭ�ļ���

				Photo p = new Photo(filename);
				mCrime.setPhoto(p);
				Log.i(TAG, "Crime: " + mCrime.getTitle() + " has a photo");
				showPhoto();
			}
		} else if (requestCode == REQUEST_CONTACT) {
			Uri contactUri = data.getData();

			String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME };

			Cursor c = getActivity().getContentResolver().query(contactUri,
					queryFields, null, null, null);

			if (c.getCount() == 0) {
				c.close();
				return;
			}
			c.moveToFirst();
			String suspect = c.getString(0);
			mCrime.setSuspect(suspect);
			mSuspectButton.setText(suspect);
			c.close();
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

	// ������ʽ���ַ���
	private String getCrimeReport() {
		String solvedString = null;
		if (mCrime.isSolved()) {
			solvedString = getString(R.string.crime_report_solved);
		} else {
			solvedString = getString(R.string.crime_report_unsolved);
		}

		String dateFormate = "EEE,MMM dd";
		String dateString = DateFormat.format(dateFormate, mCrime.getDate())
				.toString();

		String suspect = mCrime.getSuspect();
		if (suspect == null) {
			suspect = getString(R.string.crime_report_no_suspect);
		} else {
			suspect = getString(R.string.crime_report_suspect, suspect);
		}

		String report = getString(R.string.crime_report, mCrime.getTitle(),
				dateString, solvedString, suspect);
		return null;

	}

}
