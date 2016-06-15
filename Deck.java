import java.util.HashSet;

/*
 * Represents a standard deck of 52 cards; 4 suits with A, 2-10, J, Q, K.
 */
class Deck {

	/* Tracks all the cards still available to get dealt */
	Card[] allCards;
	/* Tracks all cards already dealt out */
	HashSet<Card> used;

	/* 
	 * Constructor for Deck class
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * new Deck object
	 * 
	 * Results:
	 * Gets all possible numbers and all possible suits for the cards, then 
	 * gets all combinations of numbers with suits and puts these into a list.
	 */
	public Deck() {
		allCards = new Card[52];
		used = new HashSet<Card>();

		/* Get all possible numbers and all possible suits for the cards */
		int[] allNums = possibleNumbers();
		String[] allSuits = possibleSuits();

		/* Generate the entire deck */
		int ind = 0;
		for (int i = 0; i < allNums.length; i++) {
			int num = allNums[i];
			for (int j = 0; j < allSuits.length; j++) {
				this.allCards[ind] = new Card(num, allSuits[j]);
				ind += 1;
			}
		}
	}

	/* 
	 * Generates all possible numerical values for the cards in the deck.
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int[]: an array of numbers 1-13, representing numerical values 
	 * 		for cards in the deck
	 */
	public static int[] possibleNumbers() {
		int[] allNums = new int[13];
		for (int i = 0; i < 13; i++) {
			allNums[i] = i + 1;
		}
		return allNums;
	}

	/* 
	 * Generates all possible suits for the cards in the deck.
	 *
	 * Input:
	 * void
	 * 
	 * Output:
	 * - String[]: an array of all possible suits for cards in the deck 
	 * 
	 */
	public static String[] possibleSuits() {
		String[] allSuits = {"hearts", "clubs", "spades", "diamonds"};
		return allSuits;
	}

	/* 
	 * Gets a random card from the deck, removes it from the deck, and puts it
	 * into the used pile 
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - Card: card that hasn't been given to any player yet
	 * 
	 * Results:
	 * Generates a random number between 1-52 to pick from the array of cards.
	 * Continues to randomly pick a card until a card that hasn't been given
	 * to a player already gets picked.
	 */
	public Card getRandom() {
		/* Generate a random number between 0 to 51 to pick from cards list */
		Card randomCard = allCards[(int ) (Math.random() * 52)];

		/* Until picked card isn't already given, pick another random card */
		while (used.contains(randomCard)) {
			randomCard = allCards[(int ) (Math.random() * 52)];
		}

		/* Add the picked card into the used pile */
		used.add(randomCard);
		
		return randomCard;
	}

}
