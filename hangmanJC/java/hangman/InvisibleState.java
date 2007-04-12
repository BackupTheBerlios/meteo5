package hangman;

/**
 * Cette classe correspond à l'état invisible d'un NEWord
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class InvisibleState extends AWordState {

	/**
	 * Singleton représentant une instance unique d'un objet InvisibleState.
	 */
	public static final InvisibleState Singleton = new InvisibleState();

	/**
	 * Constructeur privé de la classe InvisibleState.
	 */
	private InvisibleState() {
		// your code here
	}

	/**
	 * Méthode permettant de modifier l'état du NEWord.
	 * Le NEWord passe de l'état invisible à celui de visible.
	 * 
	 * @param host le NEWord dont l'état sera modifié.
	 * 
	 * @pre host!=null // le NEWord ne doit pas être null.
	 */
	public void toggleState(NEWord host) {
		host.setWordState(VisibleState.Singleton);
	}

	/**
	 * Méthode exécutant l'algorithme spécifique pour l'état invisible de l'objet NEWord.
	 * 
	 * @param host le NEWord sur lequel l'algorithme sera appliqué.
	 * @param algo l'algorithme qui sera appliquer sur le NEWord.
	 * @param param un paramètre quelconque
	 * @return le résultat issu suite à l'algorithme.
	 * 
	 * @pre host!=null // le NEWord doit être non null.
	 * @pre algo!=null // l'algorithme appliquer sur le NEWord doit être définie (non null).
	 */
	public Object execute(INEWord host, IWordAlgo algo, Object param) {
		return algo.invisibleCase(host, param);
	}
}
