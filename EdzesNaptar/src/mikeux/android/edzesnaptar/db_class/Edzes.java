package mikeux.android.edzesnaptar.db_class;

import java.util.Date;

/**
 * Created by Mikeux on 2014.08.04..
 */
public class Edzes {
    public int id;
    public int fk_id;
    public Date datum;
    public int idotartam;

    public Edzes(int id, int fk_id, Date datum, int idotartam) {
        this.id = id;
        this.fk_id = fk_id;
        this.datum = datum;
        this.idotartam = idotartam;
    }

    public Edzes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFk_id() {
        return fk_id;
    }

    public void setFk_id(int fk_id) {
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
