package mikeux.android.edzesnaptar.db_class;

public class Edzes {
	 private long id;
	  private String nev;
	
	  public long getId() {
	    return id;
	  }
	
	  public void setId(long id) {
	    this.id = id;
	  }
	
	  public String getComment() {
	    return nev;
	  }
	
	  public void setComment(String comment) {
	    this.nev = comment;
	  }
	
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return nev;
	  }
}
