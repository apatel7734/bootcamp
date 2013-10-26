package com.avgtechie.mytwitterappclient.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.models.HelperSharedPrefs;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetProfileActivity extends FragmentActivity {
	SharedPreferences prefs;
	ImageView imgTweetProfile;
	TextView tvFollower;
	TextView tvFollowing;
	TextView tvTagLine;
	TextView tvUserName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_profile);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		getActionBar().setTitle("@" + HelperSharedPrefs.getSharedPrefUserScreenName(prefs));
		initViews();
		loadUserInformation();
	}

	private void loadUserInformation() {
		ImageLoader.getInstance().displayImage(HelperSharedPrefs.getSharedPrefUserProfileImage(prefs), imgTweetProfile);
		tvFollower.setText(HelperSharedPrefs.getSharedPrefFollower(prefs) + " Follower");
		tvFollowing.setText(HelperSharedPrefs.getSharedPrefFollowing(prefs) + " Following");
		tvTagLine.setText(HelperSharedPrefs.getSharedPrefTagLine(prefs));
		tvUserName.setText(HelperSharedPrefs.getSharedPrefUserName(prefs));
	}

	private void initViews() {
		imgTweetProfile = (ImageView) findViewById(R.id.imgTweetProfile);
		tvFollower = (TextView) findViewById(R.id.tvFollower);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvTagLine = (TextView) findViewById(R.id.tvTagLine);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
	}
}
