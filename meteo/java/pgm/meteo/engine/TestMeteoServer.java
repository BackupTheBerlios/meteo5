package meteo.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Cette classe est utilisée pour simuler le rôle du serveur. Elle rendra une
 * ligne Metar aléatoire à partir du fichier test donné.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class TestMeteoServer extends MeteoServer {

	/** Emplacement du fichier contenant les metar de test. */
	private String metarsFile = "metars";

	/**
	 * Renvoi un metar selon le code de l'aéroport. Comme il s'agit d'un serveur
	 * de test, les metars renvoyés viennent d'un fichier.
	 * 
	 * @param code
	 *            Code de l'aéroport recherché.
	 * @param timeStamp
	 *            Heure de la meusure.
	 * @return Le metar correspondant à l'aéroport.
	 */
	public String getAMetarString(String code, long timeStamp) {
		String metar = "";

		try {
			// Ouverture du fichier contenant les metars
			BufferedReader br = new BufferedReader(new FileReader(this.metarsFile));

			// Parcours du fichier à la recherche du metar correspondant au code de l'avion.
			String line = "";
			while (metar.equals("") && (line = br.readLine()) != null) {
				String[] strs = line.split(" ");

				// Si il s'agit bien d'un metar
				if (strs[0].equals("METAR")) {

					// Si c'est le bon code d'aéroport
					if (strs[1].equals(code)) {
						metar = line;
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return metar;
	}
	
}
