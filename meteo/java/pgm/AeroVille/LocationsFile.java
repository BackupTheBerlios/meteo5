package AeroVille;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Classe permet d'obtenir les villes et les aéroports où l'on peut avoir des
 * informations.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class LocationsFile {

	/** Chemin du fichier contenant la liste des villes et les aéroports. */
	private String FilePath = "";

	/**
	 * Constructeur de la classe.
	 * 
	 * @param FilePath
	 *            Fichier contenant la liste des villes.
	 */
	public LocationsFile(String FilePath) {
		this.FilePath = FilePath;
	}


	/**
	 * Récupère la liste de tous les aéroports.
	 * 
	 * @return La liste des codes des aéroports.
	 * 
	 */
	public Vector<String> getAirports() {
		Vector<String> aeroports = new Vector<String>();
		try {
			// Ouverture du fichier
			BufferedReader br = new BufferedReader(
					new FileReader(this.FilePath));

			// Parcours du fichier
			String tmp;
			while ((tmp = br.readLine()) != null) {

				// Découpage ville / aéroports
				String[] villeAero = tmp.split(":");

				// Découpage aéroport / distance à la ville
				String[] aeroDist = villeAero[1].split(";");

				// Récupération du code de l'aéroport
				for (String couple : aeroDist) {
					String[] aeros = couple.split(",");
					for (int i = 0; i < aeros.length; i += 2) {
						aeroports.add(aeros[i].trim());
					}
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return aeroports;
	}

	/**
	 * Récupère la liste des villes.
	 * 
	 * @return La liste des villes.
	 */
	public Vector<String> getLocations() {
		Vector<String> ville = new Vector<String>();
		try {
			// Ouverture du fichier
			BufferedReader br = new BufferedReader(
					new FileReader(this.FilePath));
			
			// Parcours du fichier
			String tmp;
			while ((tmp = br.readLine()) != null) {
				// Découpage ville / aéroports
				String[] villeAero = tmp.split(":");
				ville.add(villeAero[0].trim());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return ville;
	}

	/**
	 * Récupère la liste des aéroports ainsi que la distance par rapport à la
	 * ville.
	 * 
	 * @param locations
	 *            La ville.
	 * @return La liste des codes d'aéroport ainsi que leur distance.
	 */
	public Vector<String> getLocValues(String locations) {
		Vector<String> aeroValue = new Vector<String>();
		try {
			// Ouverture du fichier
			BufferedReader br = new BufferedReader(
					new FileReader(this.FilePath));

			// Parcours du fichier
			String tmp;
			while ((tmp = br.readLine()) != null) {

				// Découpage ville / aéroports
				String[] villeAero = tmp.split(":");

				// Découpage aéroport / distance à la ville
				String[] aeroDist = villeAero[1].split(";");

				// Si c'est la bonne ville :
				if (villeAero[0].equals(locations)) {
					// Récupération du code de l'aéroport
					for (String couple : aeroDist) {
						aeroValue.add(couple.trim());
					}
				}
			}
			if (aeroValue.size() == 0) {
				System.err.println("Erreur la ville " + locations
						+ " n'a pas été trouvée.");
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		return aeroValue;
	}

}
