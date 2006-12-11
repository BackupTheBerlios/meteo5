package Client;

import java.beans.Beans;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import EventObjects.AffichageEventObject;
import EventObjects.GetListVilleEventObject;
import EventObjects.ListVilleEventObject;
import EventObjects.SaveEventObject;

import InterfaceListener.AffichageListener;
import InterfaceListener.GetListVilleListener;
import InterfaceListener.ListVilleListener;
import InterfaceListener.SelectedVilleListener;

import AeroVille.AeroVille;
import Affichage.Affichage;
import Parseur.Parseur;
import Serveur.Serveur;
import Temperature.Temperature;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant l'interface graphique.
 * Elle contient également le main chargé de la création des composants.
 */
public class Client implements Serializable, ListVilleListener, AffichageListener {
	private static final long serialVersionUID = 1l;
	
	
	/**
	 * Création des composants et lancement de l'interface 
	 * graphique du projet météo.
	 * @param args Argument passés au programme.
	 */
	public static void main(String[] args) {
		try {
			
			// Instantiation des composants
			AeroVille avComp = (AeroVille)Beans.instantiate(ClassLoader.getSystemClassLoader(), "AeroVille.AeroVille");
			Serveur servComp = (Serveur)Beans.instantiate(ClassLoader.getSystemClassLoader(), "Serveur.Serveur");
			Parseur parsComp = (Parseur)Beans.instantiate(ClassLoader.getSystemClassLoader(), "Parseur.Parseur");
			Temperature tmpComp = (Temperature)Beans.instantiate(ClassLoader.getSystemClassLoader(), "Temperature.Temperature");		
			Affichage affComp = (Affichage)Beans.instantiate(ClassLoader.getSystemClassLoader(), "Affichage.Affichage");
			Client cliComp = (Client)Beans.instantiate(ClassLoader.getSystemClassLoader(), "Client.Client");
			
			// Liaison aeroVille <-> Client
			cliComp.addGetListVilleListener(avComp);
			cliComp.addSelectedVilleListener(avComp);
			avComp.addListVilleListener(cliComp);
			
			// Liaison aeroVille <-> Serveur
			avComp.addAeroVilleListener(servComp);
			
			// Liaison Serveur <-> Parseur
			servComp.addMetarListener(parsComp);
			
			// Liaison Parseur <-> éléments météo
			parsComp.addTemperatureListener(tmpComp);
			
			// Liaison éléments météo <-> Affichage
			tmpComp.addTemperatureTraiteListener(affComp.getAdaptateur());
			
			// Liaison Affichage <-> Client
			affComp.addClientListener(cliComp);
			
			// Lancement général : envoi d'un event GetListVille
			cliComp.handleGetListVille();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
		
	/** Interface graphique du client. */
	private ClientClass cc = null;
	
	
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
	public synchronized void removeGetListVilleListener(SelectedVilleListener l) {
		this.getSelectedVilletListener.remove(l);
	}
	
	
	/**
	 * Méthode chargée d'envoyer un évènement la liste des villes dont
	 * on peut avoir les informations.
	 */
	public void handleGetListVille() {
		// Création d'un objet pour l'évènement
		GetListVilleEventObject obj = new GetListVilleEventObject(this);

		// Envoi à tous les écoutant
		synchronized (this) {
			Vector<GetListVilleListener> l = (Vector<GetListVilleListener>) this.getSelectedVilletListener
					.clone();
			for (GetListVilleListener avl : l) {
				avl.handleGetListVille(obj);
			}
		}

	}
	
	
	
	//----------------------------------------------
	// Réception d'évènements ListVille
	
	/**
	 * Méthode lancée lors de la réception d'un évènement ListVille.
	 * @param e Objet contenant la liste des villes.
	 */
	public void handleListVille(ListVilleEventObject e) {
		cc = new ClientClass(e.getVilles());		
	}
	
	
	//----------------------------------------------
	// Réception d'évènements Affichage
	
	/**
	 * Méthode lancée lors de la réception d'un évènement ListVille.
	 * @param e Objet contenant la liste des villes.
	 */
	public void handleAffichage(AffichageEventObject e) {
		cc.setTexte(e.getTexte());
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
			FileOutputStream fichier = new FileOutputStream(this.clientSaveFile);
			
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
	
	
	
	// ---------------------
	// Propriétés
	
	/** Nom du fichier servant à la persistance du composant. */
	private String clientSaveFile = "client.ser";

	/**
	 * Récupérer l'emplacement du fichier servant à la persistance du composant.
	 * 
	 * @return L'emplacement du fichier servant à la persistance du composant.
	 */
	public String getAeroVilleSaveFile() {
		return this.clientSaveFile;
	}

	/**
	 * Préciser l'emplacement du fichier servant à la persistance du composant.
	 * 
	 * @param fileName
	 *            L'emplacement du fichier servant à la persistance du
	 *            composant.
	 */
	public void setAeroVilleSaveFile(String fileName) {
		this.clientSaveFile = fileName;
	}


	
}
