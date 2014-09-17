package mikeux.android.edzesnaptar.util;

import java.util.ArrayList;

import mikeux.android.edzesnaptar.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EdzesMenuList  extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> nev;
	private final ArrayList<Integer> imageId;
	
	public EdzesMenuList(Activity context,	ArrayList<String> nev, ArrayList<Integer> imageId) {
		super(context, R.layout.list_item_menu, nev);
		this.context = context;
		this.nev = nev;
		this.imageId = imageId;
	}
	
	@Override
	public View getView(final int position, View view, ViewGroup parent) {    
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_item_menu, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
		txtTitle.setText(nev.get(position));
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		imageView.setImageResource(imageId.get(position));

		return rowView;
	}
}
