package Parseur;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import EventObjects.VisibilityEventObject;
import EventObjects.MetarEventObject;
import EventObjects.PressureEventObject;
import EventObjects.TemperatureEventObject;
import EventObjects.WeatherEventObject;
import EventObjects.WindEventObject;
import InterfaceListener.VisibilityListener;
import InterfaceListener.MetarListener;
import InterfaceListener.PressureListener;
import InterfaceListener.TemperatureListener;
import InterfaceListener.WeatherListener;
import InterfaceListener.WindListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant le parsage des metar en éléments météo.
 */
public class Parseur implements Serializable, MetarListener {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur vide nécessaire pour créer un composant.
	 */
	public Parseur() {

	}

	// ------------------------------------------------------------------------
	// Récepteur d'évènement Metar : contient une liste de metar à parser

	/**
	 * Méthode appelée lorsqu'un évènement Metar est reçu. Elle consiste à
	 * appeler le parsage des metar et à appeler à l'envoi des différentes
	 * évènements concernant les éléments météo.
	 * 
	 * @param e
	 *            Objet contenant les metars.
	 */
	public void handleParse(MetarEventObject e) {

		// Parsage des metars recuperés
		Vector<Metar> metars = new Vector<Metar>();
		for (String inf : e.getMetars()) {
			metars.add(Metar.parse(inf));
		}
		
		// Appel de l'envoi des évènements des différents éléments
		handleTemperature(metars, e.getDistances());
		handlePressure(metars, e.getDistances());
		handleWeather(metars, e.getDistances());
		handleWind(metars, e.getDistances());
		handleVisibility(metars, e.getDistances());
	}

	// ------------------------------------------------------------------------
	// Source de d'évènement Temperature : temperature a la rosee + temperature

