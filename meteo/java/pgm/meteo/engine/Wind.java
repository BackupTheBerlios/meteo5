package meteo.engine;

/**
 * Représentation du vent.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Wind {

	private int speed=0;
	private int maxSpeed=0;
	private String direction="none";
	
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
	public String getDirection(){
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
