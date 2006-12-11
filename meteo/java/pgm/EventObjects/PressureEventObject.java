package EventObjects;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Pressure,
 * il contient la pression atmosphérique.
 */
public class PressureEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public PressureEventObject(Object o) {
		super(o);
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
	
	/** Pression atmosphérique. */
	private Vector<Integer> pressure = new Vector<Integer>(); 
		
	/**
	 * Préciser la pression.
	 * @param pres Pression.
	 */
	public void setPressure(Vector<Integer> pres) {
		this.pressure = pres;
	}
	
	/**
	 * Obtenir la pression.
	 * @return La pression.
	 */
	public Vector<Integer> setTemperatureRosee() {
		return this.pressure;
	}
	
}
