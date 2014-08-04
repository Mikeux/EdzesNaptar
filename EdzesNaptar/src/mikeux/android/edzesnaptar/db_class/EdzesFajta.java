package mikeux.android.edzesnaptar.db_class;

public class EdzesFajta {
    private long id;
    private String nev;

    public EdzesFajta() { }

    public EdzesFajta(long id, String nev) {
        this.id = id;
        this.nev = nev;
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
