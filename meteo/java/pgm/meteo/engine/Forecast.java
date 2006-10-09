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
	
	public String windDir();
	
	public int temp(String codeTemp);
	
	public int visibility();
	
	public int pressure();
	
	public int dewTemp();
	
}
