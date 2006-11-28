package meteo.engineTest;


import java.util.Vector;


import meteo.engine.Metar;
import meteo.engine.Visibility;
import junit.framework.TestCase;

/**
 * Classe testant la classe Visibility.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class VisibilityTest extends TestCase {

	/**
	 * Méthode testant la méthode getVisibility().
	 */
	public void testGetVisibility() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Visibility v = new Visibility(vectMetar);
		
		assertEquals(v.getVisibility(),50);
	}

	/**
	 * Méthode testant la méthode toString().
	 */
	public void testToString() {
		Metar metar = new Metar("VANNES",16,18,00,3,15,25,true,50,20,12,1024);
		Vector<Metar> vectMetar = new Vector<Metar>();
		vectMetar.add(metar);
		Visibility v = new Visibility(vectMetar);
		
		assertEquals(v.toString(),"La visibilité est de 50 mètre(s).");
	}

}
