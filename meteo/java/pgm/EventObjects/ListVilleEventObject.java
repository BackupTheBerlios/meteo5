package EventObjects;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement ListVille,
 * il contient une liste de villes.
 */
public class ListVilleEventObject extends EventObject {
	private static final long serialVersionUID = 1l;
		
	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public ListVilleEventObject(Object o) {
		super(o);
	}
	
	/** Liste des villes pour lesquelles on peut avoir des informations météo. */
	private Vector<String> villes = new Vector<String>();
	
	/**
	 * Préciser la liste des villes.
	 * @param villes Liste des villes.
	 */
	public void setVilles(Vector<String> villes) {
		this.villes = villes;
	}
	
	/**
	 * Accéder à la liste des villes.
	 * @return Liste des villes.
	 */
	public Vector<String> getVilles() {
		return this.villes;
	}

}
