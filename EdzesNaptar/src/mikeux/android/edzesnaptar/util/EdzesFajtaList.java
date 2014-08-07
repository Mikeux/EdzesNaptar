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

public class EdzesFajtaList extends ArrayAdapter<String> {
	private final Activity context;
	private final ArrayList<String> nev;
	private final ArrayList<Integer> imageId;

	public EdzesFajtaList(Activity context,	ArrayList<String> nev, ArrayList<Integer> imageId) {
		super(context, R.layout.list_edzes_fajta, nev);
		this.context = context;
		this.nev = nev;
		this.imageId = imageId;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_edzes_fajta, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(nev.get(position));
		imageView.setImageResource(imageId.get(position));
		return rowView;
	}
}
