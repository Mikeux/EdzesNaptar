package mikeux.android.edzesnaptar;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class EdzesService extends Service {
	
	@Override
	public void onCreate() {
	   /* NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    Notification notification = new Notification(R.drawable.kulacs, "Értesítés", System.currentTimeMillis());
	    Intent myIntent = new Intent(this , EdzesActivity.class);     
	    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, myIntent, 0);
	    notification.setLatestEventInfo(this, "Notify label", "Notify text", contentIntent);
	    mNM.notify(1, notification);*/
		
		Log.e("Mikeux","EdzesService - onCreate");
		
		NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    Notification notification = new Notification();
	    notification.tickerText = "Értesítés";
	    mNM.notify(0, notification);	
	    
	    /*Intent intent = new Intent(this, EdzesService.class);
	    PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
	    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	    alarmManager.cancel(sender);	    

	    this.stopSelf();*/
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//TODO do something useful
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		//TODO for communication return IBinder implementation
		return null;
	}

}
