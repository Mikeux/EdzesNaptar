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

    // Database creation sql statement
    private static final String EDZES_FAJTA_CREATE = "create table edzes_fajta" +
          "(id integer primary key autoincrement, "+
          "nev TEXT NOT NULL, " +
          "mertekegyseg integer);";

    // Database creation sql statement
    private static final String EDZES_CREATE = "create table edzes" +
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
    	  database.execSQL(EDZES_FAJTA_CREATE);
    	  database.execSQL(EDZES_CREATE);
  	}

  	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  		Log.w(MySQLiteHelper.class.getName(), "Verzió frissítés. " + oldVersion + " => " + newVersion);
  		//db.execSQL("CREATE TABEL IF NOT EXISTS ");
	    //db.execSQL("DROP TABLE IF EXISTS edzes");
	    //db.execSQL("DROP TABLE IF EXISTS edzes_fajta");
		onCreate(db);
  	}

} 