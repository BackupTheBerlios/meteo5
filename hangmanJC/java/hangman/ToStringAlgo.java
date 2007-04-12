package hangman;

/**
 * Classe permettant d'afficher la chaine caractère du mot a trouver avec les
 * caractères cachées et les caractères invisibles.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class ToStringAlgo implements IWordAlgo {

	/**
	 * Singleton représentant une instance unique d'un objet ToStringAlgo.
	 */
	public static final ToStringAlgo Singleton = new ToStringAlgo();

	/**
	 * Constructeur privé de la classe ToStringAlgo.
	 */
	private ToStringAlgo() {
		// your code here
	}

	/**
	 * Méthode utiliser quand les objets IEmptyWord
	 * 
	 * @param host le EmptyWord sur lequel l'algorithme est appliqué.
	 * @param param un caractère.
	 * @return une chaine de caractère.
	 * 
	 * @pre host!=null // le IEmptyWord doit être non null.
	 */
	public Object emptyCase(IEmptyWord host, Object param) {
		if (param == null) {
			param="";
		}
		return param;
	}

	/**
	 * Méthode affichant les caractères visibles.
	 * 
	 * @param host le INEWord sur lequel l'algorithme est appliqué.
	 * @param param un caractère.
	 * @return une chaine de caractère.
	 * 
	 * @pre host!=null // le NEWord doit être non null.
	 */
	public Object visibleCase(INEWord host, Object param) {
		String tmp = "";
		if (param != null) {
			tmp = (String) param;
		}
		tmp += Character.toString(host.getFirst());
		return (host.getRest()).execute(this, tmp);
	}

	/**
	 * Méthode substituant les caractères invisible par le caractère "-".
	 * 
	 * @param host le INEWord sur lequel l'algorithme est appliqué.
	 * @param param un caractère.
	 * @return une chaine de caractère.
	 * 
	 * @pre host!=null // le NEWord doit être non null.
	 */
	public Object invisibleCase(INEWord host, Object param) {
		String tmp = "";
		if (param != null) {
			tmp = (String) param;
		}
		tmp += "-";

		return (host.getRest()).execute(this, tmp);
	}
	
	/*
	 * -----------------------------------------------------------------
	 * Test de la classe ToStringAlgo
	 * -----------------------------------------------------------------
	 * 
	 * @tstart
	 * 	@tcreate ToStringAlgo.Singleton
	 * 	@tunit test1 : Test de la methode emptyCase()
	 * 		@tustart
	 * 	 		testMsg("Test sur du mot vide"); 
	 * 			testCheck("Mot vide :", ((String) emptyCase(EmptyWord.Singleton,null)).equals(""));
	 * 		@tuend
	 * @tunit test2 : Test de la methode invisibleCase()
	 * 		@tustart
	 * 	 		testMsg("Creation du mot : toc"); 
	 * 			IWord myWord = WordFactory.Singleton.makeWord("toc");
	 * 			NEWord myNEWord = (NEWord)myWord;
	 * 			testMsg("mot totalement invisible ?"); 
	 * 			testCheck("invisible ?", ((String) invisibleCase(myNEWord, null)).equals("---"));
	 * 			testMsg("mot partiellement invisible ?");
	 * 			myWord.execute(GuessCharAlgo.Singleton, new Character('o'));
	 * 			testCheck("invisible ?", ((String) invisibleCase(myNEWord, null)).equals("-o-"));
	 * 		@tuend
	 * @tunit test3 : Test de la methode visibleCase()
	 * 		@tustart
	 * 	 		testMsg("Creation du mot : toc"); 
	 * 			IWord myWord1 = WordFactory.Singleton.makeWord("toc");
	 * 			NEWord myNEWord1 = (NEWord)myWord1;
	 * 			testMsg("mot partiellement visible ?");
	 * 			myWord1.execute(GuessCharAlgo.Singleton, new Character('t'));
	 * 			myWord1.execute(GuessCharAlgo.Singleton, new Character('c'));
	 * 			testCheck("visible ?", ((String) visibleCase(myNEWord1, null)).equals("t-c"));
	 * 		@tuend
	 * @tend
	 * -----------------------------------------------------------------
	 */
}
