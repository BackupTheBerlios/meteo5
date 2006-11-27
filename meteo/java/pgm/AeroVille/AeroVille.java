package AeroVille;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.AeroVilleEventObject;
import InterfaceListener.AeroVilleListener;

import meteo.VilleEventObject;
import meteo.VilleListener;


public class AeroVille implements Serializable, VilleListener {
	
	private static final long serialVersionUID = 1l;
	
	private Vector<AeroVilleListener> aeroVilleListener = new Vector<AeroVilleListener>(); 
	
	private String aeroVilleFile = "";
	
	
	public AeroVille() {
	
	}
	
	
	//------------------------------------------------
	// Source de d'évènement
	
	public synchronized void addAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.add(l);
	}
	
	public synchronized void removeAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.remove(l);
	}
	
	
	private void chercherAeroports() {		
		AeroVilleEventObject aveo = new AeroVilleEventObject(this);
		
		LocationsFile locations = new LocationsFile(this.getAeroVilleFile());
		aveo.setAeroports(locations.getLocations());
		
		synchronized (this) {
			Vector<AeroVilleListener> l = (Vector<AeroVilleListener>)this.aeroVilleListener.clone();
			
			for(AeroVilleListener avl : l) {
				avl.handleChercherAeroports(aveo);
			}
	
		}
		
	}
	
	
	//----------------------------------------------
	// Ecouteur d'évènements
	
	// exe lors de l'arrivée d'un event VilleEventObject
	public void handleVille(VilleEventObject e) {
		String ville = e.getVille();
		
		if(!ville.equals("")) {
			chercherAeroports();
		}
	}
	
	
	//---------------------------------------------
	// Accès aux propriétés
	
	public String getAeroVilleFile() {
		return this.aeroVilleFile;
	}
	
	public void setAeroVilleFile(String fileName) {
		this.aeroVilleFile = fileName;
	}
	


	
}
