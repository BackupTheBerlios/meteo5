package Parseur;

import java.text.*;
import java.util.*;

/**
 * Classe Methar servant à stocker les informations concernant un endroit à un
 * instant donné.
 * 
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 */
public class Metar {

	/*
	 * Constante de conversion : =========================
	 */

	/** Pour conversion ente noeud et km/h. */
	public static final double KT2KMH = 1.851998438;

	/** Pour conversion ente noeud et m/s. */
	public static final double KT2MS = 0.5144439844;

	/*
	 * Constante de formatage : ========================
	 */

	/** Format une nombre sur 2 chiffres. */
	public static final NumberFormat DF2 = new DecimalFormat("00");

	/** Format une nombre sur 3 chiffres. */
	public static final NumberFormat DF3 = new DecimalFormat("000");

	/** Format une nombre sur 4 chiffres. */
	public static final NumberFormat DF4 = new DecimalFormat("0000");

	/*
	 * Attributs de classe : =====================
	 */

	/** Code internationnal de l'aéroport. */
	private String place;

	/** Distance entre la ville et l'aéroport. */
	private int distance = 0;

	/** Jour du mois, heure et minute du bulletin Metar. */
	private int day, hour, min;

	/** Direction du vent. */
	private int dir;

	/** Force du vent en noeud et force des rafales. */
	private int force, forceMax;

	/** Information simplifiee en cas de beau temps (CAVOK = ). */
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

	// ==============================

	/**
	 * Constructeur complet de la classe métar.
	 * 
	 * @param place
	 *            Code de l'aéroport.
	 * @param day
	 *            Jour de la mesure.
	 * @param hour
	 *            Heure de la mesure.
	 * @param min
	 *            Minute de la mesure.
	 * @param dir
	 *            Direction du vent.
	 * @param f
	 *            Force du vent.
	 * @param fm
	 *            Force maximale du vent.
	 * @param cav
	 *            Beau temps ?
	 * @param v
	 *            Visibilité.
	 * @param t
	 *            Température.
	 * @param tr
	 *            Température à la rosée.
	 * @param qnh
	 *            Pression atmosphérique.
	 */
	public Metar(String place, int day, int hour, int min, int dir, int f,
			int fm, boolean cav, int v, int t, int tr, int qnh) {
		this.place = place;
		this.day = day;
		this.hour = hour;
		this.min = min;
		this.dir = dir;
		this.force = f;
		this.forceMax = fm;
		this.cavok = cav;
		this.visibilite = v;
		this.temperature = t;
		this.temperatureRose = tr;
		this.qnh = qnh;
	}

	/**
	 * Indique s'il fait beau ou mauvais.
	 * 
	 * @return Vrai s'il fait beau.
	 */
	public boolean isCavok() {
		return cavok;
	}

	/**
	 * Récupère le jour de la mesure.
	 * 
	 * @return Le jour.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Récupère la direction du vent.
	 * 
	 * @return la direction du vent.
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * Récupère la force du vent.
	 * 
	 * @return La force du vent.
	 */
	public int getForce() {
		return force;
	}

	/**
	 * Récupère la force maximale du vent.
	 * 
	 * @return La force maximale du vent.
	 */
	public int getForceMax() {
		return forceMax;
	}

	/**
	 * Récupère la hauteur des nuage.
	 * 
	 * @return La hauteur des nuages.
	 */
	public int getHauteurNuages() {
		return hauteurNuages;
	}

	/**
	 * Récupère l'heure de la mesure.
	 * 
	 * @return L'heure de la mesure.
	 */
	public int getHour() {
		return hour;
	}

	/**
	 * Récupère la minute de la mesure.
	 * 
	 * @return La minute de la mesure.
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Récupère la description des nuages.
	 * 
	 * @return La description des nuages.
	 */
	public String getNuages() {
		return nuages;
	}

	/**
	 * Récupère le code de l'aéroport.
	 * 
	 * @return l'aéroport.
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Récupère la pression atmosphérique.
	 * 
	 * @return La pression atmosphérique.
	 */
	public int getQnh() {
		return qnh;
	}

	/**
	 * Récupère la distance par rapport à la ville.
	 * 
	 * @return La distance.
	 */
	public int getSiteDistance() {
		return siteDistance;
	}

	/**
	 * Récupère la température.
	 * 
	 * @return La température.
	 */
	public int getTemperature() {
		return temperature;
	}

	/**
	 * Récupère la température à la rosée.
	 * 
	 * @return La température à la rosée.
	 */
	public int getTemperatureRose() {
		return temperatureRose;
	}

	/**
	 * Récupère le temps qu'il fait.
	 * 
	 * @return Le temps qu'il fait.
	 */
	public String getTempsPresent() {
		return tempsPresent;
	}

	/**
	 * Récupère la visibilité.
	 * 
	 * @return La visibilité.
	 */
	public int getVisibilite() {
		return visibilite;
	}

	/**
	 * Récupère la distance entre la ville et l'aéroport.
	 * 
	 * @return La distance entre la ville et l'aéroport.
	 */
	public int getDistance() {
		return this.distance;
	}

