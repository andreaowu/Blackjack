import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/*
 * Represents each Blackjack game. Keeps track of all the game variables 
 * (including players, turns, etc), and does the actual game play.
 * Calculates winners and ties according to cards in each player's hand.
 */

class BlackjackGame {

	/* Players who still have a move left */
	Queue<Player> futureTurn;
	/* Players who have hit exactly 21 */
	Queue<Player> winners;
	/* Players who have opted to stay with their current hand */
	Queue<Player> stay;
	/* Players who have not been busted */
	Queue<Player> notBusted;
	/* points of all player numbers who are busted */
	int busted;
	/* points of all player numbers */
	int total;
	/* Tells whether has ended */
	boolean stopGame;
	/* (Highest) Player number who got blackjack */
	int winner;
	/* Set for players who have Aces */
	HashSet<Player> hasAces;
	/* Deck of cards for this game */
	Deck d;
	/* All players in the game */
	Player[] players;
	/* Number of players in the game*/
	int numOfPlayers;

	/* 
	 * Constructor for BlackjackGame
	 *
	 * Input: 
	 * - int givenNumOfPlayers: number of players for this game
	 * 
	 * Output:
	 * - new BlackjackGame object
	 * 
	 * Results:
	 * Instantiates all the variables and creates a list 
	 * of players in this game. 
	 */
	public BlackjackGame(int numOfPlayers) {

		/* Instantiate all the variables */
		futureTurn = new LinkedList<Player>();
		winners = new LinkedList<Player>();
		stay = new LinkedList<Player>();
		notBusted = new LinkedList<Player>();
		busted = 0;
		total = 0;
		stopGame = false;
		winner = 0;
		hasAces = new HashSet<Player>();
		d = new Deck(); 
		players = new Player[numOfPlayers];
		this.numOfPlayers = numOfPlayers;
		
		/* Make list of players in this game */
		for (int i = 0; i < numOfPlayers; i++)
			players[i] = new Player(i);
	}

	/* 
	 * Starts the game
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Gives each player two random cards from the deck.
	 */
	public void startGame() {
		/* Loop through each player and give each two cards */
		for (int i = 0; i < numOfPlayers; i++) {
			Card c1 = d.getRandom();
			Card c2 = d.getRandom();
			players[i].getCard(c1);
			players[i].getCard(c2);
			
			players[i].printCards();
		}
	}

	/* 
	 * Gets called for when a player wants to "stay" (vs "hit")
	 *
	 * Input: 
	 * - Player p: player who's taking the "stay" move
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Prints out the cards and points of cards of Player p. 
	 */
	public void stayMove(Player p) {
		p.printCards();
		p.printPoints();
	}

	/* 
	 * Gets called for when a player wants to "hit" (vs "stay")
	 *
	 * Input: 
	 * - Player p: player who's taking the "hit" move
	 * 
	 * Output:
	 * - STATUS: result of the player's hand from taking this move
	 * 
	 * Results:
	 * Adds a card to the players hand, calculates points, and determines
	 * whether the player got Blackjack, went bust, or is still in the game.
	 */
	public STATUS hitMove(Player p) {

		/* Get a new card */
		Card newCard = d.getRandom();
		p.getCard(newCard);

		/* Track whether all the possible points are more than 21 */
		Boolean allpointsExceed = null;
		boolean win = false;
		
		/* Calculate new points(s) for player */
		ArrayList<Integer> allpoints = p.getPoints();
		for (int i = 0; i < allpoints.size(); i++) {
			int points = allpoints.get(i);
			
			/* See whether the points is bust or Blackjack or none */
			if (points > 21) {
				if (allpointsExceed == null) 
					allpointsExceed = true;
			} else if (points == 21) {
				win = true;
				break;
			} else {
				allpointsExceed = false;
			}
		}

		/* Print out cards and points for player */
		p.printCards();
		p.printPoints();

		/* Check flags to determine player's game status */ 
		if (win) {
			return STATUS.WINNER;
		} else if (allpointsExceed != null && allpointsExceed) {
			return STATUS.BUSTED;
		}

		return STATUS.CONTINUE;

	}

