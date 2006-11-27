package InterfaceListener;

import java.util.EventListener;

import EventObjects.SelectedVilleEventObject;


public interface SelectedVilleListener extends EventListener {

	void handleVille(SelectedVilleEventObject e);

}