	/** Liste de composants écoutant l'évènement. */
	private Vector<TemperatureListener> temperatureListener = new Vector<TemperatureListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addTemperatureListener(TemperatureListener l) {
		this.temperatureListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeTemperatureListener(TemperatureListener l) {
		this.temperatureListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur la
	 * température à tous les composants qui l'écoute.
	 * 
	 * @param metar
	 *            Metar contenant les informations sur la température.
	 */
	private void handleTemperature(Vector<Metar> metars, Vector<Float> distances) {

		// Création d'un objet pour l'évènement
		TemperatureEventObject teo = new TemperatureEventObject(this);

		// Récupération des informations voulues
		Vector<Integer> temps = new Vector<Integer>();
		Vector<Integer> tempsRosee = new Vector<Integer>();
		for (Metar m : metars) {
			temps.add(m.getTemperature());
			tempsRosee.add(m.getTemperatureRose());
		}

		// Donner les infos à l'évènement
		teo.setTemperature(temps);
		teo.setTemperatureRosee(tempsRosee);
		teo.setDistances(distances);

		// Envoi à tous les auditeurs
		Vector<TemperatureListener> l;
		synchronized (this) {
			l = (Vector<TemperatureListener>) this.temperatureListener.clone();
		}
		for (TemperatureListener item : l) {
			item.handleCalcul(teo);
		}
	}

	// ------------------------------------------------------------------------
	// Source de d'évènement Pressure : pression atmosphérique

	/** Liste de composants écoutant l'évènement. */
	private Vector<PressureListener> pressureListener = new Vector<PressureListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addPressureListener(PressureListener l) {
		this.pressureListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removePressureListener(PressureListener l) {
		this.pressureListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur la
	 * pression à tous les composants qui l'écoute.
	 * 
	 * @param metar
	 *            Metar contenant les informations.
	 */
	private void handlePressure(Vector<Metar> metars, Vector<Float> distances) {

		// Création d'un objet pour l'évènement
		PressureEventObject peo = new PressureEventObject(this);

		// Récupération des informations voulues
		Vector<Integer> pres = new Vector<Integer>();
		for (Metar m : metars) {
			pres.add(m.getQnh());
		}

		// Donner les infos à l'évènement
		peo.setPressure(pres);
		peo.setDistances(distances);

		// Envoi à tous les écoutant
		Vector<PressureListener> l;
		synchronized (this) {
			l = (Vector<PressureListener>) this.pressureListener.clone();
		}
		for (PressureListener pl : l) {
			pl.handleCalcul(peo);
		}
	}

	// ------------------------------------------------------------------------
	// Source de d'évènement Weather : information sur le temps

	/** Liste de composants écoutant l'évènement. */
	private Vector<WeatherListener> weatherListener = new Vector<WeatherListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addWeatherListener(WeatherListener l) {
		this.weatherListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeWeatherListener(WeatherListener l) {
		this.weatherListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur la
	 * température à tous les composants qui l'écoute.
	 * 
	 * @param metar
	 *            Metar contenant les informations sur la température.
	 */
	private void handleWeather(Vector<Metar> metars, Vector<Float> distances) {

		// Création d'un objet pour l'évènement
		WeatherEventObject weo = new WeatherEventObject(this);

		// Récupération des informations voulues
		Vector<Boolean> cavOk = new Vector<Boolean>();
		for (Metar m : metars) {
			cavOk.add(m.isCavok());
		}

		// Donner les infos à l'évènement
		weo.setIsCavOk(cavOk);
		weo.setDistances(distances);

		// Envoi à tous les écoutant
		Vector<WeatherListener> l;
		synchronized (this) {
			l = (Vector<WeatherListener>) this.weatherListener.clone();
		}
		for (WeatherListener wl : l) {
			wl.handleCalcul(weo);
		}

	}

	// ------------------------------------------------------------------------
	// Source de d'évènement Wind : informations sur le vent

	/** Liste de composants écoutant l'évènement. */
	private Vector<WindListener> windListener = new Vector<WindListener>();

	/**
	 * Ajoute un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addWindListener(WindListener l) {
		this.windListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeWindListener(WindListener l) {
		this.windListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur la
	 * température à tous les composants qui l'écoute. wl
	 * 
	 * @param metar
	 *            Metar contenant les informations sur la température.
	 */
	private void handleWind(Vector<Metar> metars, Vector<Float> distances) {

		// Création d'un objet pour l'évènement
		WindEventObject weo = new WindEventObject(this);

		// Récupération des informations voulues
		Vector<Integer> dir = new Vector<Integer>();
		Vector<Integer> force = new Vector<Integer>();
		Vector<Integer> forceMax = new Vector<Integer>();
		for (Metar m : metars) {
			dir.add(m.getDir());
			force.add(m.getForce());
			forceMax.add(m.getForceMax());
		}

		// Donner les infos à l'évènement
		weo.setDirections(dir);
		weo.setForces(force);
		weo.setForcesMax(forceMax);
		weo.setDistances(distances);

		// Envoi à tous les écoutant
		Vector<WindListener> l;
		synchronized (this) {
			l = (Vector<WindListener>) this.windListener.clone();
		}
		for (WindListener wl : l) {
			wl.handleCalcul(weo);
		}
	}

	// ------------------------------------------------------------------------
	// Source de d'évènement Visibility

	/** Liste de composants écoutant l'évènement. */
	private Vector<VisibilityListener> visibilityListener = new Vector<VisibilityListener>();

	/**
	 * Ajoute un composant écoutant l'évènement. visibility
	 * 
	 * @param l
	 *            Composant écoutant l'évènement.
	 */
	public synchronized void addVisibilityListener(VisibilityListener l) {
		this.visibilityListener.add(l);
	}

	/**
	 * Supprime un composant écoutant l'évènement.
	 * 
	 * @param l
	 *            Composant à supprimer.
	 */
	public synchronized void removeVisibilityListener(VisibilityListener l) {
		this.visibilityListener.remove(l);
	}

	/**
	 * Méthode chargée d'envoyer un évènement contenant les informations sur la
	 * température à tous les composants qui l'écoute.
	 * 
	 * @param metar
	 *            Metar contenant les informations sur la température.
	 */
	private void handleVisibility(Vector<Metar> metars, Vector<Float> distances) {

		// Création d'un objet pour l'évènement
		VisibilityEventObject veo = new VisibilityEventObject(this);

		// Récupération des informations voulues
		Vector<Integer> vis = new Vector<Integer>();
		for (Metar m : metars) {
			vis.add(m.getVisibilite());
		}

		// Donner les infos à l'évènement
		veo.setVisibilites(vis);
		veo.setDistances(distances);

		// Envoi à tous les écoutant
		Vector<VisibilityListener> l;
		synchronized (this) {
			l = (Vector<VisibilityListener>) this.visibilityListener.clone();
		}
		for (VisibilityListener wl : l) {
			wl.handleCalcul(veo);
		}
	}


}
