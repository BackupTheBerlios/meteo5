/**
 * @author LE NY Clément
 * @author CATRIC Jérôme
 * @author YANG Yitian
 * @author MEHEUT Emmanuel 
 */
package meteo.engine;
import java.util.*;

/**
* Classe singleton LocationFiles
*
*/
public class LocationsFile {
	
	private String FilePath;
	private String locations;
	private String airports;
	
	
	private static LocationsFile instance = new LocationsFile();

    // Mettre les constructeurs en private
    private LocationsFile() { }
    private LocationsFile(String FilePath){
    	this.FilePath = FilePath;
    }
    public static LocationsFile getInstance(String FilePath)
    {

        // Double vérification 
        if (instance == null)
        {
            synchronized(LocationsFile.class) {
                if (instance == null)
                    instance = new LocationsFile(FilePath);
            }
        }
        return instance;
    }
    
    /**
     * 
     * @param FilePath
     * @return
     */
   public LocationsFile getHandle(String FilePath){
	   return null;
   }
   
	/**
	 * @return Returns the airports.
	 */
	public String getAirports() {
		return airports;
	}
	
	/**
	 * @return Returns the locations.
	 */
	public String getLocations() {
		return locations;
	}
	
	public Vector<String> getLocValues(String locations){
		return null;
	}
   
   
    
}
