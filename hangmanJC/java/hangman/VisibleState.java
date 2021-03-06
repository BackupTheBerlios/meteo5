package hangman;


/**
 * Cette classe correspond à l'état visible d'un NEWord
 * 
 * @author Jérôme Catric & Emmanuel Meheut
 * @since 10 Avril 2007
 * 
 */
public class VisibleState extends AWordState {

	/**
	 * Singleton représentant une instance unique d'un objet VisibleState.
	 */
    public static final VisibleState Singleton = new VisibleState();

	/**
	 * Constructeur privé de la classe VisibleState.
	 */
    private  VisibleState() {        
        // your code here
    } 

	/**
	 * Méthode permettant de modifier l'état du NEWord.
	 * (rien à faire)
	 * 
	 * @param host le NEWord dont l'état sera modifié.
	 * 
	 * @pre host!=null // le NEWord ne doit pas être null.
	 */
    public void toggleState(NEWord host) {        
        // your code here
    } 

	/**
	 * Méthode exécutant l'algorithme spécifique pour l'état visible de l'objet NEWord.
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
        return algo.visibleCase(host, param);
    } 
    
	/*
	 * -----------------------------------------------------------------
	 * Test de la classe VisibleState
	 * -----------------------------------------------------------------
	 * 
	 * *@tstart
	 * 	*@tcreate VisibleState.Singleton
	 * 	*@tunit toggleState : Test de la methode toggleState()
	 * 		*@tustart
	 * 	 		*testMsg("Creation d'un NEWord et mise a l'état visible");
	 *				*NEWord myWord=new NEWord('t',new NEWord('o',new NEWord('c',null)));
	 *				*myWord.setWordState(VisibleState.Singleton);
	 *				*testMsg("Avant la méthode toggleState, vérification que l'état du NEWord soit visible");
	 *				*testCheck("visible ? :", myWord.getState().getClass().equals(VisibleState.getClass()); 
	 *				*myWord.toggleState();
	 *				*testMsg("Après la méthode toggleState, vérification que l'état du NEWord soit toujours visible");
	 *				*testCheck("visible ? :", myWord.getState().getClass().equals(VisibleState.getClass());
	 * 		*@tuend
	 * *@tend
	 * -----------------------------------------------------------------
	 */
    
 }
