package EventObjects;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Metar,
 * il contient une liste de metar.
 */
public class MetarEventObject extends EventObject {
	public static final long serialVersionUID = 1;
		
	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public MetarEventObject (Object o){
		super(o);
	}
	
	
	/** Liste des métars. */
	private Vector<String> metars = new Vector<String>();
	
	/**
	 * Obtenir la liste des métars.
	 * @return La liste des métars.
	 */
	public Vector<String> getMetars(){
		return this.metars;
	}

	/**
	 * Préciser la liste des métars.
	 * @param metars Liste des métars.
	 */	
	public void setMetars(Vector<String> metars) {
		this.metars = metars;
	}
	
	
	/** Liste des distances entre la ville et l'aéroport. */
	private Vector<Float> distances = new Vector<Float>();
	
	/**
	 * Obtenir la liste des métars.
	 * @return La liste des métars.
	 */
	public Vector<Float> getDistances(){
		return this.distances;
	}

	/**
	 * Préciser la liste des métars.
	 * @param metars Liste des métars.
	 */	
	public void setDistances(Vector<Float> dst) {
		this.distances = dst;
	}

	
	
}
