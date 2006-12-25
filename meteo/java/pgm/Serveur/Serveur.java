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
import InterfaceListener.TemperatureListener;

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


	//--------------------------
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
	 * @param l Ecouteur à supprimer de la liste des abonnés.
	 */
	public synchronized void removeMetarListener(MetarListener l) {
		metarListeners.removeElement(l);
	}

	/** 
	 * Méthode qui envoie un évènements contenant les métars.
	 * @param list Liste des métars.
	 * @param dst Distances.
	 */
	private void handleSendMetars(Vector<String> list, Vector<Float> dst) {
			// Création de l'objet de l'évènement
			MetarEventObject obj = new MetarEventObject(this);
			obj.setMetars(list);
			obj.setDistances(dst);
			
			// Envoi des évènements à tous les auditeurs
			Vector<MetarListener> l;
			synchronized (this) {
				l = (Vector<MetarListener>) this.metarListeners.clone();
			}
			for (MetarListener item : l) {
				item.handleParse(obj);
			}
	}

	
	// -------------------------------
	// Récepteur d'évènement AeroVille
	
	// executer à la réception d'un AeroVilleEventObject
	/** 
	 * Méthode éxécutée à la réception d'un évènement AeroVilleEventObject.
	 * @param o Evènement reçu.
	 */
	public void handleAeroports(AeroVilleEventObject o) {
		// Construction de la liste des métars et distances
		Vector<String> metars = new Vector<String>();
		Vector<Float> distances = new Vector<Float>();
		
		if(this.test) { // serveur de test
			TestMeteoServer serveurTest = new TestMeteoServer();
			for(String aeroDst : o.getAeroports()) {
				String[] infos = aeroDst.split(",");
				String aero = infos[0];
				Float dst = new Float(infos[1]);
				String met = serveurTest.getAMetarString(aero, 128);
				
				metars.add(met);
				distances.add(new Float(dst));
			}
		}
		else {
			// pas de serveur réel
		}
				
		// Envoi d'un évènement aux auditeur de metar
		if(metars.size() > 0) {
			handleSendMetars(metars, distances);
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
	private boolean test = true;

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
