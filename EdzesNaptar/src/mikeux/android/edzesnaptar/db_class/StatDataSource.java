package mikeux.android.edzesnaptar.db_class;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class StatDataSource {
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	public StatDataSource(Context context) {
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

	public List<StatEdzesFajta> GetEdzesFajtak(){
		this.open(true);
	    List<StatEdzesFajta> stats = new ArrayList<StatEdzesFajta>();
	    Cursor cursor = database.rawQuery(
	    		"SELECT edzes_fajta.nev, edzes_fajta.mertekegyseg, SUM(edzes.idotartam*edzes.szorzo) as osszesen" +
		    	"FROM edzes" +
		    	"LEFT OUTER JOIN edzes_fajta ON edzes.fk_edzes_fajta=edzes_fajta.id" +
		    	"GROUP BY edzes_fajta.id",null);
	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	stats.add(new StatEdzesFajta(cursor.getString(0),cursor.getInt(1),cursor.getLong(2)));
	    	cursor.moveToNext();
	    }
	    cursor.close();
	    this.close();	    
	    return stats;
	}
	
	
}
