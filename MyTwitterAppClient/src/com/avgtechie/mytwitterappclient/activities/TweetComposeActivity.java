package com.avgtechie.mytwitterappclient.activities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.models.Helper;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetComposeActivity extends Activity {
	protected static final String TAG = "TweetComposeActivity";
	Button btnCancel;
	Button btnTweet;
	EditText etTweetCompose;
	TextView tvUserName;
	ImageView imgUserProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_compose);
		initViews();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		ImageLoader.getInstance().displayImage(Helper.getSharedPrefUserProfileImage(prefs), imgUserProfile);
		tvUserName.setText(Helper.getSharedPrefUserScreenName(prefs));

	}

	private void initViews() {
		btnCancel = (Button) findViewById(R.id.btn_tweet_cancel);
		btnTweet = (Button) findViewById(R.id.btn_tweet_compose);
		etTweetCompose = (EditText) findViewById(R.id.et_tweet_compose);
		tvUserName = (TextView) findViewById(R.id.tv_tweet_username);
		imgUserProfile = (ImageView) findViewById(R.id.img_tweet_profile);
	}

	public void onClickBtnTweet(View v) {
		String status = etTweetCompose.getText().toString();
		if (status.equals("")) {
			Toast.makeText(this, "Enter the text!", Toast.LENGTH_SHORT).show();
		} else {
			try {
				status = URLEncoder.encode(status, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				Log.d(TAG, "Error thrown while encoding string in UTF-8" + e.getMessage());
			}
			Toast.makeText(this, "Encoded String = " + status, Toast.LENGTH_SHORT).show();
		}

		// tweetStatus();
		finish();
	}

	public void tweetStatus() {
		RestClientApp.getRestClient().updateStatus(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d(TAG, "Response :->" + response.toString());
			}

			@Override
			public void onFailure(Throwable e) {
				Log.d(TAG, "exception :->" + e.getMessage());
			}
		});
	}

	public void onClickBtnCancel(View v) {
		finish();
	}

}
