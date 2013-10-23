package com.avgtechie.mytwitterappclient.activities;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
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
import com.avgtechie.mytwitterappclient.models.HelperSharedPrefs;
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
		RestClientApp.getTweeterRestClient().verifyCredential(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				UserCredential userCred = UserCredential.fromJson(response);
				SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
				HelperSharedPrefs.addUserInfoSharedPref(prefs, userCred);
				getActionBar().setTitle("@" + HelperSharedPrefs.getSharedPrefUserScreenName(prefs));
			}
		});
	}

	public void getSettings() {
		RestClientApp.getTweeterRestClient().getUserSettings(new JsonHttpResponseHandler() {

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
		RestClientApp.getTweeterRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				tweets = Tweet.fromJson(jsonArray);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
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

	public void loadMoreTweets(int page) {
		RestClientApp.getTweeterRestClient().getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				List<Tweet> moreTweets = Tweet.fromJson(jsonArray);
				tweets.addAll(moreTweets);
				moreTweets.clear();
				adapter.notifyDataSetChanged();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		switch (itemId) {
		case R.id.item_refresh:
			Toast.makeText(this, "Item Refresh", Toast.LENGTH_SHORT).show();
			break;

		case R.id.item_compose:
			Intent intent = new Intent(this, TweetComposeActivity.class);
			startActivityForResult(intent, 2);
			// startActivity(intent);
			break;
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 2:
			String tweetStatus = data.getExtras().getString("tweet");
			Log.d(TAG, "status = " + tweetStatus);
			JSONArray array = getTweet(tweetStatus);
			List<Tweet> newTweet = Tweet.fromJson(array);
			tweets.add(0, newTweet.get(0));
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}
	}

	public JSONArray getTweet(String tweetStatus) {
		JSONArray tweetArr = new JSONArray();
		JSONObject tweet = new JSONObject();
		JSONObject user = new JSONObject();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		try {
			user.put("profile_image_url_https", HelperSharedPrefs.getSharedPrefUserProfileImage(prefs));
			user.put("screen_name", HelperSharedPrefs.getSharedPrefUserScreenName(prefs));
			user.put("name", HelperSharedPrefs.getSharedPrefUserName(prefs));
			tweet.put("user", user);
			tweet.put("created_at", getDate());
			tweet.put("text", tweetStatus);
			tweetArr.put(tweet);
		} catch (JSONException e) {
		}
		return tweetArr;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

	public String getDate() {
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat origFormat = new SimpleDateFormat("E MMM dd HH:mm:ss +0000 yyyy");
		String returnDate = origFormat.format(date);
		return returnDate;
	}

}
