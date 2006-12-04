package EventObjects;

import java.util.EventObject;
import java.util.Vector;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Weather,
 * il contient les informations sur le temps..
 */
public class WeatherEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public WeatherEventObject(Object o) {
		super(o);
	}
	
	
	/** Le temps est-il clair ? */
	private Vector<Boolean> isCavOk = new Vector<Boolean>(); 
		
	/**
	 * Préciser si le temps est clair.
	 * @param isCavOk True si le temps est clair.
	 */
	public void setPressure(Vector<Boolean> isCavOk) {
		this.isCavOk = isCavOk;
	}
	
	/**
	 * Savoir si le temps est clair..
	 * @return True si le temps est clair.
	 */
	public Vector<Boolean> setTemperatureRosee() {
		return this.isCavOk;
	}

}
