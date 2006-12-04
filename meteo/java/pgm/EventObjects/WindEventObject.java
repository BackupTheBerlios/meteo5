package EventObjects;

import java.util.EventObject;
import java.util.Vector;

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
	private Vector<Integer> direction = new Vector<Integer>(); 
		
	/**
	 * Préciser la direction du vent.
	 * @param dir Direction du vent.
	 */
	public void setTemperatureRosee(Vector<Integer> dir) {
		this.direction = dir;
	}
	
	/**
	 * Obtenir la direction du vent.
	 * @return La direction du vent.
	 */
	public Vector<Integer> setTemperatureRosee() {
		return this.direction;
	}
	
	
	/** Force du vent. */
	private Vector<Integer> force = new Vector<Integer>();
		
	/**
	 * Préciser la force du vent..
	 * @param f La force du vent.
	 */
	public void setForce(Vector<Integer> f) {
		this.force = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public Vector<Integer> getForece() {
		return this.force;
	}
	

	/** Force maximale du vent. */
	private Vector<Integer> forceMax = new Vector<Integer>(); 
		
	/**
	 * Préciser la force du vent.
	 * @param f La force du vent.
	 */
	public void setForceMax(Vector<Integer> f) {
		this.forceMax = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public Vector<Integer> getForceMax() {
		return this.forceMax;
	}
	
}
