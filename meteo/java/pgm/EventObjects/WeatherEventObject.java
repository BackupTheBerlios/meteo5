package EventObjects;

import java.util.EventObject;
import java.util.Vector;


/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Objet échangé dans un évènement Weather,
 * il contient les informations sur le temps..
 */
public class WeatherEventObject extends EventObject {
	private static final long serialVersionUID = 1l;

	/**
	 * Constructeur.
	 * @param o Composant émetteur.
	 */
	public WeatherEventObject(Object o) {
		super(o);
	}
	
	
	/** Liste de l'information sur la clarté du temps. */
	private Vector<Boolean> isCavOk = new Vector<Boolean>(); 
		
	/**
	 * Préciser la liste de l'imformation sur la clarté du temps.
	 * @param isCavOk Liste de l'imformation sur la clarté du temps.
	 */
	public void setIsCavOk(Vector<Boolean> isCavOk) {
		this.isCavOk = isCavOk;
	}
	
	/**
	 * Obtenir la liste de l'imformation sur la clarté du temps.
	 * @return La liste de l'imformation sur la clarté du temps.
	 */
	public Vector<Boolean> getIsCavOk() {
		return this.isCavOk;
	}

}
