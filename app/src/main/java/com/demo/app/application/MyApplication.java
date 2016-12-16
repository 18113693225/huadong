package com.demo.app.application;

import com.demo.app.util.CustomConstants;

import android.app.Application;
import android.content.SharedPreferences;

public class MyApplication extends Application
{

	@Override
	public void onCreate(){
		removeTempFromPref();
	}
	
	private void removeTempFromPref()
	{
		SharedPreferences sp = getSharedPreferences(
				CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
		sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
	}
}
