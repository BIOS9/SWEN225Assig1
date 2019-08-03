// ASSUMING PLAYERS GET NO CHOICE OF WHICH CHARACTER THEY WANT
// ASSUMING THE PLAYERS MUST MOVE ALL OF THEIR DICE ROLL IF THEY ARE IN THE HALLWAY
// PLAYERS CAN SKIP THE REST OF THEIR MOVES IF THEY ENTER A ROOM
// PLAYERS CANNOT MOVE BACK ONTO A CELL THAT THEY HAVE BEEN ON IN THAT TURN
// PLAYERS CANNOT MAKE A SUGGESTION OR ACCUSATION TWICE IN A ROW IN THE SAME ROOM
// PLAYERS CANNOT MOVE BACK INTO A ROOM THAT THEY HAVE LEFT IN THAT TURN
// ASSUMING PLAYERS ARE NOT IDIOTS WHO WILL WALK INTO A SPACE THEY CANNOT GET OUT OF
// ASSUMING SKIPPING TURN RULE APPLIES WHEN A PLAYER IS IN A ROOM
// ASSUMING PLAYERS CAN MAKE TWO SUGGESTIONS IN THE SAME ROOM IN DIFFERENT TURNS
// ASSUMING A PLAYER CAN MAKE A SUGGESTION WITHOUT MOVING INTO A ROOM IF THEY ARE ALREADY IN IT
// ASSUMING WEAPONS ARE USELESS AND DONT NEED TO BE MOVED


package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.*;

import game.board.Board;
import game.board.Cell;
import game.cards.Card;
import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * CluedoGame is responsible for initiating the game board, cards and players
 * and managing the framework of the gameplay.
 * 
 * Collaborates directly with Board, Card, Player and Turn.
 * 
 * @author abbey
 *
 */

public class CluedoGame {
	public static final int MIN_PLAYERS = 3;
	public static final int MAX_PLAYERS = 6;

	public static Scanner INPUT_SCANNER;

	// CluedoGame Attributes
	private int playerTurnIndex = 0; // Whos turn it is

	// CluedoGame Associations
	private Board board;
	private Suggestion soloutionCards;

	private final game.cards.Character[] characters = { new game.cards.Character("Miss Scarlett", 1),
			new game.cards.Character("Rev. Green", 2), new game.cards.Character("Colonel Mustard", 3),
			new game.cards.Character("Professor Plum", 4), new game.cards.Character("Mrs. Peacock", 5),
			new game.cards.Character("Mrs. White", 6) };

	private final game.cards.Room[] rooms = {
			new game.cards.Room("Kitchen", 'k'),
			new game.cards.Room("Ball Room", 'b'),
			new game.cards.Room("Conservatory", 'c'),
			new game.cards.Room("Billiard Room", 'p'),
			new game.cards.Room("Dining Room", 'd'),
			new game.cards.Room("Library", 'l'),
			new game.cards.Room("Hall", 'r'),
			new game.cards.Room("Lounge", 't'),
			new game.cards.Room("Study", 's') };

	private final game.cards.Weapon[] weapons = { new game.cards.Weapon("Candlestick"), new game.cards.Weapon("Dagger"),
			new game.cards.Weapon("Lead Pipe"), new game.cards.Weapon("Revolver"), new game.cards.Weapon("Rope"),
			new game.cards.Weapon("Spanner") };

	private final game.cards.Room hallway = new game.cards.Room("Hallway", 'h');

	private List<Player> players = new ArrayList<>();

