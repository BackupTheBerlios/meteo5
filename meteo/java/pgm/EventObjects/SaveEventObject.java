package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Save.
 */
public class SaveEventObject extends EventObject {
	private static final long serialVersionUID = 1l;
	
	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public SaveEventObject(Object o) {
		super(o);
	}

}
