package meteo;

import java.util.EventObject;

public class VilleEventObject extends EventObject {

	private String ville = ""; 
	
	public VilleEventObject(Object o) {
		super(o);
	}
	
	public void setVille(String vil) {
		this.ville = vil;
	}
	
	public String getVille() {
		return this.ville;
	}
	
}
