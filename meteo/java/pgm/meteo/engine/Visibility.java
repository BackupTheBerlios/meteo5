package meteo.engine;

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
	 * @param tabMetars ??
	 */
	public Visibility(Metar tabMetars){
		
	}
	
	/**
	 * ???
	 *
	 */
	protected void evalLocalValues() {
		
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
