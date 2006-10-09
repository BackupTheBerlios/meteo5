/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel 
 */
package meteo.engine;

import java.text.*;
import java.util.*;

/**
 * classe Methar
 * sert à stocker les informations concernant
 * un endroit à un instant donné.
 */
public class Metar {
	 /** Pour conversion ente noeud et km/h. */
    public static final		double KT2KMH = 1.851998438;

    /** Pour conversion ente noeud et m/s. */
    public static final		double  KT2MS = 0.5144439844;
    
    
    
    /** Format une nombre sur 2 chiffres. */
    public static final	 NumberFormat DF2 = new DecimalFormat ("00");
    /** Format une nombre sur 3 chiffres. */
    public static final	 NumberFormat DF3 = new DecimalFormat ("000");
    /** Format une nombre sur 4 chiffres. */
    public static final	 NumberFormat DF4 = new DecimalFormat ("0000");
	
	
	
    /** Pour debugage rapide. */
    static boolean debug = false;
	/** Code internationnal de l'aéroport.*/
    private String place;
    /** Jour du mois, heure et minute du bulletin Metar. */
    private int day, hour, min;
    /** Direction du vent. */
    private int dir;
    /** Force du vent en noeud et force des rafales. */
    private int force, forceMax;

    /** Information simplifiee en cas de beau temps (CAVOK = ).*/
    private boolean cavok;
    /** Visibilite en metres. */
    private int visibilite;
    /** Temperature sous abris et temperature de point de rose. */
    private int temperature, temperatureRose;
    /** Pression atmospherique au sol. */
    private int qnh;
    /** Temps actuel */
    private String tempsPresent;
    /** Hauteur des nuages */
    private int hauteurNuages;
    /** Description des nuages */
    private String nuages;
    /** distance par rapport au site de mesure */
    private int siteDistance;
    
    
    
    
    /** constructeur "complet de la classe Metar*/
    public Metar (String place, int day, int hour, int min, int dir, int f, int fm, boolean cav, int v, int t, int tr, int qnh) {
    	this.place = place;
    	this.day = day;
    	this.hour = hour;
    	this.min = min;
    	this.dir = dir;
    	force = f;
    	forceMax = fm;
    	this.cavok = cav;
    	visibilite = v;
    	temperature = t;
    	temperatureRose = tr;
    	this.qnh = qnh;
        }

	/**
	 * @return Returns the cavok.
	 */
	public boolean isCavok() {
		return cavok;
	}
	

	/**
	 * @return Returns the day.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @return Returns the dir.
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * @return Returns the force.
	 */
	public int getForce() {
		return force;
	}

	/**
	 * @return Returns the forceMax.
	 */
	public int getForceMax() {
		return forceMax;
	}

	/**
	 * @return Returns the hauteurNuage.
	 */
	public int getHauteurNuages() {
		return hauteurNuages;
	}

	/**
	 * @return Returns the hour.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * @return Returns the min.
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @return Returns the nuages.
	 */
	public String getNuages() {
		return nuages;
	}

	/**
	 * @return Returns the place.
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @return Returns the qnh.
	 */
	public int getQnh() {
		return qnh;
	}

	/**
	 * @return Returns the siteDistance.
	 */
	public int getSiteDistance() {
		return siteDistance;
	}

	/**
	 * @return Returns the temperature.
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * @return Returns the temperatureRose.
	 */
	public int getTemperatureRose() {
		return temperatureRose;
	}

	/**
	 * @return Returns the tempsPresent.
	 */
	public String getTempsPresent() {
		return tempsPresent;
	}

	/**
	 * @return Returns the visibilite.
	 */
	public int getVisibilite() {
		return visibilite;
	}
	
	/**
	 * Fonction ToString, sert à l'affichage d'un Metar
	 */
	public String toString () {
		String result = "METAR " + place + " " + DF2.format (day) +
		                DF2.format (hour) + DF2.format (min) + "Z AUTO " +
				DF3.format (dir) + DF2.format (force);
		if (forceMax > force)
		    result += "G" + DF2.format (forceMax);
		result += "KT ";
		if (cavok) {
		    	result += "CAVOK ";
		} else {
		    result += DF4.format (visibilite)+" ";
		}
		result += (DF2.format (temperature) + "/" +
		           DF2.format (temperatureRose) + " ").replaceAll ("-", "M");
		result += DF4.format (qnh) +" =";
		return result;
	    }
	
