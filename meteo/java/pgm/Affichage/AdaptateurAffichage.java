package Affichage;

import java.lang.reflect.Method;
import java.util.HashMap;

import EventObjects.PressureTraiteEventObject;
import EventObjects.TemperatureTraiteEventObject;
import EventObjects.VisibilityTraiteEventObject;
import EventObjects.WeatherTraiteEventObject;
import EventObjects.WindTraiteEventObject;
import InterfaceListener.PressureTraiteListener;
import InterfaceListener.TemperatureTraiteListener;
import InterfaceListener.VisibilityTraiteListener;
import InterfaceListener.WeatherTraiteListener;
import InterfaceListener.WindTraiteListener;
import Pressure.Pressure;
import Temperature.Temperature;
import Visibility.Visibility;
import Weather.Weather;
import Wind.Wind;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Adapteur servant pour différencier d'où provient le texte contenant les
 * informations météo : des prévisions ou des infos météo "normales". Il trie
 * les évènements Affichage.
 */
public class AdaptateurAffichage implements TemperatureTraiteListener,
		WindTraiteListener, VisibilityTraiteListener, WeatherTraiteListener, PressureTraiteListener {

	public AdaptateurAffichage() {

	}

	/** Objet sur lequel l'adaptateur s'applique. */
	private Affichage cible;

	/**
	 * Constructeur créant l'adaptateur.
	 * 
	 * @param cible
	 *            Composant cible de l'adaptateur.
	 */
	public AdaptateurAffichage(Affichage cible) {
		this.cible = cible;
	}

	// -----------------------------------------
	// Réception d'évènements TemperatureTraite

	/** Liste des fonctions à appeler selon la source de l'évènement reçu. */
	private HashMap<Temperature, Method> methodTemperatureTraite = new HashMap<Temperature, Method>();

	/**
	 * Permet d'ajouter la méthode a appeler lors de la réception d'un évènement
	 * TemperatureTraite d'une source précise.
	 * 
	 * @param source
	 *            Source de l'évènement.
	 * @param nomMethode
	 *            Nom de la méthode à appeler.
	 * @throws NoSuchMethodException
	 *             Ne tient pas compte des méthodes dont le nom est inexistant.
	 */
	public void addTemperature(Temperature source, String nomMethode,
			Class[] args) throws NoSuchMethodException {

		// Récupération de la méthode à appeler dans le composant cible
		Method methode = this.cible.getClass().getMethod(nomMethode, args);

		// Sauvegarde dans la table
		this.methodTemperatureTraite.put(source, methode);

		// S'inscrire chez la source comme récepteur d'évènement
		source.addTemperatureTraiteListener(this);
	}

	/**
	 * Méthode appelée lors de la réception d'un évènement TemperatureTraite.
	 * 
	 * @param e
	 *            Object ayant envoyé l'évènement.
	 */
	public void handleTraite(TemperatureTraiteEventObject e) {
		try {
			// Récupération de la méthode à appeler sur le composant cible
			Method methode = this.methodTemperatureTraite.get(e.getSource());

			// Création des arguments de la méthode
			Object[] args = new Object[] { e };

			// Execution de la méthode correspondante
			methode.invoke(this.cible, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ----------------------------------
	// Réception d'évènements WindTraite

	/** Liste des fonctions à appeler selon la source de l'évènement reçu. */
	private HashMap<Wind, Method> methodWindTraite = new HashMap<Wind, Method>();

	/**
	 * Permet d'ajouter la méthode a appeler lors de la réception d'un évènement
	 * TemperatureTraite d'une source précise.
	 * 
	 * @param source
	 *            Source de l'évènement.
	 * @param nomMethode
	 *            Nom de la méthode à appeler.
	 * @throws NoSuchMethodException
	 *             Ne tient pas compte des méthodes dont le nom est inexistant.
	 */
	public void addWind(Wind source, String nomMethode, Class[] args)
			throws NoSuchMethodException {

		// Récupération de la méthode à appeler dans le composant cible
		Method methode = this.cible.getClass().getMethod(nomMethode, args);

		// Sauvegarde dans la table
		this.methodWindTraite.put(source, methode);

		// S'inscrire chez la source comme récepteur d'évènement
		source.addWindTraiteListener(this);
	}

	/**
	 * Méthode appelée lors de la réception d'un évènement WindTraite.
	 * 
	 * @param e
	 *            Object ayant envoyé l'évènement.
	 */
	public void handleTraite(WindTraiteEventObject e) {
		try {
			// Récupération de la méthode à appeler sur le composant cible
			Method methode = this.methodWindTraite.get(e.getSource());

			// Création des arguments de la méthode
			Object[] args = new Object[] { e };

			// Execution de la méthode correspondante
			methode.invoke(this.cible, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	// ---------------------------------------
	// Réception d'évènements VisibilityTraide

	/** Liste des fonctions à appeler selon la source de l'évènement reçu. */
	private HashMap<Visibility, Method> methodVisibilityTraite = new HashMap<Visibility, Method>();

	/**
	 * Permet d'ajouter la méthode a appeler lors de la réception d'un évènement
	 * VisibilityTraide d'une source précise.
	 * 
	 * @param source
	 *            Source de l'évènement.
	 * @param nomMethode
	 *            Nom de la méthode à appeler.
	 * @throws NoSuchMethodException
	 *             Ne tient pas compte des méthodes dont le nom est inexistant.
	 */
	public void addVisibility(Visibility source, String nomMethode, Class[] args)
			throws NoSuchMethodException {

		// Récupération de la méthode à appeler dans le composant cible
		Method methode = this.cible.getClass().getMethod(nomMethode, args);

		// Sauvegarde dans la table
		this.methodVisibilityTraite.put(source, methode);

		// S'inscrire chez la source comme récepteur d'évènement
		source.addVisibilityTraiteListener(this);
	}

	/**
	 * Méthode appelée lors de la réception d'un évènement VisibilityTraite.
	 * 
	 * @param e
	 *            Object ayant envoyé l'évènement.
	 */
	public void handleTraite(VisibilityTraiteEventObject e) {
		try {
			// Récupération de la méthode à appeler sur le composant cible
			Method methode = this.methodVisibilityTraite.get(e.getSource());
			
			// Création des arguments de la méthode
			Object[] args = new Object[] { e };

			// Execution de la méthode correspondante
			methode.invoke(this.cible, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	// ---------------------------------------
	// Réception d'évènements WeatherTraide

	/** Liste des fonctions à appeler selon la source de l'évènement reçu. */
	private HashMap<Weather, Method> methodWeatherTraite = new HashMap<Weather, Method>();

	/**
	 * Permet d'ajouter la méthode a appeler lors de la réception d'un évènement
	 * WeatherTraite d'une source précise.
	 * 
	 * @param source
	 *            Source de l'évènement.
	 * @param nomMethode
	 *            Nom de la méthode à appeler.
	 * @throws NoSuchMethodException
	 *             Ne tient pas compte des méthodes dont le nom est inexistant.
	 */
	public void addWeather(Weather source, String nomMethode, Class[] args)
			throws NoSuchMethodException {

		// Récupération de la méthode à appeler dans le composant cible
		Method methode = this.cible.getClass().getMethod(nomMethode, args);

		// Sauvegarde dans la table
		this.methodWeatherTraite.put(source, methode);

		// S'inscrire chez la source comme récepteur d'évènement
		source.addWeatherTraiteListener(this);
	}

	/**
	 * Méthode appelée lors de la réception d'un évènement WeatherTraite.
	 * 
	 * @param e
	 *            Object ayant envoyé l'évènement.
	 */
	public void handleTraite(WeatherTraiteEventObject e) {
		try {
			// Récupération de la méthode à appeler sur le composant cible
			Method methode = this.methodWeatherTraite.get(e.getSource());
			
			// Création des arguments de la méthode
			Object[] args = new Object[] { e };

			// Execution de la méthode correspondante
			methode.invoke(this.cible, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	// ---------------------------------------
	// Réception d'évènements PressureTraite

	/** Liste des fonctions à appeler selon la source de l'évènement reçu. */
	private HashMap<Pressure, Method> methodPressureTraite = new HashMap<Pressure, Method>();

	/**
	 * Permet d'ajouter la méthode a appeler lors de la réception d'un évènement
	 * PressureTraite d'une source précise.
	 * 
	 * @param source
	 *            Source de l'évènement.
	 * @param nomMethode
	 *            Nom de la méthode à appeler.
	 * @throws NoSuchMethodException
	 *             Ne tient pas compte des méthodes dont le nom est inexistant.
	 */
	public void addPressure(Pressure source, String nomMethode, Class[] args)
			throws NoSuchMethodException {

		// Récupération de la méthode à appeler dans le composant cible
		Method methode = this.cible.getClass().getMethod(nomMethode, args);

		// Sauvegarde dans la table
		this.methodPressureTraite.put(source, methode);

		// S'inscrire chez la source comme récepteur d'évènement
		source.addPressureTraiteListener(this);
	}

	/**
	 * Méthode appelée lors de la réception d'un évènement PressureTraite.
	 * 
	 * @param e
	 *            Object ayant envoyé l'évènement.
	 */
	public void handleTraite(PressureTraiteEventObject e) {
		try {
			// Récupération de la méthode à appeler sur le composant cible
			Method methode = this.methodPressureTraite.get(e.getSource());
			
			// Création des arguments de la méthode
			Object[] args = new Object[] { e };

			// Execution de la méthode correspondante
			methode.invoke(this.cible, args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

	
}
