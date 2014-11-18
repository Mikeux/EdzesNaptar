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
	final float[] results= new float[3];
    
    
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
			  	    	 if(GPS.canGetLocation && GPS.location != null && GPS.location != elozo_location)  {
			  	    		Location.distanceBetween(elozo_location.getLatitude(), elozo_location.getLongitude(), 
			  	    				GPS.location.getLatitude(), GPS.location.getLongitude(), results);
			  	    		
			  	    		results[1] = calculateDistance(elozo_location.getLatitude(), elozo_location.getLongitude(), 
			  	    				GPS.location.getLatitude(), GPS.location.getLongitude());
			  	    		
			  	    		Uzen("Megtett út: "+elozo_location.distanceTo(GPS.location) + "/"+ results[0] +"/"+results[1]+" méter\n"+
			  	    			"Sebesség: "+GPS.location.getSpeed()+"/"+calculateSpeed(elozo_location,GPS.location)+" m/s");
							/*Uzen(GPS.location.getLatitude()+"/"+GPS.location.getLongitude() +" => "+
									elozo_location.distanceTo(GPS.location) + "méter => "+GPS.location.getSpeed()+"m/s");*/
						
			  	    		elozo_location = GPS.location;
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
		//String LogDate = String.format("%tY.%tm.%td %tT - ",most,most,most,most);
		if( msg != "" && msg != null) edit_msg.setText(String.format("%tT - ",new Date()) + msg + "\n" + edit_msg.getText().toString());
	}
	
	public static long calculateSpeed(Location old_location, Location new_location) {
		long distanceInMeters = calculateDistance(old_location.getLatitude(), old_location.getLongitude(),new_location.getLatitude(), new_location.getLongitude());
		long timeDelta = (new_location.getTime() - old_location.getTime())/1000;
		long speed = 0;
		if(timeDelta > 0){
			speed = (distanceInMeters/timeDelta);
		}
		Log.d("calculateSpeed","Distance: "+distanceInMeters+", TimeDelta: "+timeDelta+" seconds"+",speed: "+speed+" Accuracy: "+new_location.getAccuracy());
		return speed;
	}
	
	public static long calculateDistance(double lat1, double lng1, double lat2, double lng2) {
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lng2 - lng1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	            + Math.cos(Math.toRadians(lat1))
	            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
	            * Math.sin(dLon / 2);
	    double c = 2 * Math.asin(Math.sqrt(a));
	    long distanceInMeters = Math.round(6371000 * c);
	    //return 2 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    return distanceInMeters;
	}	
}







