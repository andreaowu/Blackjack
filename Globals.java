/*
 * STATUS for the outcome of the player's move.
 * WINNER means the points of all player's cards adds up to exactly 21.
 * BUSTED means the points of all player's cards adds up to more than 21.
 * CONTINUE means the points of all player's cards adds up to less than 21.
 */
enum STATUS { 
  WINNER, BUSTED, CONTINUE 
}

/*
 * Some global variables and a global function to delete trailing commas.
 */
final class Globals {
	/* String for "hit" move */
    public static final String HIT = "hit";
    /* String for "stay" move */
    public static final String STAY = "stay";
 
	/* 
	 * Deletes trailing comma
	 *
	 * Input: 
	 * - String s: string from trailing comma will get deleted
	 * 
	 * Output:
	 * - String: new string without the trailing comma
	 * 
	 */
    public static String deleteComma(String s) {
    	return s.substring(0, s.length() - 2);
    }
}