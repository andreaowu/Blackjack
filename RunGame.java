import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/* 
 * Class to run the entire application. 
 */
public class RunGame {
	
	public static void main(String[] args) throws IOException {
		
		/* Number of players playing the game */
		int numOfPlayers = getNumOfPlayers();
		
		/* Create game for players */ 
		BlackjackGame game = new BlackjackGame(numOfPlayers);
		
		/* Start the game and give each player two cards */
		game.startGame();
		
		/* Let's play! */
		game.playGame();
	}
	
	/* 
	 * Asks for the number of players
	 *
	 * Input: 
	 * void
	 * 
	 * Output:
	 * - int: number of players for the game
	 * 
	 * Results:
	 * Continuously prompts the user for the number of players until user
	 * provides a number greater than 1.
	 */
	public static int getNumOfPlayers() {
		int numOfPlayers = 0;
		
		/* Ask for number of players */
		BufferedReader br = 
				new BufferedReader(new InputStreamReader(System.in));
		System.out.println("How many players are playing?");
		
		/* Keep asking for number of players until 
		 * an integer greater than 1 is entered */
		while (true) {
			try {
				numOfPlayers = Integer.parseInt(br.readLine());
				if (numOfPlayers < 2) {
					System.out.println("Too few players; at least two people" +
									   " will play.");
					continue;
				}
				break;
			} catch (NumberFormatException e) {
				System.out.println("The argument entered is not a number. " +
								   "Try again.");
				System.out.println("Please enter the number of players.");
			} catch (IOException e) {
				System.out.println("Cannot read input, try again.");
			} catch (Exception e) {
				System.out.println("Error occurred, please try again and "
						+ "enter the number of players.");
			}
		}
		
		return numOfPlayers;
	}
}
