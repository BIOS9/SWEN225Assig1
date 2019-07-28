package game;

import java.util.*;

import game.board.Board;
import game.cards.Card;
import game.cards.Room;

/**
 * CluedoGame is responsible for initiating the game board, cards and players
 * and managing the framework of the gameplay.
 * 
 * Collaborates directly with Board, Card, Player and Turn.
 * 
 * @author abbey
 *
 */
class CluedoGame {
    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = 6;

    public static Scanner INPUT_SCANNER;

	// CluedoGame Attributes
	private int playerTurnIndex = 0; // Whos turn it is

	// CluedoGame Associations
	private Board board;

	private final game.cards.Character[] characters = {
	        new game.cards.Character("Miss Scarlett"),
			new game.cards.Character("Rev. Green"),
            new game.cards.Character("Colonel Mustard"),
			new game.cards.Character("Professor Plum"),
            new game.cards.Character("Mrs. Peacock"),
			new game.cards.Character("Mrs. White")};

	private final game.cards.Room[] roomCards = {
    		new game.cards.Room("Kitchen", 'k'),
    		new game.cards.Room("Ball Room", 'b'),
    		new game.cards.Room("Conservatory", 'c'),
    		new game.cards.Room("Billiard Room", 'p'),
    		new game.cards.Room("Hallway", 'h'),
    		new game.cards.Room("Dining Room", 'd'),
    		new game.cards.Room("Library", 'l'),
    		new game.cards.Room("Hall", 'r'),
    		new game.cards.Room("Lounge", 't'),
    		new game.cards.Room("Study", 's')};
	
	private final game.cards.Weapon[] weapons = {
			new game.cards.Weapon("Candlestick"),
			new game.cards.Weapon("Dagger"),
			new game.cards.Weapon("Lead Pipe"),
			new game.cards.Weapon("Revolver"),
			new game.cards.Weapon("Rope"),
			new game.cards.Weapon("Spanner")};

	private List<Player> players = new ArrayList<>();

	/**
	 * Entry point
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
        INPUT_SCANNER = new Scanner(System.in);
		new CluedoGame().initGame();
	}

	/**
	 * Initialises the game state, players and board.
	 */
	public void initGame() {
		board = new Board();
		board.generateBoard(characters);

		System.out.println("Welcome to Cluedo! Have fun >:)\n");

		// Ask user for player count
		int playerCount;
        while(true) {
            System.out.println("How many players are there? " + MIN_PLAYERS + " - " + MAX_PLAYERS + ": ");

            try {
                playerCount = INPUT_SCANNER.nextInt();

                if (playerCount >= MIN_PLAYERS && playerCount <= MAX_PLAYERS)
                    break;
                else
                    System.out.println("Player count must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS + " (both inclusive)");
            }
            catch (InputMismatchException ex) {
                System.out.println("Please enter a valid number.a");
                INPUT_SCANNER.nextLine(); // Clear input buffer
            }
        }
        System.out.println(board.toString());
	}

	/**
	 * Generates the cards, selects a solution (3 cards) shuffles remaing cards and
	 * deals them to the players.
	 */
	public void initCards() {
		// Create three stack of cards, one for each card type, Sets since order doesnt
		// matter and no dups?

		// pass to Card to populate the sets? then merge into one set.

		// Get random card of each type to make solution
		// Shuffle remaining cars into one stack
		// Deal cards to players

	}
}