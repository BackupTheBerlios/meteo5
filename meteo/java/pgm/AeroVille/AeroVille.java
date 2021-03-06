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

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant les villes et aéroports.
 */

public class AeroVille implements Serializable, SelectedVilleListener,
		GetListVilleListener {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur vide nécessaire pour créer un composant.
	 */
	public AeroVille() {
	}

	// ------------------------------------------------------------------------
	// Source de d'évènement AeroVille : liste des codes d'aéroport + distance

	/** Liste de composants écoutant l'évènement. */
	private Vector<AeroVilleListener> aeroVilleListener = new Vector<AeroVilleListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeAeroVilleListener(AeroVilleListener l) {
		this.aeroVilleListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur les
	 * aéroports à tous les composants écoutant.
	 * 
	 * @param ville
	 *            Ville dont on veut la liste des aéroports.
	 */
	private void handleSendAeroports(String ville) {
		// Création d'un objet pour l'évènement
		AeroVilleEventObject aveo = new AeroVilleEventObject(this);

		// Récupération des informations voulues
		LocationsFile locations = new LocationsFile(this.getAeroVilleFile());
		aveo.setAeroports(locations.getLocValues(ville));

		// Envoi à tous les écoutant
		Vector<AeroVilleListener> l;
		synchronized (this) {
			l = (Vector<AeroVilleListener>) this.aeroVilleListener.clone();
		}
		for (AeroVilleListener avl : l) {
			avl.handleAeroports(aveo);
		}
	}

	// ---------------------------------------------------------------
	// Source de d'évènement ListVille : contient la liste des villes

	/** Liste de composants écoutant l'évènement. */
	private Vector<ListVilleListener> listVilleListener = new Vector<ListVilleListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addListVilleListener(ListVilleListener l) {
		this.listVilleListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeListVilleListener(ListVilleListener l) {
		this.listVilleListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur les
	 * villes à tous les composants écoutant.
	 */
	private void handleListVille() {

		// Création d'un object pour l'évènement
		ListVilleEventObject obj = new ListVilleEventObject(this);

		// Récupération des informations
		LocationsFile locations = new LocationsFile(this.getAeroVilleFile());
		obj.setVilles(locations.getLocations());

		// Envoi à tous les écoutants
		Vector<ListVilleListener> l;
		synchronized (this) {
			l = (Vector<ListVilleListener>) this.listVilleListener.clone();
		}
		for (ListVilleListener avl : l) {
			avl.handleListVille(obj);
		}

	}

	// ------------------------------------------------------------------------
	// Récepteur d'évènement SelectedVille : contient la ville pour la recherche
	// des aéroports

	/**
	 * Méthode appelée lorsqu'un évènement SelectedVille est reçu.
	 * 
	 * @param e
	 *            Objet contenant les informations sur la ville choisie.
	 */
	public void handleSelectedVille(SelectedVilleEventObject e) {
		String ville = e.getVille();

		if (!ville.equals("")) {
			// Appel de la recherche de ville qui va générer un évènement
			handleSendAeroports(ville);
		}
	}

	// ----------------------------------------------
	// Récepteur d'évènement GetListVille : demande de la liste des villes

	/**
	 * Méthode azppelée lorsqu'un évènement GetListVille est reçu.
	 * 
	 * @param e
	 *            Objet ne contenant pas d'information nécessaire.
	 */
	public void handleGetListVille(GetListVilleEventObject e) {
		// Lance un évènement contenant la liste des villes
		handleListVille();
	}


	// ---------------------------------------------
	// Propriétés

	/** Nom du fichier conteannt les informations sur les villes et aéroports. */
	private String aeroVilleFile = "places";

	/**
	 * Récupérer l'emplacement du fichier contenant les informations sur les
	 * villes et aéroport.
	 * 
	 * @return L'emplacement du fichier contenant les informations sur les
	 *         villes et aéroport.
	 */
	public String getAeroVilleFile() {
		return this.aeroVilleFile;
	}

	/**
	 * Préciser l'emplacement du fichier contenant les informations sur les
	 * villes et aéroport.
	 * 
	 * @param fileName
	 *            L'emplacement du fichier contenant les informations sur les
	 *            villes et aéroport.
	 */
	public void setAeroVilleFile(String fileName) {
		this.aeroVilleFile = fileName;
	}

}
