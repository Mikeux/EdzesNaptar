package mikeux.android.edzesnaptar.db_class;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String LOG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "edzesNaptar";

    private static final String ETKEZES = "CREATE TABLE IF NOT EXISTS etkezes" +
            "(id integer primary key autoincrement, "+
            "fk_elelmiszer integer, " +   
            "nev TEXT NOT NULL, " +
            "mennyiseg integer);";    
    
    private static final String ELELMISZER = "CREATE TABLE IF NOT EXISTS elelmiszer" +
          "(id integer primary key autoincrement, "+
          "nev TEXT NOT NULL, " +          
          "feherje real, " +
          "zsir real, " +
          "szenhidrat real, " +
          "kaloria real);";
    
    private static final String EDZES_FAJTA_CREATE = "CREATE TABLE IF NOT EXISTS edzes_fajta" +
          "(id integer primary key autoincrement, "+
          "nev TEXT NOT NULL, " +
          "mertekegyseg integer);";

    private static final String EDZES_CREATE = "CREATE TABLE IF NOT EXISTS edzes" +
            "(id integer primary key autoincrement, "+
            "fk_edzes_fajta integer, "+
            "datum DATETIME, "+
            "idotartam integer, "+
            "szorzo integer);";

	public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

	@Override
  	public void onCreate(SQLiteDatabase database) {
		  database.execSQL(ETKEZES);
		  database.execSQL(ELELMISZER);
    	  database.execSQL(EDZES_FAJTA_CREATE);
    	  database.execSQL(EDZES_CREATE);
  	}

  	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
  		Log.w(MySQLiteHelper.class.getName(), "Verzió frissítés. " + oldVersion + " => " + newVersion);
  		int upgradeTo = oldVersion + 1;
        while (upgradeTo <= newVersion)
        {
            switch (upgradeTo)
            {
                case 3:
          		  	database.execSQL(ETKEZES);
          		  	database.execSQL(ELELMISZER);
          		  	this.Elelmiszer_feltolt(database);
          		  	this.EdzesFajtak_feltolt(database);
                    break;
            }
            upgradeTo++;
        }  		
		onCreate(database);
  	}
  	
  	public void Elelmiszer_feltolt(SQLiteDatabase database) {
  		database.execSQL("DELETE FROM elemiszer");
  		database.execSQL("VACUUM");
  	}
  	
  	public void EdzesFajtak_feltolt(SQLiteDatabase database) {
  		database.execSQL("DELETE FROM edzes_fajta");
  		database.execSQL("VACUUM");
  	}
  	
} 


