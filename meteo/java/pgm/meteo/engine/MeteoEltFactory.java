package meteo.engine;

import java.util.*;


/**
 * Classe construisant des MeteosElt.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class MeteoEltFactory {

	/**
	 * Méthode qui récupère les informations relatives à une ville.
	 * 
	 * @param metars
	 *            Les metar contenant les informations.
	 * @return L'ensemble des éléments météo.
	 */
	public Vector<MeteoElt> getMeteoElts(Vector<Metar> metars) {
		Vector<MeteoElt> meteoElt = new Vector<MeteoElt>();

		// Calcul les informations du vent
		Wind wind = new Wind(metars);
		wind.evalLocalValues();
		meteoElt.add(wind);

		// Calcul les informations de la temperature
		Temperature temp = new Temperature(metars);
		temp.evalLocalValues();
		meteoElt.add(temp);

		// Calcul les informations de la visibilité
		Visibility visibilite = new Visibility(metars);
		visibilite.evalLocalValues();
		meteoElt.add(visibilite);

		// Calcul les informations sur la pression
		Pressure pression = new Pressure(metars);
		pression.evalLocalValues();
		meteoElt.add(pression);

		// Calcul les informations sur le temps
		Weather weather = new Weather(metars);
		weather.evalLocalValues();
		meteoElt.add(weather);

		return meteoElt;
	}
}
