
package meteo.engine;

/**
 * Représentation de la pression d'air.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Pressure extends MeteoElt {

	private int pressure=0;
	
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
