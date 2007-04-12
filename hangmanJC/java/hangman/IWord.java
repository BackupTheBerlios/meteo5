package hangman;

/**
 * Interface représentant un mot abstrait.
 * Elle permet de construire et gérer l'expression à trouver. 
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public interface IWord {

	/**
	 * Méthode acceptant le visiteur algo sur l'objet. 
	 * Elle permet d'exécuter une action spécifique sur un objet.
	 * 
	 * @param algo algorithme utiliser.
	 * @param param paramètre pouvant être utiliser par l'algorithme. 
	 * @return le résultat obtenu suite à l'algorithme.
	 * 
	 * @pre algo!=null // l'algorithme que l'on souhaite appliquer doit avoir été choisi.
	 */
    public Object execute(IWordAlgo algo, Object param);
}


