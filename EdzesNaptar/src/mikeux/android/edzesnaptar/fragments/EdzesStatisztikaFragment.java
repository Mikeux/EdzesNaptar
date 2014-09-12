package mikeux.android.edzesnaptar.fragments;

import mikeux.android.edzesnaptar.R;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockFragment;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewStyle.GridStyle;
import com.jjoe64.graphview.LineGraphView;

public class EdzesStatisztikaFragment extends SherlockFragment  {
	public static Context ctxt;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setMenuVisibility(true);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	View rootView = inflater.inflate(R.layout.fragment_edzes_statisztika, container, false);
	  	
	  	// draw sin curve
	  	int num = 150;
	  	GraphViewData[] data = new GraphViewData[num];
	  	double v=0;
	  	for (int i=0; i<num; i++) {
	  	  v += 0.2;
	  	  data[i] = new GraphViewData(i, Math.sin(v));
	  	}
	  	GraphView graphView = new LineGraphView(
	  	    this.ctxt,
	  	    "GraphViewDemo"
	  	);
	  	// add data
	  	graphView.addSeries(new GraphViewSeries(data));
	  	// set view port, start=2, size=40
	  	graphView.setViewPort(2, 40);
	  	graphView.setScrollable(true);	  	
	  	
	  	// optional - activate scaling / zooming
	  	graphView.setScalable(true);
	  	
	  	graphView.getGraphViewStyle().setGridStyle(GridStyle.HORIZONTAL);
	  	
	  	LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layout);
	  	layout.addView(graphView);
	  	
	  	return rootView;
    }
}
