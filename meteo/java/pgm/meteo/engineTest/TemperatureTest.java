package meteo.engineTest;

import java.util.Vector;

import Parseur.Metar;


import meteo.engine.Temperature;
import junit.framework.TestCase;

/**
 * Classe testant la classe Temperature.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class TemperatureTest extends TestCase {

	/**
	 * Méthode testant la méthode getTemp().
	 */
	public void testGetTemp() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Temperature t = new Temperature(vectMetar);
				
		assertEquals(t.getTemp(),20);
	}

	/**
	 * Méthode testant la méthode getDewTemp().
	 */
	public void testGetDewTemp() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Temperature t = new Temperature(vectMetar);
		
		assertEquals(t.getDewTemp(),12);
	}

	/**
	 * Méthode testant la méthode toString().
	 */
	public void testToString() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Temperature t = new Temperature(vectMetar);
		
		String ret = "La température a la rosée était de 12 °C.\n";
		ret += "La température est de 20 °C.";
		
		assertEquals(t.toString(),ret);
	}

}
