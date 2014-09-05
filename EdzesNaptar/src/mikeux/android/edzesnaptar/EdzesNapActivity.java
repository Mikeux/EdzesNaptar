package mikeux.android.edzesnaptar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.Edzes;
import mikeux.android.edzesnaptar.db_class.EdzesDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.util.EdzesFajtaList;
import mikeux.android.edzesnaptar.util.EdzesNapList;

public class EdzesNapActivity extends ListActivity {

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
	private ArrayList<String> datum = new ArrayList<String>();
	private ArrayList<Integer> idotartam = new ArrayList<Integer>();
	private ArrayList<Integer> szorzo = new ArrayList<Integer>();
	
    private Spinner spinner;
    private ImageView Vissza_Nyil;
	private EditText mennyiseg;	
	private TextView mennyiseg_tv;
	private EditText szorzo_et;	
	private Button btn_uj_edzes_ad;
	private Button btn_uj_edzes_megse;
    
    private String Datum;
    private boolean PopupFelnyilt = false;
    
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctxt = this;
        setContentView(R.layout.activity_edzes_nap);

        Bundle b = getIntent().getExtras();
        this.Datum = b.getString("datum");        
        
        TextView datum_tv = (TextView)findViewById(R.id.datum_textview);
        datum_tv.setText(this.Datum);        
        
        Vissza_Nyil = (ImageView)findViewById(R.id.vissza_nyil);
        Vissza_Nyil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        dialogWindow = new Dialog(ctxt);
        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(R.layout.popup_uj_edzes);
        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogWindow.setCancelable(false);

        datasource_edzes = new EdzesDataSource(this);
        datasource_edzes.open();

        datasource_fajta = new EdzesFajtaDataSource(this);
        datasource_fajta.open();
        edzesfajtak = datasource_fajta.getAllEdzesFajta();

