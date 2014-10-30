package mikeux.android.edzesnaptar.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mikeux.android.edzesnaptar.ResponsiveUIActivity;
import mikeux.android.edzesnaptar.db_class.Elelmiszer;
import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;

public class u {
	public static Context ctxt;
	public static List<Elelmiszer> elelmiszerek;
	
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
		u.ctxt = responsiveUIActivity;
		settings = PreferenceManager.getDefaultSharedPreferences(u.ctxt);
	}

	public static String msValtas(long sec){
		String ret = sec+" ms";
		if(sec/3600.0 >= 1.0){
			ret = u.round(sec/3600.0,2) +" óra";
		}else if(sec/60.0 >  1.0){
			ret = u.round(sec/60.0,2) +" perc";
		}		
		return ret;
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	public static void uzen(String uzenet, boolean... hiba) {
		AlertDialog.Builder builder = new AlertDialog.Builder(u.ctxt);
		//builder.setCancelable(false);
		builder.setTitle("Üzenet");
		builder.setMessage(uzenet);
		builder.setPositiveButton("Ok", null);
		if(hiba.length>0 && hiba[0]) builder.setIcon(R.drawable.ic_dialog_alert);
		else builder.setIcon(R.drawable.ic_dialog_info);
    	builder.create().show();
    	//builder.show();
	}
	
	public static int randInt(int min, int max) {
	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();
	    
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	public static int StringToInt(String szam, int... _default) {
		int ret;
		try{
			ret = Integer.parseInt(szam);
		} catch(Exception ex) {
			ret = _default.length > 0 ? _default[0] : 0;
		}
		return ret;
	}
	
}
