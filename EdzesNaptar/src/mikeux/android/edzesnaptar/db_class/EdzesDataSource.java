package mikeux.android.edzesnaptar.db_class;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class EdzesDataSource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NEV };

	  public EdzesDataSource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Edzes createEdzes(String edzes) {
	    ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_NEV, edzes);
	    long insertId = database.insert(MySQLiteHelper.TABLE, null,
	        values);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE,
	        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Edzes newEdzes = cursorToEdzes(cursor);
	    cursor.close();
	    return newEdzes;
	  }

	  public void deleteEdzes(Edzes edzes) {
	    long id = edzes.getId();
	    System.out.println("Comment deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE, MySQLiteHelper.COLUMN_ID + " = " + id, null);
	  }

	  public List<Edzes> getAllEdzes() {
	    List<Edzes> edzesek = new ArrayList<Edzes>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Edzes edzes = cursorToEdzes(cursor);
	      edzesek.add(edzes);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return edzesek;
	  }

	  private Edzes cursorToEdzes(Cursor cursor) {
		Edzes edzes = new Edzes();
		edzes.setId(cursor.getLong(0));
		edzes.setComment(cursor.getString(1));
	    return edzes;
	  }
}
