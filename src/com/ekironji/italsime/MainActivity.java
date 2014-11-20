package com.ekironji.italsime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.csvreader.CSVReader;
import com.ekironji.italsime.database.Database;
import com.ekironji.italsime.fragment.AriaPulitaFragment;

public class MainActivity extends ActionBarActivity {
	
	private final String DEBUG_TAG = "MainActivity";
	
	public static Database database;
	
	static ProgressDialog dialog;
	
	@Override
	protected void onPause() {
		super.onPause();
		hideProgressDialog();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dialog = new ProgressDialog(this);
//		
		database = new Database(this);
        database.open();

		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new AriaPulitaFragment()).commit();
		}
		
		database.close();
	}
	
	public static void showProgressDialog(String message){
		dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();		
	}
	
	public static void hideProgressDialog(){
		dialog.dismiss();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
