package mikeux.android.edzesnaptar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mikeux.android.edzesnaptar.util.EdzesMainList;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

//http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
public class MainActivity extends ListActivity  {

	public static Context ctxt;
	private EdzesMainList adapter;
	private final ArrayList<Calendar> datumok = new ArrayList<Calendar>();
	private final ArrayList<String> napok = new ArrayList<String>();
	private final ArrayList<String> edzesekSzama = new ArrayList<String>();
	private ListView list;
    private ImageView Vissza_Nyil;
	String[] days = {"Vasárnap", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"};
	//private EdzesFajtaDataSource datasource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctxt = this;
		setContentView(R.layout.activity_main);

        Vissza_Nyil = (ImageView)findViewById(R.id.vissza_nyil);
        Vissza_Nyil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

		Calendar cal = Calendar.getInstance();  
		cal.setTime(new Date()); 
		for(int i=0; i<365; i++){
			edzesekSzama.add("0 edzés");
			datumok.add((Calendar)cal.clone());
			napok.add(days[cal.get(Calendar.DAY_OF_WEEK)-1]);
			cal.add(Calendar.DATE, -1);
		}
		adapter = new EdzesMainList(MainActivity.this, datumok, napok,edzesekSzama);
		
		//Log.e("MIkeux",adapter.)


        list=getListView();
        list.setItemsCanFocus(true);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {

				//Log.e("Mikeux","onItemClick");
				//Toast.makeText(EdzesFajtaActivity.this, "A '" +nevek.get(+ position)+"' elemre kattintottál!", Toast.LENGTH_SHORT).show();
			}
		});
        //setListAdapter(adapter);
		/*datasource = new EdzesFajtaDataSource(this);
	    datasource.open();

	    List<EdzesFajta> values = datasource.getAllEdzesFajta();

	    ArrayAdapter<EdzesFajta> adapter = new ArrayAdapter<EdzesFajta>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menu_edzes_fajtak:
	    	startActivity(new Intent(ctxt, EdzesFajtaActivity.class));
	    	break;
	    case R.id.menu_ugras:
	    	//startActivity(new Intent(ctxt, EdzesFajtaActivity.class));
	    	getListView().setSelection(100);
	    	break;	    	
	    /*case R.id.menu_settings:
	    	//Intent i = new Intent(this, SettingsActivity.class);
	        //startActivity(i);
	        //startActivity(new Intent(ctxt, SettingsActivity.class));
	    	//startActivity(new Intent(ctxt, SettingsActivity.class));
	    	//http://viralpatel.net/blogs/android-preferences-activity-example/
	    	break;
	    case R.id.menu_contact:
	        break;*/
	    case R.id.menu_exit:
	    	System.exit(0);
	        break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	    return true;
	}
	

	// Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
      /*@SuppressWarnings("unchecked")
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
      adapter.notifyDataSetChanged();*/
    }

	@Override
	protected void onResume() {
	  //datasource.open();
	  super.onResume();
	}
	
	@Override
	protected void onPause() {
	  //datasource.close();
	  super.onPause();
	}
	  
}
