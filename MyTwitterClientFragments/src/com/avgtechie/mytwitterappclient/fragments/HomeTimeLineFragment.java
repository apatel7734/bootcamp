package com.avgtechie.mytwitterappclient.fragments;

import java.util.List;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.avgtechie.mytwitterappclient.activities.TweetProfileActivity;
import com.avgtechie.mytwitterappclient.listeners.EndlessScrollListener;
import com.avgtechie.mytwitterappclient.models.Tweet;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class HomeTimeLineFragment extends TweetsBaseFragment {

	protected static final String TAG = "HomeTimeLineActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EndlessScrollListener.page = 1;
		loadHomeTimeLineTweets();
	}

	public void loadHomeTimeLineTweets() {

		RestClientApp.getTweeterRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> homeTweets = Tweet.fromJson(jsonArray);
				getAdapter().addAll(homeTweets);
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
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), TweetProfileActivity.class);
				intent.putExtra("screenName", view.getTag().toString());
				startActivity(intent);
			}
		});
	}

	public void loadMoreTweets(int page) {
		RestClientApp.getTweeterRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> moreTweets = Tweet.fromJson(jsonArray);
				Log.d(TAG, "HomeTimeLine = " + moreTweets);
				Log.d(TAG, "HomeTimeLine array = " + jsonArray);
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
