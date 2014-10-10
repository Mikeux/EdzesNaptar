package mikeux.android.edzesnaptar;

import mikeux.android.edzesnaptar.db_class.ElelmiszerDataSource;
import mikeux.android.edzesnaptar.util.u;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class EdzesNaptar extends Activity {
	public TextView loading;
	public ProgressBar loading_pb;
	public final Context ctxt = this;
	private Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_loading);
		
		loading_pb = (ProgressBar) findViewById(R.id.progressBar1);
		loading_pb.setProgress(0);
		loading_pb.setMax(100);

		new Thread(new Runnable() {
			public void run() {
				ElelmiszerDataSource elelmiszer_datasource = new ElelmiszerDataSource(ctxt);
				u.elelmiszerek = elelmiszer_datasource.getAllElelmiszer();
				loading_pb.setProgress(100);
				handler.postDelayed(new Runnable() { 
					public void run() { 
						startActivity(new Intent(ctxt, ResponsiveUIActivity.class));
						finish();
					} 
				}, 200);
				
				/*while (loading_pb.getProgress() < 100) {
		
					ElelmiszerDataSource elelmiszer_datasource = new ElelmiszerDataSource(ctxt);
					u.elelmiszerek = elelmiszer_datasource.getAllElelmiszer();
					loading_pb.setProgress(50);
					
					//loading_pb.setProgress(loading_pb.getProgress()+10);
					handler.post(new Runnable() {
						public void run() {
							loading_pb.setProgress(loading_pb.getProgress());
						}
					});
			
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		
					// Update the progress bar
					handler.post(new Runnable() {
						public void run() {
							loading_pb.setProgress(loading_pb.getProgress());
						}
					});	
				}
				
				handler.postDelayed(new Runnable() { 
					public void run() { 
						//startActivity(new Intent(ctxt, EdzesActivity.class));
						startActivity(new Intent(ctxt, ResponsiveUIActivity.class));
						finish();
					} 
				}, 100);*/
			}		
		}).start();
		
		//loading = (TextView) findViewById(R.id.loading);
		//loading.setText("Élelmiszerek betöltése...");
 
	}
}