	/**
	 * Entry point
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		INPUT_SCANNER = new Scanner(System.in);
		CluedoGame game = new CluedoGame();
		game.initGame();
		game.initCards();
		game.runGame();
		
	}

	/**
	 * Initialises the game state, players and board.
	 */
	private void initGame() {
		board = new Board(characters);

		System.out.println("Welcome to Cluedo! Have fun >:)\n");

		// Ask user for player count
		int playerCount;
		while (true) {
			System.out.println("How many players are there? " + MIN_PLAYERS + " - " + MAX_PLAYERS + ": ");

			try {
				playerCount = INPUT_SCANNER.nextInt();

				if (playerCount >= MIN_PLAYERS && playerCount <= MAX_PLAYERS)
					break;
				else
					System.out.println("Player count must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS
							+ " (both inclusive)");
			} catch (InputMismatchException ex) { // Error occurs when user enters something other than an integer
				System.out.println("Please enter a valid number.a");
				INPUT_SCANNER.nextLine(); // Clear input buffer
			}
		}

		// Generate playerss and add them to list
		for (int i = 0; i < playerCount; ++i) {
			players.add(new Player(characters[i], this));
		}
	}

	/**
	 * Generates the cards, selects a solution (3 cards) adds all cards to one list.
	 */
	private void initCards() {
		// Create three stack of cards, one for each card type, Sets since order doesnt
		// matter and no dups?
		List<Character> characterCards = new ArrayList<Character>(Arrays.asList(characters));
		List<Room> roomCards = new ArrayList<Room>(Arrays.asList(rooms));
		List<Weapon> weaponCards = new ArrayList<Weapon>(Arrays.asList(weapons));
		game.cards.Character murderer;
		game.cards.Room murderRoom;
		game.cards.Weapon murderWeapon;
		
		// Choosing the character soloution
		Collections.shuffle(characterCards);
		murderer = characterCards.get(0);
		characterCards.remove(0);

		// Choosing the room soloution
		Collections.shuffle(roomCards);
		murderRoom = roomCards.get(0);
		roomCards.remove(0);

		// Choosing the weapon soloution;
		Collections.shuffle(weaponCards);
		murderWeapon = weaponCards.get(0);
		weaponCards.remove(0);
		
		this.soloutionCards = new Suggestion(murderRoom, murderer, murderWeapon);

		// add all cards to one list
		List<Card> allCards = new ArrayList<>();
		allCards.addAll(characterCards);
		allCards.addAll(roomCards);
		allCards.addAll(weaponCards);

		Collections.shuffle(allCards);
		
		//Deals the cards to the players
		while (!allCards.isEmpty()) {
			for (Player p : this.players) { // For each player in the game
				if (!allCards.isEmpty()) { // If theres still cards to deal
					p.addCardToHand(allCards.get(0)); // deal the card and remove it.
					allCards.remove(0);
				}
			}
		}
	}

	/**
	 * Runs game, main loop for turns and user input
	 */
	private void runGame() {
		int round = 0; // Total number of rounds
		Player winner;

		while (true) {
			Player player = players.get(round % players.size());

			if(player.getHasAcused()) // If player has accused, skip them
				continue;

			game.cards.Character character = player.getCharacter();
			Turn turn = new Turn(player);

			board.print();
            System.out.println(character.getName() + " you're up!");
            System.out.println("Your dice roll was " + turn.getDiceRoll());
            System.out.println("Your character is number " + character.getNumber() + " and is located at " + character.getLocation().position.toString());
            System.out.println(turn.getPlayer().printCards());
            
			int moves = turn.getDiceRoll();

			Set<Cell> visitedCells = new HashSet<>();
			Set<Room> visitedRooms = new HashSet<>();

			visitedCells.add(character.getLocation()); // Add starting position to visitedCells

			if(!character.getLocation().isRoom(hallway)) { // Check if room is hallway
				visitedRooms.add(character.getLocation().getRoom());
			}

			// Move player until they run out of moves or skip
			while(moves > 0) {
				board.print();

				System.out.println("You have " + moves + " moves remaining.");
				System.out.print("Where would you like to move? ");
				Cell.Direction move = askDirection(!character.getLocation().isRoom(hallway));

				if(move == null) // Skip rest of moves is user skips
					break;

				Cell newCell = character.getLocation().getNeighbour(move);
				if(newCell != null && visitedCells.contains(newCell)) {
					System.out.println("You have already been there!");
					continue;
				}

				if(newCell != null && !character.getLocation().isRoom(newCell) && visitedRooms.contains(newCell.getRoom())) { // Check if new cell is a different room, and player has visited that room
					System.out.println("You have already been in that room!");
					continue;
				}

				if (board.moveCharacter(character, move)) {
					visitedCells.add(character.getLocation());

					if(!character.getLocation().isRoom(hallway)) { // Check if room is hallway or not
						visitedRooms.add(character.getLocation().getRoom());
					}
					--moves;
				} else {
					System.out.println("You can't move there!");
				}
			}

			// Ask player for suggestion
			Suggestion suggestion = askSuggestion(player, character.getLocation().getRoom(), character.getLocation().isRoom(hallway)); // Get suggestion, forcing accusation if in hallway


            // CHECK FOR WINNING SOLUTION AND END GAME
            if(false) {
                winner = null;
                break;
            }

            ++round;
        }

		System.out.println(winner.getCharacter().getName() + " has won the game in " + round + " rounds!");
	}

