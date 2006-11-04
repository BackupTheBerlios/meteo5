package meteo.engine;

import java.util.Vector;

/** 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 * Représe l'information météo sur la visibilité.
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
		float visiMoy = 0.0f;
		float coef = 0.0f;
		
		for(Metar m : this.metars) {
			// Triangularisation par sommation inverse à la distance
			visiMoy += m.getQnh() * (1 / m.getDistance());
			
			// Coefficient diviseur
			coef += 1 / m.getDistance();		
		}
		
		visiMoy /= coef;

		this.visibility = Math.round(visiMoy);
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
		return "La visibilité est de " + this.visibility + " mète(s).";
	}
	
}
