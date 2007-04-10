package hangman;


/**
 * Classe représenant le mot vide (EmptyWord).
 * 
 * @author Jerome Catric & Emmanuel Meheut
 */
public class EmptyWord implements IEmptyWord {

	/**
	 * Singleton EmptyWord.
	 */
	public static final EmptyWord Singleton = new EmptyWord();

	/**
	 * Constructeur de la classe EmptyWord
	 */
	private EmptyWord() {
	}

	/**
	 * Méthode permettant d'exécuter une actions spécifiques sur un objet.
	 * 
	 * @param algo objet IWordAlgo.
	 * @param imp paramètre quelconque d'entrée
	 * 
	 * @return le résultat de l'algorithme
	 * 
	 * @pre algo != null // l'algorithme à éxécuter ne doit pas être vide
	 */
	public Object execute(IWordAlgo algo, Object inp) {
		return algo.emptyCase(this, inp);
	}

}

