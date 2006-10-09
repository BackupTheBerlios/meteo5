/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel 
 */
package meteo.engine;

/**
* Classe singleton LocationFiles
*
*/
public class LocationsFile {
	private static LocationsFile instance = new LocationsFile();

    // Mettre le constructeur par défaut en private
    private LocationsFile() { }
    public static LocationsFile getInstance()
    {

        // Double vérification 
        if (instance == null)
        {
            synchronized(LocationsFile.class) {
                if (instance == null)
                    instance = new LocationsFile();
            }
        }
        return instance;
    }
}
