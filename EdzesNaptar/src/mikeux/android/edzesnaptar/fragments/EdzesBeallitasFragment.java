package mikeux.android.edzesnaptar.fragments;

import mikeux.android.edzesnaptar.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class EdzesBeallitasFragment extends SherlockFragment  {

	public static Context ctxt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }
    
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	
	  	View rootView = inflater.inflate(R.layout.fragment_edzes_beallitas, container, false);
	  	
	  	return rootView;
   }
   
}
