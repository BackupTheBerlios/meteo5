package hangman;

/**
 * Interface donnant accès aux différentes méthodes pouvant être utiliser sur
 * les objets de type IWord.
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public interface IWordAlgo {
	
	/**
	 * Cette méthode sera utiliser sur les objets de type IEmptyWord.
	 * 
	 * @param host l'objet IEmptyWord qui sera utilisé.
	 * @param param un caractère quelconque pouvant être utiliser ou pas.
	 * @return le résultat issue de cette méthode qui est un objet.
	 *  
	 * @pre host!=null // le IEmptyWord doit être non null.
	 */
	public Object emptyCase(IEmptyWord host, Object param);

	/**
	 * Méthode utiliser sur les objet INEWord qui on l'état visible.
	 * 
	 * @param host le INEWord qui sera utilisé.
	 * @param param un caractère quelconque pouvant être utiliser ou pas.
	 * @return le résulta issue de cette méthode qui est un objet.
	 *         
	 * @pre host!=null // le INEWord doit être non null.
	 */
	public Object visibleCase(INEWord host, Object param);

	/**
	 * Méthode utiliser sur les objet INEWord qui on l'état invisible.
	 * 
	 * @param host le INEWord qui sera utilisé.
	 * @param param un caractère quelconque pouvant être utiliser ou pas.
	 * @return le résulta issue de cette méthode qui est un objet.
	 *         
	 * @pre host!=null // le INEWord doit être non null.
	 */
	public Object invisibleCase(INEWord host, Object param);
}
