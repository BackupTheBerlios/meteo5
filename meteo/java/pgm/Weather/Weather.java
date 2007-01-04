package Weather;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.WeatherEventObject;
import EventObjects.WeatherTraiteEventObject;
import InterfaceListener.WeatherListener;
import InterfaceListener.WeatherTraiteListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant le temps global.
 */
public class Weather implements WeatherListener, Serializable {
	private static final long serialVersionUID = 1;

	/** Constructeur vide du composant. */
	public Weather() {

	}
	
	
	// --------------------------------------
	// Source d'évènements WeatherTraite
	
	/** liste des écouteurs d'évènements Metar */
	private Vector<WeatherTraiteListener> weatherTraiteListeners = new Vector<WeatherTraiteListener>();

	/**
	 * Ajout d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addWeatherTraiteListener(WeatherTraiteListener l) {
		this.weatherTraiteListeners.addElement(l);
	}

	/**
	 * Supression d'un écouteur.
	 * 
	 * @param l
	 *            Ecouteur à supprimer de la liste des abbonnés.
	 */
	public synchronized void WeatherTraiteListener(
			WeatherTraiteListener l) {
		this.weatherTraiteListeners.removeElement(l);
	}

	/**
	 * Méthode qui envoie un évènements contenant les infos sur le vent.
	 * 
	 * @param dir
	 *            Direction du vent en degré.
	 * @param fmax
	 *            Force maximale du vent.
	 * @param f
	 *            Force actuelle du vent.
	 */
	private void handleSendWeather(boolean cav) {
		// Création de l'objet de l'évènement
		WeatherTraiteEventObject obj = new WeatherTraiteEventObject(this);
		obj.setIsCavOkTraite(cav);

		// Envoi des évènements à tous les auditeurs
		Vector<WeatherTraiteListener> l;
		synchronized (this) {
			l = (Vector<WeatherTraiteListener>) this.weatherTraiteListeners.clone();
		}
		for (WeatherTraiteListener item : l) {
			item.handleTraite(obj);
		}
	}
	
	
	// --------------------------------------
	// Réception d'évènement Weather

	/**
	 * Méthode exécutée lors de la réception d'un évènement Wind.
	 * 
	 * @param e
	 *            Objet de l'évènement.
	 */
	public void handleCalcul(WeatherEventObject e) {
		boolean cav = calcul(e.getIsCavOk(), e.getDistances());
		handleSendWeather(cav);
	}

	// --------------------
	// Calcul des moyennes

	/**
	 * Calcul les informations.
	 */
	protected boolean calcul(Vector<Boolean> data, Vector<Float> dst) {
		boolean cavOk = false;
		for (boolean m : data) {
			cavOk &= m;
		}

		return cavOk;
	}
	
	
}
