package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.db_class.StatisztikaDataSource;
import mikeux.android.edzesnaptar.db_class.StatisztikaEdzesFajta;
import mikeux.android.edzesnaptar.util.StatisztikaList;
import mikeux.android.edzesnaptar.util.u;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

//ExpandableListView példa
//http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/

public class StatisztikaFragment extends SherlockFragment  {
	public static Context ctxt;
	private StatisztikaList listAdapter;
	
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
	
    StatisztikaDataSource datasourceGroup;    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	View rootView = inflater.inflate(R.layout.fragment_statisztika, container, false);
	  	
	  	datasourceGroup = new StatisztikaDataSource(this.ctxt);
	  	
        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableList_stat);
        prepareListData();
        listAdapter = new StatisztikaList(ctxt, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        
	  	//listAdapter = (StatisztikaList) rootView.findViewById(R.id.expandableList_stat);
	  	//listAdapter.setBackgroundColor(u.settings.getInt("hatterszin", -917505));	
		
	    return rootView;
   }
	
	/*
     * Preparing the list data
     */
    private void prepareListData() {
    	Cursor cursor;
    	List<StatisztikaEdzesFajta> EdzesGroup = datasourceGroup.GetEdzesFajtak(); 
    	
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        for(StatisztikaEdzesFajta sef : EdzesGroup) {
        	//Log.e("Mikeux",listDataHeader.size()+"");
        	listDataHeader.add(sef.getFajta_nev()+"    Σ "+
        			((sef.getFajta_me() == Mertekegyseg.Idő_ms.getSorszam()) ? u.msValtas(sef.getOsszesen()) : sef.getOsszesen()+" db"));
        	List<String> tetelek = new ArrayList<String>();
        	
        	cursor = datasourceGroup.GetEdzesek(sef.getEdzes_Fajta_id());
        	cursor.moveToFirst();
    	    while (!cursor.isAfterLast()) {
    	    	tetelek.add(cursor.getString(1)+" - "+cursor.getInt(3)+ " x " +
    	    			((sef.getFajta_me() == Mertekegyseg.Idő_ms.getSorszam()) ? u.msValtas(cursor.getInt(2)) : cursor.getInt(2)+" db"));
    	    	//Log.e("Mikeux","Add - "+cursor.getString(1)+" - "+cursor.getInt(2)+"x"+cursor.getInt(3));
    	    	cursor.moveToNext();
    	    }
    	    cursor.close();
        	listDataChild.put(listDataHeader.get(listDataHeader.size()-1), tetelek);
        }
        
        datasourceGroup.close();
        
        // Adding child data
        /*listDataHeader.add("Top 250");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
 
        listDataChild.put(listDataHeader.get(0), top250); */
    }	
}
