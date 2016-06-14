import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class RunGame {
	public static void main(String[] args) throws IOException {
		int numOfPlayers = 5;

		BlackjackGame game = new BlackjackGame(numOfPlayers);
		Queue<Integer> futurePlayerMoves = new LinkedList<Integer>();
		Queue<Integer> winners = new LinkedList<Integer>();
		Queue<Integer> stay = new LinkedList<Integer>();
		int busted = 0;
		int total = 0;
		HashSet<Integer> notBustedPlayers = new HashSet<Integer>();
		boolean stopGame = false;
		game.startGame();
		
		/* Put all players on the queue so everyone gets a turn */
		for (int i = 0; i < numOfPlayers; i++) {
			futurePlayerMoves.add(i);
			notBustedPlayers.add(i);
			total += i;
		}

		while (!futurePlayerMoves.isEmpty()) {
			int playerMove = futurePlayerMoves.remove();
			if (winners.contains(playerMove))
				break;
			if (game.askForMove(playerMove).equals(Constants.HIT)) {
				STATUS turnEnd = game.hitMove(playerMove);
				switch (turnEnd) {
				case CONTINUE:
					futurePlayerMoves.add(playerMove);
					break;
				case BUSTED:
					busted += playerMove;
					notBustedPlayers.remove(playerMove);
					System.out.println("Player " + (playerMove + 1) + " busted!");
					if (notBustedPlayers.size() == 1) {
						int winner = total - busted;
						System.out.println("Player " + (winner + 1) + " won!");
						stopGame = true;
						break;
					}
					break;
				case WINNER:
					winners.add(playerMove);
					futurePlayerMoves.add(playerMove);
				}
			} else {
				game.stayMove(playerMove);
				stay.add(playerMove);
			}

			if (stopGame)
				break;

			/* Reset playerMove back to zero if it has reached the max player number */
			if (playerMove == numOfPlayers - 1) {
				playerMove = 0;
			} else {
				playerMove += 1;
			}
		}

		if (winners.isEmpty()) {
			System.out.println("Nobody got Blackjack but not everyone was busted!");
			int max = -1;
			int playerWinning = -1;
			boolean tie = false;
			ArrayList<Integer> tieWinners = new ArrayList<Integer>(); 
			while (!stay.isEmpty()) {
				int player = stay.remove();
				int playerMax = game.maxSumWithoutBust(player); 
				if (playerMax > max) {
					playerWinning = player;
					max = playerMax;
					tieWinners.clear();
					tieWinners.add(player);
				} else if (playerMax == max) {
					tie = true;
					tieWinners.add(player);
				}
				System.out.println("Player " + (player + 1) + " has score: " + playerMax);
			}
			
			if (tie) {
				String tiePrint = "Tie of score " + max + " between players ";
				for (int i = 0; i < tieWinners.size(); i++) {
					tiePrint += (tieWinners.get(i) + 1) + ", ";
				}
				System.out.println(tiePrint.substring(0, tiePrint.length() - 2));
				return;
			}
			
			System.out.println("Player " + (playerWinning + 1) + " won!");
		}
		
		/* Print out winners */
		while (!winners.isEmpty())
			System.out.println("Player " + (winners.remove() + 1) + " won!");

	}
}
