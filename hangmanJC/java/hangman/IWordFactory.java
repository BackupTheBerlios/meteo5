package hangman;

/**
 * Interface fournisant la méthode de fabrication de IWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public interface IWordFactory {
	
	/**
	 * Méthode permettant de construire un IWord a partir d'une chaine de caractères.
	 *
	 * @param s la chaine de caractère.
	 * @return le IWord construit a partir de la chaine de caractère.
	 * 
	 * @pre s!=null // La chaine de caractère doit être non null.
	 */
	public IWord makeWord(String s);
}
