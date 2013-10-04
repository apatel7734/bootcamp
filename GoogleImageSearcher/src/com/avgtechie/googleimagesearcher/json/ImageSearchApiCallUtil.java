package com.avgtechie.googleimagesearcher.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.avgtechie.googleimagesearcher.R;
import com.avgtechie.googleimagesearcher.adaptors.CustomGridViewAdapter;

public class ImageSearchApiCallUtil {

	Context context;
	GridView grdView;
	String strUrl;

	public ImageSearchApiCallUtil(GridView grdView, Context context, String url) {
		this.context = context;
		this.grdView = grdView;
		this.strUrl = url;
	}

	JSONObject returnObj;

	public void getImageSearchResultInBackThread() throws IOException, JSONException {
		ApiCallAsyncTask async = new ApiCallAsyncTask();
		async.execute(strUrl);
	}

	private class ApiCallAsyncTask extends AsyncTask<String, Void, List<ResponseInformation>> {

		private static final String TAG = "ApiCallAsyncTask";

		@Override
		protected List<ResponseInformation> doInBackground(String... strUrl) {

			long endMil;
			long startMil = Calendar.getInstance().getTimeInMillis();
			Log.d(TAG, "Start time :" + startMil);

			URL url = null;
			URLConnection connection;
			String line;
			JSONObject jsonObj = null;

			List<ResponseInformation> returnList = new ArrayList<ResponseInformation>();
			try {
				url = new URL(strUrl[0]);
				Log.d(TAG, "URL :" + url);
				connection = url.openConnection();
				InputStream in = connection.getInputStream();
				InputStreamReader inReader = new InputStreamReader(in);
				BufferedReader reader = new BufferedReader(inReader);
				StringBuilder builder = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				jsonObj = new JSONObject(builder.toString());

				endMil = Calendar.getInstance().getTimeInMillis();

				Log.d(TAG, "End Of Getting Json Obj :" + endMil);
				Log.d(TAG, "Total Time taken for API info in millis= " + (endMil - startMil));

				startMil = Calendar.getInstance().getTimeInMillis();

				JSONObject responseData = jsonObj.getJSONObject("responseData");
				JSONArray resultsArray = responseData.getJSONArray("results");

				for (int i = 0; i < resultsArray.length(); i++) {
					JSONObject result = (JSONObject) resultsArray.get(i);
					ResponseInformation res = new ResponseInformation();
					Bitmap bitmap = downloadBitMap(result.getString("url"));
					res.setTitle(result.getString("title"));
					res.setUrl(result.getString("url"));
					res.setBitmap(bitmap);
					returnList.add(res);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Log.d(TAG, "List :" + returnList);

			endMil = Calendar.getInstance().getTimeInMillis();
			Log.d(TAG, "End Of Back Thread :" + endMil);
			Log.d(TAG, "Total Time taken in millis= " + (endMil - startMil));
			return returnList;
		}

		@Override
		protected void onPostExecute(List<ResponseInformation> resultList) {
			super.onPostExecute(resultList);
			if (resultList.size() == 0) {
				showAlertDialog();
			} else {
				CustomGridViewAdapter gvAdapter = new CustomGridViewAdapter(context, R.layout.gridview_item, resultList);
				grdView.setAdapter(gvAdapter);
			}

		}

		private void showAlertDialog() {
			Log.d(TAG, "showAlertDialog");
			// Step-1 Initialize builder for AlertDialog
			AlertDialog.Builder builder = new AlertDialog.Builder(context);

			// Step-2 Add various buttons or titles etc to builder.
			builder.setMessage(R.string.no_image_found).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// Do nothing on clicked ok.
				}
			});
			// Step-3 create a dialog.
			AlertDialog dialog = builder.create();
			// Step-4 Show alert dialog.
			dialog.show();
		}

		Bitmap downloadBitMap(String url) {
			AndroidHttpClient client = AndroidHttpClient.newInstance("android");
			HttpGet getRequest = new HttpGet(url);

			try {
				HttpResponse response = client.execute(getRequest);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != HttpStatus.SC_OK) {
					Log.w("BitmapDownloaderTask.java", "Error code return : " + statusCode);
					return null;
				}
				HttpEntity entity = response.getEntity();
				InputStream is = null;
				if (entity != null) {
					try {
						is = entity.getContent();
						Bitmap bitmap = BitmapFactory.decodeStream(is);
						return bitmap;
					} finally {
						if (is != null) {
							is.close();
						}
						entity.consumeContent();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				client.close();
			}
			return null;
		}
	}
}
