package mikeux.android.edzesnaptar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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

	private ArrayList<Long> ids = new ArrayList<Long>();
	private ArrayList<Long> fk_edzes_fajta = new ArrayList<Long>();
	private ArrayList<String> edzes_fajta_neve = new ArrayList<String>();
	private ArrayList<Mertekegyseg> edzes_fajta_mertekegyseg = new ArrayList<Mertekegyseg>();
	private ArrayList<String> datum = new ArrayList<String>();
	private ArrayList<Integer> idotartam = new ArrayList<Integer>();
	
    private Spinner spinner;
    private ImageView Vissza_Nyil;
	private EditText newEdzes;	
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
	    	datum.add(format1.format(E.datum));
	    	idotartam.add(E.idotartam);
	    }
	    
        adapter = new EdzesNapList(EdzesNapActivity.this, edzes_fajta_neve, idotartam);        
        //list=(ListView)findViewById(R.id.list);
        list=getListView();
        list.setItemsCanFocus(true);
        list.setAdapter(adapter);
        
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
	    	//newEdzesFajta.setText("");
	    	//btn_uj_fajta_ad.setText("Hozzáad");	   
	    	dialogWindow.show();
	    	break;
	    case R.id.menu_edzes_fajta_torles:
			/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Biztosan törölni akarod a kijelölt ("+this.adapter.chechkedList.size()+") edzés fajtákat?")
			.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Collections.sort(adapter.chechkedList);
					for(int i = adapter.chechkedList.size()-1; i>=0; i--){
						//DB Törlés
						datasource.deleteEdzesFajta(ids.get(adapter.chechkedList.get(i)));
						ids.remove(ids.get(adapter.chechkedList.get(i)));
                    	nevek.remove(nevek.get(adapter.chechkedList.get(i)));
                    	kepek.remove(kepek.get(adapter.chechkedList.get(i)));
					}
					adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek);
					adapter.notifyDataSetChanged();
					list.setAdapter(adapter);
			    }
			 })
			.setNegativeButton("Mégse", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) { }
			});	    	
	    	builder.create();
	    	builder.show();*/
	    	break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	    return true;
	}
}
