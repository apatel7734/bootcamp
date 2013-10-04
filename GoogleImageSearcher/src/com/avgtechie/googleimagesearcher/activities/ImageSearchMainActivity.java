package com.avgtechie.googleimagesearcher.activities;

import java.io.IOException;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.constant.Constants;
import com.avgtechie.googleimagesearcher.json.ImageSearchApiCallUtil;
import com.avgtechie.googleimagesearcher.json.SearchUrlGenerator;

public class ImageSearchMainActivity extends Activity {

	private final String TAG = "ImageSearchActivity";
	Button btnImgSearch;
	EditText etImgSearch;
	GridView grdView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_imagesearch);
		etImgSearch = (EditText) findViewById(R.id.et_img_search);
		grdView = (GridView) findViewById(R.id.gv_display_search_imgs);
		ActionBar actionBar = getActionBar();
		actionBar.show();
	}

	public void onSearchBtnClicked(View v) {
		String searchedString = etImgSearch.getText().toString();
		String url = SearchUrlGenerator.getSearchURL(getSharedPreferences(Constants.SETTING_PREF, 0), searchedString, 1 + "");
		ImageSearchApiCallUtil search = new ImageSearchApiCallUtil(grdView, this, url);

		try {
			search.getImageSearchResultInBackThread();

		} catch (IOException e) {
			Log.d(TAG, "IOException :" + e.getCause());
		} catch (JSONException e) {
			Log.d(TAG, "JSONException :" + e.getCause());
		}
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
