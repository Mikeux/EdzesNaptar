package mikeux.android.edzesnaptar.db_class;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mikeux.android.edzesnaptar.MainActivity;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mikeux on 2014.08.04..
 */
public class EdzesDataSource {
	
	public class NapiEdzes{
		public String datum;
		public int edzes_db;
		public NapiEdzes(String datum, int edzes_db) {
			this.datum = datum;
			this.edzes_db=edzes_db;
		}
	}
	
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { "id", "fk_edzes_fajta", "datum", "idotartam"};
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
    
    public EdzesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
    	if(database == null) database = dbHelper.getWritableDatabase();
    }
    
	public void deleteEdzes(Long id) {
		Log.i("Mikeux", "Edzés törlése. id: " + id);
		database.delete("edzes", "id = " + id, null);
	}    

    public long createEdzes(Long fk_edzes_fajta, Calendar datum, int idotartam, int szorzo) {
	    ContentValues values = new ContentValues();
	    values.put("fk_edzes_fajta", fk_edzes_fajta);
	    values.put("datum", format1.format(datum.getTime()));
	    values.put("idotartam", idotartam);
	    values.put("szorzo", szorzo);
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

    public List<Edzes> getAllEdzes(String datum) {
    	List<Edzes> lista = new ArrayList<Edzes>();
	    Cursor cursor = database.rawQuery("SELECT id,fk_edzes_fajta, datum, idotartam, szorzo FROM edzes WHERE datum='"+datum+"';", null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	lista.add(new Edzes(cursor.getLong(0),cursor.getLong(1),cursor.getString(2),cursor.getInt(3),cursor.getInt(4)));
	    	cursor.moveToNext();
	    }
	    cursor.close();
    	return lista;
    }
    
    public List<NapiEdzes> GetNapiEdzesekSzama() {
    	List<NapiEdzes> lista = new ArrayList<NapiEdzes>();
	    Cursor cursor = database.rawQuery("SELECT COUNT(*),datum FROM edzes GROUP BY datum;", null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	//Log.e("Mikeux", cursor.getString(1)+" => "+cursor.getString(0));
	    	lista.add(new NapiEdzes(cursor.getString(1),cursor.getInt(0)));
	    	cursor.moveToNext();
	    }
	    cursor.close();
    	return lista;
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
