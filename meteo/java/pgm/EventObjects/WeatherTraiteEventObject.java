package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement temps traité,
 * il contient les informations sur le temps.
 */
public class WeatherTraiteEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public WeatherTraiteEventObject(Object o) {
		super(o);
	}
	
	
	/** Le temps est-il clair ? */
	private boolean isCavOkTraite = false;
		
	/**
	 * Préciser si le temps est clair.
	 * @param isCavOk True si le temps est clair.
	 */
	public void setIsCavOkTraite(boolean isCavOk) {
		this.isCavOkTraite = isCavOk;
	}
	
	/**
	 * Savoir si le temps est clair..
	 * @return True si le temps est clair.
	 */
	public boolean getIsCavOkTraite() {
		return this.isCavOkTraite;
	}
	

}
