package mikeux.android.edzesnaptar.db_class;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mikeux on 2014.08.04..
 */
public class EdzesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { "id", "fk_edzes_fajta", "datum", "idotartam"};
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
    
    public EdzesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public long createEdzes(int fk_edzes_fajta, Calendar datum, int idotartam) {
	    ContentValues values = new ContentValues();
	    values.put("fk_edzes_fajta", fk_edzes_fajta);
	    values.put("datum", format1.format(datum.getTime()));
	    values.put("idotartam", idotartam);
	    return database.insert("edzes", null, values);
	    /*Cursor cursor = database.query("edzes_fajta",
	        allColumns, "id  = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    //EdzesFajta newEdzesFajta = cursorToEdzes(cursor);
	    Edzes newEdzes =  new Edzes(cursor.getLong(0),cursor.getString(1));
	    cursor.close();
	    return newEdzesFajta;*/
    }
    
    public long GetNapiEdzesSzam(Calendar datum) {
	    long count = 0;
    	Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM edzes WHERE datum='"+format1.format(datum.getTime())+"'", null);
    	cursor.moveToFirst();
    	count = cursor.getLong(0);
    	cursor.close();
    	return count;
	}

    public void close() {
        dbHelper.close();
    }
}
