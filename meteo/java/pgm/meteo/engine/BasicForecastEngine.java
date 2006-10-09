package meteo.engine;

/**
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public class BasicForecastEngine implements Forecast {

	/** L'élément température. */
	private Temperature temperature;
	/** L'élément vent. */
	private Wind wind;
	/** L'élément pression. */
	private Pressure pressure;
	/** L'élément temps. */
	private Weather weather;
	/** L'élément visibilité. */
	private Visibility visibility;
	
	
	public BasicForecastEngine(){
		
	}
	
	
	/**
	 * Accès à la température à la rosée en °C.
	 * @return La température à la rosée en °C.
	 */
	public int dewTemp() {
		if(temperature!=null){
			return temperature.getDewTemp();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à la pression.
	 * @return La pression.
	 */
	public int pressure() {
		if(pressure!=null){
			return pressure.getPressure();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à la température en °C.
	 * @return La température en °C.
	 */
	public int temp() {
		if(temperature!=null){
			return temperature.getTemp();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à la visibilité.
	 * @return La visibilité.
	 */
	public int visibility() {
		if(visibility!=null){
			return visibility.getVisibility();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à la direction du vent.
	 * @return La direction du vent.
	 */
	public int windDir() {
		if(wind!=null){
			return wind.getDirection();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à la vitesse du vent.
	 * @return La vitesse du vent.
	 */	
	public int windSpeed() {
		if(wind!=null){
			return wind.getSpeed();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à la vitesse maximale du vent.
	 * @return La vitesse maximale du vent.
	 */	
	public int windSpeedMax() {
		if(wind!=null){
			return wind.getSpeedMax();
		}else{
			return -1;
		}
	}

	/**
	 * Accès à ???
	 * @return
	 */
	public boolean isCavOk(){
		if(weather!=null){
			return weather.isCAVOK();
		}else{
			return false;
		}
	}
	
	
}
