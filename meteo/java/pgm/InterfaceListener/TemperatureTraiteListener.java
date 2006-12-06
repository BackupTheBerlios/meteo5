package InterfaceListener;

import EventObjects.TemperatureTraiteEventObject;

public interface TemperatureTraiteListener {
	/**
	 * Méthode appelée lors de la réception d'un évènement.
	 * @param e Objet contenant les informations sur l'évènement.
	 */
	void handleTraite(TemperatureTraiteEventObject e);	
}
