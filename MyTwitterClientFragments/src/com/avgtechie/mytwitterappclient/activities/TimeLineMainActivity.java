package com.avgtechie.mytwitterappclient.activities;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.fragments.HomeTimeLineFragment;
import com.avgtechie.mytwitterappclient.fragments.MentionsTimeLineFragment;
import com.avgtechie.mytwitterappclient.models.HelperSharedPrefs;
import com.avgtechie.mytwitterappclient.models.UserCredential;
import com.avgtechie.mytwitterappclient.restclients.RestClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineMainActivity extends FragmentActivity implements TabListener {

	protected static final String TAG = "TimeLineActivity";
	SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_time_line);
		getUserInformation();
		// 1. Get the actionbar
		ActionBar actionBar = getActionBar();
		// 2. Set navigation mode
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 3. Create Tabs
		actionBar.setDisplayShowTitleEnabled(true);
		Tab homeTab = actionBar.newTab().setTag("HomeTimeLine").setText("Home").setIcon(R.drawable.ic_tab_home).setTabListener(this);
		Tab mentionTab = actionBar.newTab().setTag("MentionTimeLine").setIcon(R.drawable.ic_tab_at).setText("Mention").setTabListener(this);
		// 4. set tabs to actionbar.
		actionBar.addTab(homeTab);
		actionBar.addTab(mentionTab);
		actionBar.selectTab(homeTab);
	}

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
	}

	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		FragmentTransaction ftTrans = getSupportFragmentManager().beginTransaction();
		if (tab.getTag().equals("HomeTimeLine")) {
			ftTrans.replace(R.id.fragments_framelayout, new HomeTimeLineFragment());
		} else if (tab.getTag().equals("MentionTimeLine")) {
			ftTrans.replace(R.id.fragments_framelayout, new MentionsTimeLineFragment());
		}
		ftTrans.commit();
	}

	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.time_line, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		Intent intent;
		switch (itemId) {
		case R.id.item_refresh:
			Toast.makeText(this, "Item Refresh", Toast.LENGTH_SHORT).show();
			break;

		case R.id.item_compose:
			intent = new Intent(this, TweetComposeActivity.class);
			startActivity(intent);
			break;
		case R.id.item_profile:
			intent = new Intent(this, TweetProfileActivity.class);
			startActivity(intent);
			break;
		}

		return true;
	}

	public void getUserInformation() {
		RestClientApp.getTweeterRestClient().getUserInfo(new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject response) {
				Log.d(TAG, response.toString());
				UserCredential userCred = UserCredential.fromJson(response);
				HelperSharedPrefs.addUserInfoSharedPref(prefs, userCred);
				getActionBar().setTitle("@" + HelperSharedPrefs.getSharedPrefUserScreenName(prefs));
			}
		});
	}

}
