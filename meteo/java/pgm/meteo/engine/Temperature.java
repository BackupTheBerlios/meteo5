package meteo.engine;

import java.util.Vector;

/**
 * Représentation de la température.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Temperature extends MeteoElt {

	/** Température en °C. */
	private int temperature=0;
	
	/** Température à la rosée en °C. */
	private int dewTemp=0;
	
	/**
	 * Constructeur de l'élément température. 
	 * @param m Liste de métars contenant les informations.
	 */
	public Temperature(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}
	
	/**
	 * Calcul les informations sur la température.
	 */
	public void evalLocalValues() {

		// Plusieurs métars :
		if (this.metars.size() > 1) {
			for(Metar m : this.metars) {
				// Calcul ???
			}
		}
		else {
			this.temperature = metars.get(0).getTemperature();
			this.dewTemp = metars.get(0).getTemperatureRose();
		}
	}
	
	
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
