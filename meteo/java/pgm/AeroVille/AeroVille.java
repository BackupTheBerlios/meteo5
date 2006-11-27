package AeroVille;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.AeroVilleEventObject;
import EventObjects.GetListVilleEventObject;
import EventObjects.ListVilleEventObject;
import EventObjects.SelectedVilleEventObject;
import InterfaceListener.AeroVilleListener;
import InterfaceListener.GetListVilleListener;
import InterfaceListener.ListVilleListener;
import InterfaceListener.SelectedVilleListener;


public class AeroVille implements Serializable, SelectedVilleListener, GetListVilleListener {
	
	private static final long serialVersionUID = 1l;
		
	private String aeroVilleFile = "";
	
	
	public AeroVille() {
	
	}
	
	
	//------------------------------------------------
	// Source de d'évènement AeroVille
	
	private Vector<AeroVilleListener> aeroVilleListener = new Vector<AeroVilleListener>();
	
	public synchronized void addAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.add(l);
	}
	
	public synchronized void removeAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.remove(l);
	}
	
	// Envoi des event AeroVille aux listener
	private void chercherAeroports(String ville) {		
		AeroVilleEventObject aveo = new AeroVilleEventObject(this);
		
		LocationsFile locations = new LocationsFile(this.getAeroVilleFile());
		aveo.setAeroports(locations.getLocValues(ville));
		
		synchronized (this) {
			Vector<AeroVilleListener> l = (Vector<AeroVilleListener>)this.aeroVilleListener.clone();
			
			for(AeroVilleListener avl : l) {
				avl.handleChercherAeroports(aveo);
			}
	
		}
	}
	
	
	//------------------------------------------------
	// Source de d'évènement ListVille
	
	private Vector<ListVilleListener> listVilleListener = new Vector<ListVilleListener>();
	
	public synchronized void addListVilleListener(ListVilleListener l) {
		this.listVilleListener.add(l);
	}
	
	public synchronized void removeListVilleListener(ListVilleListener l) {
		this.listVilleListener.remove(l);
	}
	
	// Envoi des event ListVille aux listener
	private void getListVille() {
		ListVilleEventObject obj = new ListVilleEventObject(this);
		
		LocationsFile locations = new LocationsFile(this.getAeroVilleFile());
		obj.setVilles(locations.getLocations());
		
		synchronized (this) {
			Vector<ListVilleListener> l = (Vector<ListVilleListener>)this.listVilleListener.clone();
			
			for(ListVilleListener avl : l) {
				avl.handleGetListVille(obj);
			}
	
		}
	}
	
	
	//----------------------------------------------
	// Ecouteur d'évènement SelectedVille
	
	// exe lors de l'arrivée d'un event
	public void handleSelectedVille(SelectedVilleEventObject e) {
		String ville = e.getVille();
		
		if(!ville.equals("")) {
			chercherAeroports(ville);
		}
	}
	
	//----------------------------------------------
	// Ecouteur d'évènement GetListVille
	
	// exe lors de l'arrivée d'un event
	public void handleGetListVille(GetListVilleEventObject e) {
		getListVille();
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
