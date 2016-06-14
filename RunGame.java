import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class RunGame {
	public static void main(String[] args) throws IOException {
		int numOfPlayers = 5;

		BlackjackGame game = new BlackjackGame(numOfPlayers);
		Queue<Player> futurePlayerMoves = new LinkedList<Player>();
		Queue<Player> winners = new LinkedList<Player>();
		Queue<Player> stay = new LinkedList<Player>();
		int busted = 0;
		int total = 0;
		HashSet<Player> notBustedPlayers = new HashSet<Player>();
		boolean stopGame = false;
		game.startGame();
		
		/* Put all players on the queue so everyone gets a turn */
		Player[] players = game.getPlayers();
		for (int i = 0; i < players.length; i++) {
			futurePlayerMoves.add(players[i]);
			notBustedPlayers.add(players[i]);
			total += i + 1;
		}

		while (!futurePlayerMoves.isEmpty()) {
			Player player = futurePlayerMoves.remove();
			if (winners.contains(player))
				break;
			if (game.askForMove(player).equals(Globals.HIT)) {
				STATUS turnEnd = game.hitMove(player);
				switch (turnEnd) {
				case CONTINUE:
					futurePlayerMoves.add(player);
					break;
				case BUSTED:
					busted += player.getNumber();
					notBustedPlayers.remove(player);
					System.out.println("Player " + player.getNumber() + " busted!");
					if (notBustedPlayers.size() == 1) {
						int winner = total - busted - 1;
						System.out.println("Player " + (winner + 1) + " won!");
						stopGame = true;
						break;
					}
					break;
				case WINNER:
					winners.add(player);
					futurePlayerMoves.add(player);
				}
			} else {
				game.stayMove(player);
				stay.add(player);
				if (player.maxSumWithoutBust == 21) {
					winners.add(player);
				}
			}

			if (stopGame)
				break;
		}

		if (winners.isEmpty()) {
			System.out.println("\nNobody got Blackjack but not everyone was busted!");
			int max = -1;
			Player playerWinning = null;
			boolean tie = false;
			ArrayList<Player> tieWinners = new ArrayList<Player>(); 
			while (!stay.isEmpty()) {
				Player player = stay.remove();
				int playerMax = player.getMaxSumWithoutBust(); 
				if (playerMax > max) {
					playerWinning = player;
					max = playerMax;
					tieWinners.clear();
					tieWinners.add(player);
					tie = false;
				} else if (playerMax == max) {
					tie = true;
					tieWinners.add(player);
				}
				System.out.println("Player " + player.getNumber() + " has score: " + playerMax);
			}
			
			if (tie) {
				String tiePrint = "Tie of score " + max + " between players ";
				for (int i = 0; i < tieWinners.size(); i++) {
					tiePrint += tieWinners.get(i).getNumber() + ", ";
				}
				System.out.println(tiePrint.substring(0, tiePrint.length() - 2));
				return;
			}
			
			System.out.println("Player " + playerWinning.getNumber() + " won!");
		}
		
		if (winners.size() > 0) {
			/* Print out winners */
			String winnerPlayers = "";
			if (winners.size() > 1) {
				winnerPlayers += "More than one player got Blackjack!";
			}
			while (!winners.isEmpty())
				winnerPlayers += "Player " + winners.remove().getNumber() + "won!";
			System.out.println(winnerPlayers);
		}
	}
}
