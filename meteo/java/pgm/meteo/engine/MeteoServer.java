package meteo.engine;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 *  Définit un serveur de métar.
 */
public abstract class MeteoServer {
	
	public abstract String getAMetarString(String code, long timeStamp);
	
}
