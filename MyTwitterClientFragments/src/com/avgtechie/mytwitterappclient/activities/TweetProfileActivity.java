package com.avgtechie.mytwitterappclient.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.fragments.UserTimeLineFragment;
import com.avgtechie.mytwitterappclient.fragments.UserTimeLineFragment.onUserInfoUpdateListener;
import com.avgtechie.mytwitterappclient.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetProfileActivity extends FragmentActivity implements onUserInfoUpdateListener {
	private static final String TAG = "TweetProfileActivity";
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
		Bundle bundle = getIntent().getExtras();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		UserTimeLineFragment userTimeFragment = new UserTimeLineFragment();
		userTimeFragment.setArguments(bundle);
		getSupportFragmentManager().beginTransaction().replace(R.id.frag_user_container, userTimeFragment).commit();
		initViews();
	}

	private void initViews() {
		imgTweetProfile = (ImageView) findViewById(R.id.imgTweetProfile);
		tvFollower = (TextView) findViewById(R.id.tvFollower);
		tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		tvTagLine = (TextView) findViewById(R.id.tvTagLine);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
	}

	@Override
	public void updateUserInfo(User user) {
		Log.d(TAG, "Callback :" + user.getScreenName());
		tvFollower.setText(user.getFollowersCount() + " Follower");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		tvUserName.setText(user.getScreenName());
		tvTagLine.setText(user.getTagLine());
		ImageLoader.getInstance().displayImage(user.getProfileImageUrlHttps(), imgTweetProfile);
	}
}
