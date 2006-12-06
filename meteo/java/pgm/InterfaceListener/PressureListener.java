package InterfaceListener;

import java.util.EventListener;

import EventObjects.PressureEventObject;

/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel
 *
 * Interface implémentée par les composants pour écouter 
 * les évènements Pressure.
 */
public interface PressureListener extends EventListener {
	void handleCalcul(PressureEventObject e);
}
