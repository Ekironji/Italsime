package com.ekironji.italsime.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.ekironji.italsime.Modello.Modello;

public class Database {
	
	SQLiteDatabase mDb;
    DbHelper mDbHelper;
    Context mContext;
    private static final String DB_NAME="ItalsimeModelsDatabase";//nome del db
    private static final int DB_VERSION=1; //numero di versione del nostro db
    
    public Database(Context ctx) {
        mContext=ctx;
        mDbHelper=new DbHelper(ctx, DB_NAME, null, DB_VERSION);   //quando istanziamo questa classe, istanziamo anche l'helper (vedi sotto)     
	}
	
	public void open() {  //il database su cui agiamo � leggibile/scrivibile
	        mDb=mDbHelper.getWritableDatabase();	        
	}
	
	public void close() { //chiudiamo il database su cui agiamo
	        mDb.close();
	}
	
    //inserisce un modello
    public void insertModel(Modello modello) {
			mDb.insert(ModelsMetaData.ModelsTab_TABLE, null, getContentFromModello(modello));
    }
    
    //update un modello
    public int updateModel(Modello modello) {    
        return mDb.update(ModelsMetaData.ModelsTab_TABLE, getContentFromModello(modello), ModelsMetaData.ModelsTab_ID_KEY + " =?",
                new String[] { String.valueOf(modello.getId()) });
    }
    
    //elimina un modello
    public void deleteModel(Modello modello) {
        mDb.delete(ModelsMetaData.ModelsTab_TABLE, ModelsMetaData.ModelsTab_ID_KEY + " =?",
                new String[] { String.valueOf(modello.getId()) });
    }
    
    //ritorna tutti i modelli
    public ArrayList<Modello> getAllModels() { //metodo per fare la query di tutti i dati    	
	    	Cursor cursor = mDb.query(ModelsMetaData.ModelsTab_TABLE, null,null,null,null,null,null);
	    	return returnModelsFromCursor(cursor);
    }
    
    public ArrayList<Modello> getAllAriaPulitaModels() {
    	Cursor cursor = mDb.query(ModelsMetaData.ModelsTab_TABLE, null, 
    			ModelsMetaData.ModelsTab_ARIATYPE_KEY + "= ?",
				new String[] { String.valueOf(Modello.ARIA_PULITA) }, null, null, null);
    	return returnModelsFromCursor(cursor);
    }
    
    public ArrayList<Modello> getAllAriaSporcaModels() {
    	Cursor cursor = mDb.query(ModelsMetaData.ModelsTab_TABLE, null, 
    			ModelsMetaData.ModelsTab_ARIATYPE_KEY + "= ?",
				new String[] { String.valueOf(Modello.ARIA_SPORCA) }, null, null, null);
    	return returnModelsFromCursor(cursor);
    }
    
    public ArrayList<Modello> getAllModelsBySerie(int serie) {
    	Cursor cursor = mDb.query(ModelsMetaData.ModelsTab_TABLE, null, 
    			ModelsMetaData.ModelsTab_SERIE_KEY + "= ?",
				new String[] { String.valueOf(serie) }, null, null, null);
    	return returnModelsFromCursor(cursor);
    }
    
    public ArrayList<Modello> getModelsByFilteredSearch(int sporcaOPulita,
    		int portataM3hMIN, int portataM3hMAX,
    		int pressioneMMh2oMIN, int pressioneMMh2oMAX) {
    	Cursor cursor = mDb.query(ModelsMetaData.ModelsTab_TABLE, null, 
    			ModelsMetaData.ModelsTab_ARIATYPE_KEY + "=? and " +
    			ModelsMetaData.ModelsTab_M3H1_KEY + ">? and " +
    			ModelsMetaData.ModelsTab_M3H5_KEY + "<? and " +
    			ModelsMetaData.ModelsTab_MMH2O1_KEY + "<? and " +
    			ModelsMetaData.ModelsTab_MMH2O5_KEY + ">?",
				new String[] { String.valueOf(sporcaOPulita),
    							String.valueOf(portataM3hMIN),
    							String.valueOf(portataM3hMAX),
    							String.valueOf(pressioneMMh2oMAX),
    							String.valueOf(pressioneMMh2oMIN) }, null, null, null);
    	return returnModelsFromCursor(cursor); 	
    }
    
    public ArrayList<Modello> getModelsbyKW(int sporcaOPulita, int kwMAX, int kwMIN){
    	Cursor cursor = mDb.query(ModelsMetaData.ModelsTab_TABLE, null, 
    			ModelsMetaData.ModelsTab_ARIATYPE_KEY + "=? and " +
    			ModelsMetaData.ModelsTab_KW_KEY + "<? and " +
    			ModelsMetaData.ModelsTab_KW_KEY + ">?",
				new String[] { String.valueOf(sporcaOPulita),
    							String.valueOf(kwMAX),
    							String.valueOf(kwMIN)}, null, null, null);
    	
		return returnModelsFromCursor(cursor); 	
    }
    
