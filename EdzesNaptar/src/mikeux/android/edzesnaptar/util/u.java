package mikeux.android.edzesnaptar.util;

import mikeux.android.edzesnaptar.ResponsiveUIActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class u {
	
	public static SharedPreferences settings;
	
	public u(ResponsiveUIActivity responsiveUIActivity){
		settings = PreferenceManager.getDefaultSharedPreferences(responsiveUIActivity);
	}
	
	public static int StringToInt(String szam) {
		int ret;
		try{
			ret = Integer.parseInt(szam);
		} catch(Exception ex) {
			ret = 0;
		}
		return ret;
	}
	
}
