package Temperature;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.TemperatureEventObject;
import EventObjects.TemperatureTraiteEventObject;
import InterfaceListener.TemperatureListener;
import InterfaceListener.TemperatureTraiteListener;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 * 
 * Composant gérant les températures.
 */
public class Temperature implements Serializable, TemperatureListener {
	private static final long serialVersionUID =1;

	/** Constructeur vide pour un composant. */
	public Temperature() {
		
	}
	
	//--------------------------------------
	// Source d'évènements TemperatureTraite
	
	/** liste des écouteurs d'évènements Temperature */
	private Vector<TemperatureTraiteListener> temperatureTraiteListeners = new Vector<TemperatureTraiteListener>();
	
	/** 
	 * Ajout d'un écouteur.
	 * @param l Ecouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addTemperatureTraiteListener(TemperatureTraiteListener l) {
		this.temperatureTraiteListeners.addElement(l);
	}
	
	/** 
	 * Supression d'un écouteur.
	 * @param l Ecouteur à supprimer de la liste des abbonnés.
	 */
	public synchronized void removeTemperatureTraiteListener(TemperatureTraiteListener l) {
		this.temperatureTraiteListeners.removeElement(l);
	}
	
	/** 
	 * Méthode qui envoie un évènements contenant les températures.
	 * @param temp Température actuelle.
	 * @param tempRosee Température à la rosée.
	 */
	private void handleSendTemperature(float temp, float tempRosee) {
		// Création de l'objet de l'évènement
		TemperatureTraiteEventObject obj = new TemperatureTraiteEventObject(this);
		obj.setTemperatureTraite(temp);
		obj.setTemperatureTraiteRosee(tempRosee);
		
		// Envoi des évènements à tous les auditeurs
		Vector<TemperatureTraiteListener> l;
		synchronized (this) {
			l = (Vector<TemperatureTraiteListener>) this.temperatureTraiteListeners.clone();
		}
		for (TemperatureTraiteListener item : l) {
			item.handleTraite(obj);
		}
	}
	
	
	//--------------------------------------
	// Réception d'évènements température
	
	/**
	 * Méthode appelée lors de la réception d'un évènement température.
	 * @param e Objet de l'évènement.
	 */
	public void handleCalcul(TemperatureEventObject e) {
		float temp = calcul(e.getTemperature(), e.getDistances());
		float tempRosee = calcul(e.getTemperatureRosee(), e.getDistances());
		
		handleSendTemperature(temp, tempRosee);
	}
	
	
	//--------------------------------------------------------
	// Fonction de calcul.
		
	/**
	 * Calcul les informations.
	 * 
	 * @param data valeurs environnantes relevées
	 * @param dst distances entre le point recherché et les points de mesure.
	 */
	protected int calcul(Vector<Integer> data, Vector<Float> dst) {
		float dirMoy = 0.0f;
		float coef = 0.0f;
		
		for (int i = 0; i < data.size(); i++) {
			if (dst.get(i) != 0) {
				dirMoy += data.get(i) * (1 / dst.get(i));
				coef += 1 / dst.get(i);
			} else {
				dirMoy += data.get(i);
				coef++;
			}
		}

		dirMoy /= coef;

		return Math.round(dirMoy);
	}
	
	
	
}
