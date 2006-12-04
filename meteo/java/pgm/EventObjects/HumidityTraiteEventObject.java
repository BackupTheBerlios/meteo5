package EventObjects;

import java.util.EventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement humidité traitée,
 * il contient les informations sur l'humidité de l'air après traitement.
 */
public class HumidityTraiteEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public HumidityTraiteEventObject(Object o) {
		super(o);
	}

	

	/** Humidité de l'air. */
	private int humidite = 0; 
		
	/**
	 * Préciser l'humidités d'air.
	 * @param hum Humidité de l'air.
	 */
	public void setHumidite(int hum) {
		this.humidite = hum;
	}
	
	/**
	 * Obtenir l'humidité de l'air.
	 * @return L'humidité de l'air.
	 */
	public int getHumidite() {
		return this.humidite;
	}
	


}
