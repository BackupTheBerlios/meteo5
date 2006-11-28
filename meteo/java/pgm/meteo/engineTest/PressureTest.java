package meteo.engineTest;

import java.util.Vector;


import meteo.engine.Metar;
import meteo.engine.Pressure;
import junit.framework.TestCase;

/**
 * Classe testant la classe Pressure.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class PressureTest extends TestCase {

	/**
	 * Méthode testant la méthode getPressure().
	 */
	public void testGetPressure() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Pressure qnh = new Pressure(vectMetar);
		
		assertEquals(qnh.getPressure(),1024);
	}

	/**
	 * Méthode testant la méthode toString().
	 */
	public void testToString() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Pressure qnh = new Pressure(vectMetar);
		
		assertEquals(qnh.toString(),"La pression atmosphérique au sol est de 1024.");
	}

}
