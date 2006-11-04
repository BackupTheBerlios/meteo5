package meteo.engine;

import java.util.Vector;

/** 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 * Représente un élément météo de type température.
 */
public class Temperature extends MeteoElt {

	/** Température en °C. */
	private int temperature = 0;
	
	/** Température à la rosée en °C. */
	private int dewTemp = 0;
	
	
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
		float tempMoy = 0.0f;
		float dewTempMoy = 0.0f;
		float coef = 0.0f;
		
		for(Metar m : this.metars) {
			// Triangularisation par sommation inverse à la distance
			tempMoy += m.getTemperature() * (1 / m.getDistance());
			dewTempMoy += m.getTemperatureRose() * (1 / m.getDistance());
			
			// Coefficient diviseur
			coef += 1 / m.getDistance();		
		}
		
		tempMoy /= coef;
		dewTempMoy /= coef;
		
		this.temperature = Math.round(tempMoy);
		this.dewTemp = Math.round(dewTempMoy);
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
		String ret;
		ret = "La température a la rosée était de " + this.dewTemp + " °C.\n";
		ret += "La température est de " + this.temperature + " °C.";
		return ret;
	}

}