    private ArrayList<Modello> returnModelsFromCursor(Cursor cursor){
    	ArrayList<Modello> modelsList = new ArrayList<Modello>();
    	if(cursor.moveToFirst()){
    		do {    			
    			Modello myModello = new Modello();
    			myModello.setId(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_ID));
    			myModello.setName(cursor.getString(ModelsMetaData.ModelsTab_COLUMNINDEX_NAME));
    			myModello.setSerie(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_SERIE));
    			myModello.setAriaType(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_ARIATYPE));
    			myModello.setKw(cursor.getDouble(ModelsMetaData.ModelsTab_COLUMNINDEX_KW)); 
    			myModello.setRpm(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_RPM));
    			myModello.setMisura1(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA1));
    			myModello.setMisura2(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA2));
    			myModello.setMisura3(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA3));
    			myModello.setMisura4(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA4));
    			myModello.setMisura5(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA5));
    			myModello.setMisura6(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA6));
    			myModello.setMisura7(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA7));
    			myModello.setMisura8(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA8));
    			myModello.setMisura9(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA9));
    			myModello.setMisura10(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA10));
    			myModello.setMisura11(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA11));
    			myModello.setMisura12(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA12));
    			myModello.setMisura13(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA13));
    			myModello.setMisura14(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA14));
    			myModello.setMisura15(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA15));
    			myModello.setMisura16(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA16));
    			myModello.setMisura17(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA17));
    			myModello.setMisura18(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MISURA18));
    			myModello.setKg(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_KG));
    			myModello.setM3h1(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_M3H1));
    			myModello.setM3h2(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_M3H2));
    			myModello.setM3h3(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_M3H3));
    			myModello.setM3h4(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_M3H4));
    			myModello.setM3h5(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_M3H5));
    			myModello.setMmH2O1(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MMH2O1));
    			myModello.setMmH2O2(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MMH2O2));
    			myModello.setMmH2O3(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MMH2O3));
    			myModello.setMmH2O4(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MMH2O4));
    			myModello.setMmH2O5(cursor.getInt(ModelsMetaData.ModelsTab_COLUMNINDEX_MMH2O5));
    			    			
    			modelsList.add(myModello);   			
    		} while(cursor.moveToNext());
    	} 
		return modelsList;
    }
    
    private ContentValues getContentFromModello(Modello modello) {
    	ContentValues cv = new ContentValues();
    	cv.put(ModelsMetaData.ModelsTab_NAME_KEY, modello.getName());
    	cv.put(ModelsMetaData.ModelsTab_SERIE_KEY, modello.getSerie());
    	cv.put(ModelsMetaData.ModelsTab_ARIATYPE_KEY, modello.getAriaType());
    	cv.put(ModelsMetaData.ModelsTab_KW_KEY, modello.getKw());
    	cv.put(ModelsMetaData.ModelsTab_RPM_KEY, modello.getRpm());
    	cv.put(ModelsMetaData.ModelsTab_MISURA1_KEY, modello.getMisura1());
    	cv.put(ModelsMetaData.ModelsTab_MISURA2_KEY, modello.getMisura2());
    	cv.put(ModelsMetaData.ModelsTab_MISURA3_KEY, modello.getMisura3());
    	cv.put(ModelsMetaData.ModelsTab_MISURA4_KEY, modello.getMisura4());
    	cv.put(ModelsMetaData.ModelsTab_MISURA5_KEY, modello.getMisura5());
    	cv.put(ModelsMetaData.ModelsTab_MISURA6_KEY, modello.getMisura6());
    	cv.put(ModelsMetaData.ModelsTab_MISURA7_KEY, modello.getMisura7());
    	cv.put(ModelsMetaData.ModelsTab_MISURA8_KEY, modello.getMisura8());
    	cv.put(ModelsMetaData.ModelsTab_MISURA9_KEY, modello.getMisura9());
    	cv.put(ModelsMetaData.ModelsTab_MISURA10_KEY, modello.getMisura10());
    	cv.put(ModelsMetaData.ModelsTab_MISURA11_KEY, modello.getMisura11());
    	cv.put(ModelsMetaData.ModelsTab_MISURA12_KEY, modello.getMisura12());
    	cv.put(ModelsMetaData.ModelsTab_MISURA13_KEY, modello.getMisura13());
    	cv.put(ModelsMetaData.ModelsTab_MISURA14_KEY, modello.getMisura14());
    	cv.put(ModelsMetaData.ModelsTab_MISURA15_KEY, modello.getMisura15());
    	cv.put(ModelsMetaData.ModelsTab_MISURA16_KEY, modello.getMisura16());
    	cv.put(ModelsMetaData.ModelsTab_MISURA17_KEY, modello.getMisura17());
    	cv.put(ModelsMetaData.ModelsTab_MISURA18_KEY, modello.getMisura18());
    	cv.put(ModelsMetaData.ModelsTab_KG_KEY, modello.getKg());
    	cv.put(ModelsMetaData.ModelsTab_M3H1_KEY, modello.getM3h1());
    	cv.put(ModelsMetaData.ModelsTab_M3H2_KEY, modello.getM3h2());
    	cv.put(ModelsMetaData.ModelsTab_M3H3_KEY, modello.getM3h3());
    	cv.put(ModelsMetaData.ModelsTab_M3H4_KEY, modello.getM3h4());
    	cv.put(ModelsMetaData.ModelsTab_M3H5_KEY, modello.getM3h5());
    	cv.put(ModelsMetaData.ModelsTab_MMH2O1_KEY, modello.getMmH2O1());
    	cv.put(ModelsMetaData.ModelsTab_MMH2O2_KEY, modello.getMmH2O2());
    	cv.put(ModelsMetaData.ModelsTab_MMH2O3_KEY, modello.getMmH2O3());
    	cv.put(ModelsMetaData.ModelsTab_MMH2O4_KEY, modello.getMmH2O4());
    	cv.put(ModelsMetaData.ModelsTab_MMH2O5_KEY, modello.getMmH2O5());
    	return cv;
    }
	
	static class ModelsMetaData {  // i metadati della tabella models, accessibili ovunque
        static final String ModelsTab_TABLE = "models";
        static final String ModelsTab_ID_KEY = "_id";
        static final String ModelsTab_NAME_KEY = "name";
        static final String ModelsTab_SERIE_KEY = "serie";
        static final String ModelsTab_ARIATYPE_KEY = "ariatype";
        static final String ModelsTab_KW_KEY = "kw";  
        static final String ModelsTab_RPM_KEY = "rpm";  
        static final String ModelsTab_MISURA1_KEY = "misura1";  
        static final String ModelsTab_MISURA2_KEY = "misura2";
        static final String ModelsTab_MISURA3_KEY = "misura3";  
        static final String ModelsTab_MISURA4_KEY = "misura4";  
        static final String ModelsTab_MISURA5_KEY = "misura5";
        static final String ModelsTab_MISURA6_KEY = "misura6";  
        static final String ModelsTab_MISURA7_KEY = "misura7";  
        static final String ModelsTab_MISURA8_KEY = "misura8";  
        static final String ModelsTab_MISURA9_KEY = "misura9";  
        static final String ModelsTab_MISURA10_KEY = "misura10";  
        static final String ModelsTab_MISURA11_KEY = "misura11";  
        static final String ModelsTab_MISURA12_KEY = "misura12";  
        static final String ModelsTab_MISURA13_KEY = "misura13";  
        static final String ModelsTab_MISURA14_KEY = "misura14";  
        static final String ModelsTab_MISURA15_KEY = "misura15";  
        static final String ModelsTab_MISURA16_KEY = "misura16";  
        static final String ModelsTab_MISURA17_KEY = "misura17";  
        static final String ModelsTab_MISURA18_KEY = "misura18";
        static final String ModelsTab_KG_KEY = "kg";  
        static final String ModelsTab_M3H1_KEY = "m3h1";   
        static final String ModelsTab_M3H2_KEY = "m3h2";   
        static final String ModelsTab_M3H3_KEY = "m3h3";   
        static final String ModelsTab_M3H4_KEY = "m3h4";
        static final String ModelsTab_M3H5_KEY = "m3h5";
        static final String ModelsTab_MMH2O1_KEY = "mmH2O1";
        static final String ModelsTab_MMH2O2_KEY = "mmH2O2";
        static final String ModelsTab_MMH2O3_KEY = "mmH2O3";
        static final String ModelsTab_MMH2O4_KEY = "mmH2O4";
        static final String ModelsTab_MMH2O5_KEY = "mmH2O5";
        
        static final int ModelsTab_COLUMNINDEX_ID 		=	0;
        static final int ModelsTab_COLUMNINDEX_NAME 	=	1;
        static final int ModelsTab_COLUMNINDEX_SERIE 	=	2;
        static final int ModelsTab_COLUMNINDEX_ARIATYPE = 	3;
        static final int ModelsTab_COLUMNINDEX_KW		= 	4; 
        static final int ModelsTab_COLUMNINDEX_RPM		= 	5;
        static final int ModelsTab_COLUMNINDEX_MISURA1	= 	6;
        static final int ModelsTab_COLUMNINDEX_MISURA2	= 	7;
        static final int ModelsTab_COLUMNINDEX_MISURA3	= 	8;
        static final int ModelsTab_COLUMNINDEX_MISURA4	= 	9;
        static final int ModelsTab_COLUMNINDEX_MISURA5	= 	10;
        static final int ModelsTab_COLUMNINDEX_MISURA6	= 	11;
        static final int ModelsTab_COLUMNINDEX_MISURA7	= 	12;
        static final int ModelsTab_COLUMNINDEX_MISURA8	= 	13;
        static final int ModelsTab_COLUMNINDEX_MISURA9	= 	14;
        static final int ModelsTab_COLUMNINDEX_MISURA10	= 	15;
        static final int ModelsTab_COLUMNINDEX_MISURA11	= 	16;
        static final int ModelsTab_COLUMNINDEX_MISURA12	= 	17;
        static final int ModelsTab_COLUMNINDEX_MISURA13	= 	18;
        static final int ModelsTab_COLUMNINDEX_MISURA14	= 	19;
        static final int ModelsTab_COLUMNINDEX_MISURA15	= 	20;
        static final int ModelsTab_COLUMNINDEX_MISURA16	= 	21;
        static final int ModelsTab_COLUMNINDEX_MISURA17	= 	22;
        static final int ModelsTab_COLUMNINDEX_MISURA18	= 	23;
        static final int ModelsTab_COLUMNINDEX_KG		= 	24;
        static final int ModelsTab_COLUMNINDEX_M3H1		= 	25;
        static final int ModelsTab_COLUMNINDEX_M3H2		= 	26;
        static final int ModelsTab_COLUMNINDEX_M3H3		= 	27;
        static final int ModelsTab_COLUMNINDEX_M3H4		= 	28;
        static final int ModelsTab_COLUMNINDEX_M3H5		= 	29;
        static final int ModelsTab_COLUMNINDEX_MMH2O1	= 	30;
        static final int ModelsTab_COLUMNINDEX_MMH2O2	= 	31;
        static final int ModelsTab_COLUMNINDEX_MMH2O3	= 	32;
        static final int ModelsTab_COLUMNINDEX_MMH2O4	= 	33;
        static final int ModelsTab_COLUMNINDEX_MMH2O5	= 	34;
    }
	
	private static final String MODELS_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "  //codice sql di creazione della tabella
            + ModelsMetaData.ModelsTab_TABLE + " (" 
            + ModelsMetaData.ModelsTab_ID_KEY + " integer primary key autoincrement, "
            + ModelsMetaData.ModelsTab_NAME_KEY + " text not null, "
            + ModelsMetaData.ModelsTab_SERIE_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_ARIATYPE_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_KW_KEY + " real not null, "
            + ModelsMetaData.ModelsTab_RPM_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA1_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA2_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA3_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA4_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA5_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA6_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA7_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA8_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA9_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA10_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA11_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA12_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA13_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA14_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA15_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA16_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA17_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_MISURA18_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_KG_KEY + " integer not null, "
            + ModelsMetaData.ModelsTab_M3H1_KEY + " integer, "
            + ModelsMetaData.ModelsTab_M3H2_KEY + " integer, "
            + ModelsMetaData.ModelsTab_M3H3_KEY + " integer, "
            + ModelsMetaData.ModelsTab_M3H4_KEY + " integer, "
            + ModelsMetaData.ModelsTab_M3H5_KEY + " integer, "
            + ModelsMetaData.ModelsTab_MMH2O1_KEY + " integer, "
            + ModelsMetaData.ModelsTab_MMH2O2_KEY + " integer, "
            + ModelsMetaData.ModelsTab_MMH2O3_KEY + " integer, "
            + ModelsMetaData.ModelsTab_MMH2O4_KEY + " integer, "
            + ModelsMetaData.ModelsTab_MMH2O5_KEY + " integer);";
	
	private class DbHelper extends SQLiteOpenHelper { //classe che ci aiuta nella creazione del db
		
	    public DbHelper(Context context, String name, CursorFactory factory,int version) {
	            super(context, name, factory, version);
	    }
	
	    @Override
	    public void onCreate(SQLiteDatabase _db) { //solo quando il db viene creato, creiamo la tabella
	            _db.execSQL(MODELS_TABLE_CREATE);
	    }
	
	    @Override
	    public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
	            //qui mettiamo eventuali modifiche al db, se nella nostra nuova versione della app, il db cambia numero di versione
	
	    }
	
	}

}
