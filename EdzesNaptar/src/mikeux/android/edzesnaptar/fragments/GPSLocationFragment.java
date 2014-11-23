package mikeux.android.edzesnaptar.fragments;

import java.util.ArrayList;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;

/*
Google: android GPS tracking programming
http://www.codeproject.com/Articles/665527/A-GPS-Location-Plotting-Android-Application
http://www.javabeat.net/location-tracker-in-android-using-gps-positioning-and-sqlite/
http://www.androidhive.info/2012/07/android-gps-location-manager-tutorial/
http://www.androidsnippets.com/distance-between-two-gps-coordinates-in-meter
http://developer.android.com/reference/android/location/Location.html#distanceTo(android.location.Location)
http://blog.doityourselfandroid.com/2010/12/25/understanding-locationlistener-android/
 
 http://www.haakseth.com/?p=30
 http://mobiledevstories.wordpress.com/2014/02/27/osmdroid-mobile-atlas-creator-tutorial/
 */

public class GPSLocationFragment extends SherlockFragment {
	public static Context ctxt;
	private  EditText edit_msg;
	private Handler handler = new Handler();
	
	private Thread szal;
	private Boolean fut = false;
	public Location elozo_location;
	
	float[] results= new float[3];
	double distance;
	
  	private MapView         mMapView;
    private MapController   mMapController;
    
    private Button btn_gyorsulas;
    private Button btn_tavolsag;
    private Button btn_gpsaktivitas;
    private float ossz_tavalosag = 0.0f;
    private float akt_tavolsag = 0.0f;
    private float min_tavolsag = 1.0f;
    ArrayList<Float> sebessegek = new ArrayList<Float>();
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	  	this.ctxt = inflater.getContext();
	  	View rootView = inflater.inflate(R.layout.fragment_gps_location, container, false);

	  	/*mMapView = (MapView) rootView.findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPQUESTOSM);
        mMapView.setBuiltInZoomControls(true);
        mMapView.setClickable(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(16);
        mMapView.setUseDataConnection(true); 
        mMapView.setMultiTouchControls(false);
        mMapController.setCenter(new GeoPoint(47.953988, 21.717904));*/
	  	
	  	btn_gyorsulas = (Button) rootView.findViewById(R.id.btn_gyorsulas);
	  	btn_tavolsag = (Button) rootView.findViewById(R.id.btn_tavolsag);
	  	btn_gpsaktivitas = (Button) rootView.findViewById(R.id.btn_gpsaktivitas);
	  	edit_msg = (EditText) rootView.findViewById(R.id.edit_msg);

	  	if(u.GPS == null) {
	  		Log.e("Mikeux","onCreateView");
	  		
	  		u.GPS = new GPSTracker(ctxt);
	  		u.GPS.edit_msg = edit_msg;
	  		
			kiirSebesseg(0.0f);
			kiirTavolsag();
	  		
		  	TimerTask task = new TimerTask() {
			@Override
			public void run() {
			((Activity) ctxt).runOnUiThread(new Runnable() {
		  	     @Override
		  	     public void run() {
		  	    	//Log.e("MIkeux","Run");
		  	    	btn_gpsaktivitas.setText(u.GPS.canGetLocation ? "Aktív" : "Nem aktív");
					if(u.GPS.canGetLocation && u.GPS.location != null)  {	  	    		 
						if(elozo_location != null) {
							akt_tavolsag = elozo_location.distanceTo(u.GPS.location);
							Uzen("Távolság: "+akt_tavolsag);
							if(akt_tavolsag > min_tavolsag){
								ossz_tavalosag += akt_tavolsag;
								kiirSebesseg(calculateSpeed(elozo_location,u.GPS.location));
								kiirTavolsag();								
								elozo_location = u.GPS.location;
							} else {
								kiirSebesseg(0.0f);
							}		  	    			
							
						} else {
							elozo_location = u.GPS.location;
						}
					}
		  	    }
		  	});
			}
		  	};
			Timer timer = new Timer();
			timer.schedule(task, 1000, 2000);
	  	}
	    return rootView;
   }
	
	
	/* Kiírja az aktuális sebességet. */
	public void kiirSebesseg(float sebesseg) {
		sebessegek.add(sebesseg);
		btn_gyorsulas.setText(u.round(sebesseg*3.6, 2)+" km/h ("+getAtlagSebesseg()+")");
		
	}
	
	/* Kiírja az aktuális megtett távolságot. */
	public void kiirTavolsag() {
		if(this.ossz_tavalosag>1000.00) 
			btn_tavolsag.setText(u.round(this.ossz_tavalosag, 2)+" m");
		else 
			btn_tavolsag.setText(u.round(this.ossz_tavalosag/1000.00, 2)+" km");
	}
	
	public float getAtlagSebesseg() {
		float atlag = 0.0f;
		for(float s : sebessegek) atlag += s;
		return (float) u.round(atlag/sebessegek.size(),2);
	}
	
   /* Request updates at startup */
   @Override
   public void onResume() {
	   super.onResume();
	   Log.e("Mikeux", "onResume");
	   //if(!szal.isAlive() && GPS.canGetLocation) szal.start();
	   //locationManager.requestLocationUpdates(provider, 400, 1, this);
   }

   /* Remove the locationlistener updates when Activity is paused */
   @Override
   public void onPause() {
	   super.onPause();
	   Log.e("Mikeux", "onPause");
	   //Log.e("Miekux","GPS "+this.GPS == null ? "null" : "not null");
	   //if(szal.isAlive()) szal.stop();
	   //locationManager.removeUpdates(this);
   }
   
	public void Uzen(String msg){
		//String LogDate = String.format("%tY.%tm.%td %tT - ",most,most,most,most);
		if( msg != "" && msg != null) edit_msg.setText(String.format("%tT - ",new Date()) + msg + "\n" + edit_msg.getText().toString());
	}
	
	/*public static double calculateSpeed(Location old_location, Location new_location) {
		double distanceInMeters = calculateDistance(old_location.getLatitude(), old_location.getLongitude(),new_location.getLatitude(), new_location.getLongitude());
		long timeDelta = (new_location.getTime() - old_location.getTime())/1000;
		double speed = 0;
		if(timeDelta > 0){
			speed = (distanceInMeters/timeDelta);
		}
		Log.d("calculateSpeed","Distance: "+distanceInMeters+", TimeDelta: "+timeDelta+" seconds"+",speed: "+speed+" Accuracy: "+new_location.getAccuracy());
		return speed;
	}*/
	
	public static float calculateSpeed(Location old_location, Location new_location) {
		float distanceInMeters = old_location.distanceTo(new_location);
		long timeDelta = (new_location.getTime() - old_location.getTime())/1000;
		float speed = 0;
		if(timeDelta > 0){
			speed = (distanceInMeters/timeDelta);
		}
		Log.d("calculateSpeed2","Distance: "+distanceInMeters+", TimeDelta: "+timeDelta+" seconds"+",speed: "+speed+" Accuracy: "+new_location.getAccuracy());
		return speed;
	}
	
	/*public static double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
	    double dLat = Math.toRadians(lat2 - lat1);
	    double dLon = Math.toRadians(lng2 - lng1);
	    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
	            + Math.cos(Math.toRadians(lat1))
	            * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
	            * Math.sin(dLon / 2);
	    //double c = 2 * Math.asin(Math.sqrt(a));
	    //long distanceInMeters = Math.round(6371000 * c);
	    //return distanceInMeters;
	    return 2 * 6371000 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	}*/
}







