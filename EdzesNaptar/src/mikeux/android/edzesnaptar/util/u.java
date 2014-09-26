package mikeux.android.edzesnaptar.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import mikeux.android.edzesnaptar.ResponsiveUIActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class u {
	
	public static Map<String,String> napokMap = new LinkedHashMap<String, String>(){{
	    put("Hétfő", "H");
	    put("Kedd", "K");
	    put("Szerda", "SZE");
	    put("Csütörtök", "CS");
	    put("Péntek", "P");
	    put("Szombat", "SZO");
	    put("Vasárnap", "V");
	}};
	
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
