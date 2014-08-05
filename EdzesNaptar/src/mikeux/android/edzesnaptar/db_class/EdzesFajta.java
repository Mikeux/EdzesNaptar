package mikeux.android.edzesnaptar.db_class;

public class EdzesFajta {
	/*public enum Mertekegyseg { 
		Id�_ms (0,0), 
		GyakorlatSz�m (1,1);
		private final long mass; 
	}*/
	
	public enum Mertekegyseg {
		Id�_ms (0),
		GyakorlatSz�m (1);

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
        this.mertekegyseg = Mertekegyseg.GyakorlatSz�m;
    }

    public EdzesFajta(long id, String nev, long mertekegyseg) {
        this.id = id;
        this.nev = nev;
        if(mertekegyseg == Mertekegyseg.Id�_ms.getSorszam()) this.mertekegyseg = Mertekegyseg.Id�_ms;
        else if(mertekegyseg == Mertekegyseg.GyakorlatSz�m.getSorszam()) this.mertekegyseg = Mertekegyseg.GyakorlatSz�m;
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
