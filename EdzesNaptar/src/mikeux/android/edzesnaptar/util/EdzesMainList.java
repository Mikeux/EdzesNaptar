package mikeux.android.edzesnaptar.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import mikeux.android.edzesnaptar.R;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class EdzesMainList extends ArrayAdapter<Calendar> {
	private final Activity context;
	private final ArrayList<Calendar> datum;
	private final ArrayList<String> nap;
	private final ArrayList<String> edzesekSzama;
	private	SimpleDateFormat format1 = new SimpleDateFormat("yyyy.MM.dd");
	
	public EdzesMainList(Activity context,	ArrayList<Calendar> datum, ArrayList<String> nap, ArrayList<String> edzesekSzama) {
		super(context, R.layout.row_edzes_main, datum);
		this.context = context;
		this.datum = datum;
		this.nap = nap;
		this.edzesekSzama = edzesekSzama;
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {    
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.row_edzes_main, null, true);

		TextView DatumTitle = (TextView) rowView.findViewById(R.id.datum);
		DatumTitle.setText(format1.format(datum.get(position).getTime()));
		
		TextView NapTitle = (TextView) rowView.findViewById(R.id.nap);
		NapTitle.setText(nap.get(position));
		
		TextView EdzesekSzama = (TextView) rowView.findViewById(R.id.edzesek_szama);
		EdzesekSzama.setText(edzesekSzama.get(position));
		
		return rowView;
	}
}
