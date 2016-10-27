package com.qf.kenlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Ken
 *
 */
public class SharedUtil {

	private static SharedPreferences sharedPreferences;
	private static SharedPreferences.Editor editor;
	
	/**
	 */
	public static void init(Context context){
		sharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
	}
	
	public static void putString(String key, String value){
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getString(String key){
		return sharedPreferences.getString(key, null);
	}
	
	public static void putInt(String key, int value){
		editor.putInt(key, value);
		editor.commit();
	}
	
	public static int getInt(String key){
		return sharedPreferences.getInt(key, -1);
	}
	
	public static void putBoolean(String key, boolean value){
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static boolean getBoolean(String key){
		return sharedPreferences.getBoolean(key, false);
	}
}
