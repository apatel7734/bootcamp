package com.avgtechie.mytwitterappclient.fragments;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.avgtechie.mytwitterappclient.listeners.EndlessScrollListener;
import com.avgtechie.mytwitterappclient.models.Tweet;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TweetsBaseFragment {

	protected static final String TAG = "UserTimeLineFragment";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMentionsTimeLineTweets();
	}

	public void loadMentionsTimeLineTweets() {

		RestClientApp.getTweeterRestClient().getUserTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> userTweets = Tweet.fromJson(jsonArray);
				Log.d(TAG, "UserTimeLineFragment = " + userTweets);
				Log.d(TAG, "UserTimeLineFragment array = " + jsonArray);
				getAdapter().addAll(userTweets);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Failed in load");
				Log.d(TAG, t.getCause().toString());
				Log.d(TAG, t.toString());
			}
		});

	}
}
