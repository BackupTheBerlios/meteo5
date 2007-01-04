package Affichage;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.AffichageEventObject;
import EventObjects.PressureTraiteEventObject;
import EventObjects.TemperatureTraiteEventObject;
import EventObjects.VisibilityTraiteEventObject;
import EventObjects.WeatherTraiteEventObject;
import EventObjects.WindTraiteEventObject;
import InterfaceListener.AffichageListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant traitant les informations pour créer un texte
 * qui pourra être afficher par un client.
 */
public class Affichage implements Serializable { 
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur vide pour le composant.
	 */
	public Affichage() {
		// Création de l'adaptateur
		this.adaptateur = new AdaptateurAffichage(this);
	}
	
	
	// ------------------------------------------
	// Réception d'un évènement températureTraite
	
	/**
	 * Méthode appelée par l'adaptateur lorsqu'un évènement temperatureTraite
	 * est reçu.
	 * @param e Objet reçu avec l'évènement.
	 */
	public void handleTemperatureTraite(TemperatureTraiteEventObject e) {
		String ret = "";
		ret = "La température a la rosée était de " + e.getTemperatureTraiteRosee() + " °C.\n";
		ret += "La température est de " + e.getTemperatureTraite() + " °C.";
		handleAffichage(ret);
	}
	
	
	// -----------------------------------
	// Réception d'un évènement windTraite
	
	/**
	 * Méthode appelée par l'adaptateur lorsqu'un évènement WindTraite
	 * est reçu.
	 * @param e Objet reçu avec l'évènement.
	 */
	public void handleWindTraite(WindTraiteEventObject e) {
		String ret = "";
		ret = "La vitesse maximale du vent a été de " + e.getForceMaxTraite()
		+ " km/h.\n";
		ret += "La vitesse du vent est de " + e.getForceTraite() + " km/h.\n";
		ret += "La direction du vent vaut " + e.getDirectionTraite() + ".";
		handleAffichage(ret);
	}

	
	
	// -----------------------------------
	// Réception d'un évènement visibilityTraite
	
	/**
	 * Méthode appelée par l'adaptateur lorsqu'un évènement WindTraite
	 * est reçu.
	 * @param e Objet reçu avec l'évènement.
	 */
	public void handleVisibilityTraite(VisibilityTraiteEventObject e) {
		String ret = "";
		ret += "La visibilité est de " + e.getVisibility() + " mètre(s).";
		handleAffichage(ret);
	}
	
	
	// --------------------------------------
	// Réception d'un évènement weatherTraite
	
	/**
	 * Méthode appelée par l'adaptateur lorsqu'un évènement weatherTraite
	 * est reçu.
	 * @param e Objet reçu avec l'évènement.
	 */
	public void handleWeatherTraite(WeatherTraiteEventObject e) {
		String ret = "";
		if (e.getIsCavOkTraite()) {
			ret = "Le temps est clair.";
		} else {
			ret = "Le temps n'est pas clair, ce référer à la visibilité.";
		}
		handleAffichage(ret);
	}
	
	
	// ---------------------------------------
	// Réception d'un évènement pressureTraite
	
	/**
	 * Méthode appelée par l'adaptateur lorsqu'un évènement pressureTraite
	 * est reçu.
	 * @param e Objet reçu avec l'évènement.
	 */
	public void handlePressureTraite(PressureTraiteEventObject e) {
		String ret = "";
		ret = "La pression atmosphérique au sol est de " + e.getPressureTraite() + ".";
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
	

}
