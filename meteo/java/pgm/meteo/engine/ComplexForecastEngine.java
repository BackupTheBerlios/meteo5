package meteo.engine;

import java.util.Vector;

/**
 * Classe servant aux prévisions météorologiques complexes
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class ComplexForecastEngine extends BasicForecastEngine {

	/**
	 * Constructeur de la classe.
	 * 
	 * @param v
	 *            Liste des éléments météo.
	 */
	public ComplexForecastEngine(Vector<MeteoElt> v) {
		super(v);
	}

}
