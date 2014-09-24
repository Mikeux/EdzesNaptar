package mikeux.android.edzesnaptar.fragments;

import mikeux.android.edzesnaptar.ColorPickerDialog;
import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.ColorPickerDialog.OnColorChangedListener;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;

public class EdzesBeallitasFragment extends SherlockFragment  {
	public Button hatterszin_button;
	public static Context ctxt;
	public OnColorChangedListener mColorListener;
	public SharedPreferences settings;
	
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
	  	settings = PreferenceManager.getDefaultSharedPreferences(getActivity());

	  	hatterszin_button = (Button) rootView.findViewById(R.id.hatterszin_button);
	  	hatterszin_button.setBackgroundColor(settings.getInt("hatterszin", -917505));

	  	mColorListener = new OnColorChangedListener(){
			@Override
			public void colorChanged(int color) {
				//Log.e("Mikeux","Szín => "+color);
				hatterszin_button.setBackgroundColor(color);
				Editor edit = settings.edit();
				edit.putInt("hatterszin", color);
				edit.commit(); 
			}
	  	};
	  	OnClickListener listener_szinkivalaszto = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	new ColorPickerDialog(ctxt, mColorListener, settings.getInt("hatterszin", -917505)).show();            	
            }
	  	};
	  	hatterszin_button.setOnClickListener(listener_szinkivalaszto);

	  	return rootView;
   }
   
}
