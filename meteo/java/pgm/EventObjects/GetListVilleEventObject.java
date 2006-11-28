package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement GetListVille.
 */
public class GetListVilleEventObject extends EventObject {
	private static final long serialVersionUID = 1l;
	
	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public GetListVilleEventObject(Object o) {
		super(o);
	}

}
