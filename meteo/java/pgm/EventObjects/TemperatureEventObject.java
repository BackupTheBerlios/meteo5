package EventObjects;

import java.util.EventObject;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement température,
 * il contient la température à la rosée et actuelle.
 */
public class TemperatureEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public TemperatureEventObject(Object o) {
		super(o);
	}
	
	
	/** Température à la rosée. */
	private float temperatureRosee = 0.0f; 
		
	/**
	 * Préciser la température à la rosée.
	 * @param tRosee Température à la rosée.
	 */
	public void setTemperatureRosee(float tRosee) {
		this.temperatureRosee = tRosee;
	}
	
	/**
	 * Obtenir la température à la rosée.
	 * @return La température à la rosée.
	 */
	public float setTemperatureRosee() {
		return this.temperatureRosee;
	}
	
	
	/** Température la plus récentre. */
	private float temperature = 0.0f; 
		
	/**
	 * Préciser la température..
	 * @param tRosee Température.
	 */
	public void setTemperature(float t) {
		this.temperature = t;
	}
	
	/**
	 * Obtenir la température.
	 * @return La température.
	 */
	public float getTemperature() {
		return this.temperature;
	}

}

