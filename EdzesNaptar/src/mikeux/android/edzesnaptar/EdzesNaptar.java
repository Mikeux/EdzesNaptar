package mikeux.android.edzesnaptar;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Window;

public class EdzesNaptar extends Activity {
	
	public final Context ctxt = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_loading);
		
		Handler handler = new Handler(); 
	    handler.postDelayed(new Runnable() { 
	         public void run() { 
	     		//startActivity(new Intent(ctxt, EdzesActivity.class));
	        	 startActivity(new Intent(ctxt, ResponsiveUIActivity.class));
	        	 finish();
	         } 
	    }, 100);
	    
	}
}
