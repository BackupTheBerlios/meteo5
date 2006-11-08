package meteo.engineTest;

import meteo.engine.Metar;
import junit.framework.TestCase;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * 
 *  Test du passage de ligne écrite à métar
 *  et de metar à ligne écrite.
 */
public class MetarTest extends TestCase {
	/** Vérifie que le metar créé a bien la forme textuelle eattendue */
	public void testToString(){
		String msg = "METAR VANNES 161800Z AUTO 00315G25KT CAVOK 20/12 1024 =";
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		assertEquals(msg,metar.toString());
	}
	
	/** Vérifie qu'un metar créé à partir d'une ligne est coorrect
	 * en comparant les valeurs de chaque a 
	 */
	public void testParse(){
		String msg = "METAR LFBD 280830Z 03005KT 360V070 9999 FEW006 BKN017 15/12 Q1023 NOSIG=";
		Metar met = Metar.parse(msg);
		assertEquals("LFBD",met.getPlace());
		assertEquals(28, met.getDay());
		assertEquals(8, met.getHour());
		assertEquals(30, met.getMin());
		assertEquals(3, met.getDir());
		assertEquals(0, met.getForce());
		assertEquals(9999, met.getVisibilite());
		assertEquals(15, met.getTemperature());
		assertEquals(12, met.getTemperatureRose());
	}

}
