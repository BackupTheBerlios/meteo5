package meteo.engine;

import java.util.Date;
import java.util.Vector;

/**
 *  
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 * 
 * Interface pour les classes clientes du moteur.
 * Elle donne accès à toutes les fonctionnalités.
 */
public class MeteoReport {

		/** Contient les éléments météo pour la ville. */
		private Vector<MeteoElt> report = null;
		
		/** Ville où l'on veut les infos sur la météo. */
		private String location = "";
		
		/** Usine à metars. */
		private MetarsFactory metarsFact = null;
		
		
		/**
		 * Constructeur de la classe.
		 */
		public MeteoReport(){
			init();
		}
		
		
		/**
		 * Initialise le moteur météo.
		 */
		public void init(){
			// Initialise l'usine de metars
			this.metarsFact = new MetarsFactory();
		}
		

		/**
		 * Génre les différents metar pour la ville donnée.
		 * @param location Ville où l'on veut les informations météo.
		 */
		public void creerReport(String location) {		
			this.location = location;
			
			// Récupération des metars pour la ville.
			Vector<Metar> metars = this.metarsFact.getMetars(this.location, new Date().getTime());
			
			// Récupération des données météo à partir des metars
			MeteoEltFactory meteoEltFac = new MeteoEltFactory();
			this.report = meteoEltFac.getMeteoElts(metars);
		}
	

		/**
		 * Donne les villes où l'on peut avoir des informations météo.
		 * @return Les villes où l'on peut avoir des informations météo.
		 */
		public Vector<String> possibleLocations() {
			return this.metarsFact.getPossibleLocations();
		}
		
		/**
		 * Affiche les informations météo sur la ville.
		 */
		public String toString() {
			String ret = new String();
			
			Weather w = null;
			Temperature t = null;
			Wind wind = null;
			Pressure p = null;
			Visibility v = null;
			
			// Récupération des éléments meteo
			for(MeteoElt e : this.report) {
				if(e.getClass().getName().equals("Temperature")){
					t = (Temperature) e;
				}
				if(e.getClass().getName().equals("Wind")){
					wind = (Wind) e;
				}
				if(e.getClass().getName().equals("Pressure")){
					p = (Pressure) e;
				}
				if(e.getClass().getName().equals("Weather")){
					w = (Weather) e;
				}
				if(e.getClass().getName().equals("Visibility")){
					v = (Visibility) e;
				}
			}
			
			ret += t.toString() + "\n";
			ret += v.toString() + "\n";
			ret += p.toString() + "\n";
			ret += wind.toString() + "\n";
			ret += w.toString();
			
			return ret;
		}
		
}
