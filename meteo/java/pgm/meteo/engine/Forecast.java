package meteo.engine;

/**
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public interface Forecast {
	
	public int windSpeed();
	
	public int windSpeedMax();
	
	public int windDir();
	
	public int temp();
	
	public int visibility();
	
	public int pressure();
	
	public int dewTemp();
	
	public boolean isCavOk();
	
}
