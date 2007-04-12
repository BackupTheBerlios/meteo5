package hangman;

/**
 * Classe représentant le mot vide (EmptyWord)
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class EmptyWord implements IEmptyWord {

	/**
	 * Singleton représentant une instance unique d'un objet EmptyWord. 
	 */
	public static final EmptyWord Singleton = new EmptyWord();

	/**
	 * Constructeur privé de la classe EmptyWord. 
	 */
	private  EmptyWord() {        
		// your code here
	} 

	/**
	 * Méthode permettant d'éxécuter un algorithme spécifique sur l'objet courant.
	 *  
	 * @param algo l'algorithme a appliquer sur l'objet courant.
	 * @param param un paramètre quelconque.
	 * @return le résultat suite à l'algorithme.
	 * 
	 * @pre algo!=null // l'algorithme doit avoir été choisi.
	 */
	public Object execute(IWordAlgo algo, Object param) {        
		return algo.emptyCase(this, param);
	} 
}