        List<String> edzesfajtaneve = new ArrayList<String>();
        for(EdzesFajta ef:edzesfajtak) {
            edzesfajtaneve.add(ef.nev);
        }
        spinner = (Spinner) dialogWindow.findViewById(R.id.spinner_edzesfajta);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
        	@Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        		String CurrentItem = spinner.getItemAtPosition(position).toString();
        		//Toast.makeText(EdzesNapActivity.this, spinner.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
        		for(EdzesFajta ef : edzesfajtak){
        			if(ef.nev.equals(CurrentItem)){
        				if(ef.mertekegyseg == Mertekegyseg.Idő_ms) mennyiseg_tv.setText("ms");
        				else mennyiseg_tv.setText("db");
        				break;
        			}
        		}
            }
        	@Override
            public void onNothingSelected(AdapterView<?> parentView) { }
        });
        
        
        mennyiseg = (EditText) dialogWindow.findViewById(R.id.mennyiseg);
        mennyiseg_tv = (TextView) dialogWindow.findViewById(R.id.mertekegyseg_tv);
        
        szorzo_et = (EditText) dialogWindow.findViewById(R.id.szorzo);
        
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, edzesfajtaneve);
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
	    			break;
	    		}
	    	}
	    	szorzo.add(E.szorzo);
	    	datum.add(format1.format(E.datum));
	    	idotartam.add(E.idotartam);
	    }
	    
        adapter = new EdzesNapList(EdzesNapActivity.this, edzes_fajta_neve,edzes_fajta_mertekegyseg, idotartam, szorzo);        
        //list=(ListView)findViewById(R.id.list);
        list=getListView();
        list.setItemsCanFocus(true);
        list.setAdapter(adapter);
        
        
        OnClickListener listener_uj_ad = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	//Log.e("Mikeux","Hozzáad => "+newEdzesFajta.getText());
            	
            	if(btn_uj_edzes_ad.getText().equals("Hozzáad")) { //Insert
            		//ids,fk_edzes_fajta,edzes_fajta_neve,edzes_fajta_mertekegyseg,datum,idotartam  
            		String _edzes_fajta_neve = spinner.getSelectedItem().toString();
            		Integer me = Integer.parseInt(mennyiseg.getText().toString());
            		Integer _szorzo = Integer.parseInt(szorzo_et.getText().toString());
            		Long _fk_edzes_fajta = (long) 0;
            		Mertekegyseg _edzes_fajta_mertekegyseg = Mertekegyseg.Idő_ms;
            		for(EdzesFajta ef: edzesfajtak){
        	    		if(ef.nev == _edzes_fajta_neve) {
        	    			_fk_edzes_fajta = ef.id;
        	    			_edzes_fajta_mertekegyseg = ef.mertekegyseg;
        	    			break;
        	    		}
        	    	}          
            		
            		Calendar cal = Calendar.getInstance();
            	    try {
						cal.setTime(format1.parse(Datum));
					} catch (ParseException e) {
						cal.setTime(new Date());
					}
            		
            	    Long Id = datasource_edzes.createEdzes(_fk_edzes_fajta,cal,me,_szorzo);
            	    //ids,fk_edzes_fajta,edzes_fajta_neve,edzes_fajta_mertekegyseg,datum,idotartam
            	    edzes_fajta_neve.add(0, _edzes_fajta_neve);
            	    idotartam.add(0, me);
            	    ids.add(0, Id);
            	    fk_edzes_fajta.add(0, _fk_edzes_fajta);
            	    edzes_fajta_mertekegyseg.add(0, _edzes_fajta_mertekegyseg);
            	    datum.add(0, Datum);
            	    szorzo.add(0,_szorzo);
            	     
            	    adapter = new EdzesNapList(EdzesNapActivity.this, edzes_fajta_neve,edzes_fajta_mertekegyseg, idotartam, szorzo);
	                adapter.notifyDataSetChanged();
	                list.setAdapter(adapter);
            	} else { //Update
            		//Log.e("Mikeux","Selected item => "+ModositPosition);
            		/*String nev = newEdzesFajta.getText().toString();
	            	Mertekegyseg me = (spinner.getSelectedItem().toString().equals("Idő"))?Mertekegyseg.Idő_ms:Mertekegyseg.GyakorlatSzám;
	            	if(datasource.updateEdzesFajta(ids.get(ModositPosition),nev,me) > 0) {
		            	nevek.set(ModositPosition, nev);
		            	kepek.set(ModositPosition, (me == Mertekegyseg.Idő_ms)?R.drawable.ora_96x96:R.drawable.coutner_96x96);
		            	
		            	adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek); 
		                adapter.notifyDataSetChanged();
		                list.setAdapter(adapter);
		                Log.i("Mikeux","Sikeres módosítás!");
	            	} else {
	            		Log.i("Mikeux","Sikertelen módosítás!");
	            	}*/
            	}
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
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edzesfajta, menu);
		return true;
	}    
	
	/*@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		menu.getItem(1).setEnabled(this.adapter.chechkedList.size() > 0);
	    return !PopupFelnyilt;
	}*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_uj_edzes_fajta:
	    	PopupFelnyilt = true;
	    	mennyiseg.setText("");
	    	szorzo_et.setText("1");
	    	btn_uj_edzes_ad.setText("Hozzáad");	   
	    	dialogWindow.show();
	    	break;
	    case R.id.menu_edzes_fajta_torles:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Biztosan törölni akarod a kijelölt ("+this.adapter.chechkedList.size()+") edzéseket?")
			.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Collections.sort(adapter.chechkedList);
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
					}
            	    
					adapter = new EdzesNapList(EdzesNapActivity.this, edzes_fajta_neve,edzes_fajta_mertekegyseg, idotartam, szorzo);
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
	protected void onResume() {
		//datasource_edzes.open();
		//datasource_fajta.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		//datasource_edzes.close();
		//datasource_fajta.close();
	    super.onPause();
	}
	
}
