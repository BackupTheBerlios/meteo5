package hangman;


/**
 * Classe correspond à l'état invisible d'un objet NEWord.
 * 
 * @author Jerome Catric & Emmanuel Meheut 
 */
public class InvisibleState extends AWordState {

	/**
	 * Singleton InvisibleState.
	 */
	public static final InvisibleState Singleton = new InvisibleState();

	/**
	 * Constructeur de la classe InvisibleState.
	 */
	private InvisibleState() {
	}

	/**
	 * Change l'état de l'objet d'invisible à visible.
	 * 
	 * @param host l'objet NEWord dont on doit changer l'état
	 * 
	 * @post this.getClass().getName().endsWith("VisibleState") //l'état après modification doit être "visible".
	 */
	public void toggleState(NEWord host) {
		host.setWordState(VisibleState.Singleton);
	}

	/**
	 * Effectue l'algorithme spécifique à l'état invisible.
	 * 
	 * @param host objet sur lequel l'algorithme est appliqué
	 * @param algo algorithme à appliquer
	 * @param inp paramètre éventuel de l'agorithme
	 * 
	 * @return résultat de l'algorithme.
	 */
	public Object execute(INEWord host, IWordAlgo algo, Object inp) {
		return algo.invisibleCase(host, inp);
	}

	/*
	 * -----------------------------------------------------------------
	 * 
	 * Test de la classe
	 * 
	 * Configuration de test
	 * 
	 * Constructeur de la classe de test : 
	 * @tcreate InvisibleState.Singleton
	 * 
	 * @tunit TST_create() : test que le méthode toggleState modifie l'état de
	 * visibilité d'un objet NEWord
	 * 
	 * @tunitcode
	 * { 
	 * NEWord word=new NEWord('d',new NEWord('a',new NEWord('v',null)));
	 * testCheck("Visible ? :", word.getState().getClass().equals(VisibleState.getClass() );
	 * word.toggleState();
	 * testCheck("Visible ? :",
	 * word.getState().getClass().equals(InvisibleState.getClass() );
	 * }
	 */
}

