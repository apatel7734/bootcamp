package com.avgtechie.googleimagesearcher.activities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	Button btnLoadMore;
	GridView grdView;
	CustomGridViewAdapter grdViewAdapter;
	Context context = this;
	static Integer startPage = 1;
	List<ResponseInformation> resInfoList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imagesearch);

		initViews();
		btnLoadMore.setVisibility(View.INVISIBLE);

		ActionBar actionBar = getActionBar();
		actionBar.show();

		resInfoList = new ArrayList<ResponseInformation>();
		grdViewAdapter = new CustomGridViewAdapter(this, resInfoList);
		grdView.setAdapter(grdViewAdapter);
		SearchUrlGenerator.createAdvSearchOptDefSharedPrefs(getSharedPreferences(Constants.SETTING_PREF, 0));
		grdView.setOnItemClickListener(grvViewItemClickListener);
		disableSearchButton();

		//etImgSearch.setOnKeyListener(keyListener);

		etImgSearch.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable s) {
				if (s.length() > 2)
					enableSearchButton();
				else
					disableSearchButton();
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
		});

	}

	private OnKeyListener keyListener = new EditText.OnKeyListener() {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (etImgSearch.getText().length() > 2) {
				enableSearchButton();
			} else {
				disableSearchButton();
			}
			return false;
		}

	};

	private void disableSearchButton() {
		btnImgSearch.setClickable(false);
		btnImgSearch.setTextColor(Color.GRAY);
	}

	private void enableSearchButton() {
		btnImgSearch.setClickable(true);
		btnImgSearch.setTextColor(Color.BLACK);
	}

	private void initViews() {
		etImgSearch = (EditText) findViewById(R.id.et_img_search);
		btnImgSearch = (Button) findViewById(R.id.btn_img_search);
		grdView = (GridView) findViewById(R.id.gv_display_search_imgs);
		btnLoadMore = (Button) findViewById(R.id.btn_img_loadmore);
	}

	public void onSearchBtnClicked(View v) {
		startPage = 1;
		resInfoList.clear();
		grdViewAdapter.clear();
		AsyncImageLoader();
	}

	public void onLoadMoreBtnClicked(View v) {
		AsyncImageLoader();
	}

	public void AsyncImageLoader() {
		String url = SearchUrlGenerator.getSearchURL(getSharedPreferences(Constants.SETTING_PREF, 0), etImgSearch.getText().toString(),
				startPage.toString());
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {

				ResponseInformation resInfo = new ResponseInformation();
				resInfoList.addAll(resInfo.fromJSONArrayToResInfo(response));
				if (resInfoList.size() < 1) {
					Toast.makeText(context, R.string.no_image_found, Toast.LENGTH_SHORT).show();
					btnLoadMore.setVisibility(View.INVISIBLE);
				} else {
					grdViewAdapter.notifyDataSetChanged();
					btnLoadMore.setVisibility(View.VISIBLE);
				}

			}
		});
		startPage = startPage + 1;
	}

	OnItemClickListener grvViewItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			Intent intent = new Intent(getBaseContext(), ImageViewActivity.class);
			intent.putExtra(Constants.IMAGE_URL, view.getTag().toString());
			startActivity(intent);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(this, AdvancedSearchOptionActivity.class);
		startActivity(intent);
		return true;
	}

}
