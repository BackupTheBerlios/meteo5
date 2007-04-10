package hangman;


/**
 * Classe correspondant à l'état visible d'un objet NEWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 */
public class VisibleState extends AWordState {

	/**
	 * Singleton VisibleState.
	 */
	public static final VisibleState Singleton = new VisibleState();

	/**
	 * Constructeur de la classe VisibleState. 
	 */
	private VisibleState() {
	}

	/**
	 * Méthode permettant de changer l'état de l'objet en paramètre.
	 * 
	 * @post host.getClass().equals(host.getClass()@pre) //on ne modifie pas
	 *       l'objet (=> on ne peut pas passer de l'état visible à invisible)
	 */
	public void toggleState(NEWord host) {
	}

	/**
	 * Méthode exécutant l'algorithme spécifique à l'état visible.
	 * 
	 * @param host objet INEWord
	 * @param algo objet IWordAlgo
	 * @param inp paramètre quelconque
	 * 
	 * @return résultat de l'algorithme
	 */
	public Object execute(INEWord host, IWordAlgo algo, Object inp) {
		return algo.visibleCase(host, inp);
	}

	/*
	 * -----------------------------------------------------------------
	 * 
	 * Test de la classe
	 * 
	 * Configuration de test
	 * 
	 * Constructeur de la classe de test : @tcreate VisibleState.Singleton
	 * 
	 * @tunit TST_VisibleState() : test que le méthode toggleState ne change pas
	 * l'état d'un objet NEWord
	 * 
	 * @tunitcode
	 * { 
	 * NEWord word=new NEWord('d',new NEWord('a',new NEWord('v',null))); 
	 * testCheck("Visible ? :", word.getState().getClass().equals(VisibleState.getClass());
	 * word.toggleState(); 
	 * testCheck("Visible ? :", word.getState().getClass().equals(VisibleState.getClass());
	 */
}

