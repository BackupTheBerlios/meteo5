package Temperature;

import java.io.Serializable;
import java.util.Vector;

import EventObjects.TemperatureEventObject;
import InterfaceListener.MetarListener;
import InterfaceListener.TemperatureListener;

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

	//--------------------------------
	// Source d'évènements Temperature
	
	/** liste des écouteurs d'évènements Metar */
	private Vector<TemperatureListener> temperatureListeners = new Vector<TemperatureListener>();
	
	/** 
	 * Ajout d'un écouteur.
	 * @param l Ecouteur à ajouter à la liste des abbonnés.
	 */
	public synchronized void addMetarListener(TemperatureListener l) {
		temperatureListeners.addElement(l);
	}
	
	
	
	
	
	
	

	public void handleCalcul(TemperatureEventObject e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
