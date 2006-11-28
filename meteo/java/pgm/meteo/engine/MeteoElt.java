package meteo.engine;

import java.util.Vector;


/**
 * Super classe des éléments météorologique.
 * (pression, temperature, vent, visibilité et temps).
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public abstract class MeteoElt {

	/** Liste des 3 métars. */
	protected Vector<Metar> metars = new Vector<Metar>();

	/** Calcul les données fournis par les différents métars. */
	protected abstract void evalLocalValues();

}
