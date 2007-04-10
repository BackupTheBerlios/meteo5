package hangman;

/**
 * Interface des classes permettant de construire et gérer l'expression à trouver. 
 * Elle permet aussi d'indiquer au modèle quand la partie est gagnée, 
 * car tous ses caractères sont alors visibles.
 * 
 * @author Jerome Catric & Emmanuel Meheut 
 */
public interface IWord {
	
	/**
	 * Permet d'exécuter une actions spécifiques sur un objet. 
	 * 
	 * @param algo objet IWordAlgo
	 * @param imp paramètre quelconque.
	 *            
	 * @return le résultat de l'algorithme
	 * 
	 * @pre algo != null // algo doit être non null
	 */
	public Object execute(IWordAlgo algo, Object imp);
}
