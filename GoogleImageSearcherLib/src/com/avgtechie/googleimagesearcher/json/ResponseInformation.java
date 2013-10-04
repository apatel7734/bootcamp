package com.avgtechie.googleimagesearcher.json;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ResponseInformation {
	private static final String TAG = "ResponseInformation";
	private String title;
	private String fullUrl;
	private String thumbUrl;

	public ResponseInformation(JSONObject result) {
		try {
			this.fullUrl = result.getString("url");
			this.thumbUrl = result.getString("tbUrl");
			this.title = result.getString("title");
		} catch (JSONException e) {
			Log.d(TAG, "Error while converting json object to ResponseInformation object" + e);
			this.fullUrl = null;
			this.title = null;
			this.thumbUrl = null;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullUrl() {
		return fullUrl;
	}

	public void setFullUrl(String fullUrl) {
		this.fullUrl = fullUrl;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	@Override
	public String toString() {
		return "ResponseInformation [title=" + title + ", fullUrl=" + fullUrl + ", thumbUrl=" + thumbUrl + "]";
	}

}
