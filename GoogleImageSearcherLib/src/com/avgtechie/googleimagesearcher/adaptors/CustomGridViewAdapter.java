package com.avgtechie.googleimagesearcher.adaptors;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.json.ResponseInformation;
import com.loopj.android.image.SmartImageView;

public class CustomGridViewAdapter extends ArrayAdapter<ResponseInformation> {

	private Context context;
	List<ResponseInformation> listItems;
	SmartImageView smartImgView;
	TextView txtView;

	public CustomGridViewAdapter(Context context, List<ResponseInformation> items) {
		super(context, 0, items);
		this.context = context;
		this.listItems = items;
	}

	@Override
	public ResponseInformation getItem(int position) {
		return listItems.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflator.inflate(R.layout.gridview_item, parent, false);
		}
		smartImgView = (SmartImageView) rowView.findViewById(R.id.imageView);
		txtView = (TextView) rowView.findViewById(R.id.txtImgDesc);

		ResponseInformation resInfo = (ResponseInformation) listItems.get(position);

		String title = resInfo.getTitle();
		String url = resInfo.getThumbUrl();
		smartImgView.setImageUrl(url);
		txtView.setText(title);

		return rowView;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

}
