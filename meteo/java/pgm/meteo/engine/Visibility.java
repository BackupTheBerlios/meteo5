package meteo.engine;

import java.util.Vector;

/**
 * Représentation de la visibilité.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Visibility extends MeteoElt {
	
	private int visibility = 0;

	/**
	 * Constructeur de la classe.
	 * @param m Liste des metars contenant les informations.
	 */
	public Visibility(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}
	
	/**
	 * Calcul les informations sur la visibilité.
	 */
	protected void evalLocalValues() {
		
		// Plusieurs métars :
		if (this.metars.size() > 1) {
			for (Metar m : this.metars) {
				// calcul
			}
		}
		else { // 1 métar
			this.visibility = metars.get(0).getVisibilite();
		}
	}
	
	/**
	 * Accès à la visibilité.
	 * @return La visibilité.
	 */
	public int getVisibility() {
		return visibility;
	}
	
	/**
	 * Renvoie les informations sur la visibilité.
	 * @return Les informations sur la visibilité sous forme de texte.
	 */
	public String toString(){
		return "Visibility is "+visibility+".";
	}
	
}
