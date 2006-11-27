package meteo;

import java.util.EventListener;

public interface VilleListener extends EventListener {

	void handleVille(VilleEventObject e);

}
