package com.avgtechie.mytwitterappclient.models;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class HelperSharedPrefs {

	private final static String SCREEN_NAME_KEY = "screennamekey";
	private final static String PROFILE_URL_KEY = "profile_url_key";
	
	

	public static void addUserInfoSharedPref(SharedPreferences pref, UserCredential userCred) {
		
		Editor edit = pref.edit();
		edit.putString(SCREEN_NAME_KEY, userCred.getScreenName());
		edit.putString(PROFILE_URL_KEY, userCred.getProfileImageUrl());
		edit.commit();
	}
	
	

	public static String getSharedPrefUserScreenName(SharedPreferences pref) {
		return "@" + pref.getString(SCREEN_NAME_KEY, "@user");
	}

	public static String getSharedPrefUserProfileImage(SharedPreferences pref) {
		return pref.getString(PROFILE_URL_KEY, "none");
	}
}
