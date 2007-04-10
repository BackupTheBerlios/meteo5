package hangman;


/**
 * Cette classe permet d'afficher l'expression qui doit apparaitre selon 
 * les caractères trouvés en parcourant l'objet IWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 */
public class ToStringAlgo implements IWordAlgo {

	/**
	 * Singleton ToStringAlgo.
	 */
	public static final ToStringAlgo Singleton = new ToStringAlgo();

	/**
	 * Constructeur de la classe ToStringAlgo.
	 */
	private ToStringAlgo() {
	}

	/**
	 * Cette fonction n'effectue aucun traitement.
	 * (les EmptyWord ne sont pas affiché)
	 * 
	 * @param host objet IEmptyWord
	 * @param inp non utilisé.
	 * 
	 * @return une chaine vide.
	 */
	public Object emptyCase(IEmptyWord host, Object inp) {
		return "";
	}

	/**
	 * Méthode retournant le caractère stocké par l'objet INEWord 
	 * entré en paramètre.
	 * 
	 * @param host objet INEWord
	 * @param inp non utilisé
	 * 
	 * @return le caractère disponible dans host
	 */
	public Object visibleCase(INEWord host, Object inp) {
		return new Character(host.getFirst());
	}

	/**
	 * Méthode retournant le caractère '-' qui se substitue
	 * à  un caractère non trouvé.
	 * 
	 * @param host objet INEWord
	 * @param inp non utilisé
	 * 
	 * @return le caractère '-'
	 */
	public Object invisibleCase(INEWord host, Object inp) {
		return new Character('-');
	}

	/*
	  * @tstart
	 * 		@tcreate ToStringAlgo.Singleton
	 * 		@tunit defaultConstructor : default constructor and accessors
	 *   	@tustart
     *    		testMsg("object created");
     *    		NEWord word = new NEWord('d',new NEWord('a',new NEWord('v',null)));
     *    		testCheck("Chaine='---' :", ( (String) invisibleCase(word, null)).equals("---") );
     * 		@tuend
	 * @tend
	 */
	/*
	 * -----------------------------------------------------------------
	 * 
	 * Test de la classe
	 * 
	 * Configuration de test
	 * 
	 * Constructeur de la classe de test : @tcreate ToStringAlgo.Singleton
	 * 
	 * @tstart
	 * 		
	 * 		@tcreate new ToStringAlgo()
	 * 		@tunit defaultConstructor : default constructor and accessors
	 * 		@tustart
	 * 			NEWord word = new NEWord('d',new NEWord('a',new NEWord('v',null)));
	 * 			testCheck("Chaine='---' :", ( (String) invisibleCase(word, null)).equals("---") ); 
	 * 			word.execute(GuessCharAlgo.Singleton, 'a');
	 * 			testCheck("Chaine='-a-' :", ( (String) visibleCase(word, null)).equals("-a-") ); 
	 * 			testCheck("Chaine='' :", ( (String) emptyCase(EmptyWord.Singleton, null) ) == "" );
	 * 		@tuend
	 * @tend
	 */

}

