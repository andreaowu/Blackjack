import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

class BlackjackGame {

	/* Tracks number of players in this game*/
	int numOfPlayers;
	/* Tracks cards received by each player:
	 * key: player number
	 * value: list of cards for that player */
	Hashtable<Integer, ArrayList<Card>> cardsPerPlayer;
	/* Tracks sum of all cards for each player:
	 * key: player number
	 * value: sum for cards of that player */
	Hashtable<Integer, Integer> sumPerPlayer;
	/* Tracks print string of cards for each player
	 * key: player number
	 * value: string to be printed to show what cards the player has */
	Hashtable<Integer, String> displayPerPlayer;
	/* Set for players who have Aces
	 * key: player number
	 * value: number of Aces player has */
	Hashtable<Integer, Integer> hasAces;
	/* Deck of cards for this game */
	Deck d;

	/* 
	 * Constructor for BlackjackGame
	 *
	 * Input: 
	 * - givenNumOfPlayers: number of players for this game
	 */
	public BlackjackGame(int numOfPlayers) {
		/* Instantiate all the variables */
		this.numOfPlayers = numOfPlayers;
		cardsPerPlayer = new Hashtable<Integer, ArrayList<Card>>();
		sumPerPlayer = new Hashtable<Integer, Integer>();
		displayPerPlayer = new Hashtable<Integer, String>();
		hasAces = new Hashtable<Integer, Integer>();
		d = new Deck(); 
		for (int i = 0; i < numOfPlayers; i++) {
			displayPerPlayer.put(i, "Player " + (i + 1) + " has these cards: ");
			sumPerPlayer.put(i, 0);
		}
	}

	public void startGame() {
		/* Loop through each player and give each two cards */
		for (int i = 0; i < numOfPlayers; i++) {
			Card c1 = d.getRandom();
			Card c2 = d.getRandom();

			/* Save cards for this player */
			ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(c1, c2));
			cardsPerPlayer.put(i, cards);

			/* Save values for this player */
			sumPerPlayer.put(i, c1.getValue() + c2.getValue());

      		/* Need to have both if statements just in case both cards are Aces */
			if (c1.getNumber() == 1) {
				addAce(i);
				sumPerPlayer.put(i, 0);
			}
			if (c2.getNumber() == 1) {
				addAce(i);
				sumPerPlayer.put(i, sumPerPlayer.get(i) - 1);
			}

			/* Add to the string of cards to print */
			String newHand = displayPerPlayer.get(i) + c1.printInfo() 
			+ ", " + c2.printInfo();
			displayPerPlayer.put(i, newHand);
			System.out.println(newHand);
		}
	}

	/*
	 * Taking care of the case when a player has an Ace
	 * Input:
	 * - player: player number
	 */
	public void addAce(int player) {
		if (hasAces == null)
			return;
		if (hasAces.containsKey(player)) {
			hasAces.put(player, hasAces.get(player) + 1);
		} else {
			hasAces.put(player, 1);
		}
	}

	public void stayMove(int player) {
		System.out.println(displayPerPlayer.get(player));
		printPossibleSums(player);
	}
	
	public void printPossibleSums(int player) {
		ArrayList<Integer> allSums = possibleSums(player);
		String possibleSums = "";
		
		for (int i = 0; i < allSums.size(); i++) {
			int sum = allSums.get(i);
			possibleSums += sum + ", ";
		}
		
		System.out.println("Possible sums: " + possibleSums.substring(0, possibleSums.length() - 2));
	}
	
	public int maxSumWithoutBust(int player) {
		ArrayList<Integer> allSums = possibleSums(player);
		int maxSum = allSums.get(0);
		for (int i = 1; i < allSums.size(); i++) {
			int sum = allSums.get(i);
			if (sum > maxSum)
				maxSum = sum; 
		}
		return maxSum;
	}
	
	/* */
	public ArrayList<Integer> possibleSums(int player) {
		ArrayList<Integer> allSums = new ArrayList<Integer>();
		
		/* Get player's sum total */
		int total = sumPerPlayer.get(player);
		
		if (hasAces.containsKey(player)) {

			/* Calculate possible sums with Ace */
			int numOfAces = hasAces.get(player);
			if (hasAces != null && hasAces.containsKey(player) && numOfAces > 0) {
				for (int i = 0; i <= numOfAces; i++) 
					allSums.add(total + (numOfAces - i) * 1 + i * 11);
			}
		} else {
			allSums.add(total);
		}
		
		return allSums;
	}
	
	/* 
	 * Gets another random card from the deck and returns it
	 * Calculates the player's total and tells busted if so
	 * Inputs:
	 * - player: player number
	 * Outputs:
	 * - true: player can still continue playing
	 * - false: player is busted
	 */
	public STATUS hitMove(int player) {

		/* Get a new card */
		Card newCard = d.getRandom();

		/* Add it to player's hand */
		cardsPerPlayer.get(player).add(newCard);
		
		/* Case when the new card is an Ace or this player has an Ace */
		if (newCard.getNumber() == 1)
			addAce(player);
		
		/* Update the player's sum if the new card isn't an Ace */
		if (newCard.getNumber() != 1) {
			sumPerPlayer.put(player, sumPerPlayer.get(player) + newCard.getValue());
		}

		/* Get all possible sums using cards in player's hand */
		ArrayList<Integer> allSums = possibleSums(player);

		/* Track whether all the possible sums are more than 21 */
		Boolean allSumsExceed = null;
		boolean win = false;

		for (int i = 0; i < allSums.size(); i++) {
			int sum = allSums.get(i);
			if (sum > 21) {
				if (allSumsExceed == null) 
					allSumsExceed = true;
			} else if (sum == 21) {
				win = true;
				break;
			} else {
				allSumsExceed = false;
			}
		}

		/* Print out new hand */
		String newHand = displayPerPlayer.get(player) + ", " + newCard.printInfo();
		displayPerPlayer.put(player, newHand);
		System.out.println(newHand);

		printPossibleSums(player);

		if (win) {
			return STATUS.WINNER;
		}

		if (allSumsExceed) {
			return STATUS.BUSTED;
		}

		return STATUS.CONTINUE;

	}

	/* 
	 * Asks for a move from the player
	 * Input:
	 * - player: player number
	 */
	public String askForMove(int player) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		/* Prompt the user and show the cards they currently have */
		System.out.println("");
		System.out.println(displayPerPlayer.get(player));
		System.out.println("Player " + (player + 1) + ": Hit or stay?");
		/* Read input from user */
		input = br.readLine();

		/* Sanitize input and make sure it's "hit" or "miss" */
		while ( !input.equals(Constants.HIT)
				&& !input.equals(Constants.STAY) ) {
			System.out.println("Player " + (player + 1) + ": Hit or Stay? (Please type 'Hit' or 'Stay') ");
			input = br.readLine();
		}
		
		return input;
	}

}