package mikeux.android.edzesnaptar.util;

import java.util.List;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSTracker extends Service implements LocationListener {
	 
    private final Context mContext;
 
    // flag for GPS status
    boolean isGPSEnabled = false;
 
    // flag for network status
    boolean isNetworkEnabled = false;
 
    // flag for GPS status
    public boolean canGetLocation = false;
 
    public Location location; // location
    double latitude; // latitude
    double longitude; // longitude
 
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
 
    // Declaring a Location Manager
    protected LocationManager locationManager;
    
    public GPSTracker(Context context) {
        this.mContext = context;
        //getLocation();
        init();
        //getLatestLocation();
    }
    
    public void init() {
    	locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
    	
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    	
    	isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    	if (!isGPSEnabled && !isNetworkEnabled) {
        	this.showSettingsAlert();
        } else {
        	this.canGetLocation = true;    
        }
    }
    
    public Location getLatestLocation() {
        LocationManager manager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = manager.getBestProvider(criteria, true);
        Location bestLocation;
        if (provider != null)
          bestLocation = manager.getLastKnownLocation(provider);
        else 
          bestLocation = null;
        
        Location latestLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latestLocation = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        latestLocation = manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        //u.uzen(latestLocation.getAccuracy()+" "+latestLocation.getLatitude()+":"+latestLocation.getLongitude());
        return latestLocation;
      }     
 
    public Location getLocation() {
    	/*try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            
            // getting GPS status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            Log.e("Mikeux","isGPSEnabled=>"+(isGPSEnabled?"true":"false"));
            // getting network status
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            Log.e("Mikeux","isNetworkEnabled=>"+(isNetworkEnabled?"true":"false"));
            
            if (!isGPSEnabled && !isNetworkEnabled) {
            	//this.showSettingsAlert();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }
 
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    	/*LocationManager mgr = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
    	boolean network_enabled = mgr.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
    	if(network_enabled){
    		u.uzen("network_enabled");
    		Location location = mgr.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
    	}else{
    		u.uzen("network_disabled");
    	} */  
    	
    	Location location = null;
    	locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
    	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, this);
    	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, this);
    	
        /*if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        	u.uzen("GPS_PROVIDER");
        	locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        	location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        	
        }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
        	u.uzen("NETWORK_PROVIDER");
        	locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        	location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        	
        }else if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
        	u.uzen("PASSIVE_PROVIDER");
        	locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
        	location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        }
        
        if(location != null) 
        {
        	u.uzen("Location nem NULL");
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else {
        	u.uzen("Location NULL");
        }*/
        
    	/*Criteria criteria = new Criteria();
    	String  provider = locationManager.getBestProvider(criteria, true);
    	u.uzen("provider => "+provider);
    	
    	provider = locationManager.getBestProvider(criteria, false);
    	u.uzen("provider => "+provider);
    	//Location location = locationManager.getLastKnownLocation(provider);
    	*/
    	/*Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location != null) 
        {
        	u.uzen("Location nem NULL");
        	this.canGetLocation = true;
        	Log.e("Mikeux","Location nem NULL");
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else {
        	u.uzen("Location NULL");
        }*/
        
        return location;
    }
    
    private double[] getGPS() {
		 LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		 List<String> providers = lm.getProviders(true);
		
		 Location l = null;
		
		 for (int i=providers.size()-1; i>=0; i--) {
		  l = lm.getLastKnownLocation(providers.get(i));
		  if (l != null) break;
		 }
		
		 double[] gps = new double[2];
		 if (l != null) {
		  gps[0] = l.getLatitude();
		  gps[1] = l.getLongitude();
		 }
		
		 return gps;
	}
    
     
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }       
    }
     
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
         
        // return latitude
        return latitude;
    }
     
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
         
        // return longitude
        return longitude;
    }
     
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
     
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
      
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
  
        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
 
    @Override
    public void onLocationChanged(Location location) {
    	this.location = location;
    	Log.e("Mikeux","onLocationChanged ("+location.getLatitude()+"/"+location.getLongitude()+")");
    }
 
    @Override
    public void onProviderDisabled(String provider) {
    	isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    	if (!isGPSEnabled && !isNetworkEnabled) {
    		this.canGetLocation = false;
    		u.uzen("GPS követés kikapcsolva!");
    	}
    	Log.e("Mikeux","onProviderDisabled "+provider);
    }
 
    @Override
    public void onProviderEnabled(String provider) {
    	isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    	isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    	if (isGPSEnabled || isNetworkEnabled) {
    		this.canGetLocation = true;
    		u.uzen("GPS követés bekapcsolva!");
    	}
    	Log.e("Mikeux","onProviderEnabled "+provider);
    }
 
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    	Log.e("Mikeux","onStatusChanged ("+provider+"/"+status+")");
    }
 
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
    	
}
