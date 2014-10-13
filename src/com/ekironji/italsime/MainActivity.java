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

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
        //mostro il dialogo di attesa caricamento
		dialog = new ProgressDialog(this);
		
		database = new Database(getApplicationContext());
        database.open();
        
		int size = database.getAllModels().size();
		
		if (size == 0) {
			
			showProgressDialog("Creazione del database...");
			
			String next[] = {};
			List<String[]> list = new ArrayList<String[]>();
			int count = 1;
			try {
				CSVReader reader = new CSVReader(new InputStreamReader(
						getAssets().open("tabellaAriaPulitaClosedBlade.csv")));
				for (;;) {
					next = reader.readNext();
					if (next != null) {
						
						Log.i(DEBUG_TAG, "Riga " + count + " = ");
						Modello temp = new Modello(next[0],Modello.ARIA_PULITA,Double.parseDouble(next[1]),Integer.parseInt(next[2]),
								Integer.parseInt(next[3]),Integer.parseInt(next[4]),Integer.parseInt(next[5]),Integer.parseInt(next[6]),
								Integer.parseInt(next[7]),Integer.parseInt(next[8]),Integer.parseInt(next[9]),Integer.parseInt(next[10]),
								Integer.parseInt(next[11]),Integer.parseInt(next[12]),Integer.parseInt(next[13]),Integer.parseInt(next[14]),
								Integer.parseInt(next[15]),Integer.parseInt(next[16]),Integer.parseInt(next[17]),Integer.parseInt(next[18]),
								Integer.parseInt(next[19]),Integer.parseInt(next[20]),Integer.parseInt(next[21]),Integer.parseInt(next[22]),
								Integer.parseInt(next[23]),
								(next[24]=="")? 0 : Integer.parseInt(next[24]),
								(next[25]=="")? 0 : Integer.parseInt(next[25]),
								(next[26]=="")? 0 : Integer.parseInt(next[26]));
						Log.i(DEBUG_TAG, ""+ temp.toString());	
						database.insertModel(temp);
						list.add(next);
						count++;
					} else {
						break;
					}
				}
				
				hideProgressDialog();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Log.i(DEBUG_TAG, "database size = " + size);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
