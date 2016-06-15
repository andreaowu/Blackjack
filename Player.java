import java.util.ArrayList;
import java.util.Arrays;

/*
 * Represents each player. Every player has a unique ID, which is unique per
 * BlackjackGame. Also holds all the cards and everything connected to that,
 * most importantly sums of the cards. Aces are taken account in the sums.
 */
public class Player {
	
	/* Player number */
	int id;
	/* Cards in hand */
	ArrayList<Card> cards;
	/* All possible sums of all cards in hard */
	ArrayList<Integer> possibleSums;
	/* String to print out cards */
	String displayCards;
	/* Number of Aces in hand */
	int numOfAces;
	/* Sum if there are no Aces in hand */
	int sum;
	/* Max sum of hand without going bust */
	int maxSumWithoutBust;

	/* 
	 * Constructor for Player class
	 *
	 * Input: 
	 * - int number: player ID, unique in each game
	 * 
	 * Output:
	 * - new Player object
	 * 
	 * Results:
	 * Initializes all the appropriate variables
	 */
	public Player(int number) {
		this.id = number + 1;
		cards = new ArrayList<Card>();
		possibleSums = new ArrayList<Integer>();
		displayCards = "Player " + (number + 1) + " has these cards: ";
		numOfAces = 0;
		sum = 0;
		maxSumWithoutBust = 0;
	}
	
	/* 
	 * Getter function for player ID
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int: player id
	 * 
	 */
	public int getNumber() {
		return id;
	}
	
	/* 
	 * Player gets a new card.
	 *
	 * Input: 
	 * - Card c: card that player receives
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * The card gets added to player's hand and display string. The new
	 * sum is calculated, and if the new card is an Ace, new sums are 
	 * calculated. The maximum sum is re-calculated and set each time a new
	 * card is received.
	 */
	public void getCard(Card c) {
		
		/* Add card to player's hand and display string */
		cards.add(c);
		displayCards += c.printInfo() + ", ";
		
		if (c.getValue() == 1) {

			/* New card is an Ace, increment counter */
			numOfAces += 1;
			ArrayList<Integer> newSums = new ArrayList<Integer>();
			maxSumWithoutBust = -1;
			
			/* This is the first Ace encountered */
			if (possibleSums.size() == 0) {
				newSums = addForAce(sum);
			} else {
				/* This is the second Ace encountered, so 
				 * there's already a list of possible sums from which
				 * to calculate the new list of possible sums */
				for (int i = 0; i < possibleSums.size(); i++) {
					int sum = possibleSums.get(i);
					newSums = addForAce(sum);
				}
			}
			
			/* Set possible sums to new array of calculated sums */
			possibleSums = newSums;
			
		} else {
			/* New card is not an Ace */
			
			if (numOfAces == 0) {
				/* No Aces in hand*/
				sum += c.getValue();
				setMaxSumWithoutBust(sum);
			} else {
				/* At least one Ace in hand */
				
				/* Iterate through all currently possible sums and add new
				 * card value to each possible sum. Find new maximum sum
				 * that doesn't go bust in the list of possible sums */
				for (int i = 0; i < possibleSums.size(); i++) {
					sum = possibleSums.get(i);
					possibleSums.set(i, sum + c.getValue());
					setMaxSumWithoutBust(sum);
				}
			}
		}
	}
	
	/* 
	 * Updates all possible sums list for this player when an Ace gets added
	 * to the hand.
	 *
	 * Input: 
	 * - int existingSum: sum already calculated from other cards in hand
	 * 
	 * Output:
	 * - ArrayList<Integer>: list of possible sums with an Ace added to the
	 * 		sum given as input 
	 * 
	 * Results:
	 * Creates a new list of sums to be calculated. Takes the given number and
	 * adds 1 and 11 to it, finds the new maximum sum without bust, then 
	 * finally returns these new sums in a list.
	 */
	public ArrayList<Integer> addForAce(int existingSum) {
		/* New list to return */
		ArrayList<Integer> newSums = new ArrayList<Integer>();
		
		/* Add Ace values to existing sum */
		int addOne = existingSum + 1;
		int addEleven = existingSum + 11;
		
		/* Add new sums to the list */
		newSums.add(addOne);
		newSums.add(addEleven);
		
		/* Calculate the maximum sum without busting using these new sums */
		setMaxSumWithoutBust(addEleven);
		setMaxSumWithoutBust(addOne);
		
		/* Return the lits of new sums with the Ace added */
		return newSums;
	}
	
	/* 
	 * Sets the new maximum sum of the cards in player's hand without 
	 * going bust.
	 *
	 * Input: 
	 * - int sum: number to be checked against current maximum sum without 
	 * 		going bust
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Sees whether the new sum is greater than 21; if it is, set it as the new
	 * maximum sum without going bust. Otherwise, change the max to -1 so the
	 * player becomes busted.
	 */
	public void setMaxSumWithoutBust(int sum) {
		if (sum <= 21) {
			if (sum > maxSumWithoutBust)
				maxSumWithoutBust = sum;
		} else {
			if (maxSumWithoutBust > 0)
				maxSumWithoutBust = -1;
		}
	}
	
	/* 
	 * Getter function for maxSumWithoutBust
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int: maximum sum of cards for this player that isn't over 21
	 * 
	 */
	public int getMaxSumWithoutBust() {
		return maxSumWithoutBust;
	}
	
	/* 
	 * Getter function for sums
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - ArrayList<Integer>: list of sums
	 * 
	 * Results:
	 * If the player has at least one Ace, it returns the list of possible
	 * sums. Otherwise, it creates a new list for the one possible sum and
	 * returns that. 
	 */
	public ArrayList<Integer> getSums() {
		
		/* Check if player has more than one Ace */
		if (numOfAces > 0)
			return possibleSums;
		
		/* Return new list with just the one sum */
		return new ArrayList<Integer>(Arrays.asList(sum));
	}
	
	/* 
	 * Prints out all cards in player's hand
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * void
	 * 
	 */
	public void printCards() {
		System.out.println(Globals.deleteComma(displayCards));
	}
	
	/* 
	 * Prints out possible sum of all cards in player's hand
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Makes a string out of all possible sum of cards in player's hand,
	 * then prints the string out.
	 */
	public void printSums() {
		
		/* Start all strings the same way */
		String possibleSumsString = "Possible sums: ";
		
		if (numOfAces == 0) {
			/* No Aces in hand, so concatenate sum into the possible sums */
			possibleSumsString += sum;
		} else {
			/* Aces in hand, iterate through all possible sums for printing */
			for (int i = 0; i < possibleSums.size(); i++)  {
				possibleSumsString += possibleSums.get(i) + ", ";
			}
			possibleSumsString = Globals.deleteComma(possibleSumsString);
		}
		
		System.out.println(possibleSumsString);
	}
}
