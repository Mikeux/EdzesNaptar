package mikeux.android.edzesnaptar;

import java.util.ArrayList;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.util.EdzesFajtaList;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
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
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctxt = this;
        setContentView(R.layout.activity_edzes_fajta);

        //final EditText newEdzesFajta = (EditText) findViewById(R.id.txtItem);
        //final Spinner mertekegyseg = (Spinner)findViewById(R.id.spinner_mertekegyseg);
        
        Button btn = (Button) findViewById(R.id.btnAdd);
        Button btnDel = (Button) findViewById(R.id.btnDel);

        //EdzesFajták beolvasása
        datasource = new EdzesFajtaDataSource(this);
	    datasource.open();
	    List<EdzesFajta> EFList = datasource.getAllEdzesFajta();
	    for(EdzesFajta EF : EFList) {
			ids.add(EF.id);
	    	nevek.add(EF.nev);
	    	kepek.add((EF.mertekegyseg == Mertekegyseg.Idõ_ms)?R.drawable.ora_96x96:R.drawable.coutner_96x96);
	    }
        
        adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek);        
        list=(ListView)findViewById(R.id.list); 
        list.setItemsCanFocus(true);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Log.e("Mikeux","onItemClick");
				Toast.makeText(EdzesFajtaActivity.this, "A '" +nevek.get(+ position)+"' elemre kattintottál!", Toast.LENGTH_SHORT).show();
			}
		});
        
        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*adapter = null;
                String nev = newEdzesFajta.getText().toString();
                Mertekegyseg me = (mertekegyseg.getSelectedItem().toString() == "Idõ")?Mertekegyseg.Idõ_ms:Mertekegyseg.GyakorlatSzám;
            	nevek.add(nev);
            	kepek.add((newEdzesFajta.getText().toString() == "Idõ")?R.drawable.ora_96x96:R.drawable.coutner_96x96);
            	adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek);  
            	datasource.createEdzes(nev,me);
                adapter.notifyDataSetChanged();*/
                //list.setAdapter(adapter);
            }
        };
        btn.setOnClickListener(listener);
        
        OnClickListener listenerDel = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	adapter = null;
            	SparseBooleanArray checkedItemPositions = list.getCheckedItemPositions();
            	int itemCount = checkedItemPositions.size();
            	Log.e("Mikeux","checkedItemPositions size => "+itemCount);
            	for(int i=itemCount-1; i >= 0; i--){
            		Log.e("Mikeux","checkedItemPositions get(i) => "+checkedItemPositions.get(i));
                    if(checkedItemPositions.get(i)){
                    	ids.remove(checkedItemPositions.get(i));
                    	nevek.remove(checkedItemPositions.get(i));
                    	kepek.remove(checkedItemPositions.get(i));
                        //adapter.remove(adapter.getItem(i));
                    }
                }                
                checkedItemPositions.clear();
                adapter = new EdzesFajtaList(EdzesFajtaActivity.this, nevek, kepek);                
                adapter.notifyDataSetChanged();
                //list.setAdapter(adapter);
            }
        };        
        btnDel.setOnClickListener(listenerDel);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edzesfajta, menu);
		return true;
	}    
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.menu_uj_edzes_fajta:
	    	//LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
	    	View popupView = getLayoutInflater().inflate(R.layout.popup_uj_edzesfajta, null,true);  
	        
	        //Spinner
	        Spinner spinner = (Spinner) popupView.findViewById(R.id.spinner_mertekegyseg);       
			ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,R.array.mertekegysegek, android.R.layout.simple_spinner_item);
	        //final ArrayAdapter<String> spinner_adapter  = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,R.array.mertekegysegek);
	        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(spinner_adapter);
			
	        //Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
	        //popupWindow.showAtLocation(list, Gravity.CENTER, 0, 10);
			final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
			popupWindow.showAsDropDown(list);
	    	break;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	    return true;
	}	
    
}
