package EventObjects;

import java.util.EventObject;
import java.util.Vector;

public class AeroVilleEventObject extends EventObject {

	private Vector<String> aeroports = new Vector<String>();
	
	public AeroVilleEventObject(Object o) {
		super(o);
	
	}
	
	public Vector<String> getAeroports() {
		return this.aeroports;
	}
	
	public void setAeroports(Vector<String> liste) {
		this.aeroports = liste;
	}
	
}
