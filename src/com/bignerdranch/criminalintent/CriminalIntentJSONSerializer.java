package com.bignerdranch.criminalintent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class CriminalIntentJSONSerializer {

	private Context mContext;
	private String mFilename;

	public CriminalIntentJSONSerializer(Context c, String f) {
		mContext = c;
		mFilename = f;
	}

	// 读取Crime数组列表中的数据，进行数据格式的转换，然后写JSON文件
	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,
			IOException {
		JSONArray array = new JSONArray();
		for (Crime crime : crimes) {
			array.put(crime.toJSON());
		}
		Writer writer = null;
		try {
			OutputStream out = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
		} finally {
			// TODO: handle exception
			if (writer != null)
				writer.close();
		}
	}

	// 从JSON文件中读取JSON数据，并转换为JSONObject类型的string，然后解析为JSONArray，再解析为ArrayList并返回ArrayList
	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;
		try {
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				jsonString.append(line);
			}
			// 将JSONObject类型的string解析为json数组
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
					.nextValue();
			for (int i = 0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return crimes;
	}
}
