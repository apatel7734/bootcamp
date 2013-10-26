package com.avgtechie.mytwitterappclient.models;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet extends BaseModel {
	private static final String TAG = "Tweet";

	private User user;

	public User getUser() {
		return user;
	}

	public String getBody() {
		return getString("text");
	}

	public long getId() {
		return getLong("id");
	}

	public boolean isFavorited() {
		return getBoolean("favorited");
	}

	public boolean isRetweeted() {
		return getBoolean("retweeted");
	}

	public String getTweetCreatedDate() {
		return getString("created_at");
	}

	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		try {
			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public static List<Tweet> fromJson(JSONArray jsonArray) {
		List<Tweet> tweets = new LinkedList<Tweet>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
}