package EventObjects;

import java.util.EventObject;

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

	
	
	/** Pression atmosphérique. */
	private float pressure = 0.0f; 
		
	/**
	 * Préciser la pression.
	 * @param pres Pression.
	 */
	public void setPressure(float pres) {
		this.pressure = pres;
	}
	
	/**
	 * Obtenir la pression.
	 * @return La pression.
	 */
	public float setTemperatureRosee() {
		return this.pressure;
	}
	
}
