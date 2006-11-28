package meteo.engine;

import java.util.Vector;


/**
 * Classe représentant un élément météo de type température.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 */
public class Temperature extends MeteoElt {

	/** Température en °C. */
	private int temperature = 0;

	/** Température à la rosée en °C. */
	private int dewTemp = 0;

	/**
	 * Constructeur de l'élément température.
	 * 
	 * @param m
	 *            Liste de métars contenant les informations.
	 */
	public Temperature(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}

	/**
	 * Calcul les informations sur la température.
	 */
	public void evalLocalValues() {

		if (this.metars.size() > 1) {
			float tempMoy = 0.0f;
			float dewTempMoy = 0.0f;
			float coef = 0.0f;

			for (Metar m : this.metars) {
				// Triangularisation par sommation inverse à la distance
				tempMoy += m.getTemperature() * (1 / m.getDistance());
				dewTempMoy += m.getTemperatureRose() * (1 / m.getDistance());

				// Coefficient diviseur
				if (m.getDistance() != 0) {
					coef += 1 / m.getDistance();
				} else {
					coef += 1;
				}
			}

			tempMoy /= coef;
			dewTempMoy /= coef;

			this.temperature = Math.round(tempMoy);
			this.dewTemp = Math.round(dewTempMoy);
		} else if (this.metars.size() != 0) {
			this.temperature = this.metars.get(0).getTemperature();
			this.dewTemp = this.metars.get(0).getTemperatureRose();
		}
	}

	/**
	 * Accesseur à la température actuelle.
	 * 
	 * @return La température actuelle.
	 */
	public int getTemp() {
		return this.temperature;
	}

	/**
	 * Accesseur à la température à la rosée.
	 * 
	 * @return La température à la rosée.
	 */
	public int getDewTemp() {
		return this.dewTemp;
	}

	/**
	 * Renvoie la température actuelle et celle à la rosée en format texte.
	 * 
	 * @return La pression sous forme de texte.
	 */
	public String toString() {
		String ret;
		ret = "La température a la rosée était de " + this.dewTemp + " °C.\n";
		ret += "La température est de " + this.temperature + " °C.";
		return ret;
	}

}
