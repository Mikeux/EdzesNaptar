package mikeux.android.edzesnaptar.db_class;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
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
    
    // Database creation sql statement
    private static final String EDZES_FAJTA_CREATE = "CREATE TABLE IF NOT EXISTS edzes_fajta" +
          "(id integer primary key autoincrement, "+
          "nev TEXT NOT NULL, " +
          "mertekegyseg integer);";

    // Database creation sql statement
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
                    break;
            }
            upgradeTo++;
        }  		
  		
  		/*if(newVersion == 3) {
  			database.execSQL(KALORIA_TABLAZAT);
  		}*/
  		//db.execSQL("CREATE TABEL IF NOT EXISTS ");
	    //db.execSQL("DROP TABLE IF EXISTS edzes");
	    //db.execSQL("DROP TABLE IF EXISTS edzes_fajta");
		onCreate(database);
  	}

} 