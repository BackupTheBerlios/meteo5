package meteo.engineTest;

import java.util.Vector;

import Parseur.Metar;


import meteo.engine.Weather;
import junit.framework.TestCase;

/**
 * Classe testant la classe Weather.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class WeatherTest extends TestCase {

	/**
	 * Méthode testant la méthode isCAVOK().
	 */
	public void testIsCAVOK() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Weather tps = new Weather(vectMetar);
		
		assertEquals(tps.isCAVOK(),true);
	}

	/**
	 * Méthode testant la méthode toString().
	 */
	public void testToString() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Weather tps = new Weather(vectMetar);
		
		assertEquals(tps.toString(),"Le temps est clair.");
	}

}
