import java.util.HashSet;

class Deck {

	/* Tracks all the cards still available to get dealt */
	Card[] allCards;
	/* Tracks all cards already dealt out */
	HashSet<Card> used;

	/* 
	 * Constructor for Deck class
	 * It creates a new array of 52 distinct cards, 
	 * numerical values 1-13 for each suit
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

	/* Returns all possible numbers for the deck */
	public static int[] possibleNumbers() {
		int[] allNums = new int[13];
		for (int i = 0; i < 13; i++) {
			allNums[i] = i + 1;
		}
		return allNums;
	}

	/* Returns all possible suits for the deck */
	public static String[] possibleSuits() {
		String[] allSuits = {"hearts", "clubs", "spades", "diamonds"};
		return allSuits;
	}

	/* 
	 * Gets a random card from the deck, removes it from the deck, and puts it
	 * into the used pile 
	 */
	public Card getRandom() {
		/* Generate a random number to pick up from the array of cards */
		Card randomCard = allCards[(int ) (Math.random() * 52)];

		/* Until the card picked isn't in the used pile, pick another random card */
		while (used.contains(randomCard)) {
			randomCard = allCards[(int ) (Math.random() * 52)];
		}

		/* Add the picked card into the used pile */
		used.add(randomCard);
		return randomCard;
	}

}
