package com.example.pytest.app.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Conf {
	private final String SP_FILE = "steampy_preference";
    private Context context;

	public Conf(Context context) {
		this.context=context;
	}

	public boolean createConfigInt(String Key, int Value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putInt(Key, Value);
		return editor.commit();
	}

	public boolean createString(String Key, String Value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_MULTI_PROCESS);
		Editor editor = mPreferences.edit();
		editor.putString(Key, Value);
		return editor.commit();
	}

	public boolean createLong(String Key, long Value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_MULTI_PROCESS);
		Editor editor = mPreferences.edit();
		editor.putLong(Key, Value);
		return editor.commit();
	}

	public boolean createBoolean(String Key, boolean Value) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		Editor editor = mPreferences.edit();
		editor.putBoolean(Key, Value);
		return editor.commit();
	}

	public int getConfigInt(String Key) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		return mPreferences.getInt(Key, 0);
	}

	public String getConfigString(String Key) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_MULTI_PROCESS);
		return mPreferences.getString(Key, "");
	}

	public long getConfigLong(String Key) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_MULTI_PROCESS);
		return mPreferences.getLong(Key, 0);
	}

	public boolean getBooleanConfigPara(String Key) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		return mPreferences.getBoolean(Key, false);
	}

	public boolean ClearConfigs(String Key) {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		return mPreferences.edit().remove(Key).commit();
	}

	public boolean clearAll() {
		SharedPreferences mPreferences = context.getSharedPreferences(SP_FILE, Context.MODE_PRIVATE);
		return mPreferences.edit().clear().commit();
	}


}
