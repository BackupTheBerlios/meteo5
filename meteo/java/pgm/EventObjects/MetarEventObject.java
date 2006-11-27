package EventObjects;

import java.util.EventObject;
import java.util.Vector;

public class MetarEventObject extends EventObject {
	private Vector<String> metars = new Vector<String>();
	
	public MetarEventObject (Object o){
		super(o);
	}
	
	public static final long serialVersionUID = 1;
	
	
	public Vector<String> getMetars(){
		return this.metars;
	}

	/**
	 * @param metars The metars to set.
	 */
	public void setMetars(Vector<String> metars) {
		this.metars = metars;
	}
	
	
}
