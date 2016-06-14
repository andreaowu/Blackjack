import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;

class BlackjackGame {

	/* Set for players who have Aces
	 * key: player number
	 * value: number of Aces player has */
	HashSet<Player> hasAces;
	/* Deck of cards for this game */
	Deck d;
	Player[] players;
	int numOfPlayers;

	/* 
	 * Constructor for BlackjackGame
	 *
	 * Input: 
	 * - givenNumOfPlayers: number of players for this game
	 */
	public BlackjackGame(int numOfPlayers) {
		this.numOfPlayers = numOfPlayers;
		/* Instantiate all the variables */
		hasAces = new HashSet<Player>();
		d = new Deck(); 
		players = new Player[numOfPlayers];
		for (int i = 0; i < numOfPlayers; i++)
			players[i] = new Player(i);
	}

	public void startGame() {
		/* Loop through each player and give each two cards */
		for (int i = 0; i < numOfPlayers; i++) {
			Card c1 = d.getRandom();
			Card c2 = d.getRandom();
			
			players[i].getCard(c1);
			players[i].getCard(c2);
		}
	}

	public void stayMove(Player p) {
		p.printCards();
		p.printSums();
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
	public STATUS hitMove(Player p) {

		/* Get a new card */
		Card newCard = d.getRandom();
		
		p.getCard(newCard);

		/* Track whether all the possible sums are more than 21 */
		Boolean allSumsExceed = null;
		boolean win = false;
		
		ArrayList<Integer> allSums = p.getSums();
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

		p.printCards();
		p.printSums();

		if (win) {
			return STATUS.WINNER;
		}

		if (allSumsExceed != null && allSumsExceed) {
			return STATUS.BUSTED;
		}

		return STATUS.CONTINUE;

	}

	/* 
	 * Asks for a move from the player
	 * Input:
	 * - player: player number
	 */
	public String askForMove(Player p) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;
		/* Prompt the user and show the cards they currently have */
		System.out.println("");
		p.printCards();
		System.out.println("Player " + p.getNumber() + ": Hit or stay?");
		/* Read input from user */
		input = br.readLine();

		/* Sanitize input and make sure it's "hit" or "miss" */
		while ( !input.equals(Globals.HIT)
				&& !input.equals(Globals.STAY) ) {
			System.out.println("Player " + p.getNumber() + ": Hit or Stay? (Please type 'Hit' or 'Stay') ");
			input = br.readLine();
		}
		
		return input;
	}
	
	public Player[] getPlayers() {
		return players;
	}

}