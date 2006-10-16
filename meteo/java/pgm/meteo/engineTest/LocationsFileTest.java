package meteo.engineTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import junit.framework.TestCase;
import meteo.engine.LocationsFile;
/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * 
 *  Test des différentes méthodes de LocationsFile.
 */
public class LocationsFileTest extends TestCase {
	
	/**
	 * Vérifie que getHandle crée le bon LocationFile en vérifiant
	 * que les aéroports récupérés correspondent bien à ceux dans le fichier.
	 */
	public void testGetHandle(){
		Vector<String> aeroports = new Vector<String>();
		LocationsFile LocaTest = LocationsFile.getHandle("fichierTest");
		
		try {
			// Ouverture du fichier
			BufferedReader br = new BufferedReader(new FileReader("fichierTest"));
			
			// Parcours du fichier
			String tmp;
			while ((tmp = br.readLine()) != null) {
				
				// Découpage ville / aéroports
				String[] villeAero = tmp.split(":");
				
				// Découpage aéroport / distance à la ville
				String[] aeroDist = villeAero[1].split(";");
				
				// Récupération du code de l'aéroport
				for(String couple : aeroDist) {
					String[] aeros = couple.split(",");
					for(int i = 0; i < aeros.length; i+=2) {
						aeroports.add(aeros[i].trim());
					}
				}
			}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		assertEquals(LocaTest.getAirports(),aeroports);
	}

}
