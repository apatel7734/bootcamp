package com.avgtechie.googleimagesearcher.adaptors;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.json.ResponseInformation;

public class CustomGridViewAdapter extends ArrayAdapter<ResponseInformation> {

	private Context context;
	List<ResponseInformation> listItems;
	ImageView imgView;
	TextView txtView;

	public CustomGridViewAdapter(Context context, int resource, List<ResponseInformation> items) {
		super(context, resource, items);
		this.context = context;
		this.listItems = items;
	}

	@Override
	public ResponseInformation getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflator.inflate(R.layout.gridview_item, parent, false);
		imgView = (ImageView) rowView.findViewById(R.id.imageView);
		txtView = (TextView) rowView.findViewById(R.id.txtImgDesc);

		ResponseInformation resInfo = (ResponseInformation) listItems.get(position);

		String title = resInfo.getTitle();
		String url = resInfo.getUrl();
		Bitmap bitmap = resInfo.getBitmap();

		imgView.setImageBitmap(bitmap);
		txtView.setText(title);

		return rowView;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

}
