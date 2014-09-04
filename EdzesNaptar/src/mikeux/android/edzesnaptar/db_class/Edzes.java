package mikeux.android.edzesnaptar.db_class;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

/**
 * Created by Mikeux on 2014.08.04..
 */
public class Edzes {
    public Long id;
    public Long fk_id;
    public Date datum;
    public int idotartam;
    
    public Edzes(Long id, Long fk_id, String datum, int idotartam) {
        this.id = id;
        this.fk_id = fk_id;
        try {
			this.datum = new SimpleDateFormat("yyyy.MM.dd").parse(datum);
		} catch (ParseException e) {
			Log.e("Mikeux","Nem sikerült a dátummá alakítás! (Edzes contructor)");
			this.datum = Calendar.getInstance().getTime();
		};
        this.idotartam = idotartam;
    }
    
    public Edzes(Long id, Long fk_id, Date datum, int idotartam) {
        this.id = id;
        this.fk_id = fk_id;
        this.datum = datum;
        this.idotartam = idotartam;
    }

    public Edzes() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFk_id() {
        return fk_id;
    }

    public void setFk_id(Long fk_id) {
        this.fk_id = fk_id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getIdotartam() {
        return idotartam;
    }

    public void setIdotartam(int idotartam) {
        this.idotartam = idotartam;
    }
}
