package mikeux.android.edzesnaptar.util;

import java.util.ArrayList;
import java.util.List;

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
	public final Activity context;
	public List<String>  elelmiszer_list;

	public ElelmiszerList(Activity context, List<String> elelmiszer_list) {
		super(context, R.layout.row_elelmiszer, elelmiszer_list);
		this.context = context;
		this.elelmiszer_list = elelmiszer_list;
		this.chechkedList = new ArrayList<Integer>();
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {    
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.row_elelmiszer, null, true);

		((TextView) rowView.findViewById(R.id.elemiszer_nev)).setText(this.elelmiszer_list.get(position));
		
		CheckBox _CheckBox = (CheckBox) rowView.findViewById(R.id.elemiszer_kijelol_checkbox);
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
