package mikeux.android.edzesnaptar.fragments;

import mikeux.android.edzesnaptar.R;
import mikeux.android.edzesnaptar.db_class.StatisztikaDataSource;
import mikeux.android.edzesnaptar.util.GPSTracker;
import mikeux.android.edzesnaptar.util.StatisztikaList;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	View rootView = inflater.inflate(R.layout.fragment_gps_location, container, false);
        
	  	//TextView tv = (TextView) rootView.findViewById(R.id.textView_lat);
	  	//listAdapter = (StatisztikaList) rootView.findViewById(R.id.expandableList_stat);
	  	//listAdapter.setBackgroundColor(u.settings.getInt("hatterszin", -917505));	
	  	//LocationManager mylocman = (LocationManager)this.ctxt.getSystemService(Context.LOCATION_SERVICE);
		//LocationListener myloclist = new MylocListener();
		//mylocman.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,myloclist);
		
	  	GPSTracker gps = new GPSTracker(ctxt);
        /*if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Toast.makeText(ctxt, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();    
        }else{
            //gps.showSettingsAlert();
        }*/
	  	
	    return rootView;
   }
	
   /* Request updates at startup */
   @Override
   public void onResume() {
	   super.onResume();
	   //locationManager.requestLocationUpdates(provider, 400, 1, this);
   }

   /* Remove the locationlistener updates when Activity is paused */
   @Override
   public void onPause() {
	   super.onPause();
	   //locationManager.removeUpdates(this);
   }
}







