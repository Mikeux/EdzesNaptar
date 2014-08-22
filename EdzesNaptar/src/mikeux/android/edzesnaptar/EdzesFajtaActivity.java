package mikeux.android.edzesnaptar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.util.EdzesFajtaList;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class EdzesFajtaActivity extends Activity {
	public static Context ctxt;
	
	private EdzesFajtaDataSource datasource;
	private EdzesFajtaList adapter;
	private ListView list;
	private ArrayList<Long> ids = new ArrayList<Long>();
	private ArrayList<String> nevek = new ArrayList<String>();
	private ArrayList<Integer> kepek = new ArrayList<Integer>();
	private Dialog dialogWindow;
	//private RelativeLayout mainLayout;
	private boolean PopupFelnyilt = false;
	private Spinner spinner;
	private EditText newEdzesFajta;	
	private Button btn_uj_fajta_ad;
	private Button btn_uj_fajta_megse;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctxt = this;
        setContentView(R.layout.activity_edzes_fajta);
        //mainLayout = new RelativeLayout(this);
        
        dialogWindow = new Dialog(ctxt);
        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(R.layout.popup_uj_edzesfajta);
        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogWindow.setCancelable(false);
        
        //Spinner
        spinner = (Spinner) dialogWindow.findViewById(R.id.spinner_mertekegyseg);       
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,R.array.mertekegysegek, android.R.layout.simple_spinner_item);
        //final ArrayAdapter<String> spinner_adapter  = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,R.array.mertekegysegek);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinner_adapter);
		
        btn_uj_fajta_ad = (Button) dialogWindow.findViewById(R.id.button_uj_fajta_ad);
        btn_uj_fajta_megse = (Button) dialogWindow.findViewById(R.id.button_uj_fajta_megse);
        
        newEdzesFajta = (EditText) dialogWindow.findViewById(R.id.txtItem);
        
        //EdzesFajt�k beolvas�sa
        datasource = new EdzesFajtaDataSource(this);
	    datasource.open();
	    List<EdzesFajta> EFList = datasource.getAllEdzesFajta();
	    for(EdzesFajta EF : EFList) {
	    	ids.add(EF.id);
	    	nevek.add(EF.nev);
	    	kepek.add((EF.mertekegyseg == Mertekegyseg.Id�_ms)?R.drawable.ora_96x96:R.drawable.coutner_96x96);
	    }
        
        adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek);        
        list=(ListView)findViewById(R.id.list); 
        list.setItemsCanFocus(true);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Log.e("Mikeux","onItemClick");
				//Toast.makeText(EdzesFajtaActivity.this, "A '" +nevek.get(+ position)+"' elemre kattintott�l!", Toast.LENGTH_SHORT).show();
		    	PopupFelnyilt = true;
		    	newEdzesFajta.setText(nevek.get(+ position));
		    	spinner.setSelection((kepek.get(position) == R.drawable.ora_96x96) ? 0 : 1);
		    	btn_uj_fajta_ad.setText("M�dos�t");
		    	dialogWindow.show();
			}
		});
        
        OnClickListener listener_uj_fajta_ad = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	String nev = newEdzesFajta.getText().toString();
            	Mertekegyseg me = (spinner.getSelectedItem().toString().equals("Id�"))?Mertekegyseg.Id�_ms:Mertekegyseg.GyakorlatSz�m;
            	EdzesFajta EF = datasource.createEdzesFajta(nev,me);
            	
            	nevek.add(nev);
            	kepek.add((me == Mertekegyseg.Id�_ms)?R.drawable.ora_96x96:R.drawable.coutner_96x96);
            	ids.add(EF.getId());
            	
            	adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek); 
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
                
                dialogWindow.cancel();
            	PopupFelnyilt = false;                
            }
        };
        btn_uj_fajta_ad.setOnClickListener(listener_uj_fajta_ad);
        
        OnClickListener listener_uj_fajta_megse = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	dialogWindow.cancel();
            	Toast.makeText(EdzesFajtaActivity.this,"M�gse", Toast.LENGTH_SHORT);
            	PopupFelnyilt = false;
            }
        };
        btn_uj_fajta_megse.setOnClickListener(listener_uj_fajta_megse);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edzesfajta, menu);
		return true;
	}    
	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
		menu.getItem(1).setEnabled(this.adapter.chechkedList.size() > 0);
	    return !PopupFelnyilt;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_uj_edzes_fajta:
	    	PopupFelnyilt = true;
	    	newEdzesFajta.setText("");
	    	btn_uj_fajta_ad.setText("Hozz�ad");	   
	    	dialogWindow.show();
	    	break;
	    case R.id.menu_edzes_fajta_torles:
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Biztosan t�r�lni akarod a kijel�lt ("+this.adapter.chechkedList.size()+") edz�s fajt�kat?")
			.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Collections.sort(adapter.chechkedList);
					for(int i = adapter.chechkedList.size()-1; i>=0; i--){
						//DB T�rl�s 
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
			.setNegativeButton("M�gse", new DialogInterface.OnClickListener() {
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
    
}
