package meteo.engine;

import java.util.Vector;

/**
 * Représentation du temps.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class Weather extends MeteoElt{

	private boolean cavok = true;
	
	public Weather(Vector<Metar> m) {
		this.metars = m;
		this.evalLocalValues();
	}
	
	/**
	 * Calcul les informations sur le temps.
	 */
	protected void evalLocalValues() {
		this.cavok = true;
		// ??????
	}
	
	/**
	 * Accès à ???.
	 * @return ???
	 */
	public boolean isCAVOK(){
		return cavok;
	}
	
	
	/**
	 * Renvoie les informations sur le temps
	 * @return Les informations sur le temps.
	 */
	public String toString(){
		return "Weather is "+cavok+".";
	}
}
