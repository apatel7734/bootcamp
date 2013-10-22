package com.avgtechie.mytwitterappclient.adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.content.Context;
import android.text.Html;
import android.util.Log;
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

	private static final String TAG = "TweetsAdapter";
	StringBuilder dateDisp = new StringBuilder();

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
		TextView dateView = (TextView) tweetView.findViewById(R.id.tv_tweet_date);
		ImageView imageView = (ImageView) tweetView.findViewById(R.id.img_tweeter_profile);

		String formattedName = "<b>" + tweet.getUser().getName() + "</b> <small><font color='#777777'>@" + tweet.getUser().getScreenName()
				+ "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrlHttps(), imageView);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		Log.d(TAG, "Created Date = " + tweet.getTweetCreatedDate());
		Log.d(TAG, "Converted Date = " + StringtoDate(tweet.getTweetCreatedDate()));
		dateView.setText(StringtoDate(tweet.getTweetCreatedDate()) + "");

		return tweetView;
	}

	private String StringtoDate(String date) {
		if (!date.isEmpty()) {
			SimpleDateFormat origFormat = new SimpleDateFormat("E MMM dd HH:mm:ss +0000 yyyy");
			SimpleDateFormat targetFormat = new SimpleDateFormat("MMM dd");
			origFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				Date origDate = origFormat.parse(date);
				String formatedDate = targetFormat.format(origDate);

				return formatedDate;
			} catch (ParseException e) {
				Log.d(TAG, "Error parsing date" + date);
				return null;
			}

		}
		return null;
	}
}
