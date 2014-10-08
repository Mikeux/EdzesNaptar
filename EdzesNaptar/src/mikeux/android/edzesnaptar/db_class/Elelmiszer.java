package mikeux.android.edzesnaptar.db_class;

public class Elelmiszer {
	
	public long id;
	public String nev;
    public double feherje;
    public double zsir;
    public double szenhidrat;
    public double kaloria;
    
	public Elelmiszer() {
		super();
	}

	public Elelmiszer(long id, String nev, double feherje, double zsir,double szenhidrat, double kaloria) {
		super();
		this.id = id;
		this.nev = nev;
		this.feherje = feherje;
		this.zsir = zsir;
		this.szenhidrat = szenhidrat;
		this.kaloria = kaloria;
	}
		
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNev() {
		return nev;
	}

	public void setNev(String nev) {
		this.nev = nev;
	}

	public double getFeherje() {
		return feherje;
	}

	public void setFeherje(double feherje) {
		this.feherje = feherje;
	}

	public double getZsir() {
		return zsir;
	}

	public void setZsir(double zsir) {
		this.zsir = zsir;
	}

	public double getSzenhidrat() {
		return szenhidrat;
	}

	public void setSzenhidrat(double szenhidrat) {
		this.szenhidrat = szenhidrat;
	}

	public double getKaloria() {
		return kaloria;
	}

	public void setKaloria(double kaloria) {
		this.kaloria = kaloria;
	}
   
    
    
}
