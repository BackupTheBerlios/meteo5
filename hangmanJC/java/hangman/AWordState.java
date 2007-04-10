package hangman;


/**
 * Classe permettant de modéliser l'état d'un NEWord
 * (visible ou invisible)
 * 
 * @author Jerome Catric & Emmanuel Meheut
 */
public abstract class AWordState {

	/**
	 * Méthode permettant de changer l'état d'un objet NEWord
	 * 
	 * @param host objet NEWord
	 * 
	 * @pre host != null // L'objet doit être non null
	 */
	public abstract void toggleState(NEWord host);

	/**
	 * Fonction acceptant le visiteur algo au nom de l'objet INEWord host
	 * 
	 * @param host objet INEWord          
	 * @param algo objet IWordAlgo
	 * @param inp paramètre optionnel
	 *
	 * @return résultat de l'algorithme
	 * 
	 * @pre host != null // host ne doit pas être null
	 * @pre algo != null // algo ne doit pas être null
	 * 
	 */
	public abstract Object execute(INEWord host, IWordAlgo algo, Object inp);

}
