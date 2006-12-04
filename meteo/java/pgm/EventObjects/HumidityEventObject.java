package EventObjects;

import java.util.EventObject;
import java.util.Vector;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Humidity,
 * il contient les informations sur l'humidité de l'air.
 */
public class HumidityEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public HumidityEventObject(Object o) {
		super(o);
	}

	

	/** Liste des humidités de l'air. */
	private Vector<Integer> humidites = new Vector<Integer>(); 
		
	/**
	 * Préciser la liste des humidités d'air.
	 * @param hum Liste des humidités de l'air.
	 */
	public void setHumidites(Vector<Integer> hum) {
		this.humidites = hum;
	}
	
	/**
	 * Obtenir la liste des humidités de l'air.
	 * @return La liste des humidités de l'air.
	 */
	public Vector<Integer> getHumidites() {
		return this.humidites;
	}
	
	
}
