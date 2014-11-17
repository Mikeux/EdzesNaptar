package mikeux.android.edzesnaptar.fragments;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.ResponsiveUIActivity;
import mikeux.android.edzesnaptar.db_class.ElelmiszerDataSource;
import mikeux.android.edzesnaptar.db_class.StatisztikaDataSource;
import mikeux.android.edzesnaptar.util.GPSTracker;
import mikeux.android.edzesnaptar.util.StatisztikaList;
import mikeux.android.edzesnaptar.util.u;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

/*
Google: android GPS tracking programming
http://www.codeproject.com/Articles/665527/A-GPS-Location-Plotting-Android-Application
http://www.javabeat.net/location-tracker-in-android-using-gps-positioning-and-sqlite/
http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
http://www.androidsnippets.com/distance-between-two-gps-coordinates-in-meter
http://developer.android.com/reference/android/location/Location.html#distanceTo(android.location.Location)
http://blog.doityourselfandroid.com/2010/12/25/understanding-locationlistener-android/
 */

public class GPSLocationFragment extends SherlockFragment {
	public static Context ctxt;
	private  EditText edit_msg;
	private Handler handler = new Handler();
	public GPSTracker GPS;
	private Thread szal;
	private Boolean fut = false;
	public Location elozo_location;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	View rootView = inflater.inflate(R.layout.fragment_gps_location, container, false);
        
	  	edit_msg = (EditText) rootView.findViewById(R.id.edit_msg);
	  	//edit_msg.setEnabled(false);
	  	
	  	//TextView tv = (TextView) rootView.findViewById(R.id.textView_lat);
	  	//listAdapter = (StatisztikaList) rootView.findViewById(R.id.expandableList_stat);
	  	//listAdapter.setBackgroundColor(u.settings.getInt("hatterszin", -917505));	
	  	//LocationManager mylocman = (LocationManager)this.ctxt.getSystemService(Context.LOCATION_SERVICE);
		//LocationListener myloclist = new MylocListener();
		//mylocman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,myloclist);
		
	  	GPS = new GPSTracker(ctxt);
	 	
	  	TimerTask task = new TimerTask() {
			@Override
			public void run() {
				((Activity) ctxt).runOnUiThread(new Runnable() {
			  	     @Override
			  	     public void run() {
			  	    	 if(GPS.canGetLocation && GPS.location != null)  {
							elozo_location = GPS.location;
							Uzen(GPS.location.getLatitude()+"/"+GPS.location.getLongitude() +" => "+
									GPS.location.distanceTo(elozo_location) + "méter => "+GPS.location.getSpeed()+"m/s");
						}
			  	    }
			  	});
			}
	  	};
		Timer timer = new Timer();
		timer.schedule(task, 2000, 4000);

	  		  	
	    return rootView;
   }
	
   /* Request updates at startup */
   @Override
   public void onResume() {
	   super.onResume();
	   //if(!szal.isAlive() && GPS.canGetLocation) szal.start();
	   //locationManager.requestLocationUpdates(provider, 400, 1, this);
   }

   /* Remove the locationlistener updates when Activity is paused */
   @Override
   public void onPause() {
	   super.onPause();
	   //if(szal.isAlive()) szal.stop();
	   //locationManager.removeUpdates(this);
   }
   
	public void Uzen(String msg){
		//Log.e("Mikeux",edit_msg.getText().toString());
		//Log.e("Mikeux",String.format("%tT - ",new Date()) + msg + "\n" + edit_msg.getText());
		//String LogDate = String.format("%tY.%tm.%td %tT - ",most,most,most,most);
		if( msg != "" && msg != null) edit_msg.setText(String.format("%tT - ",new Date()) + msg + "\n" + edit_msg.getText().toString());
	}
}







