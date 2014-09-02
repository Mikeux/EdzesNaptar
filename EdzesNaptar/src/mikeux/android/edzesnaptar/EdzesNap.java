package mikeux.android.edzesnaptar;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import mikeux.android.edzesnaptar.db_class.EdzesDataSource;
import mikeux.android.edzesnaptar.db_class.EdzesFajta;
import mikeux.android.edzesnaptar.db_class.EdzesFajtaDataSource;
import mikeux.android.edzesnaptar.util.EdzesNapList;

public class EdzesNap extends ListActivity {

	public static Context ctxt;
    private Dialog dialogWindow;
	private EdzesDataSource datasource_edzes;
    private EdzesFajtaDataSource datasource_fajta;
	private EdzesNapList adapter;
	private ListView list;
    List<EdzesFajta> edzesfajtak;

    private Spinner spinner;
    private ImageView Vissza_Nyil;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctxt = this;
        setContentView(R.layout.activity_edzes_nap);

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
