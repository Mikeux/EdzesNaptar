package mikeux.android.edzesnaptar.fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.db_class.Edzes;
import mikeux.android.edzesnaptar.db_class.EdzesDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import mikeux.android.edzesnaptar.util.EdzesNapList;
import mikeux.android.edzesnaptar.util.u;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class EdzesNapFragment extends SherlockFragment {
	public static Context ctxt;
    private Dialog dialogWindow;
	private EdzesDataSource datasource_edzes;
    private EdzesFajtaDataSource datasource_fajta;
	private EdzesNapList adapter;
	private ListView list;
    List<EdzesFajta> edzesfajtak;
    //ids,fk_edzes_fajta,edzes_fajta_neve,edzes_fajta_mertekegyseg,datum,idotartam
	private ArrayList<Long> ids = new ArrayList<Long>();
	private ArrayList<Long> fk_edzes_fajta = new ArrayList<Long>();
	private ArrayList<String> edzes_fajta_neve = new ArrayList<String>();
	private ArrayList<Mertekegyseg> edzes_fajta_mertekegyseg = new ArrayList<Mertekegyseg>();
	private ArrayList<String> edzes_fajta_mertekegyseg_fajta = new ArrayList<String>();
	private ArrayList<String> datum = new ArrayList<String>();
	private ArrayList<Integer> idotartam = new ArrayList<Integer>();
	private ArrayList<Integer> szorzo = new ArrayList<Integer>();
	
    private Spinner spinner;
    private Spinner spinner_me;
    private ImageView Vissza_Nyil;
	private EditText mennyiseg;	
	private TextView mennyiseg_tv;
	private EditText szorzo_et;	
	private Button btn_uj_edzes_ad;
	private Button btn_uj_edzes_megse;
    
    private String Datum;
    private boolean PopupFelnyilt = false;
    private int ModositPosition = -1;
    
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
    
    
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	this.ctxt = inflater.getContext();

    	View rootView = inflater.inflate(R.layout.fragment_edzes_nap, container, false);
    	Bundle b = this.getArguments();
        this.Datum = b.getString("datum");     
        
        TextView datum_tv = (TextView)rootView.findViewById(R.id.datum_textview);
        datum_tv.setText(this.Datum);        
        
        /*Vissza_Nyil = (ImageView)findViewById(R.id.vissza_nyil);
        Vissza_Nyil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        dialogWindow = new Dialog(ctxt);
        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(R.layout.popup_uj_edzes);
        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialogWindow.setCancelable(false);

        datasource_edzes = new EdzesDataSource(this.getActivity());
        datasource_edzes.open();

        datasource_fajta = new EdzesFajtaDataSource(this.getActivity());
        datasource_fajta.open();
        edzesfajtak = datasource_fajta.getAllEdzesFajta();

        List<String> edzesfajtaneve = new ArrayList<String>();
        for(EdzesFajta ef:edzesfajtak) {
            edzesfajtaneve.add(ef.nev);
        }
        datasource_fajta.close();
        
        spinner_me = (Spinner) dialogWindow.findViewById(R.id.spinner_mertekegyseg_fajta);
        
        spinner = (Spinner) dialogWindow.findViewById(R.id.spinner_edzesfajta);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		String CurrentItem = spinner.getItemAtPosition(position).toString();
        		//Toast.makeText(EdzesNapActivity.this, spinner.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();

        		List<String> elemek = new ArrayList<String>();
        		for(EdzesFajta ef : edzesfajtak){
        			if(ef.nev.equals(CurrentItem)){
        				if(ef.mertekegyseg == Mertekegyseg.Idő_ms) {
        					elemek = Arrays.asList("sec","min");
        					spinner_me.setEnabled(true);        					
        				}
        				else {
        					elemek = Arrays.asList("db");
        					spinner_me.setEnabled(false);
        				}
        				break;
        			}
        		}
        		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(ctxt, android.R.layout.simple_spinner_item, elemek);
        		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        		spinner_me.setAdapter(spinnerArrayAdapter);	

        		if(ModositPosition != -1 && edzes_fajta_mertekegyseg_fajta.get(ModositPosition).equals("min")) {
        			//Log.e("Mikeux","setSelection(1)");
        			spinner_me.setSelection(1);
        		}
		        else {
		        	//Log.e("Mikeux","setSelection(0)");
		        	spinner_me.setSelection(0);	
		        }
            }
        	@Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
        
        mennyiseg = (EditText) dialogWindow.findViewById(R.id.mennyiseg);
        //mennyiseg_tv = (TextView) dialogWindow.findViewById(R.id.mertekegyseg_tv);
        szorzo_et = (EditText) dialogWindow.findViewById(R.id.szorzo);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.ctxt,android.R.layout.simple_spinner_item, edzesfajtaneve);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        btn_uj_edzes_ad = (Button) dialogWindow.findViewById(R.id.button_uj_edzes_ad);
        btn_uj_edzes_megse = (Button) dialogWindow.findViewById(R.id.button_uj_edzes_megse);
        
        
        List<Edzes> EFList = datasource_edzes.getAllEdzes(this.Datum);
	    for(Edzes E : EFList) {
	    	ids.add(E.id);
	    	fk_edzes_fajta.add(E.fk_id);
	    	for(EdzesFajta ef: edzesfajtak){
	    		if(ef.id == E.fk_id) {
	    			edzes_fajta_neve.add(ef.nev);
	    			edzes_fajta_mertekegyseg.add(ef.mertekegyseg);
	    			if(ef.mertekegyseg == Mertekegyseg.GyakorlatSzám){
	    				edzes_fajta_mertekegyseg_fajta.add("db");
	    			} else {
	    				if(E.idotartam%60==0) edzes_fajta_mertekegyseg_fajta.add("min");
	    				else edzes_fajta_mertekegyseg_fajta.add("sec");
	    			}	    			
	    			break;
	    		}
	    	}
	    	szorzo.add(E.szorzo);
	    	datum.add(format1.format(E.datum));
	    	idotartam.add(E.idotartam);
	    }
	    datasource_edzes.close();
	    
        adapter = new EdzesNapList(this.getActivity(), edzes_fajta_neve,edzes_fajta_mertekegyseg_fajta, idotartam, szorzo);        
        list=(ListView)rootView.findViewById(R.id.list);
        list.setBackgroundColor(u.settings.getInt("hatterszin", -917505));
        list.setItemsCanFocus(true);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Log.e("Mikeux","position => "+position);
				//Toast.makeText(EdzesFajtaActivity.this, "A '" +nevek.get(+ position)+"' elemre kattintottál!", Toast.LENGTH_SHORT).show();
				ModositPosition = position;
				PopupFelnyilt = true;
				//Log.e("Mikeux","NULL ? => "+ (szorzo_et == null ? "Igen" : "Nem ("+szorzo_et.toString()+")"));
				//mennyiseg.setText("");
				int osztoszam = 1;				
				if(edzes_fajta_mertekegyseg_fajta.get(position) == "min") osztoszam = 60;

				mennyiseg.setText((idotartam.get(position)/osztoszam)+"");
				
		        for(int i=0; i< edzesfajtak.size(); i++) {
		        	//EdzesFajta ef:edzesfajtak
		        	if(edzesfajtak.get(i).nev.equals(edzes_fajta_neve.get(position))) {
		        		spinner.setSelection(i);
		        		break;
		        	}
		        }        		
		    	//spinner.setSelection(position);
				szorzo_et.setText(szorzo.get(position)+"");
				btn_uj_edzes_ad.setText("Módosít");
		    	dialogWindow.show();
			}
			
		});
        
        OnClickListener listener_uj_ad = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	//Log.e("Mikeux","Hozzáad => "+newEdzesFajta.getText());
            	String mennyiseg_fajta = "db";
            	int mennyiseg_szorzo = 1;
            	
        		String _edzes_fajta_neve = (spinner.getSelectedItem() == null) ? "" : spinner.getSelectedItem().toString();
        		Integer me = u.StringToInt(mennyiseg.getText().toString());
        		Integer _szorzo = u.StringToInt(szorzo_et.getText().toString());
            	
            	//Hiba ellenőrzések
            	if(_edzes_fajta_neve.equals("")){
            		u.uzen("Az edzés fajtája nincs kiválasztva!", true);
            		return;
            	} else if(me < 1){
            		u.uzen("A mennyiség nem lehet kisebb, mint 1!", true);
            		return;
            	} else if(_szorzo < 1){
            		u.uzen("A szorzó nem lehet kisebb, mint 1!", true);
            		return;
            	}
            	
            	datasource_edzes.open();
            	if(btn_uj_edzes_ad.getText().equals("Hozzáad")) { //Insert
            		//ids,fk_edzes_fajta,edzes_fajta_neve,edzes_fajta_mertekegyseg,datum,idotartam  
            		Long _fk_edzes_fajta = (long) 0;
            		Mertekegyseg _edzes_fajta_mertekegyseg = Mertekegyseg.Idő_ms;
            		for(EdzesFajta ef: edzesfajtak){
        	    		if(ef.nev == _edzes_fajta_neve) {
        	    			_fk_edzes_fajta = ef.id;
        	    			_edzes_fajta_mertekegyseg = ef.mertekegyseg;
        	    			break;
        	    		}
        	    	}          
            		
            		if(_edzes_fajta_mertekegyseg == Mertekegyseg.Idő_ms) {
            			if(spinner_me.getSelectedItem().equals("min")) {
            				edzes_fajta_mertekegyseg_fajta.add(0,"min");
            				mennyiseg_szorzo = 60;
            			}
	            		else {
	            			edzes_fajta_mertekegyseg_fajta.add(0,"sec");
	            			mennyiseg_szorzo = 1;
	            		}
            		} else edzes_fajta_mertekegyseg_fajta.add(0,"db");

            		Calendar cal = Calendar.getInstance();
            	    try {
						cal.setTime(format1.parse(Datum));
					} catch (ParseException e) {
						cal.setTime(new Date());
					}
            		
            	    Long Id = datasource_edzes.createEdzes(_fk_edzes_fajta,cal,me*mennyiseg_szorzo,_szorzo);
            	    //ids,fk_edzes_fajta,edzes_fajta_neve,edzes_fajta_mertekegyseg,datum,idotartam
            	    edzes_fajta_neve.add(0, _edzes_fajta_neve);
            	    idotartam.add(0, me*mennyiseg_szorzo);
            	    ids.add(0, Id);
            	    fk_edzes_fajta.add(0, _fk_edzes_fajta);
            	    edzes_fajta_mertekegyseg.add(0, _edzes_fajta_mertekegyseg);
            	    datum.add(0, Datum);
            	    szorzo.add(0,_szorzo);
            	     
            	    adapter = new EdzesNapList(getActivity(), edzes_fajta_neve,edzes_fajta_mertekegyseg_fajta, idotartam, szorzo);
	                adapter.notifyDataSetChanged();
	                list.setAdapter(adapter);
            	} else { //Update
            		//Log.e("Mikeux","Selected item => "+ModositPosition);
            		Long _fk_edzes_fajta = (long) 0;
            		Mertekegyseg _edzes_fajta_mertekegyseg = Mertekegyseg.Idő_ms;
            		for(EdzesFajta ef: edzesfajtak){
        	    		if(ef.nev == _edzes_fajta_neve) {
        	    			_fk_edzes_fajta = ef.id;
        	    			_edzes_fajta_mertekegyseg = ef.mertekegyseg;
        	    			break;
        	    		}
        	    	}
            		
            		if(_edzes_fajta_mertekegyseg == Mertekegyseg.Idő_ms) {
            			if(spinner_me.getSelectedItem().equals("min")) {
            				edzes_fajta_mertekegyseg_fajta.set(ModositPosition,"min");
            				mennyiseg_szorzo = 60;
            			}
	            		else {
	            			edzes_fajta_mertekegyseg_fajta.set(ModositPosition,"sec");
	            			mennyiseg_szorzo = 1;
	            		}
            		} else edzes_fajta_mertekegyseg_fajta.set(ModositPosition,"db");
            		
            		if(datasource_edzes.updateEdzes(ids.get(ModositPosition),_fk_edzes_fajta,me*mennyiseg_szorzo,_szorzo) > 0) {
	            	    edzes_fajta_neve.set(ModositPosition, _edzes_fajta_neve);
	            	    idotartam.set(ModositPosition, me*mennyiseg_szorzo);
	            	    fk_edzes_fajta.set(ModositPosition, _fk_edzes_fajta);
	            	    edzes_fajta_mertekegyseg.set(ModositPosition, _edzes_fajta_mertekegyseg);
	            	    //datum.set(ModositPosition, Datum);
	            	    szorzo.set(ModositPosition,_szorzo);
		            	
	            	    adapter = new EdzesNapList(getActivity(), edzes_fajta_neve,edzes_fajta_mertekegyseg_fajta, idotartam, szorzo);
		                adapter.notifyDataSetChanged();
		                list.setAdapter(adapter);
		                Log.i("Mikeux","Sikeres módosítás!");
	            	} else {
	            		Log.i("Mikeux","Sikertelen módosítás!");
	            	}
            	}
            	datasource_edzes.close();
                dialogWindow.cancel();
            	PopupFelnyilt = false;                
            }
        };
        btn_uj_edzes_ad.setOnClickListener(listener_uj_ad);
        
        OnClickListener listener_uj_edzes_megse = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	dialogWindow.cancel();
            	PopupFelnyilt = false;
            }
        };
        btn_uj_edzes_megse.setOnClickListener(listener_uj_edzes_megse);  
        
        return rootView;
    }
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    	super.onCreateOptionsMenu(menu, inflater);
    	inflater.inflate(R.menu.edzesfajta, menu);
	}

	@Override
	public void onPrepareOptionsMenu (Menu menu) {
		if(this.adapter != null) menu.getItem(1).setEnabled(this.adapter.chechkedList.size() > 0);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_uj_edzes_fajta:
	    	ModositPosition = -1;
	    	PopupFelnyilt = true;
	    	mennyiseg.setText("");
	    	szorzo_et.setText("1");
	    	btn_uj_edzes_ad.setText("Hozzáad");	   
	    	dialogWindow.show();
	    	break;
	    case R.id.menu_edzes_fajta_torles:
			AlertDialog.Builder builder = new AlertDialog.Builder(this.ctxt);
			builder.setMessage("Biztosan törölni akarod a kijelölt ("+this.adapter.chechkedList.size()+") edzéseket?")
			.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Collections.sort(adapter.chechkedList);
					datasource_edzes.open();
					for(int i = adapter.chechkedList.size()-1; i>=0; i--){
						//DB Törlés
						datasource_edzes.deleteEdzes(ids.get(adapter.chechkedList.get(i)));
						ids.remove(ids.get(adapter.chechkedList.get(i)));
						edzes_fajta_neve.remove(edzes_fajta_neve.get(adapter.chechkedList.get(i)));
						edzes_fajta_mertekegyseg.remove(edzes_fajta_mertekegyseg.get(adapter.chechkedList.get(i)));
						idotartam.remove(idotartam.get(adapter.chechkedList.get(i)));
						datum.remove(datum.get(adapter.chechkedList.get(i)));
						fk_edzes_fajta.remove(fk_edzes_fajta.get(adapter.chechkedList.get(i)));
						szorzo.remove(szorzo.get(adapter.chechkedList.get(i)));
						edzes_fajta_mertekegyseg_fajta.remove(edzes_fajta_mertekegyseg_fajta.get(adapter.chechkedList.get(i)));
					}
					datasource_edzes.close();	
					adapter = new EdzesNapList(getActivity(), edzes_fajta_neve,edzes_fajta_mertekegyseg_fajta, idotartam, szorzo);
					adapter.notifyDataSetChanged();
					list.setAdapter(adapter);
			    }
			 })
			.setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { }
			});	    	
	    	builder.create();
	    	builder.show();
	    	break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	    return true;
	}

    @Override
	public void onResume() {
		//datasource_edzes.open();
		//datasource_fajta.open();
		super.onResume();
	}
	
	@Override
	public void onPause() {
		//datasource_edzes.close();
		//datasource_fajta.close();
	    super.onPause();
	}
}












