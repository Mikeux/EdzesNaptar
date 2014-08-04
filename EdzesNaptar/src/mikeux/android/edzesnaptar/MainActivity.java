package mikeux.android.edzesnaptar;

import java.util.List;
import java.util.Random;

import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import android.os.Bundle;
import android.app.ListActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends ListActivity  {
	private EdzesFajtaDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		datasource = new EdzesFajtaDataSource(this);
	    datasource.open();

	    List<EdzesFajta> values = datasource.getAllEdzesFajta();

	    // use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<EdzesFajta> adapter = new ArrayAdapter<EdzesFajta>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	// Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
      @SuppressWarnings("unchecked")
      ArrayAdapter<EdzesFajta> adapter = (ArrayAdapter<EdzesFajta>) getListAdapter();
      EdzesFajta edzes = null;
      switch (view.getId()) {
      case R.id.add:
        String[] edzesek = new String[] { "Cool", "Very nice", "Hate it" };
        int nextInt = new Random().nextInt(3);
        // save the new comment to the database
        edzes = datasource.createEdzes(edzesek[nextInt]);
        adapter.add(edzes);
        break;
      case R.id.delete:
        if (getListAdapter().getCount() > 0) {
        	edzes = (EdzesFajta) getListAdapter().getItem(0);
          datasource.deleteEdzesFajta(edzes);
          adapter.remove(edzes);
        }
        break;
      }
      adapter.notifyDataSetChanged();
    }

	@Override
	protected void onResume() {
	  datasource.open();
	  super.onResume();
	}
	
	@Override
	protected void onPause() {
	  datasource.close();
	  super.onPause();
	}
	  
}
