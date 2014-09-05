package mikeux.android.edzesnaptar.util;

import java.util.ArrayList;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.db_class.EdzesFajta.Mertekegyseg;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class EdzesNapList extends ArrayAdapter<String> {
	public ArrayList<Integer> chechkedList;
	private final Activity context;
	private final ArrayList<String> edzesNev;
	private final ArrayList<Mertekegyseg> edzes_mertekegyseg;
	private final ArrayList<Integer> idotartam;
	private final ArrayList<Integer> szorzo;
	
	
	public EdzesNapList(Activity context,	ArrayList<String> edzesNev,ArrayList<Mertekegyseg> edzes_mertekegyseg ,ArrayList<Integer> idotartam,ArrayList<Integer> szorzo) {
		super(context, R.layout.row_edzes_fajta, edzesNev);
		this.context = context;
		this.edzesNev = edzesNev;
		this.idotartam = idotartam;
		this.edzes_mertekegyseg = edzes_mertekegyseg;
		this.chechkedList = new ArrayList<Integer>();
		this.szorzo = szorzo;
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {    
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.row_edzes_nap, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		txtTitle.setText(edzesNev.get(position));
		
		TextView ido = (TextView) rowView.findViewById(R.id.ido);
		ido.setText(idotartam.get(position).toString() + (edzes_mertekegyseg.get(position) == Mertekegyseg.Idő_ms ? " ms" : " db") +
				" X "+szorzo.get(position));
		
		//ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		//imageView.setImageResource(imageId.get(position));

		CheckBox _CheckBox = (CheckBox) rowView.findViewById(R.id.kijelol_checkbox);
		
		_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				CheckBox checkbox = (CheckBox)arg0; 
			    boolean isChecked = checkbox.isChecked();
			    if(isChecked) chechkedList.add(position); //Log.e("Mikeux","Checked => "+position);
			    else chechkedList.remove(position); //Log.e("Mikeux","Not Checked => "+position);
			}
			
		});
		return rowView;
	}
}
