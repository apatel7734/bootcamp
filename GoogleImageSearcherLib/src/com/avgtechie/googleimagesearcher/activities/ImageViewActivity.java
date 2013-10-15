package com.avgtechie.googleimagesearcher.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.constant.Constants;
import com.loopj.android.image.SmartImageView;

public class ImageViewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_view);
		SmartImageView smrtImageView = (SmartImageView) findViewById(R.id.imgDispImage);
		Bundle extras = getIntent().getExtras();
		String fullUrl = extras.getString(Constants.IMAGE_URL);
		smrtImageView.setImageUrl(fullUrl);
	}

	public void onFinishClick(View v) {
		finish();
	}

}
