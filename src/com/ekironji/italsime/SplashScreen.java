package com.ekironji.italsime;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ekironji.italsime.Modello.Modello;
import com.ekironji.italsime.csvreader.CSVReader;
import com.ekironji.italsime.database.Database;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

 
public class SplashScreen extends Activity {
	
	private final String DEBUG_TAG = "SplashScreen";
    
    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;    

	static ProgressDialog dialog;
	
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
        setContentView(R.layout.splash);
        
		dialog = new ProgressDialog(this);
		
        final SplashScreen sPlashScreen = this;   
        
        // The thread to wait for splash screen events
        mSplashThread =  new Thread(){
            @Override
            public void run(){
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
        
		new CreateDatabaseTask().execute();
		                    
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
    
	public static void showProgressDialog(String message){
		dialog.setMessage(message);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();		
	}
	
	public static void hideProgressDialog(){
		dialog.dismiss();		
	}
	
	 private class CreateDatabaseTask extends AsyncTask<Void, Void, Void> {
		 
		 protected void onPreExecute(){
			 showProgressDialog("Creazione del database...");
		 }
		 
		 protected Void doInBackground(Void... args) {

			 int size = database.getAllModels().size();
			 
			 if (size == 0) {
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
									 Integer.parseInt(next[19]),Integer.parseInt(next[20]),Integer.parseInt(next[21]),
									 (next[22]=="")? 0 : Integer.parseInt(next[22]),
									 (next[23]=="")? 0 : Integer.parseInt(next[23]),
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
				 } catch (IOException e) {
					 e.printStackTrace();
				 }
			 } else {
				Log.i(DEBUG_TAG, "database size = " + size);
			 }
			 
			 database.close();

			 return null;	    	 
		 }

//	     protected void onProgressUpdate(Integer... progress) {
//	         setProgressPercent(progress[0]);
//	     }

	     protected void onPostExecute(Void result) {
	    	 hideProgressDialog();
	    	 mSplashThread.start();
	     }
	 }
} 