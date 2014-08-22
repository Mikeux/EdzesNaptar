package mikeux.android.edzesnaptar.util;

import java.util.ArrayList;

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

public class EdzesFajtaList extends ArrayAdapter<String> {
	public ArrayList<Integer> chechkedList;
	private final Activity context;
	private final ArrayList<String> nev;
	private final ArrayList<Integer> imageId;

	public EdzesFajtaList(Activity context,	ArrayList<String> nev, ArrayList<Integer> imageId) {
		super(context, R.layout.list_edzes_fajta, nev);
		this.context = context;
		this.nev = nev;
		this.imageId = imageId;
		this.chechkedList = new ArrayList<Integer>();
	}
	@Override
	public View getView(final int position, View view, ViewGroup parent) {    
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_edzes_fajta, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		txtTitle.setText(nev.get(position));
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		imageView.setImageResource(imageId.get(position));

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
