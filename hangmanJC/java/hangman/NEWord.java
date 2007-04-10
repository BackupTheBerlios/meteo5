package hangman;


/**
 * Classe permettant de stocker les différentes instances du word à afficher
 * ainsi que leur état.
 * 
 * @author Jerome Catric & Emmanuel Meheut
 */
public class NEWord implements INEWord {

	/**
	 * Le caractère stocké dans l'objet
	 */
	private char first;

	/**
	 * Le rest de cd NEWord.
	 */
	private IWord rest;

	/**
	 * Etat du carcatère
	 */
	private AWordState state;
	
	/**
	 * Constructeur de la classe permettant de construire un caractère 
	 * qui compose le mot à découvrir.
	 * 
	 * @param first caractère à ajouter
	 * @param rest  pointeur vers le caractère suivant du mot
	 * 
	 */
	public NEWord(char first, IWord rest) {
		this.first = first;
		this.rest = rest;
		this.setWordState(InvisibleState.Singleton);
	}

	/**
	 * Méthode retournant le premier caractère.
	 * 
	 * @return le premier caractère de ce INEWord
	 */
	public char getFirst() {
		return this.first;
	}

	/**
	 * Méthode retournant le rest de IWord.
	 * 
	 * @return le rest de IWord.
	 */
	public IWord getRest() {
		return this.rest;
	}

	/**
	 * Méthode permettant de changer l'état.
	 * (visible ou invisble)
	 */
	public void toggleState() {
		state.toggleState(this);
	}

	/**
	 * Permet d'exécuter une actions spécifiques sur un objet. 
	 * 
	 * @param algo objet IWordAlgo
	 * @param imp paramètre quelconque.
	 *            
	 * @return le résultat de l'algorithme
	 * 
	 * @pre algo != null // algo doit être non null
	 */
	public Object execute(IWordAlgo algo, Object inp) {
		return state.execute(this, algo, inp);
	}

	/**
	 * Permet d'initialiser l'état du caractère de l'expression.
	 * 
	 * @param state nouveau etat (visible ou invisible)
	 */
	public void setWordState(AWordState state) {
		this.state = state;
	}

	/**
	 * Retourne l'état du NEWord
	 * 
	 * @return l'état du NEWord
	 */
	public AWordState getState() {
		return this.state;
	}

}
