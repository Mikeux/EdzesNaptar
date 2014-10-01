package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map.Entry;

import mikeux.android.edzesnaptar.ColorPickerDialog;
import mikeux.android.edzesnaptar.ColorPickerDialog.OnColorChangedListener;
import mikeux.android.edzesnaptar.EdzesService;
import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.util.u;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
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
	public ArrayAdapter<String> arrayAdapter;
	public ArrayList<String> napokLista= new ArrayList<String>();
	private String cSeged;
	private int nSeged;
	private Intent edzesServiceIntent; 
	private PendingIntent pendingIntent;
	private AlarmManager alarmManager;
	private Calendar cal = Calendar.getInstance();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }
    
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	
	  	edzesServiceIntent = new Intent(ctxt , EdzesService.class);  
	  	pendingIntent = PendingIntent.getService(ctxt, 0, edzesServiceIntent, 0);
	  	alarmManager = (AlarmManager)getActivity().getSystemService(ctxt.ALARM_SERVICE);
	  	
	  	
	  	View rootView = inflater.inflate(R.layout.fragment_edzes_beallitas, container, false);
	  	
        edzes_napok  = (TextView) rootView.findViewById(R.id.edzes_napok_TextView);
        nSeged = 0;
        cSeged = u.settings.getString("edzes_napok", "");
        for(String nap : cSeged.split(",")) {
        	if(!nap.equals("")) nSeged++;
        }
        edzes_napok.setText(nSeged+" / hét");
        
        edzes_napok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View viewIn) {
        	  	AlertDialog.Builder builderSingle = new AlertDialog.Builder(ctxt);
                builderSingle.setIcon(R.drawable.ic_launcher);
                builderSingle.setTitle("Válaszd ki a napokat!");
                
                arrayAdapter = new ArrayAdapter<String>(ctxt,android.R.layout.select_dialog_multichoice);
                for(Entry<String, String> nap : u.napokMap.entrySet()) {
                	arrayAdapter.add(nap.getKey());
                }
                final ListView listView = new ListView(ctxt);
                listView.setBackgroundColor(Color.WHITE);

                listView.setAdapter(arrayAdapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                /*listView.setOnItemClickListener(new ListView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int pos, long id) {
												
					}
                });*/
                
                int len = listView.getCount();
                String napkod;
                for (int i = 0; i < len; i++) {
                	napkod = u.napokMap.get(listView.getItemAtPosition(i).toString());	               	
                    for(String nap : cSeged.split(",")) {
                    	if(!nap.equals("") && nap.equals(napkod))
                    		listView.setItemChecked(i, true);
                    }
                }
                
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
                    	SparseBooleanArray checked = listView.getCheckedItemPositions();
                    	nSeged = 0;
                    	cSeged= "";
                    	String item;
                    	
                    	alarmManager.cancel(pendingIntent); 
                    	
                    	for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    	    if (checked.get(i)) {
                    	    	item =  listView.getAdapter().getItem(i).toString();
                    	    	if(!cSeged.equals("")) cSeged += ",";
                    	    	cSeged += u.napokMap.get(item);
                    	    	                    	    	
                    			if (u.napokMap.get(item).equals("H")) {
                                    forday(2);
                                } else if (u.napokMap.get(item).equals("K")) {
                                    forday(3);
                                } else if (u.napokMap.get(item).equals("SZE")) {
                                    forday(4);
                                } else if (u.napokMap.get(item).equals("CS")) {
                                    forday(5);
                                } else if (u.napokMap.get(item).equals("P")) {
                                    forday(6);
                                } else if (u.napokMap.get(item).equals("SZO")) {
                                    forday(7);
                                } else if (u.napokMap.get(item).equals("V")) {
                                    forday(1);
                                }
                    	    	//Log.e("Mikeux",cSeged);
                    	    	//u.napokMap.get()
                    	    	nSeged++;
                    	    }
                    	}
                    	//if(nSeged>0) HatterFolyamatBeallit();
                    	
        				Editor edit = u.settings.edit();
        				edit.putString("edzes_napok", cSeged);
        				edit.commit();
        				
                    	edzes_napok.setText(nSeged+" / hét");             	
                        
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
	  				  	
	  	/*napok_szama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					Log.e("MIkeux","IME_ACTION_DONE");
				}
				return false;
			}
	    });*/
	  	
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
   
   public void HatterFolyamatBeallit() {
	   cal = Calendar.getInstance();
	   cal.setTime(new Date()); 
	   cal.add(Calendar.MINUTE, 1);
	   //Óránként fut
	   alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), 60 * 60 * 1000, pendingIntent);
   }
   
   public void forday(int day) {
	   int ora = u.randInt(12, 16); 
	   int perc = u.randInt(0, 60);
	   
	   Calendar calNow = Calendar.getInstance();
	   cal = Calendar.getInstance();
	   
	   cal.set(Calendar.DAY_OF_WEEK, day);
	   cal.set(Calendar.HOUR_OF_DAY, ora);
	   cal.set(Calendar.MINUTE, perc);
	   cal.set(Calendar.SECOND, 0);
	   cal.set(Calendar.MILLISECOND, 0);
	   
	   /*Log.e("Mikeux","day - "+day);
	   Log.e("Mikeux","ora - "+ora);
	   Log.e("Mikeux","perc - "+perc);*/

	   /*Log.e("Mikeux","DAY_OF_WEEK - "+cal.get(Calendar.DAY_OF_WEEK));
	   Log.e("Mikeux","HOUR_OF_DAY - "+cal.get(Calendar.HOUR_OF_DAY));
	   Log.e("Mikeux","MINUTE - "+cal.get(Calendar.MINUTE));
	   Log.e("Mikeux","DATE - "+cal.get(Calendar.DATE));*/
	   
	   if(cal.getTimeInMillis() <= calNow.getTimeInMillis()){
		   cal.add(Calendar.DATE, 7);
	   }	   
	   /*if(cal.get(Calendar.DAY_OF_WEEK) > day || (cal.get(Calendar.DAY_OF_WEEK) ==  day && cal.get(Calendar.HOUR_OF_DAY)>=ora)) {
		   cal.add(Calendar.DATE, 7);
	   }*/	   
	   
	   //Log.e("Mikeux","DATE - "+cal.get(Calendar.DATE));
	   //Log.e("Mikeux","--------------------------------");
	   
	   alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), 7 * 24 * 60 * 60 * 1000, pendingIntent);
   }
   
}
