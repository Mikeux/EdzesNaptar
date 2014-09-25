package mikeux.android.edzesnaptar.fragments;

import java.util.Collections;

import mikeux.android.edzesnaptar.ColorPickerDialog;
import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.ColorPickerDialog.OnColorChangedListener;
import mikeux.android.edzesnaptar.util.u;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class EdzesBeallitasFragment extends SherlockFragment  {
	public Dialog dialogWindow;
	public EditText napok_szama;
	public TextView edzes_napok;
	public Button hatterszin_button;
	public static Context ctxt;
	public OnColorChangedListener mColorListener;
	
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
        
        edzes_napok  = (TextView) rootView.findViewById(R.id.edzes_napok_TextView);
        edzes_napok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
        	  	AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctxt);
                builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Válaszd ki a napokat!");
                
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ctxt,android.R.layout.select_dialog_multichoice);
                arrayAdapter.add("Hétfő");
                arrayAdapter.add("Kedd");
                arrayAdapter.add("Szerda");
                arrayAdapter.add("Csütörtök");
                arrayAdapter.add("Péntek");
                arrayAdapter.add("Szombat");
                arrayAdapter.add("Vasárnap");
                
                final ListView listView = new ListView(ctxt);
                listView.setBackgroundColor(Color.WHITE);

                listView.setAdapter(arrayAdapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                /*listView.setOnItemClickListener(new ListView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
					}
                });*/
                builderSingle.setView(listView);

                builderSingle.setNegativeButton("Mégse",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                
                builderSingle.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    	/*Collections.sort(arrayAdapter.chechkedList);
    					for(int i = arrayAdapter.chechkedList.size()-1; i>=0; i--){
    						//DB Törlés
    						datasource.deleteEdzesFajta(ids.get(adapter.chechkedList.get(i)));
    						ids.remove(ids.get(adapter.chechkedList.get(i)));
                        	nevek.remove(nevek.get(adapter.chechkedList.get(i)));
                        	kepek.remove(kepek.get(adapter.chechkedList.get(i)));
    					}   */                 	
                        dialog.dismiss();
                    }
                });
                builderSingle.show();            	
            }
        });
        
        
	  	napok_szama = (EditText) rootView.findViewById(R.id.napok_szama_editText);
	  	napok_szama.setText(u.settings.getInt("napok_szama", 31)+"");
	  	
	  	napok_szama.addTextChangedListener(new TextWatcher(){
			@Override
			public void afterTextChanged(Editable arg0) { }
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { }
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//Log.e("Mikeux","Napok száma => "+arg0.toString());
				Editor edit = u.settings.edit();
				edit.putInt("napok_szama", u.StringToInt(arg0.toString()));
				edit.commit();
			}
	    }); 
	  	
	  	hatterszin_button = (Button) rootView.findViewById(R.id.hatterszin_button);
	  	hatterszin_button.setBackgroundColor(u.settings.getInt("hatterszin", -917505));

	  	mColorListener = new OnColorChangedListener(){
			@Override
			public void colorChanged(int color) {
				//Log.e("Mikeux","Szín => "+color);
				hatterszin_button.setBackgroundColor(color);
				Editor edit = u.settings.edit();
				edit.putInt("hatterszin", color);
				edit.commit(); 
			}
	  	};
	  	OnClickListener listener_szinkivalaszto = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	new ColorPickerDialog(ctxt, mColorListener, u.settings.getInt("hatterszin", -917505)).show();            	
            }
	  	};
	  	hatterszin_button.setOnClickListener(listener_szinkivalaszto);

	  	return rootView;
   }
   
}
