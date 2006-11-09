package meteo.engineTest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import junit.framework.TestCase;
import meteo.engine.LocationsFile;

/**
 * Classe testant la classe LocationsFile
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class LocationsFileTest extends TestCase {
	
	/**
	 * Vérifie que getHandle crée le bon LocationFile en vérifiant
	 * que les aéroports récupérés correspondent bien à ceux dans le fichier.
	 * Ainsi que les villes.
	 */
	public void testGetHandle(){
		Vector<String> aeroports = new Vector<String>();
		LocationsFile LocaTest = LocationsFile.getHandle("fichierTest");
		Vector<String> ville = new Vector<String>();

		try {
			// Ouverture du fichier
			BufferedReader br = new BufferedReader(new FileReader("fichierTest"));
			
			// Parcours du fichier
			String tmp;
			while ((tmp = br.readLine()) != null) {
				
				// Découpage ville / aéroports
				String[] villeAero = tmp.split(":");
				ville.add(villeAero[0].trim());
				
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
		assertEquals(LocaTest.getLocations(),ville);
	}
	
	/**
	 * Teste getLocValue, compare le resultat de getLocValue("Vannes")
	 * au contenu du vecteur rempli manuellement.
	 * Fait également le test pour une ville qui n'est pas dans la liste.
	 */
	public void testGetlocValues(){
		Vector<String> aeroValue = new Vector<String>();
		Vector<String> aeroValueInexistant = new Vector<String>();
		LocationsFile LocaTest = LocationsFile.getHandle("fichierTest");
	
		// Le code dans le try sert à remplir le vecteur aerovalue.
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
				
				
				if(villeAero[0].equals("Vannes")){
					// Récupération du code de l'aéroport
					for(String couple : aeroDist) {
						aeroValue.add(couple.trim());
					}
				}
				if(villeAero[0].equals("Inexistant")){
					// Récupération du code de l'aéroport
					for(String couple : aeroDist) {
						aeroValueInexistant.add(couple.trim());
					}
				}
								
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		assertEquals(LocaTest.getLocValues("Vannes"),aeroValue);
		assertEquals(LocaTest.getLocValues("Inexistant"),aeroValueInexistant);
	}


}
