package EventObjects;

import java.util.EventObject;
import java.util.Vector;

public class WindTraiteEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public WindTraiteEventObject(Object o) {
		super(o);
	}
	
	
	
	/** Direction du vent (en degré). */
	private int directionTraite = 0; 
		
	/**
	 * Préciser la direction du vent.
	 * @param dir Direction du vent.
	 */
	public void setDirectionTraite(int dir) {
		this.directionTraite = dir;
	}
	
	/**
	 * Obtenir la direction du vent.
	 * @return La direction du vent.
	 */
	public int getDirectionTraite() {
		return this.directionTraite;
	}
	
	
	/** Force du vent. */
	private int forceTraite = 0;
		
	/**
	 * Préciser la force du vent..
	 * @param f La force du vent.
	 */
	public void setForceTraite(int f) {
		this.forceTraite = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public int getForceTraite() {
		return this.forceTraite;
	}
	

	/** Force maximale du vent. */
	private int forceMaxTraite = 0; 
		
	/**
	 * Préciser la force du vent.
	 * @param f La force du vent.
	 */
	public void setForceMaxTraite(int f) {
		this.forceMaxTraite = f;
	}
	
	/**
	 * Obtenir la force du vent.
	 * @return La force du vent.
	 */
	public int getForceMaxTraite() {
		return this.forceMaxTraite;
	}

	/** Liste des distances par rapport au point de calcul */
	private Vector<Float> distances = new Vector<Float>();
	
	/**
	 * Préciser la liste des distances.
	 * @param d Liste des distances.
	 */
	public void setDistances(Vector<Float> d) {
		this.distances = d;
	}
	
	/**
	 * Obtenir la liste des distances.
	 * @return La liste des disttances.
	 */
	public Vector<Float> getDistances() {
		return this.distances;
	}
	
}
