package AeroVille;

import java.io.Serializable;
import java.util.Vector;

public class AeroVille implements Serializable {
	
	private Vector<AeroVilleListener> aeroVilleListener = new Vector<AeroVilleListener>(); 
	
	public AeroVille() {
	
	}
	
	
	
	
	public synchronized void addAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.add(l);
	}
	
	public synchronized void removeAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.remove(l);
	}
	
	
}
