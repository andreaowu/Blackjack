import java.util.Hashtable;

/*
 * Represents each card in the game. Each card has a number and a suit, as
 * do normal playing cards.
 */

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
	 * 
	 * Input:
	 * - givenNumber: numerical value of the card
	 * - givenSuit: suit of the card
	 * 
	 * Output:
	 * None
	 * 
	 * Result:
	 * Creates card with given number and suit
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

	/* 
	 * Getter function for card number
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int: card number
	 * 
	 */
	public int getNumber() {
		return number;
	}

	/* 
	 * Getter function for suit of the card
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - String: suit of the card
	 * 
	 */
	public String getSuit() {
		return suit;
	}

	/* 
	 * Getter function for card value, as some card numbers don't map to
	 * their value (ie card with number 11 maps to card value 10, not 11).
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int: card value
	 * 
	 */
	public int getValue() {
		return value;
	}

	/* 
	 * Prints out the number and suit of this card
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - String: number and suit of this card
	 *
	 * Result:
	 * Translates the number into the appropriate card representation 
	 * (ie 11 translates to "J", 1 to "A", etc)
	 */
	public String printInfo() {
		
		String printNum;
		
		/* Find proper representation of card number */
		if (number > 1 && number < 11) {
			printNum = "" + number;
		} else {
			printNum = numToLetter.get(number);
		}
		
		return "" + printNum + " of " + suit;
	}

}
