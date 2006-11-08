package meteo.engine;

import java.util.Vector;

/**
 * Classe représentant l'information météo sur la visibilité.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 */
public class Visibility extends MeteoElt {

	/** Element visibilité. */
	private int visibility = 0;

	/**
	 * Constructeur de la classe.
	 * 
	 * @param m
	 *            Liste des metars contenant les informations.
	 */
	public Visibility(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}

	/**
	 * Calcul les informations sur la visibilité.
	 */
	protected void evalLocalValues() {

		if (this.metars.size() > 1) {
			float visiMoy = 0.0f;
			float coef = 0.0f;

			for (Metar m : this.metars) {
				// Triangularisation par sommation inverse à la distance
				visiMoy += m.getQnh() * (1 / m.getDistance());

				// Coefficient diviseur
				if (m.getDistance() != 0) {
					coef += 1 / m.getDistance();
				} else {
					coef += 1;
				}
			}

			visiMoy /= coef;

			this.visibility = Math.round(visiMoy);
		} else if (this.metars.size() != 0) {
			this.visibility = this.metars.get(0).getVisibilite();
		}
	}

	/**
	 * Accès à la visibilité.
	 * 
	 * @return La visibilité.
	 */
	public int getVisibility() {
		return visibility;
	}

	/**
	 * Renvoie les informations sur la visibilité.
	 * 
	 * @return Les informations sur la visibilité sous forme de texte.
	 */
	public String toString() {
		return "La visibilité est de " + this.visibility + " mètre(s).";
	}

}
