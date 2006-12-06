package EventObjects;

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
	private Vector<Integer> temperatureRosee = new Vector<Integer>(); 
		
	/**
	 * Préciser la liste des températures à la rosée.
	 * @param tRosee Liste des températures à la rosée.
	 */
	public void setTemperatureRosee(Vector<Integer> tRosee) {
		this.temperatureRosee = tRosee;
	}
	
	/**
	 * Obtenir la liste des températures à la rosée.
	 * @return La liste des températures à la rosée.
	 */
	public Vector<Integer> getTemperatureRosee() {
		return this.temperatureRosee;
	}
	
	
	/** Liste des températures les plus récentes. */
	private Vector<Integer> temperature = new Vector<Integer>(); 
		
	/**
	 * Préciser la liste des températures.
	 * @param t Liste des températures.
	 */
	public void setTemperature(Vector<Integer> t) {
		this.temperature = t;
	}
	
	/**
	 * Obtenir la liste des températures.
	 * @return La liste des températures.
	 */
	public Vector<Integer> getTemperature() {
		return this.temperature;
	}

}

