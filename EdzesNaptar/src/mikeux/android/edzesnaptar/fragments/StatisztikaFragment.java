package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.util.StatisztikaList;
import mikeux.android.edzesnaptar.util.u;
import android.content.Context;
import android.os.Bundle;
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
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	View rootView = inflater.inflate(R.layout.fragment_statisztika, container, false);
	  	
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
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");
 
        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");
 
        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");
 
        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");
 
        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }	
}
