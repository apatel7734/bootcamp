package com.avgtechie.mytwitterappclient.models;

import org.json.JSONObject;

public class UserCredential extends BaseModel {

	// private String screenName;
	// private String profileImageUrl;

	public static UserCredential fromJson(JSONObject jsonObject) {
		UserCredential userCred = new UserCredential();
		userCred.jsonObject = jsonObject;
		// userCred.setScreenName(jsonObject.getString("screen_name"));
		// userCred.setProfileImageUrl(jsonObject.getString("profile_image_url_https"));
		return userCred;
	}

	public String getScreenName() {
		return this.getString("screen_name");
		// return screenName;
	}

	public String getProfileImageUrl() {
		return this.getString("profile_image_url_https");
		// return profileImageUrl;
	}
}
