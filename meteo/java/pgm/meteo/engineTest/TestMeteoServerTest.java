package meteo.engineTest;

import junit.framework.TestCase;
import meteo.engine.TestMeteoServer;
/**
 * Test de TestmeteoServer
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 */

public class TestMeteoServerTest extends TestCase {
	/** 
	 * 2 test en 1: le premier sert à vérifier que pour un code
	 * présent dans la liste, la fonction getAMetarString retourne bien
	 * la ligne correspondant au code.
	 * Le 2ème vérifie que dans le cas contraire on reçoit bien la chîne vide.
	 *
	 */
	public void testGetAMetarString(){
		TestMeteoServer server = new TestMeteoServer();
		String test = server.getAMetarString("LFPG",128);
		String test2 = server.getAMetarString("hjfh",128);
		assertEquals("METAR LFPG 280830Z 03005KT 360V070 9999 FEW006 BKN017 15/12 Q1023 NOSIG=",test);
		assertEquals("",test2);
	}

}
