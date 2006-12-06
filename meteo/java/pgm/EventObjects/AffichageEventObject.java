package EventObjects;

import java.util.EventObject;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Affichage,
 * il contient le texte à afficher des différentes informations.
 */
public class AffichageEventObject extends EventObject {
	private static final long serialVersionUID = 1l;
	
	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public AffichageEventObject(Object o) {
		super(o);
	}
	
	
	/** Texte à afficher par le client. */
	private String texte = new String();
	
	/**
	 * Préciser le texte.
	 * @param txt Texte.
	 */
	public void setTexte(String txt) {
		this.texte = txt;
	}
	
	/**
	 * Accéder au texte.
	 * @return Texte.
	 */
	public String getTexte() {
		return this.texte;
	}


}
