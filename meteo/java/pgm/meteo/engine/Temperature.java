package meteo.engine;

/**
 * Représentation de la température.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Temperature extends MeteoElt {

	private int temperature=0;
	private int dewTemp=0;
	
	/**
	 * Accesseur à la température actuelle.
	 * @return La température actuelle.
	 */
	public int getTemp(){
		return this.temperature;
	}
	
	/**
	 * Accesseur à la température à la rosée.
	 * @return La température à la rosée.
	 */
	public int getDewTemp(){
		return this.dewTemp;
	}
	
	/**
	 * Renvoie la température actuelle et celle à la rosée en format texte.
	 * @return La pression sous forme de texte.
	 */
	public String toString(){
		return "The temperature is "+temperature+" and dew temperature is "+dewTemp+".";
	}

}
