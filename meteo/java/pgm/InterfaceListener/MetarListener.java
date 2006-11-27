package InterfaceListener;

import EventObjects.MetarEventObject;

public interface MetarListener {
	void handleparse (MetarEventObject e);
}
