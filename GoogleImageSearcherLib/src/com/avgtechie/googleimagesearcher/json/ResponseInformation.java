package com.avgtechie.googleimagesearcher.json;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ResponseInformation {
	private static final String TAG = "ResponseInformation";
	private String title;
	private String fullUrl;
	private String thumbUrl;

	public ResponseInformation() {
	}

	public ResponseInformation(JSONObject result) {
		try {
			this.fullUrl = result.getString("url");
			this.thumbUrl = result.getString("tbUrl");
			this.title = result.getString("title");
		} catch (JSONException e) {
			this.fullUrl = null;
			this.title = null;
			this.thumbUrl = null;
		}
	}

	public List<ResponseInformation> fromJSONArrayToResInfo(JSONObject res) {
		JSONArray resArray;
		JSONObject responseData;
		List<ResponseInformation> resInfoList = new ArrayList<ResponseInformation>();
		try {
			responseData = res.getJSONObject("responseData");
			resArray = responseData.getJSONArray("results");
			if (resArray != null && resArray.length() > 0) {
				for (int i = 0; i < resArray.length(); i++) {
					JSONObject resultObj = resArray.getJSONObject(i);
					ResponseInformation resInfo = new ResponseInformation(resultObj);
					resInfoList.add(resInfo);
				}
			}
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage());
		}

		return resInfoList;
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
