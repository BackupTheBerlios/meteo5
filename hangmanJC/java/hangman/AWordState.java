package hangman;

/**
 * Classe permettant de définir l'état d'un NEWord.<br>
 * (Visible ou invisible)
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 */
public abstract class AWordState {

	/**
	 * Méthode permettant de changer l'état d'un NEWord.
	 * 
	 * @param host objet NEWord dont l'état sera changé.
	 * 
	 * @pre host!=null // l'objet NEWord dont on veut changer l'état ne doit pas être null.
	 */
	public abstract void toggleState(NEWord host);

	/**
	 * Méthode acceptant le visiteur algo sur l'objet INEWord.
	 * 
	 * @param host objet INEWord sur lequel l'algorithme est appliqué.
	 * @param algo algorithme appliquer sur l'objet host.
	 * @param param paramètre pouvant être utiliser par l'algorithme. 
	 * @return le résultat obtenu suite à l'algorithme.
	 * 
	 * @pre host!=null // l'objet INEWord sur lequel on applique l'algorithme ne doit pas être null.
	 * @pre algo!=null // l'algorithme que l'on souhaite appliquer sur le INEWord doit avoir été choisi.
	 */
	public abstract Object execute(INEWord host, IWordAlgo algo, Object param);
}
