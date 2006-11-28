package meteo.engine;

import java.util.Date;
import java.util.Vector;


/**
 * Classe servant a faire un rapport métérologique pour un aréoport choisi.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 */
public class MeteoReport {

	/** Contient les éléments météo pour la ville. */
	private Vector<MeteoElt> report = null;

	/** Ville où l'on veut les infos sur la météo. */
	private String location = "";

	/** Usine à metars. */
	private MetarsFactory metarsFact = null;

	/**
	 * Constructeur de la classe.
	 */
	public MeteoReport() {
		// Initialise l'usine de metars
		this.metarsFact = new MetarsFactory();
	}

	/**
	 * Génére les différents metar pour la ville donnée.
	 * 
	 * @param location
	 *            Ville où l'on veut les informations météo.
	 */
	public void creerReport(String location) {
		this.location = location;

		// Récupération des metars pour la ville.
		Vector<Metar> metars = this.metarsFact.getMetars(this.location,	new Date().getTime());

		// Récupération des données météo à partir des metars
		MeteoEltFactory meteoEltFac = new MeteoEltFactory();
		this.report = meteoEltFac.getMeteoElts(metars);
	}

	/**
	 * Donne les villes où l'on peut avoir des informations météo.
	 * 
	 * @return Les villes où l'on peut avoir des informations météo.
	 */
	public Vector<String> possibleLocations() {
		return this.metarsFact.getPossibleLocations();
	}

	/**
	 * Affiche les informations météo sur la ville.
	 */
	public String toString() {
		String ret = new String();

		Weather w = null;
		Temperature t = null;
		Wind wind = null;
		Pressure p = null;
		Visibility v = null;

		// Récupération des éléments meteo
		for (MeteoElt e : this.report) {
			if (e.getClass().getName().equals("meteo.engine.Temperature")) {
				t = (Temperature) e;
			}
			if (e.getClass().getName().equals("meteo.engine.Wind")) {
				wind = (Wind) e;
			}
			if (e.getClass().getName().equals("meteo.engine.Pressure")) {
				p = (Pressure) e;
			}
			if (e.getClass().getName().equals("meteo.engine.Weather")) {
				w = (Weather) e;
			}
			if (e.getClass().getName().equals("meteo.engine.Visibility")) {
				v = (Visibility) e;
			}
		}

		if (t != null) {
			ret += t.toString() + "\n";
		}
		if (v != null) {
			ret += v.toString() + "\n";
		}
		if (p != null) {
			ret += p.toString() + "\n";
		}
		if (wind != null) {
			ret += wind.toString() + "\n";
		}
		if (w != null) {
			ret += w.toString() + "\n";
		}

		return ret;
	}

}
