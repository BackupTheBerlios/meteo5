/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel 
 */
package meteo.engine;

/**
 * 
 * Interface MeteoServer
 *
 */
public interface MeteoServer {
	public String getAMetarString(String code, long timeStamp);
}
