enum STATUS { 
  WINNER, BUSTED, CONTINUE 
}

final class Globals {

    public static final String HIT = "hit";
    public static final String STAY = "stay";
 
    public static String deleteComma(String s) {
    	return s.substring(0, s.length() - 2);
    }
}