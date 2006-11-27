package InterfaceListener;

import java.util.EventListener;

import EventObjects.AeroVilleEventObject;

public interface AeroVilleListener extends EventListener {
	void handleChercherAeroports(AeroVilleEventObject e);
}
