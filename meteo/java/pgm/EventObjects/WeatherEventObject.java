package EventObjects;

import java.util.EventObject;


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
	private boolean isCavOk = false; 
		
	/**
	 * Préciser si le temps est clair.
	 * @param isCavOk True si le temps est clair.
	 */
	public void setPressure(boolean isCavOk) {
		this.isCavOk = isCavOk;
	}
	
	/**
	 * Savoir si le temps est clair..
	 * @return True si le temps est clair.
	 */
	public boolean setTemperatureRosee() {
		return this.isCavOk;
	}

}
