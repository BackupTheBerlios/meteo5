package meteo.engineTest;

import java.util.Vector;


import meteo.engine.Metar;
import meteo.engine.MetarsFactory;
import junit.framework.TestCase;

/**
 * Classe testant la classe MetarsFacotry.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class MetarsFactoryTest extends TestCase {
	
	/**
	 * Ce test vérifie que la fonction rend bien un 
	 * vecteur de metar non null.
	 */
	public void testGetMetars(){
		Vector<Metar> metars = null;
		MetarsFactory Mfact = new MetarsFactory();
		metars = Mfact.getMetars("Brest",1234);
		assertNotNull(metars);
	}
}
