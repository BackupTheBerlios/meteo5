package hangman;

/**
 * Classe permettant de verifier si le caractère saisi par l'utilisateur est
 * présent dans le mot (IWord) que l'on recherche. Si le caractère est présent,
 * on le rend visible a chaque fois que l'on le trouve et on renvoie true, sinon
 * false.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 */
public class GuessCharAlgo implements IWordAlgo {

	/**
	 * Singleton représentant une instance unique d'un objet GuessCharAlgo.
	 */
	public static final GuessCharAlgo Singleton = new GuessCharAlgo();

	/**
	 * Constructeur privé de la classe GuessCharAlgo.
	 */
	private GuessCharAlgo() {
		// your code here
	}

	/**
	 * Fonction ne faisant rien, car un EmptyWord ne contient aucun caractères.
	 * 
	 * @param host l'objet IEmptyWord sur lequel on effectue la recherche
	 * @param param le caractère a recherché.
	 * @return false car l'objet EmptyWord ne contient aucun caractères.
	 * 
	 * @pre host!=null // le IEmptyWord doit être non null.
	 * @pre param!= null // le paramètre doit être non null.
	 * @pre param.getClass().equals(Character.class) // Le paramètre doit être un caractère.
	 */
	public Object emptyCase(IEmptyWord host, Object param) {
		return new Boolean(false);
	}

	/**
	 * Fonction verifiant si le caractère saisi est présent (une ou plusieur
	 * fois) dans le NEWord, Si oui, on retourne true, sinon false.
	 * 
	 * @param host le INEWord sur lequel on effectue la recherche.
	 * @param param le caractère a recherché.
	 * @return le résultat de la recherche. true si le caractère est présent
	 *         dans le mot, false sinon.
	 *         
	 * @pre host!=null // le INEWord doit être non null.
	 * @pre param!= null // le paramètre doit être non null.
	 * @pre param.getClass().equals(Character.class) // Le paramètre doit être un caractère.
	 */
	public Object visibleCase(INEWord host, Object param) {
		if (host.getFirst() == ((Character) param).charValue()) {
			return (new Boolean(true) || ((Boolean) host.getRest().execute(this, param)));
		} else {
			return (new Boolean(false) || ((Boolean) host.getRest().execute(this, param)));
		}
	}

	/**
	 * Fonction verifiant si le caractère saisi est présent (une ou plusieur
	 * fois) dans le NEWord, Si oui, on change l'état du caractère à chaque fois
	 * que l'on le trouve dans le mot, puis, on retourne true, sinon false.
	 * 
	 * @param host le INEWord sur lequel on effectue la recherche.
	 * @param param le caractère a recherché.
	 * @return le résultat de la recherche. true si le caractère est présent
	 *         dans le mot, false sinon.
	 *         
	 * @pre host!=null // le INEWord doit être non null.
	 * @pre param!= null // le paramètre doit être non null.
	 * @pre param.getClass().equals(Character.class) // Le paramètre doit être un caractère.      
	 */
	public Object invisibleCase(INEWord host, Object param) {
		if (host.getFirst() == ((Character) param).charValue()) {
			host.toggleState();
			return (new Boolean(true) || ((Boolean) host.getRest().execute(this, param)));
		} else {
			return (new Boolean(false) || ((Boolean) host.getRest().execute(this, param)));
		}
	}
	
	/*
	 * -----------------------------------------------------------------
	 * Test de la classe GuessCharAlgo
	 * -----------------------------------------------------------------
	 * 
	 * @tstart
	 * 	@tcreate GuessCharAlgo.Singleton
	 * 	@tunit test1 : Test de la methode emptyCase()
	 * 		@tustart
	 * 	 		testMsg("Test sur du mot vide"); 
	 * 			testCheck("Mot vide :", ((Boolean) emptyCase(EmptyWord.Singleton,new Character('u'))).booleanValue() == false);
	 * 		@tuend
	 * @tunit test2 : Test de la methode invisibleCase()
	 * 		@tustart
	 * 	 		testMsg("Creation du mot : laval"); 
	 * 			IWord myWord = WordFactory.Singleton.makeWord("laval");
	 * 			NEWord myNEWord = (NEWord)myWord;
	 * 			testMsg("lettre v ?"); 
	 * 			testCheck("lettre v ?", ((Boolean) invisibleCase(myNEWord, new Character('v'))).booleanValue() == true);
	 * 			testMsg("lettre b ?"); 
	 * 			testCheck("lettre b ?", ((Boolean) invisibleCase(myNEWord, new Character('b'))).booleanValue() == false);
	 * 			testMsg("lettre a ?"); 
	 * 			testCheck("lettre a ?", ((Boolean) invisibleCase(myNEWord, new Character('a'))).booleanValue() == true);
	 * 		@tuend
	 * @tunit test3 : Test de la methode visibleCase()
	 * 		@tustart
	 * 	 		testMsg("Creation du mot : laval"); 
	 * 			IWord myWord = WordFactory.Singleton.makeWord("laval");
	 * 			NEWord myNEWord = (NEWord)myWord;
	 * 			testMsg("lettre v ?"); 
	 * 			testCheck("lettre v ?", ((Boolean) visibleCase(myNEWord, new Character('v'))).booleanValue() == true);
	 * 			testMsg("lettre b ?"); 
	 * 			testCheck("lettre b ?", ((Boolean) visibleCase(myNEWord, new Character('b'))).booleanValue() == false);
	 * 			testMsg("lettre a ?"); 
	 * 			testCheck("lettre a ?", ((Boolean) visibleCase(myNEWord, new Character('a'))).booleanValue() == true);
	 * 		@tuend
	 * @tend
	 * -----------------------------------------------------------------
	 */
}
