package InterfaceListener;

import java.util.EventListener;

import EventObjects.VisibilityEventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Interface implémentée par les composants pour écouter 
 * les évènements Visibility.
 */
public interface VisibilityListener extends EventListener {
	/**
	 * Méthode appelée lors de la réception d'un évènement.
	 * @param e Objet contenant les informations sur l'évènement.
	 */
	void handleCalcul(VisibilityEventObject e);	
}
