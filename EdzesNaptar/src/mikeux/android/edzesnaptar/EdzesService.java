package mikeux.android.edzesnaptar;

import mikeux.android.edzesnaptar.fragments.EdzesFajtaFragment;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class EdzesService extends Service {
		
	@Override
	public void onCreate() {
		//Log.e("Mikeux","EdzesService - onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//TODO do something useful
		//Log.e("Mikeux","EdzesService - onStartCommand");
		//Intent resultIntent = new Intent(this, EdzesFajtaFragment.class);
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,new Intent(),PendingIntent.FLAG_CANCEL_CURRENT);
		
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
		        .setContentTitle("Edzés Naptár")
		        .setContentText("Edzeni kell lustaság!")
		        .setContentIntent(resultPendingIntent)
		        //.addAction(R.drawable.icon, "Call", pIntent)
		        .setAutoCancel(true);
		
		NotificationManager manager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
		manager.notify(0, mBuilder.build());
		
	    Intent intent2 = new Intent(this, EdzesService.class);
	    PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent2, 0);
	    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
	    alarmManager.cancel(sender);	    
		
	    this.stopSelf();
		
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		//TODO for communication return IBinder implementation
		//Log.e("Mikeux","EdzesService - onBind");
		return null;
	}

}
