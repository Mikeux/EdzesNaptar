package mikeux.android.edzesnaptar.db_class;

import java.util.ArrayList;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ElelmiszerDataSource {
	private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns = { "id", "nev", "mennyiseg", "me", "feherje", "zsir" ,"szenhidrat", "kaloria"};

	  public ElelmiszerDataSource(Context context) {
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
	  
	  public Elelmiszer createElelmiszer(Elelmiszer newElelmiszer) {	
		  	this.open();
		    ContentValues values = new ContentValues();
		    values.put("nev", newElelmiszer.nev);
		    values.put("mennyiseg", newElelmiszer.mennyiseg);
		    values.put("me", newElelmiszer.me);
		    values.put("feherje", newElelmiszer.feherje);
		    values.put("zsir", newElelmiszer.zsir);
		    values.put("szenhidrat", newElelmiszer.szenhidrat);
		    values.put("kaloria", newElelmiszer.kaloria);	    
		    long insertId = database.insert("elelmiszer", null, values);
		    newElelmiszer.setId(insertId);
		    /*Cursor cursor = database.query("elelmiszer", allColumns, "id  = " + insertId, null, null, null, null);
		    cursor.moveToFirst();
		    //EdzesFajta newEdzesFajta = cursorToEdzes(cursor);
		    Elelmiszer newElelmiszer =  new Elelmiszer(cursor.getLong(0),cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
		    cursor.close();*/
		    this.close();
		    return newElelmiszer;
		  }

	  /*public Elelmiszer createElelmiszer(String nev, double feherje,double zsir, double szenhidrat, double kaloria) {		  
	    ContentValues values = new ContentValues();
	    values.put("nev", nev);
	    values.put("feherje", feherje);
	    values.put("zsir", zsir);
	    values.put("szenhidrat", szenhidrat);
	    values.put("kaloria", kaloria);	    
	    long insertId = database.insert("elelmiszer", null, values);
	    Cursor cursor = database.query("elelmiszer", allColumns, "id  = " + insertId, null, null, null, null);
	    cursor.moveToFirst();
	    //EdzesFajta newEdzesFajta = cursorToEdzes(cursor);
	    Elelmiszer newElelmiszer =  new Elelmiszer(cursor.getLong(0),cursor.getString(1), cursor.getDouble(2), cursor.getDouble(3), cursor.getDouble(4), cursor.getDouble(5));
	    cursor.close();
	    return newElelmiszer;
	    
	  }*/
	  
	  public int updateElelmiszer(Elelmiszer elelmiszer) {
		  	this.open();
		    ContentValues values = new ContentValues();
		    values.put("nev", elelmiszer.getNev());
		    values.put("mennyiseg", elelmiszer.getMennyiseg());
		    values.put("me", elelmiszer.getMe());
		    values.put("feherje", elelmiszer.getFeherje());
		    values.put("zsir", elelmiszer.getZsir());
		    values.put("szenhidrat", elelmiszer.getSzenhidrat());
		    values.put("kaloria", elelmiszer.getKaloria());	  
		    Log.i("Mikeux", "Élelmiszer Update. id: " + elelmiszer.getId());
		    this.close();
		    return database.update("elemiszer", values, "id "+"="+elelmiszer.getId(),null);
		    /*Cursor cursor = database.query("edzes_fajta",
		        allColumns, "id  = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    //EdzesFajta newEdzesFajta = cursorToEdzes(cursor);
	        EdzesFajta newEdzesFajta =  new EdzesFajta(cursor.getLong(0),cursor.getString(1));
		    cursor.close();
		    return newEdzesFajta;*/
	  }
	  
	  /*public int updateElelmiszer(long id, String nev, double feherje,double zsir, double szenhidrat, double kaloria) {
		    ContentValues values = new ContentValues();
		    values.put("nev", nev);
		    values.put("feherje", feherje);
		    values.put("zsir", zsir);
		    values.put("szenhidrat", szenhidrat);
		    values.put("kaloria", kaloria);	  
		    Log.i("Mikeux", "Élelmiszer Update. id: " + id);
		    return database.update("elemiszer", values, "id "+"="+id,null);
	  }*/
	  
	  public void deleteElelmiszer(Elelmiszer elelmiszer) {
		long id = elelmiszer.getId();
		Log.i("Mikeux", "Élelmiszer törlése. id: " + id);
		//System.out.println("Edzésfajta törlése. id: " + id);
		database.delete("elelmiszer", "id = " + id, null);
	  }

	  /*public void deleteElelmiszer(Long id) {
		Log.i("Mikeux", "Edzésfajta törlése. id: " + id);
		//System.out.println("Edzésfajta törlése. id: " + id);
		database.delete("elelmiszer", "id = " + id, null);
	  }*/
	  
	  public List<Elelmiszer> getAllElelmiszer() {
		    this.open(true);
		  	List<Elelmiszer> elelmiszer = new ArrayList<Elelmiszer>();
		    Cursor cursor = database.query("elelmiszer",allColumns, null, null, null, null, null);
		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		    	elelmiszer.add(new Elelmiszer(cursor.getLong(0),cursor.getString(1), cursor.getInt(2), cursor.getString(3), 
		    			cursor.getDouble(4), cursor.getDouble(5), cursor.getDouble(6), cursor.getDouble(7)));
		    	cursor.moveToNext();
		    }
		    cursor.close();
		    this.close();
		    return elelmiszer;
		  }

		  /*private Elelmiszer cursorToElelmiszer(Cursor cursor) {
			Elelmiszer elelmiszer = new Elelmiszer();
			elelmiszer.setId(cursor.getLong(0));
			elelmiszer.setNev(cursor.getString(1));
		    return elelmiszer;
		  }*/
}
