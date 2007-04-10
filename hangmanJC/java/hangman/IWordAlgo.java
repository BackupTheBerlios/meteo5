package hangman;


/**
 * Interface permettant de modéliser les différents algorithmes pouvant être
 * employer sur les éléments IWord. Un élément IWord peut être sous trois 
 * états différents : visible, invisible ou vide. Trois algorithmes sont 
 * donc spécifiés dans cette interface.
 * 
 * @author Jerome Catric & Emmanuel Meheut 
 */
public interface IWordAlgo {
	
	/**
	 * Méthode permettant d'éxécutet un algorithme sur des éléments de type
	 * EmptyWord.
	 * 
	 * @param host objet IEmptyWord
	 * @param inp paramètre quelconque
	 * 
	 * @return objet de retour de la méthode
	 * 
	 * @pre host != null // l'objet ne doit pas être null
	 */
	public Object emptyCase(IEmptyWord host, Object inp);

	/**
	 * Méthode permettant de d'exécuter un algorithme sur des éléments dont
	 * l'état est visible.
	 * 
	 * @param host objet INEWord
	 * @param inp paramètre quelconque
	 * 
	 * @return objet de retour de la méthode
	 * 
	 * @pre host != null // l'objet ne doit pas être null
	 */
	public Object visibleCase(INEWord host, Object inp);

	/**
	 * Méthode permettant de d'exécuter un algorithme sur des éléments dont
	 * l'état est invisible.
	 * 
	 * @param host objet INEWord
	 * @param inp paramètre quelconque
	 * 
	 * @return objet de retour de la méthode
	 * 
	 * @pre host != null // l'objet ne doit pas être null
	 */
	public Object invisibleCase(INEWord host, Object inp);
}
