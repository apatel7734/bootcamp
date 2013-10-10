package com.avgtechie.mytwitterappclient.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avgtechie.mytwitterappclient.R;
import com.avgtechie.mytwitterappclient.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View tweetView = convertView;
		if (tweetView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			tweetView = inflater.inflate(R.layout.tweet_item, null);
		}

		Tweet tweet = getItem(position);

		TextView nameView = (TextView) tweetView.findViewById(R.id.tv_tweeter_name);
		TextView bodyView = (TextView) tweetView.findViewById(R.id.tv_tweet_body);
		ImageView imageView = (ImageView) tweetView.findViewById(R.id.img_tweeter_profile);

		String formattedName = "<b>" + tweet.getUser().getName() + "</b> <small><font color='#777777'>@" + tweet.getUser().getScreenName()
				+ "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrlHttps(), imageView);
		bodyView.setText(Html.fromHtml(tweet.getBody()));

		return tweetView;
	}

}
