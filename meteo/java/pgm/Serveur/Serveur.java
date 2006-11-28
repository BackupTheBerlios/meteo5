package Serveur;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.AeroVilleEventObject;
import EventObjects.MetarEventObject;
import InterfaceListener.AeroVilleListener;
import InterfaceListener.MetarListener;

public class Serveur implements Serializable, AeroVilleListener {


	/** url du serveur réel ou fichier. */
	private String url;

	/** Vrai si on se sert d'un fichier en guise de serveur. */
	private boolean test;

	
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

	
	//---------
	// Ecouteur d'event AeroVille
	
	// executer à la réception d'un AeroVilleEventObject
	/** 
	 * Méthode éxécutée à la réception d'un évènement AeroVilleEventObject.
	 * @param o: évènement reçu.
	 */
	public void handleChercherAeroports(EventObjects.AeroVilleEventObject o) {
		envoieMetar(o);
	}

	
	//--------------
	// Acces propriété
	
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
