package mikeux.android.edzesnaptar.util;

import java.util.ArrayList;

import mikeux.android.edzesnaptar.R;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class ElelmiszerList extends ArrayAdapter<String> 
{
	public ArrayList<Integer> chechkedList;
	private final Activity context;
	private String [] elelmiszer_arr;

	public ElelmiszerList(Activity context, String [] elelmiszer_ar) {
		super(context, R.layout.row_elelmiszer, elelmiszer_ar);
		this.context = context;
		this.elelmiszer_arr = elelmiszer_ar;
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {    
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.row_elelmiszer, null, true);

		//Filter filter = inflater.getFilter();
		//Log.e("Mikeux","Filter => "+ filter.toString());
		//((TextView) rowView.findViewById(R.id.elemiszer_nev)).setText(this.elelmiszer_arr[position]);
		
		CheckBox _CheckBox = (CheckBox) rowView.findViewById(R.id.elemiszer_kijelol_checkbox);
		
		_CheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				CheckBox checkbox = (CheckBox)arg0; 
			    boolean isChecked = checkbox.isChecked();
			    if(isChecked) Log.e("Mikeux","Checked => "+position);  //chechkedList.add(position); 
			    else  Log.e("Mikeux","Not Checked => "+position); //chechkedList.remove(position);
			}
			
		});
		return rowView;
	}

}
