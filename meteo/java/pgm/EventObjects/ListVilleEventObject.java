package EventObjects;

import java.util.EventObject;
import java.util.Vector;

public class ListVilleEventObject extends EventObject {

	private static final long serialVersionUID = 1l;
	
	private Vector<String> villes = new Vector<String>();
	
	public ListVilleEventObject(Object o) {
		super(o);
	}
	
	public void setVilles(Vector<String> villes) {
		this.villes = villes;
	}
	
	public Vector<String> getVilles() {
		return this.villes;
	}

}
