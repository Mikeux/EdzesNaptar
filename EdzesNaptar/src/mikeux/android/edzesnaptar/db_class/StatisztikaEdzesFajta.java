package mikeux.android.edzesnaptar.db_class;

public class StatisztikaEdzesFajta {
	private long edzes_fajta_id;
	private String fajta_nev;
	private int fajta_me;
	private long osszesen;
	
	public StatisztikaEdzesFajta(long edzes_fajta_id, String fajta_nev, int fajta_me, long osszesen) {
		super();
		this.edzes_fajta_id = edzes_fajta_id;
		this.fajta_nev = fajta_nev;
		this.fajta_me = fajta_me;
		this.osszesen = osszesen;
	}

	public long getEdzes_Fajta_id() {
		return edzes_fajta_id;
	}

	public void setEdzes_Fajta_id(long edzes_id) {
		this.edzes_fajta_id = edzes_id;
	}

	public void setOsszesen(long osszesen) {
		this.osszesen = osszesen;
	}

	public String getFajta_nev() {
		return fajta_nev;
	}

	public void setFajta_nev(String fajta_nev) {
		this.fajta_nev = fajta_nev;
	}

	public int getFajta_me() {
		return fajta_me;
	}

	public void setFajta_me(int fajta_me) {
		this.fajta_me = fajta_me;
	}

	public long getOsszesen() {
		return osszesen;
	}
	
}
