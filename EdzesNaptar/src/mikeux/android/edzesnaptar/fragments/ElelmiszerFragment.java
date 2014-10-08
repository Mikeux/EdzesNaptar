package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;
import java.util.List;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.db_class.Elelmiszer;
import mikeux.android.edzesnaptar.db_class.ElelmiszerDataSource;
import mikeux.android.edzesnaptar.util.u;

import com.actionbarsherlock.app.SherlockFragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ElelmiszerFragment  extends SherlockFragment {
	public static Context ctxt;
	private ListView list;
	private EditText inputSearch;
    ArrayAdapter<String> adapter;
    List<Elelmiszer> elelmiszerek;
    ElelmiszerDataSource elelmiszer_datasource;
    String [] elelmiszer_arr;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setHasOptionsMenu(true);
	    setMenuVisibility(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.ctxt = inflater.getContext();
		View rootView = inflater.inflate(R.layout.fragment_elelmiszer, container, false);
		
		/*this.elelmiszer_datasource = new ElelmiszerDataSource(this.ctxt);
		this.elelmiszerek = elelmiszer_datasource.getAllElelmiszer();
		
		elelmiszer_arr = new String[elelmiszerek.size()];
		for(int i=0; i<elelmiszerek.size();i++) {
			elelmiszer_arr[i] = elelmiszerek.get(i).getNev();
		}*/
		
        String products[] = {"Dell Inspiron", "HTC One X", "HTC Wildfire S", "HTC Sense", "HTC Sensation XE",
                "iPhone 4S", "Samsung Galaxy Note 800",
                "Samsung Galaxy S3", "MacBook Air", "Mac Mini", "MacBook Pro"};
		
		adapter = new ArrayAdapter<String>(ctxt, R.layout.row_elelmiszer, R.id.elemiszer_nev, products);
		
		list=(ListView)rootView.findViewById(R.id.list); 
        list.setBackgroundColor(u.settings.getInt("hatterszin", -917505));
        list.setItemsCanFocus(true);
		list.setAdapter(adapter);  
		
		inputSearch = (EditText) rootView.findViewById(R.id.inputSearch);
		inputSearch.addTextChangedListener(new TextWatcher() {
		    @Override
		    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
		        adapter.getFilter().filter(cs);   
		    }		     
		    @Override
		    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) { }
		    @Override
			public void afterTextChanged(Editable arg0) { }
		});
		
	    return rootView;
    }
}
