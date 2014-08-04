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

    // Table Names
    /*private static final String TABLE_edzes_fajta = "edzes_fajta";
    private static final String TABLE_edzes = "edzes";

    public static final String COL_NEV = "nev";
    public static final String COL_ID = "id";

    public static final String COL_FK_ID = "fk_id";
    public static final String COL_CREATED_AT = "datum";

    // NOTES Table - column nmaes
    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    public static final String TABLE = "edzes";
    public static final String COLUMN_ID = "id";*/

    // Database creation sql statement
    private static final String EDZES_FAJTA_CREATE = "create table edzes_fajta" +
          "(id integer primary key autoincrement, "+
          "nev TEXT NOT NULL);";

    // Database creation sql statement
    private static final String EDZES_CREATE = "create table edzes" +
            "(id integer primary key autoincrement, "+
            "fk_edzes_fajta integer, "+
            "datum DATETIME, "+
            "idotartam integer);";

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
    Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
      db.execSQL("DROP TABLE IF EXISTS edzes");
      db.execSQL("DROP TABLE IF EXISTS edzes_fajta");
    onCreate(db);
  }

} 