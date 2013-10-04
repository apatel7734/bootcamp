package com.avgtechie.googleimagesearcher.activities;

import java.util.Arrays;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.constant.Constants;

public class AdvancedSearchOptionActivity extends Activity {

	private static final String TAG = "AdvancedSearchOptionActivity";
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
		String imgSiteFilterPref = getSharedPreferences(Constants.SETTING_PREF, 0).getString(Constants.PREF_IMG_SITE_FILTER_KEY, "none");
		if (!imgSiteFilterPref.equalsIgnoreCase("none")) {
			etSiteUrlFilter.setText(imgSiteFilterPref);
		}

	}

	public void populateSpnrsAdapters() {
		Resources res = getResources();

		// Image Size Spinner Adapter
		ArrayAdapter<CharSequence> imgSizeSpnrAdapter = ArrayAdapter.createFromResource(this, R.array.array_img_size,
				android.R.layout.simple_spinner_item);
		imgSizeSpnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imgSizeSpinner.setAdapter(imgSizeSpnrAdapter);

		String[] imgSizeArr = res.getStringArray(R.array.array_img_size);
		String imgSizePref = getSharedPreferences(Constants.SETTING_PREF, 0).getString(Constants.PREF_IMG_SIZE_KEY, "none");
		int sizePos = Arrays.asList(imgSizeArr).indexOf(imgSizePref);
		imgSizeSpinner.setSelection(sizePos);

		// Image Color Spinner Adapter
		ArrayAdapter<CharSequence> imgColorSpnrAdapter = ArrayAdapter.createFromResource(this, R.array.array_img_color,
				android.R.layout.simple_spinner_item);
		imgColorSpnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imgColorSpinner.setAdapter(imgColorSpnrAdapter);

		String[] imgColorArr = res.getStringArray(R.array.array_img_color);
		String imgColorPref = getSharedPreferences(Constants.SETTING_PREF, 0).getString(Constants.PREF_IMG_COLOR_KEY, "none");
		int colPos = Arrays.asList(imgColorArr).indexOf(imgColorPref);
		imgColorSpinner.setSelection(colPos);

		// Image Type Spinner Adapter
		ArrayAdapter<CharSequence> imgTypeSpnrAdapter = ArrayAdapter.createFromResource(this, R.array.array_img_type,
				android.R.layout.simple_spinner_item);
		imgTypeSpnrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imgTypeSpinner.setAdapter(imgTypeSpnrAdapter);

		String[] imgTypeArr = res.getStringArray(R.array.array_img_type);
		String imgTypePref = getSharedPreferences(Constants.SETTING_PREF, 0).getString(Constants.PREF_IMG_TYPE_KEY, "none");
		int typePos = Arrays.asList(imgTypeArr).indexOf(imgTypePref);
		imgTypeSpinner.setSelection(typePos);

	}

	public void onClickSaveSettings(View v) {

		SharedPreferences pref = getSharedPreferences(Constants.SETTING_PREF, 0);

		Editor edits = pref.edit();
		edits.putString(Constants.PREF_IMG_COLOR_KEY, imgColorSpinner.getSelectedItem().toString());
		edits.putString(Constants.PREF_IMG_SIZE_KEY, imgSizeSpinner.getSelectedItem().toString());
		edits.putString(Constants.PREF_IMG_TYPE_KEY, imgTypeSpinner.getSelectedItem().toString());
		edits.putString(Constants.PREF_IMG_SITE_FILTER_KEY, etSiteUrlFilter.getText().toString());

		edits.commit();
		Toast.makeText(this, "Settings Saved!", Toast.LENGTH_SHORT).show();

	}
}
