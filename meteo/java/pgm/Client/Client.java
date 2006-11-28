package Client;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.ListVilleEventObject;

import InterfaceListener.GetListVilleListener;
import InterfaceListener.ListVilleListener;
import InterfaceListener.SelectedVilleListener;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant l'interface graphique.
 */
public class Client implements Serializable, ListVilleListener {
	private static final long serialVersionUID = 1l;
		
	/**
	 * Constructeur vide pour le composant.
	 */
	public Client() {
	}
	
	
	//-----------------------------------------------------------------------
	// Source de d'évènement SelectedVille : envoi le nom de la ville choisie
	
	/** Liste des écouteur de l'évènement SelectedVille. */
	private Vector<SelectedVilleListener> selectedVilletListener = new Vector<SelectedVilleListener>();
	
	
	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addSelectedVilleListener(SelectedVilleListener l) {
		this.selectedVilletListener.add(l);
	}
	
	
	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeSelectedVilleListener(SelectedVilleListener l) {
		this.selectedVilletListener.remove(l);
	}
	
	
	//------------------------------------------------
	// Source de d'évènement GetListVille

	/** Liste des écouteur de l'évènement GetListVille. */
	private Vector<GetListVilleListener> getSelectedVilletListener = new Vector<GetListVilleListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addGetListVilleListener(GetListVilleListener l) {
		this.getSelectedVilletListener.add(l);
	}
	
	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
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
