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
		String msg = "METAR Vannes 161800Z AUTO 00315G25KT CAVOK 20/12 1024 =";
		Metar metar = new Metar("Vannes",16,18,00,3,15,25,true,50,20,12,1024);
		assertEquals(msg,metar.toString());
	}

}
