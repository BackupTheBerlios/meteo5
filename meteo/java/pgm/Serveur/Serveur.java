package Serveur;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import EventObjects.AeroVilleEventObject;
import EventObjects.MetarEventObject;
import EventObjects.SaveEventObject;
import InterfaceListener.AeroVilleListener;
import InterfaceListener.MetarListener;
import InterfaceListener.SaveListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant envoyant les metars au parseur.
 */

public class Serveur implements Serializable, AeroVilleListener, SaveListener {
	private static final long serialVersionUID = 1l;


	
	//-----------------
	// Source d'évènements Metar
	
	/** liste des écouteurs d'évènements Metar */
	private Vector<MetarListener> metarListeners = new Vector<MetarListener>();
	
	/** 
	 * Ajout d'un écouteur.
	 * @param l écouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addMetarListener(MetarListener l) {
		metarListeners.addElement(l);
	}

	/** 
	 * Supression d'un écouteur.
	 * @param l écouteur à supprimer de la liste des abonnés.
	 */
	public synchronized void removeMetarListener(MetarListener l) {
		metarListeners.removeElement(l);
	}

	// Notifie les ecouteurs d'event Metar
	/** 
	 * Méthode qui envoie les metars demandés au parseur.
	 * @param o: l'évènement que le composant a reçu en entrée.
	 */
	public void envoieMetar(AeroVilleEventObject o) {
		TestMeteoServer serveurTest = new TestMeteoServer();
		RealMeteoServer serveurReel = new RealMeteoServer();
		Vector<String> met = new Vector<String>();
		if (getTest()) {

			MetarEventObject meo = new MetarEventObject(o);
			for(int i=0;i<o.getAeroports().size();i++){
				met.add(serveurTest.getAMetarString(
						o.getAeroports().firstElement(), 128));
					o.getAeroports().remove(0);
				
			}
			meo.setMetars(met);
		} else {
			// pas de serveur réel
		}
	}

	
	// ----------------------------------------------------
	// Récepteur d'évènement AeroVille
	
	// executer à la réception d'un AeroVilleEventObject
	/** 
	 * Méthode éxécutée à la réception d'un évènement AeroVilleEventObject.
	 * @param o: évènement reçu.
	 */
	public void handleChercherAeroports(EventObjects.AeroVilleEventObject o) {
		envoieMetar(o);
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
			FileOutputStream fichier = new FileOutputStream(this.serveurSaveFile);
			
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
	
	
	
	//--------------
	// Acces propriété
	
	/** url du serveur réel ou fichier. */
	private String url;

	/** Vrai si on se sert d'un fichier en guise de serveur. */
	private boolean test;

	/** Emplacement du fichier de sauvegarde du composant. */
	private String serveurSaveFile = "serveur.ser";
	
	
	/**
	 * Récupérer l'emplacement du fichier servant à la persistance du composant.
	 * 
	 * @return L'emplacement du fichier servant à la persistance du composant.
	 */
	public String getServeurSaveFile() {
		return this.serveurSaveFile;
	}

	/**
	 * Préciser l'emplacement du fichier servant à la persistance du composant.
	 * 
	 * @param fileName
	 *            L'emplacement du fichier servant à la persistance du
	 *            composant.
	 */
	public void setServeurSaveFile(String fileName) {
		this.serveurSaveFile = fileName;
	}
	
	
	/**
	 * @return Rend vrai si le serveur est un serveur de test.
	 */
	public boolean getTest() {
		return test;
	}

	/**
	 * @return Rend l'emplacement du serveur.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param test : régler le serveur en mode test ou reel
	 */
	public void setTest(boolean test) {
		this.test = test;
	}

	/**
	 * @param url: configuration de l'emplacement du serveur.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
