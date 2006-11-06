package meteo.engine;

/**
 * Interface pour les prévisions météorologique.
 * 
 * @author Clément LE NY
 * @author Jérôme CATRIC
 * @author Emmanuel MEHEUT
 * @author Yitian YANG
 */
public interface Forecast {

	/** Accesseur à la vitesse du vent. */
	public int windSpeed();

	/** Accesseur à la vitesse maximal du vent. */
	public int windSpeedMax();

	/** Accesseur à la direction du vent. */
	public int windDir();

	/** Accesseur à la température. */
	public int temp();

	/** Accesseur à la visibilité. */
	public int visibility();

	/** Accesseur à la pression atmospherique. */
	public int pressure();

	/** Accesseur à la température a la rosée. */
	public int dewTemp();

	/** Accesseur à fait-il beau temps? */
	public boolean isCavOk();

}
