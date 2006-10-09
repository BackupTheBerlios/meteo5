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

		// Plusieurs métars.
		if (this.metars.size() > 1) {
			for (Metar m : this.metars) {
				// ???
			}
		} else { // Pour 1 métar :
			this.direction = this.metars.get(0).getDir();
			this.speed = this.metars.get(0).getForce();
			this.maxSpeed = this.metars.get(0).getForceMax();
		}
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
		return "Wind speed is "+speed+". \n Wind max speed is "+maxSpeed+". \n Wind direction is "+direction+".";
	}
}
