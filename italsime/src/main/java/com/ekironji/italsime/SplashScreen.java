package com.ekironji.italsime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.Modello.Series;
import com.ekironji.italsime.csvreader.CSVReader;
import com.ekironji.italsime.database.Database;

 
public class SplashScreen extends Activity {
	
	private final String DEBUG_TAG = "SplashScreen";
    
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;    

	static ProgressDialog barProgressDialog;
	
	static Database database;
	
	@Override
	protected void onPause() {
		super.onPause();
		hideProgressDialog();
	}
	
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Splash screen view
        setContentView(R.layout.activity_splash);
        
		barProgressDialog = new ProgressDialog(this);
		
        final SplashScreen sPlashScreen = this;   
        
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
            	database.close();
                try {
                    synchronized(this){
                        // Wait given period of time or exit on touch
                        wait(2000);
                    }
                }
                catch(InterruptedException ex){                    
                }
                
                finish();
                
                // Run next activity
                Intent intent = new Intent();
                intent.setClass(sPlashScreen, MainActivity.class);
                startActivity(intent);
                //stop();                    
            }
        };
        
        database = new Database(this);
        database.open();
        
        int size = database.getAllModels().size();
		 
		if (size == 0) {
			new CreateDatabaseTask().execute();
		} else {
			Log.i(DEBUG_TAG, "database size = " + size);
			mSplashThread.start();
		}
                    
    }
        
    /**
     * Processes splash screen touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if(evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized(mSplashThread){
                mSplashThread.notifyAll();
            }
        }
        return true;
    }    
    
	public static void showProgressDialog(String title, String message){
		barProgressDialog.setTitle(title);
		barProgressDialog.setMessage(message);
		barProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        barProgressDialog.setCancelable(false);
        barProgressDialog.setProgress(0);
        barProgressDialog.setMax(456);  // totale dei prodotti(righe) nei due file
        barProgressDialog.show();		
	}
	
	public static void setProgressPercent(int count){
		barProgressDialog.setProgress(count);
		 if (count == barProgressDialog.getMax()) {
			 hideProgressDialog();
		 }
//		barProgressDialog.incrementProgressBy(1);
	}
	
	public static void hideProgressDialog(){
		barProgressDialog.dismiss();		
	}
	
	private class CreateDatabaseTask extends AsyncTask<Void, Integer, Void> {

		protected void onPreExecute(){
			showProgressDialog(getResources().getString(R.string.splash_progress_dialog_title),
					getResources().getString(R.string.splash_progress_dialog_message));
		}

		protected Void doInBackground(Void... args) {

			String next[] = {};
			List<String[]> list = new ArrayList<String[]>();
			int count = 1;
			try {
				/*
				 * Parsing primo file closed_blade_puntievirgole.csv
				 * 
				 * Closed Blade -> Area Pulita
				 */
				Log.i(DEBUG_TAG, "inizio primo file: closed_blade_puntievirgole.csv");
				CSVReader reader1 = new CSVReader(new InputStreamReader(
						getAssets().open("closed_blade_puntievirgole.csv")));
				for (;;) {
					next = reader1.readNext();
					if (next != null) {
                        int maxPor = 0;
                        if (next[22] != "") maxPor = Integer.parseInt(next[22]);
                        if (next[23] != "") maxPor = Integer.parseInt(next[23]);
                        if (next[24] != "") maxPor = Integer.parseInt(next[24]);
                        if (next[25] != "") maxPor = Integer.parseInt(next[25]);
                        if (next[26] != "") maxPor = Integer.parseInt(next[26]);
                        int maxPres = 0;
                        if (next[28] != "") maxPres = Integer.parseInt(next[28]);
                        if (next[29] != "") maxPres = Integer.parseInt(next[29]);
                        if (next[30] != "") maxPres = Integer.parseInt(next[30]);
                        if (next[31] != "") maxPres = Integer.parseInt(next[31]);
                        if (next[32] != "") maxPres = Integer.parseInt(next[32]);

						Log.i(DEBUG_TAG, "Riga " + count + " = ");
						Modello temp = new Modello(next[0],Series.getIntFromName(next[0]),Modello.ARIA_PULITA,Double.parseDouble(next[1]),Integer.parseInt(next[2]),
								Integer.parseInt(next[3]),Integer.parseInt(next[4]),Integer.parseInt(next[5]),Integer.parseInt(next[6]),
								Integer.parseInt(next[7]),Integer.parseInt(next[8]),Integer.parseInt(next[9]),Integer.parseInt(next[10]),
								Integer.parseInt(next[11]),Integer.parseInt(next[12]),Integer.parseInt(next[13]),Integer.parseInt(next[14]),
								Integer.parseInt(next[15]),Integer.parseInt(next[16]),Integer.parseInt(next[17]),Integer.parseInt(next[18]),
								Integer.parseInt(next[19]),Integer.parseInt(next[20]),Integer.parseInt(next[21]),
								(next[22]=="")? maxPor : Integer.parseInt(next[22]),
								(next[23]=="")? maxPor : Integer.parseInt(next[23]),
								(next[24]=="")? maxPor : Integer.parseInt(next[24]),
								(next[25]=="")? maxPor : Integer.parseInt(next[25]),
								(next[26]=="")? maxPor : Integer.parseInt(next[26]),
								(next[28]=="")? maxPres : Integer.parseInt(next[28]),
								(next[29]=="")? maxPres : Integer.parseInt(next[29]),
								(next[30]=="")? maxPres : Integer.parseInt(next[30]),
								(next[31]=="")? maxPres : Integer.parseInt(next[31]),
								(next[32]=="")? maxPres : Integer.parseInt(next[32]));
						Log.i(DEBUG_TAG, ""+ temp.toString());	
						database.insertModel(temp);
						list.add(next);
						publishProgress(count);
						count++;
					} else {
						break;
					}
				}

				/*
				 * Parsing secondo file open_blade_puntievirgole.csv
				 * 
				 * Open Blade -> Area Sporca
				 */
				Log.i(DEBUG_TAG, "inizio secondo file: open_blade_puntievirgole.csv");
				CSVReader reader2 = new CSVReader(new InputStreamReader(
						getAssets().open("opened_blade_puntievirgole.csv")));
				for (;;) {
					next = reader2.readNext();
					if (next != null) {

						Log.i(DEBUG_TAG, "Riga " + count + " = ");
						Modello temp = new Modello(next[0],Series.getIntFromName(next[0]),Modello.ARIA_SPORCA,Double.parseDouble(next[1]),Integer.parseInt(next[2]),
								Integer.parseInt(next[3]),Integer.parseInt(next[4]),Integer.parseInt(next[5]),Integer.parseInt(next[6]),
								Integer.parseInt(next[7]),Integer.parseInt(next[8]),Integer.parseInt(next[9]),Integer.parseInt(next[10]),
								Integer.parseInt(next[11]),Integer.parseInt(next[12]),Integer.parseInt(next[13]),Integer.parseInt(next[14]),
								Integer.parseInt(next[15]),Integer.parseInt(next[16]),Integer.parseInt(next[17]),Integer.parseInt(next[18]),
								Integer.parseInt(next[19]),Integer.parseInt(next[20]),Integer.parseInt(next[21]),
								(next[22]=="")? -1 : Integer.parseInt(next[22]),
								(next[23]=="")? -1 : Integer.parseInt(next[23]),
								(next[24]=="")? -1 : Integer.parseInt(next[24]),
								(next[25]=="")? -1 : Integer.parseInt(next[25]),
								(next[26]=="")? -1 : Integer.parseInt(next[26]),
								(next[28]=="")? -1 : Integer.parseInt(next[28]),
								(next[29]=="")? -1 : Integer.parseInt(next[29]),
								(next[30]=="")? -1 : Integer.parseInt(next[30]),
								(next[31]=="")? -1 : Integer.parseInt(next[31]),
								(next[32]=="")? -1 : Integer.parseInt(next[32]));
						Log.i(DEBUG_TAG, ""+ temp.toString());	
						database.insertModel(temp);
						list.add(next);
						publishProgress(count);
						count++;
					} else {
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			database.close();

			return null;	    	 
		}

		protected void onProgressUpdate(Integer... progress) {
			setProgressPercent(progress[0]);
		}

		protected void onPostExecute(Void result) {
			hideProgressDialog();
			mSplashThread.start();
		}
	}
} 