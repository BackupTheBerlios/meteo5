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
	
	/** Vérifie qu'un metar créé à partir d'une ligne est coorrect */
	public void testParse(){
		String msg = "METAR VANNES 161800Z AUTO 00315G25KT CAVOK 20/12 1024 =";
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Metar metarMsg = Metar.parse(msg);
		assertEquals(metar.getNuages(),metarMsg.getNuages());
		assertEquals(metar.getPlace(),metarMsg.getPlace());
		assertEquals(metar.getTempsPresent(),metarMsg.getTempsPresent());
		assertEquals(metar.getDay(),metarMsg.getDay());
		assertEquals(metar.getDir(),metarMsg.getDir());
		assertEquals(metar.getDistance(),metarMsg.getDistance());
		assertEquals(metar.getForce(),metarMsg.getForce());
		assertEquals(metar.getForceMax(),metarMsg.getForceMax());
		assertEquals(metar.getHauteurNuages(),metarMsg.getHauteurNuages());
		assertEquals(metar.getHour(),metarMsg.getHour());
		assertEquals(metar.getMin(),metarMsg.getMin());
		assertEquals(metar.getQnh(),metarMsg.getQnh());
		assertEquals(metar.getSiteDistance(),metarMsg.getSiteDistance());
		assertEquals(metar.getTemperature(),metarMsg.getTemperature());
		assertEquals(metar.getTemperatureRose(),metarMsg.getTemperatureRose());
		assertEquals(metar.getVisibilite(),metarMsg.getVisibilite());
		assertEquals(metar.isCavok(),metarMsg.isCavok());
	}

}
