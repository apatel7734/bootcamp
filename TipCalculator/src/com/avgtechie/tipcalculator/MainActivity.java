package com.avgtechie.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String TAG = "MainActivity";

	Button tenPercBtn = null;
	Button twentyPercBtn = null;
	Button thirtyPercBtn = null;
	EditText inputAmtEt = null;
	TextView resultTv = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initComponents();
	}

	private void initComponents() {
		tenPercBtn = (Button) findViewById(R.id.btnTenPerc);
		twentyPercBtn = (Button) findViewById(R.id.btnTwentyPerc);
		thirtyPercBtn = (Button) findViewById(R.id.btnThirtyPerc);
		inputAmtEt = (EditText) findViewById(R.id.etAmount);
		resultTv = (TextView) findViewById(R.id.tvResultDisp);
	}

	public void calculateTip(View v) {
		int id = v.getId();
		double result = 0;
		String amountStr = inputAmtEt.getText().toString();
		if (amountStr.isEmpty()) {
			Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show();
			return;
		}
		double amountDbl = Double.parseDouble(inputAmtEt.getText().toString());
		switch (id) {
		case R.id.btnTenPerc:
			Log.d(TAG, "Ten % clicked");
			result = calculatePercentage(amountDbl, 10);
			Log.d(TAG, "Result = " + result);
			break;
		case R.id.btnTwentyPerc:
			Log.d(TAG, "Twenty % clicked");
			result = calculatePercentage(amountDbl, 20);
			Log.d(TAG, "Result = " + result);
			break;
		case R.id.btnThirtyPerc:
			Log.d(TAG, "Thirty % clicked");
			result = calculatePercentage(amountDbl, 30);
			Log.d(TAG, "Result = " + result);
			break;
		default:
			break;
		}

		resultTv.setText("Tip is :   $" + result);
	}

	private Double calculatePercentage(double amount, int perc) {
		double result = (amount * perc) / 100;
		return result;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
