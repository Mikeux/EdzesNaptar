package mikeux.android.edzesnaptar.db_class;

public class Etkezes {
	private long id;
	private long fk_elelmiszer;
	private String nev;
	private long mennyiseg;
    
	public Etkezes(long id, long fk_elelmiszer, String nev, long mennyiseg) {
		super();
		this.id = id;
		this.fk_elelmiszer = fk_elelmiszer;
		this.nev = nev;
		this.mennyiseg = mennyiseg;
	}
	
	public Etkezes() {
		super();
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFk_elelmiszer() {
		return fk_elelmiszer;
	}
	public void setFk_elelmiszer(long fk_elelmiszer) {
		this.fk_elelmiszer = fk_elelmiszer;
	}
	public String getNev() {
		return nev;
	}
	public void setNev(String nev) {
		this.nev = nev;
	}
	public long getMennyiseg() {
		return mennyiseg;
	}
	public void setMennyiseg(long mennyiseg) {
		this.mennyiseg = mennyiseg;
	}
	
}
