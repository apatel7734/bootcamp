package com.avgtechie.mytwitterappclient.restclients;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TweeterRestClient extends OAuthBaseClient {
	/**
	 * Chage This from supported classed by scribe
	 */
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	/**
	 * Chagne this base API Url
	 */
	public static final String REST_URL = "https://api.twitter.com/1.1";
	/**
	 * Chage this from application from developers document from provider
	 */
	public static final String REST_CONSUMER_KEY = "KPQAfxLiRV9RDmXuOuvWpA";
	/**
	 * Chage this from application from developers document from provider
	 */
	public static final String REST_CONSUMER_SECRET = "ruS8KeYGhB7s8xQDeJrj6z6rzOYNKhtPiNd2yrzXI";
	/**
	 * Chage this from application from developers document from provider
	 */
	public static final String REST_CALLBACK_URL = "oauth://ashishtweeterclient";

	private static final String TAG = "RestClient";

	public TweeterRestClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getHomeTimeline(AsyncHttpResponseHandler handler, int page) {
		String url = getApiUrl("statuses/home_timeline.json?page=" + page);
		client.get(url, null, handler);
	}

	public void updateStatus(AsyncHttpResponseHandler handler, String tweet) {
		String apiUrl = getApiUrl("statuses/update.json");
		RequestParams params = new RequestParams();
		params.put("status", tweet);
		client.post(apiUrl, params, handler);
	}

	public void getUserSettings(AsyncHttpResponseHandler handler) {
		String apiurl = getApiUrl("account/settings.json");
		client.get(apiurl, handler);
	}

	public void verifyCredential(AsyncHttpResponseHandler handler) {
		String apiurl = getApiUrl("account/verify_credentials.json");
		client.get(apiurl, handler);
	}

	public void getMentionsTimeLine(AsyncHttpResponseHandler handler, int page) {
		String apiurl = getApiUrl("statuses/mentions_timeline.json?page=" + page);
		client.get(apiurl, handler);
	}

}