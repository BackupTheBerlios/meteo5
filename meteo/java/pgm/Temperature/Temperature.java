package Temperature;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.TemperatureEventObject;
import InterfaceListener.MetarListener;
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
public class Temperature implements TemperatureListener, Serializable{
	static final long serialVersionUID =1;

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
	
	
	
	
	
	
	

	public void handleCalcul(TemperatureEventObject e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
