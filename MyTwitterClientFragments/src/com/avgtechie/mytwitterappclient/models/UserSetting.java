package com.avgtechie.mytwitterappclient.models;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class UserSetting {

	private static final String TAG = "UserSetting";
	private String userScreenName;

	public UserSetting(JSONObject jsonObj) {
		try {
			this.userScreenName = jsonObj.getString("screen_name");
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}
	}

	public String getUserScreenName() {
		return userScreenName;
	}
}
