package mikeux.android.edzesnaptar.db_class;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class EtkezesDataSource {
	private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { "id","fk_elelmiszer", "nev","mennyiseg"};

	  public EtkezesDataSource(Context context) {
		  dbHelper = new MySQLiteHelper(context);
	  }
	  
	  public void open(boolean... olvasasra) throws SQLException {
		  if(database == null) {
			  if(olvasasra.length>0 && olvasasra[0]) database = dbHelper.getReadableDatabase();
			  else database = dbHelper.getWritableDatabase();
		  }
	  }

	  public void close() {
		  dbHelper.close();
		  database = null;
	  }
	  
	  public Etkezes createEtkezes(Etkezes newEtkezes) {		
		  	this.open();
		    ContentValues values = new ContentValues();
		    values.put("fk_elelmiszer", newEtkezes.getFk_elelmiszer());
		    values.put("nev", newEtkezes.getNev());
		    values.put("mennyiseg", newEtkezes.getMennyiseg());
		    
		    long insertId = database.insert("etkezes", null, values);
		    newEtkezes.setId(insertId);
		    this.close();
		    return newEtkezes;
		    
		  }
	  
	  public int updateEtkezes(Etkezes newEtkezes) {
		  this.open();
		  ContentValues values = new ContentValues();
		  values.put("fk_elelmiszer", newEtkezes.getFk_elelmiszer());
		  values.put("nev", newEtkezes.getNev());
		  values.put("mennyiseg", newEtkezes.getMennyiseg());  
		  Log.i("Mikeux", "Étkezés Update. id: " + newEtkezes.getId());
		  this.close();
		  return database.update("etkezes", values, "id "+"="+newEtkezes.getId(),null);
	  }
	  
	  
	  public void deleteEtkezes(Etkezes etkezes) {
		  this.open();
		  long id = etkezes.getId();
		  Log.i("Mikeux", "Étkezés törlése. id: " + id);
		  System.out.println("Étkezés törlése. id: " + id);
		  database.delete("Etkezes", "id = " + id, null);
		  this.close();
	  }

	  public List<Etkezes> getAllEtkezes() {
		  	this.open(true);
		    List<Etkezes> etkezesek = new ArrayList<Etkezes>();
		    Cursor cursor = database.query("Etkezes",allColumns, null, null, null, null, null);
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	etkezesek.add(new Etkezes(cursor.getLong(0),cursor.getLong(0),cursor.getString(1),cursor.getLong(2)));
		    	cursor.moveToNext();
		    }
		    cursor.close();
		    this.close();
		    return etkezesek;
		  }
	  
	  private Etkezes cursorToEtkezes(Cursor cursor) {
			Etkezes etkezes = new Etkezes();
			etkezes.setId(cursor.getLong(0));
			etkezes.setFk_elelmiszer(cursor.getLong(1));
			etkezes.setNev(cursor.getString(2));
			etkezes.setMennyiseg(cursor.getLong(3));
		    return etkezes;
		  }
}