	/**
	 * Précise la distance entre la ville et l'aéroport.
	 * 
	 * @param dst
	 *            La distance entre la ville et l'aéroport.
	 */
	public void setDistance(int dst) {
		this.distance = dst;
	}

	/**
	 * Fonction ToString, sert à l'affichage d'un Metar.
	 * 
	 * @return L'affichage d'un metar.
	 */
	public String toString() {
		String result = "METAR " + place + " " + DF2.format(day)
				+ DF2.format(hour) + DF2.format(min) + "Z AUTO "
				+ DF3.format(dir) + DF2.format(force);
		if (forceMax > force)
			result += "G" + DF2.format(forceMax);
		result += "KT ";
		if (cavok) {
			result += "CAVOK ";
		} else {
			result += DF4.format(visibilite) + " ";
		}
		result += (DF2.format(temperature) + "/" + DF2.format(temperatureRose) + " ")
				.replaceAll("-", "M");
		result += DF4.format(qnh) + " =";
		return result;
	}

	/**
	 * Analyse une ligne de texte pour en extraire un bulletin.
	 * 
	 * @param msg
	 *            Texte contenant les informations à extraire.
	 * @return un metar.
	 * 
	 */
	public static Metar parse(String msg) {

		// Lieu de la mesure.
		String place = null;

		// Date de la mesure.
		int d = 0, h = 0, m = 0;

		// Informations sur le vent.
		int dir = 0, f = 0, fm = 0;

		// Beau temps = non.
		boolean cav = false;

		// Visibilité.
		int v = 0;

		// Température et température à la rosée.
		int t = 0, tr = 0;

		// Pression.
		int qnh = 0;

		// Normalise le message :
		msg = msg.toUpperCase().trim();
		if (msg.endsWith("=")) {
			msg = msg.substring(0, msg.length() - 1).trim();
		}

		// Découpe du message selon les espaces (" ").
		StringTokenizer st = new StringTokenizer(msg);

		// Si le message ne commence pas par METAR.
		String token = st.nextToken();
		if (!token.equals("METAR")) {
			System.err.println("Le message ne commence pas par METAR !");
			return null;
		}

		// Récupération du code de l'aéroport = "LF..."
		token = st.nextToken();
		place = token;

		// Récupération de la date et de l'heure = "JJHHMMZ"
		token = st.nextToken();
		if (token.endsWith("Z")) {
			d = Integer.parseInt(token.substring(0, 2));
			h = Integer.parseInt(token.substring(2, 4));
			m = Integer.parseInt(token.substring(4, 6));
		}

		// Si le token suivant est AUTO => on passe.
		token = st.nextToken();
		if (token.equals("AUTO")) {
			token = st.nextToken();
		}

		// Informations sur le vent : ddffGrrKT
		if (token.endsWith("KT") || token.endsWith("KMH")
				|| token.endsWith("MPS")) {
			dir = Integer.parseInt(token.substring(0, 2));
			f = Integer.parseInt(token.substring(2, 4));
			if (token.charAt(5) == 'G') {
				fm = Integer.parseInt(token.substring(6, 8));
			}
			if (token.endsWith("KMH")) {
				f /= KT2KMH;
				fm /= KT2KMH;
			} else if (token.endsWith("MPS")) {
				f /= KT2MS;
				fm /= KT2MS;
			}
		} else if (token.matches("(VRB.*)|(0000)|(9999)")) {
			// VRB (<=3kt) | 00000 (calme) | 99999 (variable)
		}

		// resynchro : recherche de CAVOK ou d'un nombre à 4 chiffres
		token = st.nextToken();
		while (!token.matches("(CAVOK)|(\\d{4}.*)")) {
			token = st.nextToken();
		}

		// Si CAVOK (temps clair) :
		if (token.equals("CAVOK")) {
			cav = true;
			token = st.nextToken();
		} else { // sinon visibilité, temps présent, état du ciel

			// visibilite (en metre) = "xxxx" | "9999" (> 10km)
			v = Integer.parseInt(token.substring(0, 4));
			token = st.nextToken();

			// Eétat du temps
			if (token.matches("[-=+]?(\\w\\w)+")) {
				// phenomene {-=+}{XX}* ou //
				token = st.nextToken();
			} else if ("//".equals(token)) {
				token = st.nextToken();
			}

			// état du ciel
			while (token.matches("\\w\\w\\w\\d\\d\\d")) {
				token = st.nextToken();
			}
		}

		// resynchro sur la température (abris/rose)
		while (!token.matches("[M0-9]\\d*/[M0-9]\\d*")) {
			token = st.nextToken();
		}
		int pos = token.indexOf("/");
		token = token.replaceAll("M", "-");
		t = Integer.parseInt(token.substring(0, pos));
		tr = Integer.parseInt(token.substring(pos + 1));

		// QNH (=pression atmospherique au sol)
		token = st.nextToken();
		if (token.startsWith("Q")) {
			token = token.substring(1);
			qnh = Integer.parseInt(token);
		}

		// la suite du Metar contient des tendances
		return new Metar(place, d, h, m, dir, f, fm, cav, v, t, tr, qnh);
	}
}
