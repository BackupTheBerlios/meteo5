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

	
	
	/** Pression atmosphérique. */
	private Vector<Float> pressure = new Vector<Float>(); 
		
	/**
	 * Préciser la pression.
	 * @param pres Pression.
	 */
	public void setPressure(Vector<Float> pres) {
		this.pressure = pres;
	}
	
	/**
	 * Obtenir la pression.
	 * @return La pression.
	 */
	public Vector<Float> setTemperatureRosee() {
		return this.pressure;
	}
	
}
