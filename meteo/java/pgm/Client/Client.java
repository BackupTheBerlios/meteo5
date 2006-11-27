package Client;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.ListVilleEventObject;

import InterfaceListener.GetListVilleListener;
import InterfaceListener.ListVilleListener;
import InterfaceListener.SelectedVilleListener;



public class Client implements Serializable, ListVilleListener {
	
	private static final long serialVersionUID = 1l;
		
	public Client() {
	}
	
	//------------------------------------------------
	// Source de d'évènement SelectedVilleEventObject
	
	// Liste des écouteur de SelectedVilleEventObject
	private Vector<SelectedVilleListener> selectedVilletListener = new Vector<SelectedVilleListener>();
	
	
	public synchronized void addSelectedVilleListener(SelectedVilleListener l) {
		this.selectedVilletListener.add(l);
	}
	
	public synchronized void removeSelectedVilleListener(SelectedVilleListener l) {
		this.selectedVilletListener.remove(l);
	}
	
	
	//------------------------------------------------
	// Source de d'évènement GetListVilleEventObject

	// Liste des écouteur de GetListVilleEventObject
	private Vector<GetListVilleListener> getSelectedVilletListener = new Vector<GetListVilleListener>();

	
	public synchronized void addGetListVilleListener(GetListVilleListener l) {
		this.getSelectedVilletListener.add(l);
	}
	
	public synchronized void removeGetListVilleVilleListener(SelectedVilleListener l) {
		this.getSelectedVilletListener.remove(l);
	}
	
	
	//----------------------------------------------
	// Ecouteur d'évènements ListVilleListener
	
	// exe lors de l'arrivée d'un event
	public void handleGetListVille(ListVilleEventObject e) {
		
		ClientClass cc = new ClientClass(e.getVilles());
		
	}
	
	


	
}
