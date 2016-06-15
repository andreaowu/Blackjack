import java.util.ArrayList;
import java.util.Arrays;

/*
 * Represents each player. Every player has a unique ID, which is unique per
 * BlackjackGame. Also holds all the cards and everything connected to that,
 * most importantly points of the cards. Aces are taken account in the points.
 */
public class Player {
	
	/* Player number */
	int id;
	/* Cards in hand */
	ArrayList<Card> cards;
	/* All possible point totals of all cards in hard */
	ArrayList<Integer> possiblePointTotals;
	/* String to print out cards */
	String displayCards;
	/* Number of Aces in hand */
	int numOfAces;
	/* Points if there are no Aces in hand */
	int points;
	/* Max points of hand without going bust */
	int maxPointsWithoutBust;

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
		possiblePointTotals = new ArrayList<Integer>();
		displayCards = "Player " + (number + 1) + " has these cards: ";
		numOfAces = 0;
		points = 0;
		maxPointsWithoutBust = 0;
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
	 * points is calculated, and if the new card is an Ace, new points are 
	 * calculated. The maximum points is re-calculated and set each time a new
	 * card is received.
	 */
	public void getCard(Card c) {
		
		/* Add card to player's hand and display string */
		cards.add(c);
		displayCards += c.printInfo() + ", ";
		
		if (c.getValue() == 1) {

			/* New card is an Ace, increment counter */
			numOfAces += 1;
			ArrayList<Integer> newPoints = new ArrayList<Integer>();
			maxPointsWithoutBust = -1;
			
			/* This is the first Ace encountered */
			if (possiblePointTotals.size() == 0) {
				newPoints = addForAce(points);
			} else {
				/* This is the second Ace encountered, so 
				 * there's already a list of possible points from which
				 * to calculate the new list of possible points */
				for (int i = 0; i < possiblePointTotals.size(); i++) {
					int points = possiblePointTotals.get(i);
					newPoints = addForAce(points);
				}
			}
			
			/* Set possible points to new array of calculated points */
			possiblePointTotals = newPoints;
			
		} else {
			/* New card is not an Ace */
			
			if (numOfAces == 0) {
				/* No Aces in hand*/
				points += c.getValue();
				setMaxPointsWithoutBust(points);
			} else {
				/* At least one Ace in hand */
				
				/* Iterate through all currently possible point totals and 
				 * add new card value to each possible points. Find new maximum
				 * points that don't go bust in the list of possible points */
				for (int i = 0; i < possiblePointTotals.size(); i++) {
					points = possiblePointTotals.get(i);
					possiblePointTotals.set(i, points + c.getValue());
					setMaxPointsWithoutBust(points);
				}
			}
		}
	}
	
	/* 
	 * Updates all possible points list for this player when an Ace gets added
	 * to the hand.
	 *
	 * Input: 
	 * - int existingPoints: points already calculated from other cards in hand
	 * 
	 * Output:
	 * - ArrayList<Integer>: list of possible points with an Ace added to the
	 * 		points given as input 
	 * 
	 * Results:
	 * Creates a new list of points to be calculated. Takes given number and
	 * adds 1 and 11 to it, finds the new maximum points without bust, then 
	 * finally returns these new points in a list.
	 */
	public ArrayList<Integer> addForAce(int existingPointTotal) {
		/* New list to return */
		ArrayList<Integer> newPoints = new ArrayList<Integer>();
		
		/* Add Ace values to existing points */
		int addOne = existingPointTotal + 1;
		int addEleven = existingPointTotal + 11;
		
		/* Add new points to the list */
		newPoints.add(addOne);
		newPoints.add(addEleven);
		
		/* Calculate the maximum points without busting using these new points */
		setMaxPointsWithoutBust(addEleven);
		setMaxPointsWithoutBust(addOne);
		
		/* Return the list of new points with the Ace added */
		return newPoints;
	}
	
	/* 
	 * Sets the new maximum points of the cards in player's hand without 
	 * going bust.
	 *
	 * Input: 
	 * - int points: number to be checked against current maximum points without 
	 * 		going bust
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Sees whether the new points are greater than 21; if it is, set it as the new
	 * maximum points without going bust. Otherwise, change the max to -1 so the
	 * player becomes busted.
	 */
	public void setMaxPointsWithoutBust(int points) {
		if (points <= 21) {
			if (points > maxPointsWithoutBust)
				maxPointsWithoutBust = points;
		} else {
			if (maxPointsWithoutBust > 0)
				maxPointsWithoutBust = -1;
		}
	}
	
	/* 
	 * Getter function for maxpointsWithoutBust
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int: maximum point total of cards for this player that isn't over 21
	 * 
	 */
	public int getMaxPointsWithoutBust() {
		return maxPointsWithoutBust;
	}
	
	/* 
	 * Getter function for points
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - ArrayList<Integer>: list of points
	 * 
	 * Results:
	 * If the player has at least one Ace, it returns the list of possible
	 * points. Otherwise, it creates a new list for the one possible points and
	 * returns that. 
	 */
	public ArrayList<Integer> getPoints() {
		
		/* Check if player has more than one Ace */
		if (numOfAces > 0)
			return possiblePointTotals;
		
		/* Return new list with just the one points */
		return new ArrayList<Integer>(Arrays.asList(points));
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
	 * Prints out possible points of all cards in player's hand
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Makes a string out of all possible points of cards in player's hand,
	 * then prints the string out.
	 */
	public void printPoints() {
		
		/* Start all strings the same way */
		String possiblePointsString = "Possible points: ";
		
		if (numOfAces == 0) {
			/* No Aces in hand, so concatenate points into the possible points */
			possiblePointsString += points;
		} else {
			/* Aces in hand, iterate through all possible points for printing */
			for (int i = 0; i < possiblePointTotals.size(); i++)  {
				possiblePointsString += possiblePointTotals.get(i) + ", ";
			}
			possiblePointsString = Globals.deleteComma(possiblePointsString);
		}
		
		System.out.println(possiblePointsString);
	}
}
