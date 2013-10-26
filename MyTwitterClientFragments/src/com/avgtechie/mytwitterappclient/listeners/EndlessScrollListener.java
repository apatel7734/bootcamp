package com.avgtechie.mytwitterappclient.listeners;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener implements OnScrollListener {

	private static final String TAG = "EndlessScrollListener";
	int visibleThresholdItems = 5;
	volatile int previousItemcount = 0;
	volatile boolean loading = true;
	public volatile static boolean load = true;
	public volatile static int page = 1;

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		int viewedItems = visibleItemCount + firstVisibleItem;
		if (totalItemCount != 0 && ((totalItemCount - viewedItems) <= visibleThresholdItems)) {

			loading = false;
		}
		if (!loading && (previousItemcount < totalItemCount) && load) {
			previousItemcount = totalItemCount + 1;
			load = false;
			loadMore(page);
		}
		loading = true;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	public abstract void loadMore(int page);

}
