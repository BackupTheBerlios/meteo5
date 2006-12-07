package Affichage;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import EventObjects.SaveEventObject;
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
		this.adaptateur = new AdaptateurAffichage(this);
	}
	
	// ------------------------------------------------------------------------
	// Récepteur des évènements contenant les informations météo :
	// Temperature, Pressure, Weather, Wind, Visibility

	

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
