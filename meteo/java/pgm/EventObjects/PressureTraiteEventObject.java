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
	
	
	/** Pression moyenne. */
	private int pressureTraite = 0; 
		
	/**
	 * Préciser la pression moyenne.
	 * @param p pression moyenne.
	 */
	public void setPressureTraite(int p) {
		this.pressureTraite = p;
	}
	
	/**
	 * Obtenir la pression moyenne.
	 * @return La pression moyenne.
	 */
	public float getPressureTraite() {
		return this.pressureTraite;
	}


}
