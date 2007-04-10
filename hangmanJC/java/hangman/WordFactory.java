package hangman;


/**
 * Classe permettant la fabrication de IWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 */
public class WordFactory implements IWordFactory {

	/**
	 * Singleton WordFactory
	 */
	public static final WordFactory Singleton = new WordFactory();

	/**
	 * Contructeur de la classe WordFactory.
	 */
	private WordFactory() {
	}

	/**
	 * Méthode permettnat la fabrication d'un IWord à partir de la chaîne de caractères
	 * placé en paramètre, en créant des objets IWord en suivant l'ordre des caractères
	 * de la chaîne w.
	 * 
	 * @param w la chaine de caractère.
	 * 
	 * @return le mot IWord ou EmptyWord si la chaine w est "".
	 * 
	 * @pre w!=null // La chaine de caractère ne peut être un objet vide
	 */
	public IWord makeWord(String w) {
		IWord wordList = EmptyWord.Singleton;
		for (int i = w.length() - 1; i >= 0; i--) {
			wordList = new NEWord(w.charAt(i), wordList);
		}
		return wordList;
	}


	
	
	/*
	 * Test de la classe WordFactory
	 * --------------------------------
	 * 
	 * @tstart
	 * 	@tcreate WordFactory.Singleton
	 * 	@tunit makeWord : test de fabrication de mot
	 * 		@tustart
	 * 		testMsg("création d'un mot vide"); 
	 * 		@tuend
	 * @tend
	 */

}
