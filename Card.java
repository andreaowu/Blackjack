import java.util.Hashtable;

class Card {

	/* Represents the value of the card, can be: 1-13 */
	int number;
	/* Represents the suit of the card, can be:
	 * hearts, clubs, spades, diamonds
	 * The suit is needed to keep each card distinct */
	String suit;
	/* Represents the value of the card: 1-9 are valued at what they numerically are, 
	 * 10-13 are all valued at 10 */ 
	int value;
	/* Translates 1, 11, 12, 13 to appropriate letters */
	Hashtable<Integer, String> numToLetter;

	/* 
	 * Constructor for Card class
	 * Input:
	 * - givenNumber: numerical value of the card
	 * - givenSuit: suit of the card
	 */
	public Card(int number, String suit) {
		this.number = number;
		this.suit = suit;

		if (number > 10) {
			value = 10;
		} else {
			value = number;
		}

		numToLetter = new Hashtable<Integer, String>();
		numToLetter.put(1, "A");
		numToLetter.put(11, "J");
		numToLetter.put(12, "Q");
		numToLetter.put(13, "K");
	}

	/* Returns the numerical value of this card */
	public int getNumber() {
		return number;
	}

	/* Returns the suit of this card */
	public String getSuit() {
		return suit;
	}

	/* Returns the value of this card */
	public int getValue() {
		return value;
	}

	/* Prints out the card */
	public String printInfo() {
		String printNum;
		if (number > 1 && number < 11) {
			printNum = "" + number;
		} else {
			printNum = numToLetter.get(number);
		}
		return "" + printNum + " of " + suit;
	}

}
