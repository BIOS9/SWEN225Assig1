package game;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
=======
import java.util.*;
>>>>>>> 9aa2777a30461a6a36ed569c24f0f25f6adf77f1

import game.board.Board;
import game.cards.Card;
import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;

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
	private game.cards.Character murderer;
	private game.cards.Weapon murderWeapon;
	private game.cards.Room murderRoom;

<<<<<<< HEAD
	private final game.cards.Character[] characters = { 
			new game.cards.Character("Miss Scarlett"),
			new game.cards.Character("Rev. Green"),
			new game.cards.Character("Colonel Mustard"),
			new game.cards.Character("Professor Plum"),
			new game.cards.Character("Mrs. Peacock"),
			new game.cards.Character("Mrs. White") };
=======
	private final game.cards.Character[] characters = {
	        new game.cards.Character("Miss Scarlett"),
			new game.cards.Character("Rev. Green"),
            new game.cards.Character("Colonel Mustard"),
			new game.cards.Character("Professor Plum"),
            new game.cards.Character("Mrs. Peacock"),
			new game.cards.Character("Mrs. White")};
>>>>>>> 9aa2777a30461a6a36ed569c24f0f25f6adf77f1

	private final game.cards.Room[] rooms = {
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
		List<Character> characterCards = new ArrayList<Character>();
		List<Room> roomCards = new ArrayList<Room>();
		List<Weapon> weaponCards = new ArrayList<Weapon>();
		
		//Choosing the character soloution
		Collections.shuffle(characterCards);
		this.murderer = characterCards.get(0);
		characterCards.remove(0);
		
		//Choosing the room soloution
		Collections.shuffle(roomCards);
		this.murderRoom = roomCards.get(0);
		roomCards.remove(0);
		
		//Choosing the weapon soloution;
		Collections.shuffle(weaponCards);
		this.murderWeapon = weaponCards.get(0);
		weaponCards.remove(0);
		
		List<Card> allCards = new ArrayList<>();
		

	}
}