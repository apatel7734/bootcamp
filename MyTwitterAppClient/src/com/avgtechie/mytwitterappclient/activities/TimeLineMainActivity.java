package com.avgtechie.mytwitterappclient.activities;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.adapters.TweetsAdapter;
import com.avgtechie.mytwitterappclient.listeners.EndlessScrollListener;
import com.avgtechie.mytwitterappclient.models.Helper;
import com.avgtechie.mytwitterappclient.models.Tweet;
import com.avgtechie.mytwitterappclient.models.UserCredential;
import com.avgtechie.mytwitterappclient.models.UserSetting;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineMainActivity extends Activity {

	protected static final String TAG = "TimeLineActivity";
	List<Tweet> tweets;
	ListView lvTweets;
	TweetsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		lvTweets = (ListView) findViewById(R.id.lv_tweets);
		getUserInformation();
		loadTweets();
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void loadMore(int page) {
				loadMoreTweets(page);
			}
		});

	}

	public void getUserInformation() {
		RestClientApp.getRestClient().verifyCredential(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				UserCredential userCred = UserCredential.fromJson(response);
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				Helper.addUserInfoSharedPref(prefs, userCred);
				getActionBar().setTitle(Helper.getSharedPrefUserScreenName(prefs));
			}
		});
	}

	public void getSettings() {
		RestClientApp.getRestClient().getUserSettings(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {
				UserSetting userSet = new UserSetting(response);
				String username = userSet.getUserScreenName();
				getActionBar().setTitle("@" + username);
				Log.d(TAG, response.toString());
			}
		});
	}

	public void loadTweets() {
		RestClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				tweets = Tweet.fromJson(jsonArray);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				EndlessScrollListener.page++;
			}
		}, EndlessScrollListener.page);
	}

	public void loadMoreTweets(int page) {
		RestClientApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> moreTweets = Tweet.fromJson(jsonArray);
				tweets.addAll(moreTweets);
				moreTweets.clear();
				adapter.notifyDataSetChanged();
				EndlessScrollListener.load = true;
				EndlessScrollListener.page++;
			}
		}, page);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.item_refresh:
			Toast.makeText(this, "Item Refresh", Toast.LENGTH_SHORT).show();
			break;

		case R.id.item_compose:
			Intent intent = new Intent(this, TweetComposeActivity.class);
			startActivity(intent);
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

}
