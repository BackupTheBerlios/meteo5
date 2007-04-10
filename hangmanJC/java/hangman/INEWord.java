package hangman;


/**
 * Interface représentant la notion abstraite d'un mot non vide dont le 
 * premier est un caractère et le rest est un IWord. Elle a également 
 * deux états : visible et invisible. Et elle peut basculer entre ses 
 * deux états.
 *  
 * @author Jerome Catric & Emmanuel Meheut 
 */
public interface INEWord extends IWord {

	/**
	 * Méthode retournant le premier caractère.
	 * 
	 * @return le premier caractère de ce INEWord
	 */
	public char getFirst();

	/**
	 * Méthode retournant le rest de IWord.
	 * 
	 * @return le rest de IWord.
	 */
	public IWord getRest();

	/**
	 * Méthode permettant de changer l'état.
	 * (visible ou invisble)
	 */
	public void toggleState();
}
