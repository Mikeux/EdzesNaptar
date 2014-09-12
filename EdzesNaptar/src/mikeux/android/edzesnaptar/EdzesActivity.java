package mikeux.android.edzesnaptar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesDataSource.NapiEdzes;
import mikeux.android.edzesnaptar.util.EdzesMainList;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;



//http://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/
public class EdzesActivity extends ListActivity  {
		
	public static Context ctxt;
	private EdzesMainList adapter;
	private EdzesDataSource datasource_edzes;
	private final ArrayList<Calendar> datumok = new ArrayList<Calendar>();
	private final ArrayList<String> napok = new ArrayList<String>();
	private final ArrayList<String> edzesekSzama = new ArrayList<String>();
	private ListView list;
	String[] days = {"Vasárnap", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"};
	//private EdzesFajtaDataSource datasource;
	
	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
    private ImageView Vissza_Nyil;

    public void ListaFrissit(){
    	List<NapiEdzes> napiEdzesek = datasource_edzes.GetNapiEdzesekSzama();
        
		Calendar cal = Calendar.getInstance();  
		cal.setTime(new Date()); 
		for(int i=0; i<365; i++){
			int counter = 0;
			for(NapiEdzes elem : napiEdzesek){
				//Log.e("Mikeux", elem.datum+" => "+format1.format(cal.getTime()));				
				if(elem.datum.equals(format1.format(cal.getTime()))){
					counter = elem.edzes_db;
					break;
				}
			}
			edzesekSzama.add(counter+" gyakorlat");
			//edzesekSzama.add(datasource_edzes.GetNapiEdzesSzam(cal)+" edzés");
			datumok.add((Calendar)cal.clone());
			napok.add(days[cal.get(Calendar.DAY_OF_WEEK)-1]);
			cal.add(Calendar.DATE, -1);
		}
		adapter = new EdzesMainList(EdzesActivity.this, datumok, napok,edzesekSzama);
		adapter.notifyDataSetChanged();
		list.setAdapter(adapter);
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctxt = this;
		setContentView(R.layout.fragment_edzes_main);

        Vissza_Nyil = (ImageView)findViewById(R.id.vissza_nyil);
        Vissza_Nyil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        datasource_edzes = new EdzesDataSource(this);
        datasource_edzes.open();

        /*List<NapiEdzes> napiEdzesek = datasource_edzes.GetNapiEdzesekSzama();
        
		Calendar cal = Calendar.getInstance();  
		cal.setTime(new Date()); 
		for(int i=0; i<365; i++){
			int counter = 0;
			for(NapiEdzes elem : napiEdzesek){
				if(elem.datum.equals(format1.format(cal.getTime()))){
					counter++;
				}
			}
			edzesekSzama.add(counter+" gyakorlat");
			//edzesekSzama.add(datasource_edzes.GetNapiEdzesSzam(cal)+" edzés");
			datumok.add((Calendar)cal.clone());
			napok.add(days[cal.get(Calendar.DAY_OF_WEEK)-1]);
			cal.add(Calendar.DATE, -1);
		}
		adapter = new EdzesMainList(EdzesActivity.this, datumok, napok,edzesekSzama);*/
		
		//Log.e("MIkeux",adapter.)

        list=getListView();
        list.setItemsCanFocus(true);
        this.ListaFrissit();
        //list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				Intent intent = new Intent(ctxt, EdzesNapActivity.class);
				intent.putExtra("datum", format1.format(datumok.get(position).getTime()));
                startActivity(intent);
				//Log.e("Mikeux","onItemClick");
				//Toast.makeText(EdzesActivity.this, datumok.get(position).getTime()+"", Toast.LENGTH_SHORT).show();
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
	    	//getListView().setSelection(100);
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
	
	@Override
	protected void onResume() {
		//datasource_edzes.open();
		this.ListaFrissit();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		//datasource_edzes.close();
		this.ListaFrissit();
	    super.onPause();
	}
	  
}





