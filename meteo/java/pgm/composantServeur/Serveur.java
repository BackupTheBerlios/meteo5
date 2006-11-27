package composantServeur;

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

	private Vector<MetarListener> metarListeners = new Vector<MetarListener>();
	
	public synchronized void addMetarListener(MetarListener l) {
		metarListeners.addElement(l);
	}

	public synchronized void removeMetarListener(MetarListener l) {
		metarListeners.removeElement(l);
	}

	// Notifi les ecouteur d'event Metar
	public void envoieMetar(AeroVilleEventObject o) {
		TestMeteoServer serveurTest = new TestMeteoServer();
		RealMeteoServer serveurReel = new RealMeteoServer();
		Vector<String> met = new Vector<String>();
		if (getTest()) {

			MetarEventObject meo = new MetarEventObject(o);
			met.add(serveurTest.getAMetarString(
					o.getAeroports().firstElement(), 128));
			meo.setMetars(met);
		} else {
			// pas de serveur réel
		}
	}

	
	//---------
	// Ecouteur d'event AeroVille
	
	// executer à la réception d'un AeroVilleEventObject
	public void handleChercherAeroports(EventObjects.AeroVilleEventObject o) {
		envoieMetar(o);
	}

	
	//--------------
	// Acces propriété
	
	/**
	 * @return Returns the test.
	 */
	public boolean getTest() {
		return test;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param test
	 *            The test to set.
	 */
	public void setTest(boolean test) {
		this.test = test;
	}

	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
