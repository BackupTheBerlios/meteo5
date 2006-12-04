package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement température traité,
 * il contient les informations sur la température.
 */
public class TemperatureTraiteEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public TemperatureTraiteEventObject(Object o) {
		super(o);
	}
	
	
	/** Températures à la rosée. */
	private float temperatureTraiteRosee = 0.0f; 
		
	/**
	 * Préciser la température à la rosée.
	 * @param tRosee Température à la rosée.
	 */
	public void setTemperatureTraiteRosee(float tRosee) {
		this.temperatureTraiteRosee = tRosee;
	}
	
	/**
	 * Obtenir la température à la rosée.
	 * @return La température à la rosée.
	 */
	public float getTemperatureTraiteRosee() {
		return this.temperatureTraiteRosee;
	}
	
	
	
	/** Liste des températures les plus récentes. */
	private float temperatureTraite = 0.0f; 
		
	/**
	 * Préciser la température.
	 * @param t La température.
	 */
	public void setTemperatureTraite(float t) {
		this.temperatureTraite = t;
	}
	
	/**
	 * Obtenir la température.
	 * @return La température.
	 */
	public float getTemperatureTraite() {
		return this.temperatureTraite;
	}

}
