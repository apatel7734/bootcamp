package com.avgtechie.googleimagesearcher.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.adaptors.CustomGridViewAdapter;
import com.avgtechie.googleimagesearcher.constant.Constants;
import com.avgtechie.googleimagesearcher.json.ResponseInformation;
import com.avgtechie.googleimagesearcher.json.SearchUrlGenerator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ImageSearchMainActivity extends Activity {

	private final String TAG = "ImageSearchActivity";
	Button btnImgSearch;
	EditText etImgSearch;
	GridView grdView;
	CustomGridViewAdapter grdViewAdapter;
	Context context = this;
	static Integer startPage = 1;
	List<ResponseInformation> resInfoList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_imagesearch);
		etImgSearch = (EditText) findViewById(R.id.et_img_search);
		grdView = (GridView) findViewById(R.id.gv_display_search_imgs);
		ActionBar actionBar = getActionBar();
		resInfoList = new ArrayList<ResponseInformation>();
		Log.d(TAG, resInfoList.toString());
		Log.d(TAG, "***********************");
		grdViewAdapter = new CustomGridViewAdapter(this, resInfoList);
		grdView.setAdapter(grdViewAdapter);
		actionBar.show();

	}

	public void onSearchBtnClicked(View v) {
		startPage = 1;
		resInfoList.clear();
		grdViewAdapter.clear();
		ImageLoader();
	}

	public void onLoadMoreBtnClicked(View v) {
		ImageLoader();
	}

	public void ImageLoader() {
		String url = SearchUrlGenerator.getSearchURL(getSharedPreferences(Constants.SETTING_PREF, 0), etImgSearch.getText().toString(),
				startPage.toString());
		AsyncHttpClient client = new AsyncHttpClient();
		Log.d(TAG, "******* URL ****** :" + url);
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, JSONObject response) {
				JSONArray resultsArray = null;
				try {
					JSONObject responseData = response.getJSONObject("responseData");
					if (responseData != null) {
						resultsArray = responseData.getJSONArray("results");
						if (resultsArray.length() > 0) {
							for (int i = 0; i < resultsArray.length(); i++) {
								JSONObject resultObj = resultsArray.getJSONObject(i);
								ResponseInformation resInfo = new ResponseInformation(resultObj);
								resInfoList.add(resInfo);
							}
							Log.d(TAG, "resInfoList" + resInfoList);
							Log.d(TAG, "resInfoList size" + resInfoList.size());
							grdViewAdapter.notifyDataSetChanged();
							// grdViewAdapter.addAll(resInfoList);
						} else {
							Toast.makeText(context, "Your Search returned no result!", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(context, response.getString("responseDetails"), Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					Log.d(TAG, "Error in getting json data" + e);
				}
			}
		});
		startPage = startPage + 1;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, AdvancedSearchOptionActivity.class);
		startActivity(intent);
		return super.onOptionsItemSelected(item);
	}

}
