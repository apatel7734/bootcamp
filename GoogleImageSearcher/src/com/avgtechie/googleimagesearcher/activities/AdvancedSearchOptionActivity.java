package com.avgtechie.googleimagesearcher.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.constant.Constants;

public class AdvancedSearchOptionActivity extends Activity {

	Spinner imgSizeSpinner;
	Spinner imgColorSpinner;
	Spinner imgTypeSpinner;
	EditText etSiteUrlFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_adv_search_option);
		initViews();
		populateSpnrsAdapters();

	}

	public void initViews() {
		imgSizeSpinner = (Spinner) findViewById(R.id.spner_img_size);
		imgColorSpinner = (Spinner) findViewById(R.id.spner_img_color);
		imgTypeSpinner = (Spinner) findViewById(R.id.spner_img_type);
		etSiteUrlFilter = (EditText) findViewById(R.id.et_img_site);
	}

	public void populateSpnrsAdapters() {

		// Image Size Spinner Adapter
		ArrayAdapter<CharSequence> imgSizeSpnrAdapter = ArrayAdapter.createFromResource(this, R.array.array_img_size,
				android.R.layout.simple_spinner_item);
		imgSizeSpnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imgSizeSpinner.setAdapter(imgSizeSpnrAdapter);

		// Image Color Spinner Adapter
		ArrayAdapter<CharSequence> imgColorSpnrAdapter = ArrayAdapter.createFromResource(this, R.array.array_img_color,
				android.R.layout.simple_spinner_item);
		imgColorSpnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imgColorSpinner.setAdapter(imgColorSpnrAdapter);

		// Image Type Spinner Adapter
		ArrayAdapter<CharSequence> imgTypeSpnrAdapter = ArrayAdapter.createFromResource(this, R.array.array_img_type,
				android.R.layout.simple_spinner_item);
		imgTypeSpnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imgTypeSpinner.setAdapter(imgTypeSpnrAdapter);

	}

	public void onClickSaveSettings(View v) {

		SharedPreferences pref = getSharedPreferences(Constants.SETTING_PREF, 0);

		Editor edits = pref.edit();
		edits.putString(Constants.PREF_IMG_COLOR_KEY, imgColorSpinner.getSelectedItem().toString());
		edits.putString(Constants.PREF_IMG_SIZE_KEY, imgTypeSpinner.getSelectedItem().toString());
		edits.putString(Constants.PREF_IMG_TYPE_KEY, imgSizeSpinner.getSelectedItem().toString());
		edits.putString(Constants.PREF_IMG_SITE_FILTER_KEY, etSiteUrlFilter.getText().toString());

		edits.commit();

	}
}
