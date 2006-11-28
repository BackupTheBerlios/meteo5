package meteo.engineTest;

import java.util.Vector;


import meteo.engine.Metar;
import meteo.engine.Wind;
import junit.framework.TestCase;

/**
 * Classe testant la classe Wind.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class WindTest extends TestCase {

	/**
	 * Méthode testant la méthode getSpeed().
	 */
	public void testGetSpeed(){
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Wind vent = new Wind(vectMetar);
		
		assertEquals(vent.getSpeed(),15);
	}
	
	/**
	 * Méthode testant la méthode getSpeedMax().
	 */
	public void testGetSpeedMax(){
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Wind vent = new Wind(vectMetar);
	
		assertEquals(vent.getSpeedMax(),25);
	}
	
	/**
	 * Méthode testant la méthode getDirection().
	 */
	public void testGetDirection(){
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Wind vent = new Wind(vectMetar);
		
		assertEquals(vent.getDirection(),3);
	}
	
	/**
	 * Méthode testant la méthode toString().
	 */
	public void testToString(){
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Wind vent = new Wind(vectMetar);
		String ret = "La vitesse maximale du vent a été de 25 km/h.\n";
		ret += "La vitesse du vent est de 15 km/h.\n";
		ret += "La direction du vent vaut 3.";
		assertEquals(vent.toString(),ret);
	}
	
}