	/**
	 * méthode parse, Analyse une ligne de texte pour en extraire un bulletin.
	 * @param msg
	 * @param distance
	 * @return
	 */
	public static Metar parse (String msg) {
		/*
		 *  Un simple parseur basé sur StringTokenizer ; à améliorer et
		 *  compléter
		 */
		String place = null;
		int d = 0, h = 0, m = 0;
		int dir = 0, f = 0, fm = 0;
		boolean cav = false;
		int v = 0;
		int t = 0, tr = 0;
		int qnh = 0;

		msg = msg.toUpperCase ().trim ();

		if (msg.endsWith ("="))
		    msg = msg.substring (0, msg.length () - 1).trim ();
		if (debug)
		    System.err.println ("msg:<"+msg+">");
		StringTokenizer st = new StringTokenizer (msg);

		String token = st.nextToken ();
		// "METAR"
		if ("METAR".equals (token))
		    token = st.nextToken ();

		// aeroport = "LF..."
		place = token;
		token = st.nextToken ();

		// date/heure = "YYGGggZ"
		if (token.endsWith ("Z")) {
		    d = Integer.parseInt (token.substring (0, 2));
		    h = Integer.parseInt (token.substring (2, 4));
		    m = Integer.parseInt (token.substring (4, 6));
		    token = st.nextToken ();
		}

		// AUTO
		if ("AUTO".equals (token))
		    token = st.nextToken ();

		if (token.matches ("(VRB.*)|(0000)|(9999)")) {
		    // VRB (<=3kt) | 00000 (calme) | 99999 (variable)
		} else {
		    // dddffGrr{KT,KMH,MPS} | VRB (<=3kt) | 00000 (calme) | 99999 (variable)
		    dir = Integer.parseInt (token.substring (0, 3));
		    f = Integer.parseInt (token.substring (3, 5));
		    if (token.charAt (5) == 'G')
			fm = Integer.parseInt (token.substring (6, 8));
		    if (token.endsWith ("KMH")) {
			f /= KT2KMH;
			fm /= KT2KMH;
		    } else if (token.endsWith ("MPS")) {
			f /= KT2MS;
			fm /= KT2MS;
		    }
		}
		token = st.nextToken ();

		while (!token.matches ("(CAVOK)|(\\d{4}.*)"))
		    // resynchro
		    token = st.nextToken ();

		//CAVOK
		if ("CAVOK".equals (token)) {
		    cav = true;
		    token = st.nextToken ();
		} else {
		    // visibilite (en metre) = "xxxx" | "9999" (> 10km)
		    v = Integer.parseInt (token.substring (0, 4));
		    token = st.nextToken ();


		    if (debug)
			System.err.println ("token:<"+token+">");

		    if (token.matches ("[-=+]?(\\w\\w)+")) {
			// phenomene {-=+}{XX}* ou //
			if (debug)
			    System.err.println ("phenomene:<"+token+">");

			token = st.nextToken ();
		    } else if ("//".equals (token)) {
			token = st.nextToken ();
		    }

		}

		while (token.matches ("\\w\\w\\w\\d\\d\\d")) {
		    // etat des nuages "AAA999"
		    token = st.nextToken ();
		}

	 	while (!token.matches ("[M0-9]\\d*/[M0-9]\\d*")) {
		    // resynchro
	 	    token = st.nextToken ();
	 	}

		// temperature (abris/rose)
		if (debug)
		    System.err.println ("T:<"+token+">");
		int pos = token.indexOf ("/");
		token = token.replaceAll ("M", "-");
		t = Integer.parseInt (token.substring (0, pos));
		tr = Integer.parseInt (token.substring (pos+1));

		token = st.nextToken ();

		// QNH (=pression atmospherique au sol)
		if (token.startsWith ("Q"))
		    token = token.substring (1);
		qnh = Integer.parseInt (token);


		// la suite du Metar contient des tendances

		return new Metar (place, d, h, m, dir, f, fm, cav, v, t, tr, qnh);
	    }
}
