package meteo.engine;

import java.util.Vector;

/**
 * Classe servant aux prévisions météorologiques basics
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class BasicForecastEngine implements Forecast {

	/** L'élément température. */
	private Temperature temperature;

	/** L'élément vent. */
	private Wind wind;

	/** L'élément pression. */
	private Pressure pressure;

	/** L'élément temps. */
	private Weather weather;

	/** L'élément visibilité. */
	private Visibility visibility;

	/**
	 * Constructeur de la classe.
	 * 
	 * @param v
	 *            Liste des éléments météo.
	 */
	public BasicForecastEngine(Vector<MeteoElt> v) {
		for (MeteoElt e : v) {
			if (e.getClass().getName().equals("Temperature")) {
				this.temperature = (Temperature) e;
			}
			if (e.getClass().getName().equals("Wind")) {
				this.wind = (Wind) e;
			}
			if (e.getClass().getName().equals("Pressure")) {
				this.pressure = (Pressure) e;
			}
			if (e.getClass().getName().equals("Weather")) {
				this.weather = (Weather) e;
			}
			if (e.getClass().getName().equals("Visibility")) {
				this.visibility = (Visibility) e;
			}
		}
	}

	/**
	 * Accès à la température à la rosée en °C.
	 * 
	 * @return La température à la rosée en °C.
	 */
	public int dewTemp() {
		if (temperature != null) {
			return temperature.getDewTemp();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à la pression.
	 * 
	 * @return La pression.
	 */
	public int pressure() {
		if (pressure != null) {
			return pressure.getPressure();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à la température en °C.
	 * 
	 * @return La température en °C.
	 */
	public int temp() {
		if (temperature != null) {
			return temperature.getTemp();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à la visibilité.
	 * 
	 * @return La visibilité.
	 */
	public int visibility() {
		if (visibility != null) {
			return visibility.getVisibility();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à la direction du vent.
	 * 
	 * @return La direction du vent.
	 */
	public int windDir() {
		if (wind != null) {
			return wind.getDirection();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à la vitesse du vent.
	 * 
	 * @return La vitesse du vent.
	 */
	public int windSpeed() {
		if (wind != null) {
			return wind.getSpeed();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à la vitesse maximale du vent.
	 * 
	 * @return La vitesse maximale du vent.
	 */
	public int windSpeedMax() {
		if (wind != null) {
			return wind.getSpeedMax();
		} else {
			return -1;
		}
	}

	/**
	 * Accès à l'état du temps qu'il fait.
	 * 
	 * @return vrai, s'il fait beau temps.
	 */
	public boolean isCavOk() {
		if (weather != null) {
			return weather.isCAVOK();
		} else {
			return false;
		}
	}

}
