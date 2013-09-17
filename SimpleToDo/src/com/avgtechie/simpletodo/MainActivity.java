package com.avgtechie.simpletodo;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

	ArrayList<String> items;
	ArrayAdapter<String> itemsAdapter;
	ListView lvToDo;
	Button btnAddItem;
	EditText etNewItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initComponents();
		setupListViewListener();
	}

	private void setupListViewListener() {
		lvToDo.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> aView, View item, int position, long id) {
				items.remove(position);
				itemsAdapter.notifyDataSetInvalidated();
				return true;
			}
		});
	}

	private void initComponents() {
		lvToDo = (ListView) findViewById(R.id.lvToDoItems);
		btnAddItem = (Button) findViewById(R.id.btnAddItem);
		etNewItem = (EditText) findViewById(R.id.etAddNewItem);
		items = new ArrayList<String>();
		itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
		lvToDo.setAdapter(itemsAdapter);
	}

	public void AddToItemsList(View v) {
		itemsAdapter.add(etNewItem.getText().toString());
		etNewItem.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
