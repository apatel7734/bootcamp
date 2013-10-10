package com.avgtechie.mytwitterappclient.activities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetComposeActivity extends Activity {
	protected static final String TAG = "TweetComposeActivity";
	Button btnCancel;
	Button btnTweet;
	EditText etTweetCompose;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_compose);
		initViews();
	}

	private void initViews() {
		btnCancel = (Button) findViewById(R.id.btn_tweet_cancel);
		btnTweet = (Button) findViewById(R.id.btn_tweet_compose);
		etTweetCompose = (EditText) findViewById(R.id.et_tweet_compose);
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
		Toast.makeText(this, "onClickBtnCancel", Toast.LENGTH_SHORT).show();
	}

}
