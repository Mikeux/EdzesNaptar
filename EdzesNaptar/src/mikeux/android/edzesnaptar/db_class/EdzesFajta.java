package mikeux.android.edzesnaptar.db_class;

public class EdzesFajta {
	/*public enum Mertekegyseg { 
		Idõ_ms (0,0), 
		GyakorlatSzám (1,1);
		private final long mass; 
	}*/
	
	public enum Mertekegyseg {
		Idõ_ms (0),
		GyakorlatSzám (1);

	    private final long sorszam;
	    Mertekegyseg(long sorszam) {
	        this.sorszam = sorszam;
	    }
	    public long getSorszam() { return sorszam; } 
	}	
	
	public long id;
    public String nev;
    public Mertekegyseg mertekegyseg;
    
    public EdzesFajta() { }

    public EdzesFajta(long id, String nev) {
        this.id = id;
        this.nev = nev;
        this.mertekegyseg = Mertekegyseg.GyakorlatSzám;
    }

    public EdzesFajta(long id, String nev, long mertekegyseg) {
        this.id = id;
        this.nev = nev;
        if(mertekegyseg == Mertekegyseg.Idõ_ms.getSorszam()) this.mertekegyseg = Mertekegyseg.Idõ_ms;
        else if(mertekegyseg == Mertekegyseg.GyakorlatSzám.getSorszam()) this.mertekegyseg = Mertekegyseg.GyakorlatSzám;
    }    
    
    public EdzesFajta(long id, String nev, Mertekegyseg mertekegyseg) {
        this.id = id;
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
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

    public void setNev(String comment) {
    	this.nev = comment;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
    	return nev;
    }
}
