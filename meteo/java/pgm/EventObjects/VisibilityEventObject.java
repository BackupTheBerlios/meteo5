package EventObjects;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Visibility,
 * il contient les informations sur la visibilité de l'air.
 */
public class VisibilityEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public VisibilityEventObject(Object o) {
		super(o);
	}

	

	/** Liste des humidités de l'air. */
	private Vector<Integer> visibilites = new Vector<Integer>(); 
		
	/**
	 * Préciser la liste des visibilités.
	 * @param hum Liste des visibilités.
	 */
	public void setVisibilites(Vector<Integer> hum) {
		this.visibilites = hum;
	}
	
	/**
	 * Obtenir la liste des visibilités.
	 * @return La liste des visibilités.
	 */
	public Vector<Integer> getVisibilites() {
		return this.visibilites;
	}
	
	
}
