package com.avgtechie.mytwitterappclient.models;

import org.json.JSONObject;

public class UserCredential extends BaseModel {

	// private String screenName;
	// private String profileImageUrl;

	public static UserCredential fromJson(JSONObject jsonObject) {
		UserCredential userCred = new UserCredential();
		userCred.jsonObject = jsonObject;
		return userCred;
	}

	public String getScreenName() {
		return this.getString("screen_name");
	}

	public String getProfileImageUrl() {
		return this.getString("profile_image_url_https");
	}

	public String getUserName() {
		return this.getString("name");
	}

	public String getFollowing() {
		return this.getString("friends_count");
	}

	public String getFollower() {
		return this.getString("followers_count");
	}

	public String getTagLine() {
		return this.getString("description");
	}
}
