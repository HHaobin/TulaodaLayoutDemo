package com.gjs.gjsmall.utils;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class PreferenceUtils
{

	private static SharedPreferences mSp;

	private static SharedPreferences getSp(Context context)
	{
		if (mSp == null)
		{
			mSp = context.getSharedPreferences("zhbj", Context.MODE_PRIVATE);
		}
		return mSp;
	}


	public static boolean getBoolean(Context context, String key)
	{
		return getBoolean(context, key, false);
	}


	public static boolean getBoolean(Context context, String key,
									 boolean defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getBoolean(key, defValue);
	}


	public static void putBoolean(Context context, String key, boolean value)
	{
		SharedPreferences sp     = getSp(context);
		Editor            editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}


	public static String getString(Context context, String key)
	{
		return getString(context, key, null);
	}


	public static String getString(Context context, String key, String defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getString(key, defValue);
	}

	public static void putString(Context context, String key, String value)
	{
		SharedPreferences sp     = getSp(context);
		Editor            editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}


	public static int getInt(Context context, String key)
	{
		return getInt(context, key, -1);
	}


	public static int getInt(Context context, String key, int defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getInt(key, defValue);
	}


	public static void putInt(Context context, String key, int value)
	{
		SharedPreferences sp     = getSp(context);
		Editor            editor = sp.edit();
		editor.putInt(key, value);
		editor.commit();
	}


	public static long getLong(Context context, String key)
	{
		return getLong(context, key, -1);
	}


	public static long getLong(Context context, String key, long defValue)
	{
		SharedPreferences sp = getSp(context);
		return sp.getLong(key, defValue);
	}


	public static void putLong(Context context, String key, long value)
	{
		SharedPreferences sp     = getSp(context);
		Editor            editor = sp.edit();
		editor.putLong(key, value);
		editor.commit();
	}
}
