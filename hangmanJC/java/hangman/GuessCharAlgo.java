package hangman;


/**
 * Cette classe permet de savoir si un caractère est contenu dans l'expression 
 * à trouver. On va rechercher le caractère dans l'objet IWord qui est passée en
 * paramètre aux différentes méthodes. A chaque fois que le caractère est trouvé 
 * on le rent visible et on retourne true, sinon false.
 * 
 * @author Jerome Catric & Emmanuel Meheut
 * 
 */
public class GuessCharAlgo implements IWordAlgo {

	/**
	 * Singleton GuessCharAlgo.
	 */
	public static final GuessCharAlgo Singleton = new GuessCharAlgo();

	/**
	 * Constructeur de la classe GuessCharAlgo.
	 */
	private GuessCharAlgo() {
	}

	/**
	 * Méthode ne faisant rien car un EmptyWord ne contient aucun caractère.
	 * 
	 * @param host un objet
	 * @param inp un caractère
	 * 
	 * @return un boolean ayant la valeur false.
	 */
	public Object emptyCase(IEmptyWord host, Object inp) {
		return new Boolean(Boolean.FALSE);
	}

	/**
	 * Méthode permettant de verifier si le caractère saisie est présent dans l'objet NEWord.
	 * Si oui, on retourne true, sinon on retourne false.
	 * 
	 * @param host objet NEWord
	 * @param inp un caractère
	 * 
	 * @return booléen indiquant si le caractère saisie est présent dans le NEWord
	 * 
	 * @pre inp.getClass().equals(Character.class) // Le type de inp doit être Character.
	 */
	public Object visibleCase(INEWord host, Object inp) {
		if (host.getFirst() == ((Character) inp).charValue())
			return (new Boolean(Boolean.TRUE) || ((Boolean) host.getRest()
					.execute(this, inp)));
		else
			return (new Boolean(Boolean.FALSE) || ((Boolean) host.getRest()
					.execute(this, inp)));
	}

	/**
	 * Méthode permettant de verifier si le caractère saisie est présent dans l'objet NEWord.
	 * Si c'est le cas on le rend visible et on retourne true, sinon on retourne false.
	 * 
	 * @param host objet NEWord
	 * @param inp un caractère
	 * 
	 * @return booléen indiquant si le caractère saisie est présent dans le NEWord
	 * 
	 * @pre inp.getClass().equals(Character.class) // Le type de inp doit être Character.
	 */
	public Object invisibleCase(INEWord host, Object inp) {
		if (host.getFirst() == ((Character) inp).charValue()) {
			host.toggleState();
			return (new Boolean(Boolean.TRUE) || ((Boolean) host.getRest()
					.execute(this, inp)));
		}
		return (new Boolean(Boolean.FALSE) || ((Boolean) host.getRest()
				.execute(this, inp)));
	}

	/*
	 * -----------------------------------------------------------------
	 * 
	 * Test definition
	 * 
	 * @tcreate GuessCharAlgo.Singleton
	 * 
	 * @tunit TST_create() : default constructor and basic accessors
	 * 
	 * This unit tests the default constructor and the algorythm
	 * 
	 * @tunitcode 
	 * {
	 * 
	 * NEWord word = new NEWord('d',new NEWord('a',new NEWord('v',null)));
	 * 
	 * testCheck("Caractere b ?", ( (Boolean) invisibleCase(word, new Character('b')) ).booleanValue() == false); 
	 * testCheck("Caractere d ?", ( (Boolean) invisibleCase(word, new Character('d')) ).booleanValue() == true); 
	 * testCheck("d visible ?", ( (Boolean) visibleCase(word, null)).booleanValue() == true); 
	 * testCheck("Caractere a ?", ( (Boolean) visibleCase(word, new Character('a')) ).booleanValue() == true);
	 * testCheck("Caractere w ?", ( (Boolean) visibleCase(word, new
	 * Character('w')) ).booleanValue() == false); 
	 * testCheck("Caractere d ?", ((Boolean) emptyCase(EmptyWord.Singleton, new Character('d'))).booleanValue() == false);
	 * 
	 * }
	 * 
	 */


}
