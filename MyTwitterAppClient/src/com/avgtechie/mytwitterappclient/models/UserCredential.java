package com.avgtechie.mytwitterappclient.models;

import org.json.JSONException;
import org.json.JSONObject;

public class UserCredential {

	private String screenName;
	private String profileImageUrl;

	public static UserCredential fromJson(JSONObject jsonObject) {
		UserCredential userCred = new UserCredential();
		try {
			userCred.setScreenName(jsonObject.getString("screen_name"));
			userCred.setProfileImageUrl(jsonObject.getString("profile_image_url_https"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userCred;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

}
