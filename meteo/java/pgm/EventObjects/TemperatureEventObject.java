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
	
	
	/** Liste des températures à la rosée. */
	private Vector<Float> temperatureRosee = new Vector<Float>(); 
		
	/**
	 * Préciser la liste des températures à la rosée.
	 * @param tRosee Liste des températures à la rosée.
	 */
	public void setTemperatureRosee(Vector<Float> tRosee) {
		this.temperatureRosee = tRosee;
	}
	
	/**
	 * Obtenir la liste des températures à la rosée.
	 * @return La liste des températures à la rosée.
	 */
	public Vector<Float> getTemperatureRosee() {
		return this.temperatureRosee;
	}
	
	
	/** Liste des températures les plus récentes. */
	private Vector<Float> temperature = new Vector<Float>(); 
		
	/**
	 * Préciser la liste des températures.
	 * @param t Liste des températures.
	 */
	public void setTemperature(Vector<Float> t) {
		this.temperature = t;
	}
	
	/**
	 * Obtenir la liste des températures.
	 * @return La liste des températures.
	 */
	public Vector<Float> getTemperature() {
		return this.temperature;
	}

}

