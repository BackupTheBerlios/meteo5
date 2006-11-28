package InterfaceListener;

import java.util.EventListener;

import EventObjects.ListVilleEventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Interface implémentée par les composants pour écouter 
 * les évènements ListVille.
 */
public interface ListVilleListener extends EventListener {
	
	/**
	 * Méthode appelée lors de la réception d'un évènement.
	 * @param e Objet contenant les informations sur l'évènement.
	 */
	void handleGetListVille(ListVilleEventObject e);
}
