package hangman;


/**
 * Cette classe permet de savoir si tous les caractères de l'expression à trouver
 * sont visibles. Si tel est le cas on annonce la victoire au joueur. On va
 * rechercher dans l'objet IWord s'il y a des caractères invisible. Si oui, on
 * retourne false, sinon true.
 * 
 * @author Jerome Catric & Emmanuel Meheut 
 */
public class IsAllVisibleAlgo implements IWordAlgo {

	/**
	 * Singleton IsAllVisibleAlgo.
	 */
	public static final IsAllVisibleAlgo Singleton = new IsAllVisibleAlgo();

	/**
	 * Constructeur de la classe IsAllVisibleAlgo.
	 */
	private IsAllVisibleAlgo() {
	}

	/**
	 * Cette méthode retourne toujours vrai puisque qu'il s'agit d'un
	 * IEmptyWord, donc tous les caractères sont visibles.
	 * 
	 * @param host objet IEmptyWord
	 * @param inp non utilisé
	 * 
	 * @return un booléen dont la valeur est toujours true.
	 */
	public Object emptyCase(IEmptyWord host, Object inp) {
		return new Boolean(Boolean.TRUE);
	}

	/**
	 * Cette méthode exécute récursivement l'algorithme jusqu'à atteindre un
	 * IEmptyWord ou un INEWord non visible
	 * 
	 * @param host objet INEWord
	 * @param inp  non utilisé
	 * 
	 * @return un boolean à la valeur true si l'algorithme trouve un
	 *         IEmptyWord sinon false si c'est un INEWord non visible
	 */
	public Object visibleCase(INEWord host, Object inp) {
		return host.getRest().execute(this, inp);
	}

	/**
	 * Méthode retourant toujours faux puisque l'algorithme s'exécute
	 * sur un INEWord non visible.
	 * 
	 * @param host objet INEWord
	 * @param inp non utilisé
	 * 
	 * @return un booléen ayant toujours pour valeur false.
	 */
	public Object invisibleCase(INEWord host, Object inp) {
		return new Boolean(Boolean.FALSE);
	}

	/*
	 * -----------------------------------------------------------------
	 * 
	 * Test de la classe
	 * 
	 * Configuration de test
	 * 
	 * Constructeur de la classe de test : @tcreate IsAllVisibleAlgo.Singleton
	 * 
	 * @tunit TST_create() : Test de l'algorithme IsAllVisibleAlgo
	 * 
	 * @tunitcode 
	 * { 
	 * NEWord word=new NEWord('b',new NEWord('a',new NEWord('d',null)));
	 * 
	 * testCheck("Mot vide :", ( (Boolean) invisibleCase(EmptyWord.Singleton,null) ).booleanValue() == true); 
	 * testCheck("Perdu (invisible) ?", ((Boolean) invisibleCase(word, null) ).booleanValue() == false);
	 * testCheck("Perdu (visible) ?", ( (Boolean) visibleCase(word, null)).booleanValue() == false); 
	 * word.execute(GuessCharAlgo.Singleton, 'b');
	 * word.execute(GuessCharAlgo.Singleton, 'a');
	 * word.execute(GuessCharAlgo.Singleton, 'd'); 
	 * testCheck("Gagne ?", ((Boolean) visibleCase(word, null) ).booleanValue() == true);
	 * }
	 * 
	 */

}