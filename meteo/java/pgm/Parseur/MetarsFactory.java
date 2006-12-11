package Parseur;

import java.util.*;

import meteo.engine.MeteoServer;


import AeroVille.LocationsFile;

/**
 * Classe servant à la construction de metar pour une ville.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel 
 *
 */
public class MetarsFactory {

	/**
	 * Contient les différents endroit où les relevés météo peuvent être fait.
	 */
	private LocationsFile places = null;

	/**
	 * Constructeur de la classe.
	 */
	public MetarsFactory() {
		// Récupérer l'instance donnant l'accès aux aéroports, villes...
	//	this.places = LocationsFile.getHandle("places");
	}

	/**
	 * Permet d'obtenir les différents métars selon la ville et le date.
	 * 
	 * @param location Ville demandée.
	 * @param timeStamp Date du metar demandé.
	 * @return Un vecteur contenant de 0 à 3 metar.
	 */
	public Vector<Metar> getMetars(String location, long timeStamp) {
		Vector<Metar> metars = new Vector<Metar>();
/*
		// Code des aéroports et distance par rapport à la ville.
		Vector<String> codes = places.getLocValues(location);

		// Serveur nous donnant les metars.
		MeteoServer server = new TestMeteoServer();

		// Récupération des metars
		for (String c : codes) {
			// Découpage code / distance
			String[] infos = c.split(",");

			// Récupération du message par le serveur.
			String message = server.getAMetarString(infos[0], timeStamp);

			// Si on a bien récupéré un metar
			if (!message.equals("")) {
				// Traitement du message.
				Metar m = Metar.parse(message);
				m.setDistance(Integer.parseInt(infos[1]));
				metars.add(m);
			}
		}
*/
		return metars;
	}

	/**
	 * Accesseur à la liste des villes possibles.
	 * 
	 * @return La liste des villes possibles.
	 */
	public Vector<String> getPossibleLocations() {
		return this.places.getLocations();
	}

}
