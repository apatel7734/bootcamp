package com.avgtechie.mytwitterappclient.fragments;

import java.util.List;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.avgtechie.mytwitterappclient.listeners.EndlessScrollListener;
import com.avgtechie.mytwitterappclient.models.Tweet;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsTimeLineFragment extends TweetsBaseFragment {

	protected static final String TAG = "MentionsTimeLineActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EndlessScrollListener.page = 1;
		loadMentionsTimeLineTweets();
	}

	public void loadMentionsTimeLineTweets() {

		RestClientApp.getTweeterRestClient().getMentionsTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> mentionsTweets = Tweet.fromJson(jsonArray);
				Log.d(TAG, "MentionsTimeLine = " + mentionsTweets);
				Log.d(TAG, "MentionsTimeLine array = " + jsonArray);
				getAdapter().addAll(mentionsTweets);
				EndlessScrollListener.page++;
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Failed in load");
				Log.d(TAG, t.getCause().toString());
				Log.d(TAG, t.toString());
			}
		}, EndlessScrollListener.page);

	}

	@Override
	public void onStart() {
		super.onStart();
		getListView().setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void loadMore(int page) {
				loadMoreTweets(page);

			}
		});
	}

	public void loadMoreTweets(int page) {
		RestClientApp.getTweeterRestClient().getMentionsTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> moreTweets = Tweet.fromJson(jsonArray);
				getAdapter().addAll(moreTweets);
				moreTweets.clear();
				EndlessScrollListener.load = true;
				EndlessScrollListener.page++;
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Failed in load more");
				Log.d(TAG, t.getCause().toString());
				Log.d(TAG, t.toString());
			}

		}, page);
	}
}
