package meteo.engine;

/**
 * Classe définisant un serveur de metar.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public abstract class MeteoServer {

	/**
	 * Méthode retournant un metar.
	 * 
	 * @param code
	 *            code aéroport.
	 * @param timeStamp
	 *            heure.
	 * @return un metar.
	 */
	public abstract String getAMetarString(String code, long timeStamp);

}
