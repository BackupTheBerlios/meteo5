package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement pression traité,
 * il contient les informations sur la pression.
 */
public class PressureTraiteEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public PressureTraiteEventObject(Object o) {
		super(o);
	}
	
	
	/** Températures à la rosée. */
	private int pressureTraite = 0; 
		
	/**
	 * Préciser la température à la rosée.
	 * @param tRosee Température à la rosée.
	 */
	public void setPressureTraite(int p) {
		this.pressureTraite = p;
	}
	
	/**
	 * Obtenir la température à la rosée.
	 * @return La température à la rosée.
	 */
	public float getPressureTraite() {
		return this.pressureTraite;
	}


}
