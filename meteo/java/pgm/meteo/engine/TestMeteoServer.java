
package meteo.engine;

import java.io.*; // lecture du fichier

/**
* @author LE NY Clément
* @author CATRIC Jérôme
* @author YANG Yitian
* @author MEHEUT Emmanuel
* Classe TestMeteoServer: elle est utilisée pour simuler le rôle
* du serveur. Elle rendra une ligne Metar aléatoire à partir du fichier
* test donné.
*/

public class TestMeteoServer extends MeteoServer{
	
	/**
	 * @param code
	 * @param timeStamp
	 * @return Un metar sous forme textuelle.
	 */
	public String getAMetarString(String code, long timeStamp){
		
		String metar = "";
		try {
			// Ouverture du fichier
			BufferedReader br = new BufferedReader(new FileReader("fichierTest"));
			
			//ligne à sélectionner  (entre 1 et 14)
			int ligne = (int)(Math.random()*13+1);
			
			int i =0;
			
			while(i<ligne){
				metar += br.readLine();
				i++;
			}
			
			
			

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return metar;
	}
}
