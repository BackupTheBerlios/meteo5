package hangman;

/**
 * Classe permettant de savoir si le mot a été découvert. Pour le savoir on va
 * parcourir le mot (IWord), si on trouve au minimum un caractère invisible on
 * retourne false, sinon true.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class IsAllVisibleAlgo implements IWordAlgo {

	/**
	 * Singleton représentant une instance unique d'un objet IsAllVisibleAlgo.
	 */
	public static final IsAllVisibleAlgo Singleton = new IsAllVisibleAlgo();

	/**
	 * Constructeur privé de la classe IsAllVisibleAlgo.
	 */
	private IsAllVisibleAlgo() {
		// your code here
	}

	/**
	 * Cette méthode retourne toujours vrai puisque si l'algorithme est exécuter
	 * sur un IEmtpyWord, celui ci est arrivé au bout et tous les caractères
	 * sont visibles.
	 * 
	 * @param host objet IEmptyWord sur lequel on exécute l'algorithme.
	 * @param param un caractère quelconque - non utiliser.
	 * @return toujours true.
	 * 
	 * @pre host!=null // le IEmptyWord doit être non null.
	 */
	public Object emptyCase(IEmptyWord host, Object param) {
		return new Boolean(Boolean.TRUE);
	}

	/**
	 * Cette méthode est exécuté d'une manière récursive. Elle retourne vrai si
	 * elle trouve un IEmptyWord ou un INEWord qui entièrement visible.
	 * 
	 * 
	 * @param host objet INEWord sur lequel on exécute l'algorithme.
	 * @param param un caractère quelconque - non utiliser.
	 * @return true si aucun caractère est invisible, sinon false.
	 * 
	 * @pre host!=null // le INEWord doit être non null.
	 */
	public Object visibleCase(INEWord host, Object param) {
		return host.getRest().execute(this, param);
	}

	/**
	 * Cette méthode retourne toujours faux, car si l'algorithme est exécuter
	 * sur un INEWord non visible, sa signifie qu'il y a des caractères
	 * invisibles.
	 * 
	 * @param host objet INEWord sur lequel on exécute l'algorithme.
	 * @param param un caractère quelconque - non utiliser.
	 * @return toujours false.
	 * 
	 * @pre host!=null // le INEWord doit être non null.
	 */
	public Object invisibleCase(INEWord host, Object param) {
		return new Boolean(Boolean.FALSE);
	}
	
	/*
	 * -----------------------------------------------------------------
	 * Test de la classe IsAllVisibleAlgo
	 * -----------------------------------------------------------------
	 * 
	 * @tstart
	 * 	@tcreate IsAllVisibleAlgo.Singleton
	 * 	@tunit test1 : Test de la methode emptyCase()
	 * 		@tustart
	 * 	 		testMsg("Test du cas du mot vide"); 
	 * 			testCheck("Mot vide :", ((Boolean) emptyCase(EmptyWord.Singleton,null)).booleanValue() == true);
	 * 		@tuend
	 * @tunit test2 : Test de la methode invisibleCase()
	 * 		@tustart
	 * 	 		testMsg("Creation d'un mot"); 
	 * 			IWord myWord = WordFactory.Singleton.makeWord("toc");
	 * 			NEWord myNEWord = (NEWord)myWord;
	 * 			testMsg("Verification que le mot est invisible."); 
	 * 			testCheck("invisible ?", ((Boolean) invisibleCase(myNEWord, null)).booleanValue() == false);
	 * 		@tuend
	 * @tunit test3 : Test de la methode visibleCase()
	 * 		@tustart
	 * 	  		testMsg("Creation d'un mot"); 
	 * 			IWord myWord = WordFactory.Singleton.makeWord("toc");
	 * 			NEWord myNEWord = (NEWord)myWord;
	 * 	 		testMsg("Test sur un mot entierement cacher"); 
	 * 			testCheck("non visible ?", ((Boolean) visibleCase(myNEWord, null)).booleanValue() == false);
	 * 			testMsg("Test sur un mot partiellement visible"); 
	 * 			myNEWord.execute(GuessCharAlgo.Singleton, 't');
	 * 			testCheck("non visible ?", ((Boolean) visibleCase(myNEWord, null)).booleanValue() == false);
	 * 			myNEWord.execute(GuessCharAlgo.Singleton, 'o');
	 * 			myNEWord.execute(GuessCharAlgo.Singleton, 'c');
	 * 			testMsg("Mot visible"); 
	 * 			testCheck("visible ?", ((Boolean) visibleCase(myNEWord, null)).booleanValue() == true);
	 * 		@tuend
	 * @tend
	 * 
	 * -----------------------------------------------------------------
	 */
	
	
	
}
