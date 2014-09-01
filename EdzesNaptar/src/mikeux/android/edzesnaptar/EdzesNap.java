package mikeux.android.edzesnaptar;

import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import mikeux.android.edzesnaptar.util.EdzesFajtaList;
import mikeux.android.edzesnaptar.util.EdzesNapList;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class EdzesNap extends Activity {
	public static Context ctxt;
	private EdzesDataSource datasource;
	private EdzesNapList adapter;
	private ListView list;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctxt = this;
        setContentView(R.layout.activity_edzes_nap);
        
        datasource = new EdzesDataSource(this);
        datasource.open();
        
        /*List<EdzesFajta> EFList = datasource.getAllEdzesFajta();
	    for(EdzesFajta EF : EFList) {
	    	ids.add(EF.id);
	    	nevek.add(EF.nev);
	    	kepek.add((EF.mertekegyseg == Mertekegyseg.Idő_ms)?R.drawable.ora_96x96:R.drawable.coutner_96x96);
	    }
	    
        adapter = new EdzesNapList(EdzesNap.this, nevek, kepek);        
        list=(ListView)findViewById(R.id.list); 
        list.setItemsCanFocus(true);
        list.setAdapter(adapter); */       
        
	}
	
}
