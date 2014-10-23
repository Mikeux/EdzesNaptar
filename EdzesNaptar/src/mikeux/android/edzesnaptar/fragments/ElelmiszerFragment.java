package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.db_class.Elelmiszer;
import mikeux.android.edzesnaptar.db_class.ElelmiszerDataSource;
import mikeux.android.edzesnaptar.util.EdzesFajtaList;
import mikeux.android.edzesnaptar.util.ElelmiszerList;
import mikeux.android.edzesnaptar.util.u;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ElelmiszerFragment  extends SherlockFragment {
	private Dialog dialogWindow;
	public static Context ctxt;
	private ListView list;
	private EditText inputSearch;
    ElelmiszerList adapter;
    //List<Elelmiszer> elelmiszerek;
    ElelmiszerDataSource elelmiszer_datasource;
    String [] elelmiszer_arr;
    List<String> elelmiszer_list;
    private int ModositPosition;
    private boolean PopupFelnyilt;
    private CheckBox elemiszer_kijelol_checkbox;
    //Dialog
    private Spinner spinner_me;
    private EditText elelmiszer_nev_edittext;
    private EditText egy_adag_edittext;
    private EditText feherje_edittext;
    private EditText zsir_edittext;
    private EditText szenhidrat_edittext;
    private EditText kaloria_edittext;
    private Button btn_uj_elelmiszer_ad;
    private Button btn_uj_elelmiszer_megse;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	    setMenuVisibility(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ctxt = inflater.getContext();
		View rootView = inflater.inflate(R.layout.fragment_elelmiszer, container, false);
		
		//Dialog
    	dialogWindow = new Dialog(ctxt);
        dialogWindow.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogWindow.setContentView(R.layout.popup_uj_elelmiszer);
        dialogWindow.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //dialogWindow.setCancelable(false);
		
        spinner_me = (Spinner) dialogWindow.findViewById(R.id.spinner_me);       
		ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(ctxt,R.array.elelmiszer_me, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_me.setAdapter(spinner_adapter);
		
		elelmiszer_nev_edittext = (EditText) dialogWindow.findViewById(R.id.elelmiszer_nev);     
	    egy_adag_edittext = (EditText) dialogWindow.findViewById(R.id.edittext_egy_adag);
	    feherje_edittext = (EditText) dialogWindow.findViewById(R.id.editText_feherje);
	    zsir_edittext = (EditText) dialogWindow.findViewById(R.id.editText_zsir);
	    szenhidrat_edittext = (EditText) dialogWindow.findViewById(R.id.editText_szenhidrat);
	    kaloria_edittext = (EditText) dialogWindow.findViewById(R.id.editText_kaloria);
	    
        btn_uj_elelmiszer_ad = (Button) dialogWindow.findViewById(R.id.button_uj_fajta_ad);
        btn_uj_elelmiszer_megse = (Button) dialogWindow.findViewById(R.id.button_uj_fajta_megse);
        
        OnClickListener listener_uj_fajta_megse = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	dialogWindow.cancel();
            	PopupFelnyilt = false;
            }
        };
        btn_uj_elelmiszer_megse.setOnClickListener(listener_uj_fajta_megse);
        
		this.elelmiszer_datasource = new ElelmiszerDataSource(ctxt);
		//this.elelmiszerek = elelmiszer_datasource.getAllElelmiszer();
		
		/*elelmiszer_arr = new String[u.elelmiszerek.size()];
		for(int i=0; i<u.elelmiszerek.size();i++) {
			elelmiszer_arr[i] = u.elelmiszerek.get(i).getNev();
		}
		adapter = new ArrayAdapter<String>(ctxt, R.layout.row_elelmiszer, R.id.elemiszer_nev, elelmiszer_arr);
		 */
		
		elelmiszer_list = new ArrayList<String>();
		for(int i=0; i<u.elelmiszerek.size();i++) {
			elelmiszer_list.add(u.elelmiszerek.get(i).getNev());
		}
		adapter = new ElelmiszerList(this.getActivity(),elelmiszer_list);
		
		list=(ListView)rootView.findViewById(R.id.list); 
        list.setBackgroundColor(u.settings.getInt("hatterszin", -917505));
        list.setItemsCanFocus(true);
		list.setAdapter(adapter);  
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {        	
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				//Log.e("Mikeux","onItemClick");
				//Toast.makeText(ctxt, "A '" +u.elelmiszerek.get(position).nev+"' elemre kattintottál!", Toast.LENGTH_SHORT).show();
				//Toast.makeText(ctxt, u.elelmiszerek.get(position).nev+"  "+adapter.getItem(position), Toast.LENGTH_SHORT).show();
				
				for(int i=0; i<u.elelmiszerek.size();i++){
					if(u.elelmiszerek.get(i).getNev().equals(adapter.getItem(position))) {
						position = i;
						break;
					}
				}
				
				ModositPosition = position;
				PopupFelnyilt = true;
				spinner_me.setSelection(u.elelmiszerek.get(position).getMe().equals("dl") ? 0 : 1);
			    elelmiszer_nev_edittext.setText(u.elelmiszerek.get(position).getNev());
			    egy_adag_edittext.setText(u.elelmiszerek.get(position).getMennyiseg()+"");
			    feherje_edittext.setText(u.elelmiszerek.get(position).getFeherje()+"");
			    zsir_edittext.setText(u.elelmiszerek.get(position).getZsir()+"");
			    szenhidrat_edittext.setText(u.elelmiszerek.get(position).getSzenhidrat()+"");
			    kaloria_edittext.setText(u.elelmiszerek.get(position).getKaloria()+"");				
		    	btn_uj_elelmiszer_ad.setText("Módosít");
		    	dialogWindow.show();
			}
		});
		
		inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		    	//Log.e("Mikeux","Indul");
		    	elelmiszer_list = new ArrayList<String>();
		    	if(cs.equals("")){		    		
		    		for(int i=0; i<u.elelmiszerek.size();i++) {
		    			elelmiszer_list.add(u.elelmiszerek.get(i).getNev());
		    		}
		        }
		        else {
		    		for(int i=0; i<u.elelmiszerek.size();i++) {
		    			if(u.elelmiszerek.get(i).getNev().contains(cs)) {
		    				elelmiszer_list.add(u.elelmiszerek.get(i).getNev());
		    			}
		    		}
		        }	
		    	adapter = new ElelmiszerList(getActivity(),elelmiszer_list);
		    	list.setAdapter(adapter);  
		    	//Log.e("Mikeux","Kész");
		    	//adapter.getFilter().filter(cs);   
		    }		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) { }
		    @Override
			public void afterTextChanged(Editable arg0) { }
		});
		
        
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
			spinner_me.setSelection(1);
		    elelmiszer_nev_edittext.setText("");
		    egy_adag_edittext.setText("0.00");
		    feherje_edittext.setText("0.00");
		    zsir_edittext.setText("0.00");
		    szenhidrat_edittext.setText("0.00");
		    kaloria_edittext.setText("0.00");				
	    	btn_uj_elelmiszer_ad.setText("Hozzáad");
	    	dialogWindow.show();
	    	break;
	    case R.id.menu_edzes_fajta_torles:
			AlertDialog.Builder builder = new AlertDialog.Builder(this.ctxt);
			builder.setMessage("Biztosan törölni akarod a kijelölt ("+this.adapter.chechkedList.size()+") edzés fajtákat?")
			.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					//elelmiszer_datasource.open();
					/*Collections.sort(adapter.chechkedList);
					int position = 0;
					for(int i = adapter.chechkedList.size()-1; i>=0; i--){
						//DB Törlés
						for(int i=0; i<u.elelmiszerek.size();i++){
							if(u.elelmiszerek.get(i).getNev().equals(adapter.getItem(adapter.chechkedList.get(i)))) {
								position = i;
								break;
							}
						}
						
						elelmiszer_datasource.deleteElelmiszer(u.elelmiszerek.get(position));
						datasource.deleteEdzesFajta(ids.get(adapter.chechkedList.get(i)));
						ids.remove(ids.get(adapter.chechkedList.get(i)));
                    	nevek.remove(nevek.get(adapter.chechkedList.get(i)));
                    	kepek.remove(kepek.get(adapter.chechkedList.get(i)));
					}
					//elelmiszer_datasource.close();
					adapter = new ElelmiszerList(getActivity(), nevek, kepek);
					adapter.notifyDataSetChanged();
					list.setAdapter(adapter);*/
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
}
