package Affichage;

import java.lang.reflect.Method;
import java.util.HashMap;

import EventObjects.AffichageEventObject;
import EventObjects.TemperatureTraiteEventObject;
import InterfaceListener.TemperatureTraiteListener;
import Temperature.Temperature;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Adapteur servant pour différencier d'où provient le texte contenant
 * les informations météo : des prévisions ou des infos météo "normales".
 * Il trie les évènements Affichage.
 */
public class AdaptateurAffichage implements TemperatureTraiteListener {
	
	/** Objet sur lequel l'adaptateur s'applique. */
	private Affichage cible;
	
	/**
	 * Constructeur créant l'adaptateur.
	 * @param cible Composant cible de l'adaptateur.
	 */
	public AdaptateurAffichage(Affichage cible) {
	    this.cible = cible;
	}
	
	
	/** Liste des fonctions à appeler selon la source de l'évènement reçu. */
	private HashMap<Temperature, Method> methodTemperatureTraite = new HashMap<Temperature, Method>();
	
	
	/**
	 * Permet d'ajouter la méthode a appeler lors de la réception d'un évènement TemperatureTraite d'une source précise.
	 * @param source Source de l'évènement.
	 * @param nomMethode Nom de la méthode à appeler.
	 * @throws NoSuchMethodException Ne tient pas compte des méthodes dont le nom est inexistant.
	 */
	public void addTemperature(Temperature source, String nomMethode) throws NoSuchMethodException {
		
		// Liste des types d'arguments à passer à la méthode à appeler 
		Class[] args = new Class[] { AffichageEventObject.class };
		
		// Récupération de la méthode à appeler dans le composant cible
		Method methode = this.cible.getClass().getMethod(nomMethode, args);
		
		// Sauvegarde dans la table
	    this.methodTemperatureTraite.put(source, methode);
	    
	    // S'inscrire chez la source comme récepteur d'évènement
	    source.addTempChangeListener(this);
	}

	
	public void handleTraite(TemperatureTraiteEventObject e) {
		
	}
	
}
