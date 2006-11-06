package meteo.engine;

import java.util.Vector;

/**
 * Classe représentant l'élément temps.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Weather extends MeteoElt {

	/** Vrai si le temps est clair. */
	private boolean cavok = true;

	/**
	 * Constructeur de la classe.
	 * 
	 * @param m
	 *            Metars permettant de définir l'état du temps.
	 */
	public Weather(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}

	/**
	 * Calcul les informations sur le temps.
	 */
	protected void evalLocalValues() {
		boolean cavOk = false;
		for (Metar m : this.metars) {
			cavOk &= m.isCavok();
		}

		this.cavok = cavOk;
	}

	/**
	 * Le temps est-il clair.
	 * 
	 * @return True si le temps est clair.
	 */
	public boolean isCAVOK() {
		return cavok;
	}

	/**
	 * Renvoie les informations sur le temps
	 * 
	 * @return Les informations sur le temps.
	 */
	public String toString() {
		String ret;
		if (this.cavok) {
			ret = "Le temps est clair.";
		} else {
			ret = "Le temps n'est pas clair, ce référer à la visibilité.";
		}
		return ret;
	}
}
