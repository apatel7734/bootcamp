package com.avgtechie.googleimagesearcher.json;

import android.graphics.Bitmap;

public class ResponseInformation {
	private String title;
	private String url;
	private Bitmap bitmap;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	public String toString() {
		return "ResponseInformation [title=" + title + ", url=" + url + ", bitmap=" + bitmap + "]";
	}

}
