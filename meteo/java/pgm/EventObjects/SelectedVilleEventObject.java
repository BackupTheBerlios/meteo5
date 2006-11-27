package EventObjects;

import java.util.EventObject;

public class SelectedVilleEventObject extends EventObject {

	private static final long serialVersionUID = 1l;
	
	private String ville = ""; 
	
	public SelectedVilleEventObject(Object o) {
		super(o);
	}
	
	public void setVille(String vil) {
		this.ville = vil;
	}
	
	public String getVille() {
		return this.ville;
	}
	
}
