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
public class ComplexForecastEngine2 extends BasicForecastEngine {

	/**
	 * Constructeur de la classe.
	 * 
	 * @param v
	 *            Liste des éléments météo.
	 */
	public ComplexForecastEngine2(Vector<MeteoElt> v) {
		super(v);
	}
}
