package hangman;


/**
 * Interface modélisant la fabrication d'un IWord.
 * 
 * @author Jerome Catric & Emmanuel Meheut
 */
public interface IWordFactory {
	
	/**
	 * Méthode permettant de fabriquer un IWord à partir d'une chaîne entrée
	 * en paramètre, en créant des objets IWord en suivant l'ordre des caractères
	 * de la chaîne w. Si la chaine w est "", retourne un IEmptyWord.
	 * 
	 * @param w chaine de caractère.
	 * 
	 * @return IEmptyWord si la chaine de caractère est "", sinon un INEWord.
	 * 
	 * @pre w!=null // La chaine de caractère ne peut être null
	 */
	public IWord makeWord(String w);
}
