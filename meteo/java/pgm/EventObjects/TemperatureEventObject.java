package EventObjects;

import java.awt.geom.Arc2D.Float;
import java.util.EventObject;
import java.util.Vector;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement température,
 * il contient la liste des températures à la rosée et actuelle.
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
	private Vector<Float> temperatureRosee = new Vector<Float>(); 
		
	/**
	 * Préciser la température à la rosée.
	 * @param tRosee Température à la rosée.
	 */
	public void setTemperatureRosee(Vector<Float> tRosee) {
		this.temperatureRosee = tRosee;
	}
	
	/**
	 * Obtenir la température à la rosée.
	 * @return La température à la rosée.
	 */
	public Vector<Float> setTemperatureRosee() {
		return this.temperatureRosee;
	}
	
	
	/** Température la plus récentre. */
	private Vector<Float> temperature = new Vector<Float>(); 
		
	/**
	 * Préciser la température..
	 * @param tRosee Température.
	 */
	public void setTemperature(Vector<Float> t) {
		this.temperature = t;
	}
	
	/**
	 * Obtenir la température.
	 * @return La température.
	 */
	public Vector<Float> getTemperature() {
		return this.temperature;
	}

}

