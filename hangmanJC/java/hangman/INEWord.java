package hangman;

/**
 * Interface listant les méthodes disponibles pour un mot non vide. Un NEWord se
 * compose d'un char et d'un IWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public interface INEWord extends IWord {

	/**
	 * Méthode permettant de récuperer le premier caractère d'un NEWord.
	 * 
	 * @return le premier caractère d'un NEWord.
	 */
	public char getFirst();

	/**
	 * Méthode retournant la dernière partie d'un NEWord, soit un IWord.
	 * 
	 * @return le reste de l'expression.
	 */
	public IWord getRest();

	/**
	 * Méthode permettant de modifier l'état d'un NEWord. (visible/invisible)
	 */
	public void toggleState();
}
