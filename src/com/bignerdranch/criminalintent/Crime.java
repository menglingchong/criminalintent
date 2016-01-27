package com.bignerdranch.criminalintent;

import java.util.UUID;

public class Crime {

	private UUID mId;
	private String mTitle;

	public Crime() {
		// 生成唯一标识符
		mId = UUID.randomUUID();
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public UUID getId() {
		return mId;
	}

}
