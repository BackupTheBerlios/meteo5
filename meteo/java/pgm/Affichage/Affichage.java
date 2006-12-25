package Affichage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import EventObjects.AffichageEventObject;
import EventObjects.SaveEventObject;
import EventObjects.TemperatureTraiteEventObject;
import InterfaceListener.AeroVilleListener;
import InterfaceListener.AffichageListener;
import InterfaceListener.SaveListener;
import InterfaceListener.TemperatureTraiteListener;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant traitant les informations pour créer un texte
 * qui pourra être afficher par un client.
 */
public class Affichage implements Serializable, SaveListener { 
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur vide pour le composant.
	 */
	public Affichage() {
		// Création de l'adaptateur
		this.adaptateur = new AdaptateurAffichage(this);
	}
	
	
	// ------------------------------------
	// Réception d'un évènement température
	
	/**
	 * Méthode appelée par l'adaptateur lorsqu'un évènement température
	 * est reçu.
	 * @param e Objet reçu avec l'évènement.
	 */
	public void handleTemperatureTraite(TemperatureTraiteEventObject e) {
		String ret = "";
		ret = "La température a la rosée était de " + e.getTemperatureTraiteRosee() + " °C.\n";
		ret += "La température est de " + e.getTemperatureTraite() + " °C.";
		handleAffichage(ret);
	}
	
	
	// ---------------------------------------------------------------
	// Source d'évènement Affichage

	/** Liste de composants écoutant l'évènement. */
	private Vector<AffichageListener> clientListener = new Vector<AffichageListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addClientListener(AffichageListener l) {
		this.clientListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeListVilleListener(AffichageListener l) {
		this.clientListener.remove(l);
	}

	
	/**
	 * Méthode chargée d'envoyer un évènement contenant le texte à afficher au client.
	 * @param text Texte à envoyer.
	 */
	private void handleAffichage(String text) {

		// Création d'un object pour l'évènement
		AffichageEventObject aej = new AffichageEventObject(this);

		// Récupération des informations
		aej.setTexte(text);
		
		// Envoi à tous les écoutants
		Vector<AffichageListener> l;
		synchronized (this) {
			l = (Vector<AffichageListener>) this.clientListener.clone();
		}
		for (AffichageListener avl : l) {
			avl.handleAffichage(aej);
		}
	}
	
	

	// ----------------------------------------------------
	// Récepteur d'évènement Save : sauvegarde du composant
	
	/**
	 * Méthode azppelée lorsqu'un évènement Save est reçu.
	 * Il sauvegarde le composant pour la persistance.
	 * 
	 * @param e
	 *            Objet ne contenant pas d'information nécessaire.
	 */
	public void handleSave(SaveEventObject e) {
		try {
			// Ouverture du fichier de sauvegarde
			FileOutputStream fichier = new FileOutputStream(this.affichageSaveFile);
			
			// Ouverture du stream objet vers le fichier
			ObjectOutputStream sortie = new  ObjectOutputStream(fichier);
			
			// Ecriture de l'objet
			sortie.writeObject(this);
			
			sortie.close();
			fichier.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	
	
	// ---------------------------
	// Propriétés
	
	/** Adaptateur qui va gérer les arrivées d'évènements. */
	private AdaptateurAffichage adaptateur = null;
	
	/**
	 * Récupérer l'adaptateur du composant.
	 * @return L'adaptateur du composant.
	 */
	public AdaptateurAffichage getAdaptateur() {
		return this.adaptateur;
	}
	
	/**
	 * Préciser l'adaptateur à utiliser.
	 * @param adapt Adaptateur à utiliser.
	 */
	public void setAdaptateur(AdaptateurAffichage adapt) {
		this.adaptateur = adapt;
	}
	
	/** Nom du fichier servant à la persistance du composant. */
	private String affichageSaveFile = "affichageVille.ser";

	/**
	 * Récupérer l'emplacement du fichier servant à la persistance du composant.
	 * 
	 * @return L'emplacement du fichier servant à la persistance du composant.
	 */
	public String getAffichageSaveFile() {
		return this.affichageSaveFile;
	}

	/**
	 * Préciser l'emplacement du fichier servant à la persistance du composant.
	 * 
	 * @param fileName
	 *            L'emplacement du fichier servant à la persistance du
	 *            composant.
	 */
	public void setAffichageSaveFile(String fileName) {
		this.affichageSaveFile = fileName;
	}



}
