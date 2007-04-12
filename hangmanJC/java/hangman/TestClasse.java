package hangman;

public class TestClasse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IWord myWord = WordFactory.Singleton.makeWord("laval");
		//myWord.execute(GuessCharAlgo.Singleton, new Character('l'));
		myWord.execute(GuessCharAlgo.Singleton, new Character('a'));
		//myWord.execute(GuessCharAlgo.Singleton, new Character('k'));
		myWord.execute(GuessCharAlgo.Singleton, new Character('v'));
		/*myWord.execute(GuessCharAlgo.Singleton, new Character('t'));
		
		NEWord hh = (NEWord)myWord;
		
		if(((Boolean) GuessCharAlgo.Singleton.invisibleCase(hh, new Character('c'))).booleanValue() == true){
			System.out.println("ok pour i");
		}else{
			System.out.println("non i");
		}
		
		*/
		
		String mot = (String) myWord.execute(ToStringAlgo.Singleton, null);
		System.out.println(mot);

		
		if(((Boolean)myWord.execute(IsAllVisibleAlgo.Singleton, null)).equals(Boolean.TRUE)){
			System.out.println("gagner");
		}else{
			System.out.println("pas gagner encore");
		}
		
		
		
		
		
	}

}
