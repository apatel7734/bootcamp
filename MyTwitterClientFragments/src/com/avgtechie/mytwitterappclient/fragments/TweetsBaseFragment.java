package com.avgtechie.mytwitterappclient.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.adapters.TweetsAdapter;
import com.avgtechie.mytwitterappclient.models.Tweet;

public class TweetsBaseFragment extends Fragment {

	protected static final String TAG = "TweetsBaseFragment";
	List<Tweet> tweets;
	ListView lvTweets;
	TweetsAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View tweetsFragView = inflater.inflate(R.layout.fragment_tweets, container, false);
		return tweetsFragView;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tweets = new ArrayList<Tweet>();
		lvTweets = (ListView) getActivity().findViewById(R.id.lv_tweets);
		Log.d(TAG, "Tweets = " + tweets);
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);
	}

	public ListView getListView() {
		return lvTweets;
	}

	public TweetsAdapter getAdapter() {
		return adapter;
	}
}
