package mikeux.android.edzesnaptar.db_class;

import java.util.ArrayList;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EdzesFajtaDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { "id", "nev","mertekegyseg"};

	  public EdzesFajtaDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public EdzesFajta createEdzes(String edzes, Mertekegyseg me) {
	    ContentValues values = new ContentValues();
	    values.put("nev", edzes);
	    values.put("mertekegyseg", me.getSorszam());
	    long insertId = database.insert("edzes_fajta", null, values);
	    Cursor cursor = database.query("edzes_fajta",
	        allColumns, "id  = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    //EdzesFajta newEdzesFajta = cursorToEdzes(cursor);
        EdzesFajta newEdzesFajta =  new EdzesFajta(cursor.getLong(0),cursor.getString(1));
	    cursor.close();
	    return newEdzesFajta;
	  }

	  public void deleteEdzesFajta(EdzesFajta edzes) {
	    long id = edzes.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete("edzes_fajta", "id = " + id, null);
	  }

	  public List<EdzesFajta> getAllEdzesFajta() {
	    List<EdzesFajta> edzesek = new ArrayList<EdzesFajta>();

	    Cursor cursor = database.query("edzes_fajta",allColumns, null, null, null, null, null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      //EdzesFajta edzes = cursorToEdzes(cursor);
	      //edzesek.add(edzes);
          edzesek.add(new EdzesFajta(cursor.getLong(0),cursor.getString(1),cursor.getLong(2)));
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return edzesek;
	  }

	  private EdzesFajta cursorToEdzes(Cursor cursor) {
		EdzesFajta edzes = new EdzesFajta();
		edzes.setId(cursor.getLong(0));
		edzes.setNev(cursor.getString(1));
	    return edzes;
	  }
}