	/* 
	 * Asks player for the move they want to take on their turn.
	 *
	 * Input: 
	 * - Player p: player who's deciding to "hit" or "stay"
	 * 
	 * Output:
	 * - String: "hit" or "stay, whichever one Player p decided on
	 * 
	 * Results:
	 * Keep asking for input from the player until the player 
	 * says "hit" or "stay"
	 */
	public String askForMove(Player p) throws IOException {
		/* Get input */
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
	
	/* 
	 * Returns the list of all players in this game, regardless of whether 
	 * they have won, busted, or got Blackjack.
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - Player[]: list of Players playing this game
	 * 
	 */
	public Player[] getPlayers() {
		return players;
	}

	/* 
	 * Goes through the game play.
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Gives each player a turn and asks each player to "hit" or "stay" 
	 * until the player has won, opted to "stay", or got busted. Prints out
	 * winners and ties at the end of the game.
	 */
	public void playGame() throws IOException {
		
		/* Put all players in appropriate queues */
		Player[] players = getPlayers();
		for (int i = 0; i < players.length; i++) {
			/* Everyone gets a turn */
			futureTurn.add(players[i]);
			
			/* Nobody is busted yet */
			notBusted.add(players[i]);
			total += i + 1;
		}

		/* Keep going until all players have won, opted to stay, or busted */
		while (!futureTurn.isEmpty()) {
			/* Give player a turn */
			Player player = futureTurn.remove();
			
			if (askForMove(player).equals(Globals.HIT)) {
				/* Player opted for "hit" */
				
				/* Give a card to the player */
				STATUS turnEnd = hitMove(player);
				
				switch (turnEnd) {
				case CONTINUE:
					/* Player got a card, is not bust, and did not get Blackjack 
					 * Player will get another turn. */
					futureTurn.add(player);
					break;
					
				case BUSTED:
					/* Player got a card and went over 21. */
					busted += player.getNumber();
					notBusted.remove(player);
						
					/* If only one player who is not busted is left in the 
					 * game, that player won. */
					if (notBusted.size() == 1) {
						int playerWin = total - busted - 1;
						System.out.println("Player " + (playerWin + 1) + " won!");
						stopGame = true;
						break;
					}
					break;
					
				case WINNER:
					/* Player got Blackjack and is (one of) the winner(s) */
					winner = player.getNumber();
					winners.add(player);
					break;
				}
				
			} else {
				/* Player opted for "stay" */
				stayMove(player);
				stay.add(player);
				
				/* The player won if their "stay" hand is Blackjack */
				if (player.maxPointsWithoutBust == 21) {
					winners.add(player);
				}
			}

			if (stopGame)
				break;
		}
		
		if (!stopGame) {
			nonBustedWinners(winners, notBusted);
			blackjackWinners(winners);
		}
	}
	
	/* 
	 * Calculates the winner if nobody got Blackjack but not everyone
	 * went bust.
	 *
	 * Input: 
	 * - Queue<Player> winners: list of players who got Blackjack
	 * - Queue<Player> notBusted: set of players who did not get busted
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Checks if there were any winners (players who got Blackjack). If not,
	 * figures out the player who got the max points of cards out of
	 * the players who opted to stay (did not get Blackjack but did not go 
	 * bust). Deals with ties as well. Prints out player(s) with highest
	 * points of cards in their hand.
	 */
	public static void nonBustedWinners(Queue<Player> winners, 
			Queue<Player> notBusted) {
		/* Nobody got Blackjack but not everyone was busted */
		if (winners.isEmpty()) {
			System.out.println("\nNobody got Blackjack but "
					+ "not everyone was busted!");
			/* Max points of cards amongst players who weren't busted */
			int max = -1;
			/* Player with max points of cards */
			Player playerWinning = null;
			/* Whether there's a tie */
			boolean tie = false;
			/* List of players with the tie */
			ArrayList<Player> tieWinners = new ArrayList<Player>();

			/* Check through all non-busted players and get player with
			 * highest score */
			
			while (!notBusted.isEmpty()) {
				Player player = notBusted.poll();
				int playerMax = player.getMaxPointsWithoutBust();

				if (playerMax > max) {
					/* Found player with higher points */
					playerWinning = player;
					max = playerMax;

					/* Reset all tie-related variables */
					tieWinners.clear();
					tieWinners.add(player);
					tie = false;
				} else if (playerMax == max) {
					/* Found another player with highest points */
					tie = true;
					tieWinners.add(player);
				}
				System.out.println("Player " + player.getNumber() + " has score: " + playerMax);
			}

			/* Print out for tie case */
			if (tie) {
				String tiePrint = "Tie of score " + max + " between players ";
				for (int i = 0; i < tieWinners.size(); i++) {
					tiePrint += tieWinners.get(i).getNumber() + ", ";
				}
				System.out.println(tiePrint.substring(0, tiePrint.length() - 2));
				return;
			}
			/* Print out for one-winner case */
			System.out.println("Player " + playerWinning.getNumber() + " won!");
		}
	}
	
	/* 
	 * Displays all winners (players who got Blackjack).
	 *
	 * Input: 
	 * - Queue<Player> winners: list of players who got Blackjack
	 * 
	 * Output:
	 * void
	 * 
	 * Results:
	 * Iterates through winners and prints out each player's number.
	 */
	public static void blackjackWinners(Queue<Player> winners) {
		/* At least one player got Blackjack */
		if (winners.size() > 0) {
			/* Print out winners */
			String winnerPlayers = "";

			/* More than one player got Blackjack */
			if (winners.size() > 1) {
				winnerPlayers += "More than one player got Blackjack!";
			}

			/* Print all players who got Blackjack */
			while (!winners.isEmpty())
				winnerPlayers += "Player " + winners.remove().getNumber() + " won!";
			System.out.println(winnerPlayers);
		}
	}
	
}