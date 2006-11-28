package EventObjects;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement AeroVille,
 * il contient une liste d'aéroports.
 */
public class AeroVilleEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public AeroVilleEventObject(Object o) {
		super(o);
	}

	/** Liste d'aéroports. */
	private Vector<String> aeroports = new Vector<String>();
	
	/**
	 * Récupérer la liste des aéroports.
	 * @return Liste des aéroports.
	 */
	public Vector<String> getAeroports() {
		return this.aeroports;
	}
	
	/**
	 * Préciser la liste des aéroports.
	 * @param liste Liste des aéroports.
	 */
	public void setAeroports(Vector<String> liste) {
		this.aeroports = liste;
	}
	
}
