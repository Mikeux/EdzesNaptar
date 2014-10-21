package mikeux.android.edzesnaptar.db_class;

public class StatisztikaEdzesFajta {
	private String fajta_nev;
	private int fajta_me;
	private long osszesen;
	
	public StatisztikaEdzesFajta(String fajta_nev, int fajta_me, long osszesen) {
		super();
		this.fajta_nev = fajta_nev;
		this.fajta_me = fajta_me;
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

	public void setOsszesen(int osszesen) {
		this.osszesen = osszesen;
	}
	
	
}
