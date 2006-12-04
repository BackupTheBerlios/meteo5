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
	
	
	
	/** Liste de direction du vent (en degré). */
	private Vector<Integer> directions = new Vector<Integer>(); 
		
	/**
	 * Préciser la liste des directions du vent.
	 * @param dir Liste des directions du vent.
	 */
	public void setDirections(Vector<Integer> dir) {
		this.directions = dir;
	}
	
	/**
	 * Obtenir la liste des directions du vent.
	 * @return La liste des directions du vent.
	 */
	public Vector<Integer> getDirections() {
		return this.directions;
	}
	
	
	/** Force du vent. */
	private Vector<Integer> forces = new Vector<Integer>();
		
	/**
	 * Préciser la force du vent..
	 * @param f La force du vent.
	 */
	public void setForces(Vector<Integer> f) {
		this.forces = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public Vector<Integer> getForces() {
		return this.forces;
	}
	

	/** Force maximale du vent. */
	private Vector<Integer> forcesMax = new Vector<Integer>(); 
		
	/**
	 * Préciser la force du vent.
	 * @param f La force du vent.
	 */
	public void setForcesMax(Vector<Integer> f) {
		this.forcesMax = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public Vector<Integer> getForcesMax() {
		return this.forcesMax;
	}
	
}
