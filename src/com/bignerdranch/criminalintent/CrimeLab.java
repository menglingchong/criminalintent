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
			mCrimes = mSerializer.loadCrimes();// 先从JSON文件中加载crime数据，如果没有则创建空的crime数据列表
		} catch (Exception e) {
			// TODO Auto-generated catch block
			mCrimes = new ArrayList<Crime>();// 创建空的crime数据列表
			Log.e(TAG, "Error loading crimes:", e);
		}
	}

	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());// c.getApplicationContext(),该方法是获取全局的context
		}
		return sCrimeLab;
	}

	// 返回数组列表
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}

	// 返回带有指定ID的Crime对象
	public Crime getCrime(UUID id) {
		for (Crime c : mCrimes) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	// 添加新的Crime
	public void addCrime(Crime c) {
		mCrimes.add(c);
	}

	// 删除Crime
	public void deleteCrime(Crime c) {
		mCrimes.remove(c);
	}

	// 将crime列表中的数据转换为JSON格式的数据，并保存到文件中
	public boolean saveCrimes() {
		try {
			mSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "crimes saved to file");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Error saving crimes:", e);// 文件保存失败时，显示错误的信息
			return false;
		}
	}
}
