package meteo.engine;

import java.util.Vector;

/**
 * Classe caractérisant la pression d'air.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 */
public class Pressure extends MeteoElt {

	/** L'élément pression. */
	private int pressure = 0;

	/**
	 * Constructeur de la classe.
	 * 
	 * @param m
	 *            Liste de métars contenant les informations.
	 */
	public Pressure(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}

	/**
	 * Calcul les données fournis par les différents métars.
	 */
	protected void evalLocalValues() {

		if (this.metars.size() > 1) {
			float presMoy = 0.0f;
			float coef = 0.0f;

			for (Metar m : this.metars) {
				// Triangularisation par sommation inverse à la distance
				presMoy += m.getQnh() * (1 / m.getDistance());

				// Coefficient diviseur
				if (m.getDistance() != 0) {
					coef += 1 / m.getDistance();
				} else {
					coef += 1;
				}
			}

			presMoy /= coef;

			this.pressure = Math.round(presMoy);
		} else if (this.metars.size() != 0) {
			this.pressure = this.metars.get(0).getQnh();
		}
	}

	/**
	 * Accesseur à la pression.
	 * 
	 * @return La pression.
	 */
	public int getPressure() {
		return this.pressure;
	}

	/**
	 * Renvoie la pression en format texte.
	 * 
	 * @return La pression sous forme de texte.
	 */
	public String toString() {
		return "La pression atmosphérique au sol est de " + this.pressure + ".";
	}

}
