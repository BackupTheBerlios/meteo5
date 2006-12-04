package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Wind,
 * il contient les informations sur le vent.
 */
public class WindEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public WindEventObject(Object o) {
		super(o);
	}
	
	
	
	/** Direction du vent (en degré). */
	private int direction = 0; 
		
	/**
	 * Préciser la direction du vent.
	 * @param dir Direction du vent.
	 */
	public void setTemperatureRosee(int dir) {
		this.direction = dir;
	}
	
	/**
	 * Obtenir la direction du vent.
	 * @return La direction du vent.
	 */
	public int setTemperatureRosee() {
		return this.direction;
	}
	
	
	/** Force du vent. */
	private int force = 0; 
		
	/**
	 * Préciser la force du vent..
	 * @param f La force du vent.
	 */
	public void setForce(int f) {
		this.force = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public int getForece() {
		return this.force;
	}
	

	/** Force maximale du vent. */
	private int forceMax = 0; 
		
	/**
	 * Préciser la force du vent.
	 * @param f La force du vent.
	 */
	public void setForceMax(int f) {
		this.forceMax = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public int getForceMax() {
		return this.forceMax;
	}
	
}
