package mikeux.android.edzesnaptar.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

//import com.jeremyfeinstein.slidingmenu.example.R;

import mikeux.android.edzesnaptar.EdzesActivity;
import mikeux.android.edzesnaptar.EdzesNapActivity;
import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.ResponsiveUIActivity;
import mikeux.android.edzesnaptar.db_class.EdzesDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesDataSource.NapiEdzes;
import mikeux.android.edzesnaptar.util.EdzesMainList;

/**
 * Created by Mikeux on 2014.09.09..
 */

@SuppressLint("ValidFragment")
public class EdzesFragment extends Fragment {
	
	public static Context ctxt;
	private EdzesMainList adapter;
	private EdzesDataSource datasource_edzes;
	private final ArrayList<Calendar> datumok = new ArrayList<Calendar>();
	private final ArrayList<String> napok = new ArrayList<String>();
	private final ArrayList<String> edzesekSzama = new ArrayList<String>();
	private ListView list;
	String[] days = {"Vasárnap", "Hétfő", "Kedd", "Szerda", "Csütörtök", "Péntek", "Szombat"};

	private SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
    private ImageView Vissza_Nyil;
    
    private int mPos = -1;


    public EdzesFragment() { }
    public EdzesFragment(int pos) {
        mPos = pos;
    }

  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	Log.e("Mikeux","OnCreateView");
	  	this.ctxt = inflater.getContext();
	  	
		View rootView = inflater.inflate(R.layout.activity_main, container, false);
		list = (ListView) rootView.findViewById(R.id.list);
        datasource_edzes = new EdzesDataSource(this.ctxt);
        datasource_edzes.open();
        list.setItemsCanFocus(true);
        this.ListaFrissit();
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
	    return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mPos", mPos);
    }

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
									//EdzesFragment.this
		adapter = new EdzesMainList(this.getActivity(), datumok, napok,edzesekSzama);
		adapter.notifyDataSetChanged();
		list.setAdapter(adapter);
    }
}
