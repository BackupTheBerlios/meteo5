package meteo.engine;

import java.util.Vector;

/**
 * Représentation du vent.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Wind extends MeteoElt {

	/** Vitesse du vent. */
	private int speed=0;
	
	/** Vitesse maximale du vent dans la journée. */
	private int maxSpeed=0;
	
	/** Direction du vent. */
	private int direction=0;
	
	/**
	 * Constructeur.
	 * 
	 * @param m Liste de métars contenant les informations.
	 */
	public Wind(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}

	/**
	 * Calcul les informations concernant le vent.
	 */
	protected void evalLocalValues() {
		this.direction = 0;
		this.speed = 0;
		this.maxSpeed = 0;

		float speedMoy = 0.0f;
		float maxSpeedMoy = 0.0f;
		float dirMoy = 0.0f;
		float coef = 0.0f;
		
		for(Metar m : this.metars) {
			// Triangularisation par sommation inverse à la distance
			speedMoy += m.getForce() * (1 / m.getDistance());
			maxSpeedMoy += m.getForceMax() * (1 / m.getDistance());
			dirMoy += m.getDir() * (1 / m.getDistance());
			
			// Coefficient diviseur
			coef += 1 / m.getDistance();		
		}
		
		speedMoy /= coef;
		maxSpeedMoy /= coef;
		dirMoy /= coef;
		
		this.direction = Math.round(dirMoy);
		this.maxSpeed = Math.round(maxSpeedMoy);
		this.speed = Math.round(speedMoy);
	}
	
	
	/**
	 * Accès à la vitesse du vent.
	 * @return La vitesse du vent.
	 */
	public int getSpeed(){
		return this.speed;
	}
	
	/**
	 * Accès à la vitesse maximum du vent.
	 * @return La vitesse maximum du vent.
	 */
	public int getSpeedMax(){
		return this.maxSpeed;
	}
	
	/**
	 * Accès à la direction du vent.
	 * @return La direction  du vent.
	 */
	public int getDirection(){
		return this.direction;
	}
	
	
	/**
	 * Renvoie les informations sur le vent.
	 * @return Les informations sous forme de texte.
	 */
	public String toString(){
		String ret;
		ret = "La vitesse maximale du vent a été de " + this.maxSpeed + " km/h.\n";
		ret += "La vitesse du vent est de " + this.speed + " km/h.\n";
		ret += "La direction du vent vaut " + this.direction + ".";
		return ret;
	}
}