	/**
	 * Asks user for a suggestion or accusation or skip.
	 * @param forceAccusation Disallows player from providing a suggestion
	 * @return
	 */
	private Suggestion askSuggestion(Player player, Room suggestionRoom, boolean forceAccusation) {
		while (true) {
			if(forceAccusation)
				System.out.print("Would you like to make a: Accusation (A), Nothing (X): (A, X)");
			else
				System.out.print("Would you like to make a: Suggestion (S), Accusation (A), Nothing (X): (S, A, X)");

			try {
				String input = INPUT_SCANNER.next().toUpperCase();

				// User selected cards
				game.cards.Character character;
				game.cards.Weapon weapon;
				game.cards.Room room;

				switch (input) {
					case "S":
						if(forceAccusation) {
							System.out.println("Suggestions are not allowed here!");
							continue;
						}
						character = (game.cards.Character)askCard(characters, "character");
						weapon = (game.cards.Weapon)askCard(weapons, "weapon");

						return new Suggestion(suggestionRoom, character, weapon, player, false);
					case "A":
						room = (game.cards.Room)askCard(rooms, "room");
						character = (game.cards.Character)askCard(characters, "character");
						weapon = (game.cards.Weapon)askCard(weapons, "weapon");

						return new Suggestion(room, character, weapon, player, true);
					case "X":
						return null;
				}
			} catch (InputMismatchException ex) {}

			System.out.println("Invalid input.");
		}
	}

	/**
	 * Asks user to select a card
	 * @return
	 */
	private game.cards.Card askCard(Card[] cards, String type) {
		for(int i = 0; i < cards.length; ++i) {
			System.out.print(i + 1);
			System.out.println(": " + cards[i].getName());
		}
		while (true) {
			try {
				System.out.println("Please choose a " + type + " (1-" + cards.length + "):");
				int input = INPUT_SCANNER.nextInt();
				if(input < 1 || input > cards.length)
					continue;

				return cards[input - 1];
			} catch (InputMismatchException ex) {}

			System.out.println("Invalid input.");
		}
	}

	/**
	 * Asks user for direction, allows skipping
	 * @param allowSkip
	 * @return Direction, or null if skipped
	 */
	private Cell.Direction askDirection(boolean allowSkip) {
		while (true) {
			System.out.print("Choose a direction using W A S D");
			if(allowSkip)
				System.out.print(" or X for skip");
			System.out.print(": ");

			try {
				String input = INPUT_SCANNER.next().toUpperCase();
				switch (input) {
					case "W":
						return Cell.Direction.NORTH;
					case "S":
						return Cell.Direction.SOUTH;
					case "A":
						return Cell.Direction.WEST;
					case "D":
						return Cell.Direction.EAST;
					case "X":
						if(allowSkip)
							return null;
						else {
							System.out.println("Skipping not allowed!");
							continue;
						}
				}
			} catch (InputMismatchException ex) {}

			System.out.println("Invalid input.");
		}
	}


}