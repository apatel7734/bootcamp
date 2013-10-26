package com.avgtechie.mytwitterappclient.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.models.HelperSharedPrefs;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetComposeActivity extends Activity {
	protected static final String TAG = "TweetComposeActivity";
	Button btnCancel;
	Button btnTweet;
	EditText etTweetCompose;
	TextView tvUserName;
	TextView tvCharCount;
	ImageView imgUserProfile;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_compose);
		initViews();
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		ImageLoader.getInstance().displayImage(HelperSharedPrefs.getSharedPrefUserProfileImage(prefs), imgUserProfile);

		tvUserName.setText("@" + HelperSharedPrefs.getSharedPrefUserScreenName(prefs));

		etTweetCompose.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable paramEditable) {
				tvCharCount.setText(getResources().getInteger(R.integer.max_length) - paramEditable.length() + "");
			}
		});

	}

	private void initViews() {
		btnCancel = (Button) findViewById(R.id.btn_tweet_cancel);
		btnTweet = (Button) findViewById(R.id.btn_tweet_compose);
		etTweetCompose = (EditText) findViewById(R.id.et_tweet_compose);
		tvUserName = (TextView) findViewById(R.id.tv_tweet_username);
		tvCharCount = (TextView) findViewById(R.id.tv_tweet_charcount);
		imgUserProfile = (ImageView) findViewById(R.id.img_tweet_profile);
	}

	public void onClickBtnClear(View v) {
		etTweetCompose.setText("");
		tvCharCount.setText(getResources().getInteger(R.integer.max_length) + "");
	}

	public void onClickBtnTweet(View v) {
		String status = etTweetCompose.getText().toString();
		if (status.equals("")) {
			Toast.makeText(this, "Enter the text!", Toast.LENGTH_SHORT).show();
		}
		postTweet();
		Toast.makeText(this, "tweeted successfully.", Toast.LENGTH_SHORT).show();
		Intent resultIntent = new Intent();
		resultIntent.putExtra("tweet", status);
		setResult(RESULT_OK, resultIntent);
		finish();
	}

	public void postTweet() {
		String tweet = etTweetCompose.getText().toString();
		if (tweet.isEmpty()) {
			Toast.makeText(this, "Nothing to tweet.", Toast.LENGTH_SHORT).show();
			return;
		}
		RestClientApp.getTweeterRestClient().updateStatus(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				Log.d(TAG, "Response :->" + response.toString());
			}

			@Override
			public void onFailure(Throwable e) {
				Log.d(TAG, "exception :->" + e.getMessage());
			}
		}, tweet);
	}

	public void onClickBtnCancel(View v) {
		finish();
	}

}
