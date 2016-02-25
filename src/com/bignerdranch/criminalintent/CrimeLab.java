package com.bignerdranch.criminalintent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class CrimeLab {
	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";
	private ArrayList<Crime> mCrimes;
	private CriminalIntentJSONSerializer mSerializer;

	private static CrimeLab sCrimeLab;
	private Context mAppContext;

	private CrimeLab(Context appContext) {
		mAppContext = appContext;
		// mCrimes = new ArrayList<Crime>();
		// for (int i = 0; i < 100; i++) {
		// Crime c = new Crime();
		// c.setTitle("Crime #" + i);
		// c.setSolved(i % 2 == 0);
		// mCrimes.add(c);
		// }
		mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);
		try {
			mCrimes = mSerializer.loadCrimes();// �ȴ�JSON�ļ��м���crime���ݣ����û���򴴽��յ�crime�����б�
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mCrimes = new ArrayList<Crime>();// �����յ�crime�����б�
			Log.e(TAG, "Error loading crimes:", e);
		}
	}

	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());// c.getApplicationContext(),�÷����ǻ�ȡȫ�ֵ�context
		}
		return sCrimeLab;
	}

	// ���������б�
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}

	// ���ش���ָ��ID��Crime����
	public Crime getCrime(UUID id) {
		for (Crime c : mCrimes) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	// ����µ�Crime
	public void addCrime(Crime c) {
		mCrimes.add(c);
	}

	// ɾ��Crime
	public void deleteCrime(Crime c) {
		mCrimes.remove(c);
	}

	// ��crime�б��е�����ת��ΪJSON��ʽ�����ݣ������浽�ļ���
	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Error saving crimes:", e);// �ļ�����ʧ��ʱ����ʾ�������Ϣ
			return false;
		}
	}
}
