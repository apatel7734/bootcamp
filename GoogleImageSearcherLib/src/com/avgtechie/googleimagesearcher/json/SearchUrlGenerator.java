package com.avgtechie.googleimagesearcher.json;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.avgtechie.googleimagesearcher.constant.Constants;

public class SearchUrlGenerator {

	private final String URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0";

	private final String imgcolor = "&imgcolor=";
	private final String imgsz = "&imgsz=";
	private final String imgtype = "&imgtype=";
	private final String rsz = "&rsz=";
	private final String start = "&start=";
	private final String as_sitesearch = "&as_sitesearch=";
	private final String query = "&q=";

	private String imgcolorVal;
	private String imgszVal;
	private String queryVal;
	private String imgtypeVal;
	private String rszVal;
	private String startVal;
	private String sitesearchVal;

	public static String getSearchURL(SharedPreferences prefs, String query, String startPage) {
		final String resultToReturn = "8";
		String defaultValue = "none";
		String color = prefs.getString(Constants.PREF_IMG_COLOR_KEY, defaultValue);
		String type = prefs.getString(Constants.PREF_IMG_TYPE_KEY, defaultValue);
		String size = prefs.getString(Constants.PREF_IMG_SIZE_KEY, defaultValue);
		String site = prefs.getString(Constants.PREF_IMG_SITE_FILTER_KEY, defaultValue);
		SearchUrlGenerator searchGen = new SearchUrlGenerator(color, size, type, resultToReturn, startPage, site, query);

		return searchGen.getSearchGenerated();
		// return null;
	}

	public SearchUrlGenerator(String imgcolor, String imgsz, String imgtype, String rsz, String start, String as_sitesearch, String query) {
		this.imgcolorVal = imgcolor;
		this.imgszVal = imgsz;
		this.imgtypeVal = imgtype;
		this.rszVal = rsz;
		this.startVal = start;
		this.sitesearchVal = as_sitesearch;
		this.queryVal = query;
	}

	private String getSearchGenerated() {
		StringBuilder builder = new StringBuilder();
		try {
			queryVal = URLEncoder.encode(queryVal, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}

		builder.append(URL);

		builder.append(imgcolor);
		builder.append(imgcolorVal);
		builder.append(imgsz);
		builder.append(imgszVal);
		builder.append(imgtype);
		builder.append(imgtypeVal);
		builder.append(rsz);
		builder.append(rszVal);
		builder.append(start);
		builder.append(startVal);
		builder.append(as_sitesearch);
		builder.append(sitesearchVal);
		builder.append(query);
		builder.append(queryVal);

		return builder.toString();
	}

	/**
	 * This method sets the default values for the parameter if activity is
	 * launched first time.
	 * 
	 * @param pref
	 *            - setting_pref
	 */
	public static void createAdvSearchOptDefSharedPrefs(SharedPreferences pref) {
		int firstTimeLaunched = pref.getInt(Constants.FIRST_TIME_LAUNCHED, -1);
		if (firstTimeLaunched == -1) {
			Editor edits = pref.edit();
			edits.putInt(Constants.FIRST_TIME_LAUNCHED, 1);
			edits.putString(Constants.PREF_IMG_COLOR_KEY, "blue");
			edits.putString(Constants.PREF_IMG_SIZE_KEY, "small");
			edits.putString(Constants.PREF_IMG_TYPE_KEY, "photo");
			edits.putString(Constants.PREF_IMG_SITE_FILTER_KEY, "google.com");
			edits.commit();
		}

	}
}
