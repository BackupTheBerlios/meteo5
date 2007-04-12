package hangman;

/**
 * Classe représentant la structure d'un NEWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class NEWord implements INEWord {

	/**
	 * Le caractère stoquer dans l'objet NEWord.
	 */
	private char first;

	/**
	 * L'état de l'objet NEWord.
	 */
	private AWordState state;

	/**
	 * Pointeur sur la suite du mot.
	 */
	private IWord rest;

	/**
	 * Constructeur de NEWord
	 * 
	 * @param c un caractère.
	 * @param iword un iword (pointeur vers la suite du mot).
	 */
	public NEWord(char c, IWord iword) {
		this.first = c;
		this.rest = iword;
		this.state = InvisibleState.Singleton;
	}

	/**
	 * Acceseurs au premier caractère.
	 * 
	 * @return le premier caractère stoquer.
	 */
	public char getFirst() {
		return this.first;
	}

	/**
	 * Accesseur au pointeur sur la suite du mot.
	 * 
	 * @return un pointeur vers la suite du mot.
	 */
	public IWord getRest() {
		return this.rest;
	}

	/**
	 * Méthode permetant de modifier l'état du NEWord.
	 * 
	 * @param s le nouveau etat.
	 */
	public void setWordState(AWordState s) {
		this.state = s;
	}

	/**
	 * Méthode permettant de récuperer l'état du NEWord.
	 *  
	 * @return l'état du NEWord.
	 */
	public AWordState getWordState() {
		return this.state;
	}

	/**
	 * Méthode permettant de basculer entre l'état visible et invisible d'un NEWord.
	 * 
	 */
	public void toggleState() {
		this.state.toggleState(this);
	}

	/**
	 * Méthode acceptant le visiteur algo sur l'objet. 
	 * Elle permet d'exécuter une action spécifique sur un objet.
	 * 
	 * @param algo algorithme utiliser.
	 * @param param paramètre pouvant être utiliser par l'algorithme. 
	 * @return le résultat obtenu suite à l'algorithme.
	 * 
	 * @pre algo!=null // l'algorithme que l'on souhaite appliquer doit avoir été choisi.
	 */
	public Object execute(IWordAlgo algo, Object param) {
		return this.state.execute(this, algo, param);
	}
	
	/*
	 * -----------------------------------------------------------------
	 * Test de la classe NEWord
	 * -----------------------------------------------------------------
	 * 
	 * @tstart
	 * 	@tcreate new NEWord('t',new NEWord('o',new NEWord('c',null)));
	 * 	@tunit test1 : Test du constructeur
	 * 		@tustart
	 * 	 		testMsg("Test sur le construteur"); 
	 * 			//testCheck("lettre t ?", getFirst()=='t');
	 * 		@tuend
	 * @tend
	 */ 
}
