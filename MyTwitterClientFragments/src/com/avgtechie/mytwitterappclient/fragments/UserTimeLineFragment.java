package com.avgtechie.mytwitterappclient.fragments;

import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.avgtechie.mytwitterappclient.models.Tweet;
import com.avgtechie.mytwitterappclient.models.User;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TweetsBaseFragment {

	protected static final String TAG = "UserTimeLineFragment";

	private onUserInfoUpdateListener callBackHandler;

	public interface onUserInfoUpdateListener {
		public void updateUserInfo(User user);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		callBackHandler = (onUserInfoUpdateListener) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMentionsTimeLineTweets();
	}

	public void loadMentionsTimeLineTweets() {
		Bundle bundle = getActivity().getIntent().getExtras();
		String screenName = bundle.getString("screenName");
		RestClientApp.getTweeterRestClient().getUserTimeLine(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> userTweets = Tweet.fromJson(jsonArray);
				Log.d(TAG, "UserTimeLineFragment = " + userTweets);
				Log.d(TAG, "UserTimeLineFragment array = " + jsonArray);
				if (userTweets.size() > 0 && callBackHandler != null) {
					User user = userTweets.get(0).getUser();
					callBackHandler.updateUserInfo(user);
				}
				getAdapter().addAll(userTweets);
			}

			@Override
			public void onFailure(Throwable t) {
				Log.d(TAG, "Failed in load");
				Log.d(TAG, t.getCause().toString());
				Log.d(TAG, t.toString());
			}
		}, screenName);
	}
}
