package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement visibilityTraite,
 * il contient les informations sur la visibilité.
 */
public class VisibilityTraiteEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public VisibilityTraiteEventObject(Object o) {
		super(o);
	}

	

	/** Visibilité. */
	private int visibility = 0; 
		
	/**
	 * Préciser l'humidités d'air.
	 * @param vis Visibilité.
	 */
	public void setVisibility(int vis) {
		this.visibility = vis;
	}
	
	/**
	 * Obtenir la visibilité.
	 * @return La visibilité.
	 */
	public int getVisibility() {
		return this.visibility;
	}
	


}
