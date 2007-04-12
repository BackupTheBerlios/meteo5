package hangman;

/**
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class WordFactory implements IWordFactory {

	/**
	 * Singleton représentant une instance unique d'un objet WordFactory.
	 */
	public static final WordFactory Singleton = new WordFactory();

	/**
	 * Constructeur privé de la classe WordFactory.
	 */
	private WordFactory() {
		// your code here
	}

	/**
	 * Méthode permettant de construire un IWord a partir d'une chaine de caractères.
	 *
	 * @param s la chaine de caractère.
	 * @return le IWord construit a partir de la chaine de caractère.
	 * 
	 * @pre s!=null // La chaine de caractère doit être non null.
	 */
	public IWord makeWord(String s) {
		IWord wordList = EmptyWord.Singleton;
		for (int i = s.length() - 1; i >= 0; i--) {
			wordList = new NEWord(s.charAt(i), wordList);
		}
		return wordList;
	}

	
	/*
	 * -----------------------------------------------------------------
	 * Test de la classe WordFactory
	 * -----------------------------------------------------------------
	 * 
	 * @tstart
	 * 	@tcreate WordFactory.Singleton
	 * 	@tunit makeWord : Test de fabrication d'un mot
	 * 		@tustart
	 * 	 		testMsg("Création du mot voiture"); 
	 * 			IWord myWord = makeWord("voiture");
	 * 			testCheck("------- ?", ((String) myWord.execute(ToStringAlgo.Singleton, null)).equals("-------"));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('v'));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('o'));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('i'));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('t'));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('u'));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('r'));
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('e'));
	 * 			testCheck("voiture ?", ((String) myWord.execute(ToStringAlgo.Singleton, null)).equals("voiture"));
	 * 			testMsg("Création du mot vide"); 
	 * 			IWord myWord2 = makeWord("");
	 * 			testCheck("mot vide ?", ((String) myWord2.execute(ToStringAlgo.Singleton, null)).equals(""));
	 * 		@tuend
	 * @tend
	 * 
	 * -----------------------------------------------------------------
	 */
	
	
	
}
