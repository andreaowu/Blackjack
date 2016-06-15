Blackjack Card Game
===========
## What is Blackjack?
Blackjack is a traditional card game played at many casinos, in which the goal is to get cards summing up to as close to 21 points as possible. If a player's cards add up to exactly 21 points, that player will win; if the player's cards add up to more than 21 points, the player will definitely lose. If the player's cards don't sum to 21 points but they don't go over, the player has a chance of winning if none of the other players' cards sum up to 21 points and the player has the highest number of points.

## Blackjack Rules
In the beginning, each player is dealt 2 cards. Any card 2-10 is worth that number of points, J Q K are worth 10 points each, and Aces can be worth 1 or 11 points. Each player gets a turn, and on the turn, the player can choose to "hit" or "stay". "Hit" means take another card; "stay" means stay at the current hand and not take more cards. After a player chooses "stay", the player does not take any more turns for that round. At the end of the round (at which players have all chose to "stay"), cards are revealed and the winner(s) is determined.

Implemented Game
===========
## Play Locally
To deploy this locally, download the Blackjack.jar. Navigate to the folder where you downloaded it, and run 'java -jar Blackjack.jar'. <br>

Once open, it will prompt for number of players. At least two players will have to play. <br>

The console will print out the initial dealing of two cards for each player. Each player can then type in "hit" or "stay", and the console will print out the new hand of cards for that player along with the possible sums with that hand. Each player with a turn will continue to have a chance to type in "hit" or "stay". The program will automatically force a player to take "stay" if the player's hand has reached at least 21 points, and everyone who hasn't gotten a turn that round yet gets their turn. After all players are on "stay", the program will then calculate the winner and print it in the console.

Game Design
===========
## RunGame
The game application begins here (ie RunGame contains the "main" function). The number of players gets prompted and received from the user, and a new BlackjackGame gets created and started.

## Card
Represents each card in a standard 52-deck of cards. Holds value and suit information.

## Deck
Represents a standard 52-deck of cards.

## Player
Contains all data pertaining to a player. Tracks cards and point totals of player's cards, and takes care of cases when a player adds an Ace to the hand of cards.

## BlackjackGame
Represents each Blackjack game. Stores all variables relevant to the game and does the heavy-lifting game-play. Prompts players for "hit" or "stay", and calculates point values and winners/ties at the end.
