
package meteo.engine;

import java.util.Vector;

/**
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 * Représentation de la pression d'air.
 */
public class Pressure extends MeteoElt {

	/** Pression. */
	private int pressure=0;
	
	/**
	 * Constructeur.
	 * @param m Liste de métars contenant les informations.
	 */
	public Pressure(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}
	

	/**
	 * Calcul les données fournis par les différents métars.
	 */
	protected void evalLocalValues() {
		float presMoy = 0.0f;
		float coef = 0.0f;
		
		for(Metar m : this.metars) {
			// Triangularisation par sommation inverse à la distance
			presMoy += m.getQnh() * (1 / m.getDistance());
			
			// Coefficient diviseur
			coef += 1 / m.getDistance();		
		}
		
		presMoy /= coef;

		this.pressure = Math.round(presMoy);
	}
	
	/**
	 * Accesseur à la pression.
	 * @return La pression.
	 */
	public int getPressure(){
		return this.pressure;
	}
	
	/**
	 * Renvoie la pression en format texte.
	 * @return La pression sous forme de texte.
	 */
	public String toString(){
		return "La pression atmosphérique au sol est de " + this.pressure + ".";
	}

}
