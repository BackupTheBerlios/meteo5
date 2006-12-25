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
	static final long serialVersionUID =1;

	public Temperature() {
		
	}
	
	//--------------------------------------
	// Source d'évènements TemperatureTraite
	
	/** liste des écouteurs d'évènements Metar */
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
		float temp = calculeTempMoyenne(e);
		float tempRosee = calculeTempRoseeMoyenne(e);
		
		handleSendTemperature(temp, tempRosee);
	}
	
	
	//--------------------------------------------------------
	// Fonction de calcul.
		
	/** 
	 * @param e: Evenement reçu.
	 * @return la température moyenne calculée.
	 */
	private float calculeTempMoyenne(TemperatureEventObject e){
		float tempMoyenne = 0;
		float coef = 0;
		for(int i=0; i< e.getDistances().size(); i++){
			if (e.getDistances().get(i)!= 0){
				tempMoyenne += e.getTemperature().get(i) * (1 / e.getDistances().get(i));
			}
			else{
				tempMoyenne += e.getTemperature().get(i);
			}
//			coef diviseur
			if (e.getDistances().get(i) != 0) {
				coef += 1 / e.getDistances().get(i);
			} else {
				coef += 1;
			}
			tempMoyenne /= coef;
		}
		return tempMoyenne;
	}
	
	/** 
	 * @param e: Evenement reçu.
	 * @return la température à la rosée moyenne calculée.
	 */
	private float calculeTempRoseeMoyenne(TemperatureEventObject e){
		float tempRoseeMoyenne = 0;
		float coef = 0;
		for(int i=0; i< e.getDistances().size(); i++){
			if(e.getDistances().get(i)!=0){
				tempRoseeMoyenne += e.getTemperatureRosee().get(i) * (1 / e.getDistances().get(i));
			}
			else{
				tempRoseeMoyenne += e.getTemperatureRosee().get(i);
			}
			//coef diviseur
			if (e.getDistances().get(i) != 0) {
				coef += 1 / e.getDistances().get(i);
			} else {
				coef += 1;
			}
			tempRoseeMoyenne /= coef;
		}
		return tempRoseeMoyenne;
	}
	
	
	
}
