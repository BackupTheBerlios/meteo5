package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement SelectedVille,
 * il contient un nom de ville.
 */
public class SelectedVilleEventObject extends EventObject {
	private static final long serialVersionUID = 1l;
		
	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public SelectedVilleEventObject(Object o) {
		super(o);
	}
	
	/** Ville dont on veut les codes d'aéroports. */
	private String ville = ""; 
	
	/**
	 * Préciser la ville.
	 * @param vil Le nom de la ville.
	 */
	public void setVille(String vil) {
		this.ville = vil;
	}
	
	/**
	 * Obtenir le nom de la ville.
	 * @return Le nom de la ville.
	 */
	public String getVille() {
		return this.ville;
	}
	
}
