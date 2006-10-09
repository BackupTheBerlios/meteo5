
package meteo.engine;

import java.util.Vector;

/**
 * Représentation de la pression d'air.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
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
		
		// Plusieurs métars
		if (this.metars.size() > 1) {
			for(Metar m : this.metars) {
				// Calcul à faire !				
			}
			this.pressure = 0;
		}
		else {
			this.pressure = this.metars.get(0).getQnh();
		}
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
		return "The pressure is "+pressure+".";
	}

}
