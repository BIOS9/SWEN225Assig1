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
// ASSUMING WEAPONS ARE USELESS AND DON'T NEED TO BE MOVED
// ASSUMING NOBODY IS LOOKING AT THE SCREEN WHEN THEY SHOULDN'T BE
// -> ASSUMING PLAYERS CANT PLAY ONCE THEY HAVE MADE A WRONG 
//ACCUSATION
// -> ASSUMING REFUTATIONS END ONCE ONE CARD HAS BEEN 
//SUCCESSFULLY REFUTED


package game;

import java.io.IOException;
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
import gui.GameWindow;
import gui.request.PlayerCountRequest;
import gui.request.PlayerRequest;


/**
 * CluedoGame is responsible for initiating the game board, cards and players
 * and managing the framework of the gameplay.
 * 
 * Collaborates directly with Board, Card, Player and Turn.
 * 
 * @author abbey
 */
public class CluedoGame extends Observable {
	public static final int MIN_PLAYERS = 3;
	public static final int MAX_PLAYERS = 6;

	public static Scanner INPUT_SCANNER;

	// CluedoGame Associations
	private Board board;
	private Suggestion solutionCards;

	// Characters used in card generation
	private final game.cards.Character[] characters = { 
			new game.cards.Character("Miss Scarlett", 1),
			new game.cards.Character("Mr Green", 2),
			new game.cards.Character("Colonel Mustard", 3),
			new game.cards.Character("Professor Plum", 4), 
			new game.cards.Character("Mrs. Peacock", 5),
			new game.cards.Character("Mrs. White", 6) };

	// Rooms used in card generation
	private final game.cards.Room[] rooms = {
			new game.cards.Room("Kitchen", 'K'),
			new game.cards.Room("Ballroom", 'B'),
			new game.cards.Room("Conservatory", 'C'),
			new game.cards.Room("Billiard Room", 'P'),
			new game.cards.Room("Dining Room", 'D'),
			new game.cards.Room("Library", 'L'),
			new game.cards.Room("Hall", 'R'),
			new game.cards.Room("Lounge", 'T'),
			new game.cards.Room("Study", 'S') };

	// Weapons used in card generation
	private final game.cards.Weapon[] weapons = { 
			new game.cards.Weapon("Candlestick"), 
			new game.cards.Weapon("Dagger"),
			new game.cards.Weapon("Lead Pipe"), 
			new game.cards.Weapon("Revolver"), 
			new game.cards.Weapon("Rope"),
			new game.cards.Weapon("Spanner") };

	// Separate hallway because there are no hallway cards, but still needed for comparisons
	private final game.cards.Room hallway = new game.cards.Room("Hallway", 'H');

	private List<Player> players = new ArrayList<>();

	/**
	 * Entry point, main method
	 * 
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
        INPUT_SCANNER = new Scanner(System.in);

	    GameWindow window = new GameWindow();
	}

    public void startGame() {
        initGame();
        initCards();
        runGame();
    }

	/**
	 * Initialises the game state, players and board.
	 */
	private void initGame() {
		board = new Board(characters);

		// Ask user how many players will be playing
		int playerCount = makeRequest(new PlayerCountRequest()).waitResponse();

		// Generate players and add them to list.
		for (int i = 0; i < playerCount; ++i) {
			players.add(new Player(characters[i], this));
		}
	}

	/**
	 * Creates the lists of cards and selects the solution from the shuffled list.
	 * Then adds all cards into one list, shuffles it and deals them to the players.
	 */
	private void initCards() {
		List<Character> characterCards = new ArrayList<Character>(Arrays.asList(characters));
		List<Room> roomCards = new ArrayList<Room>(Arrays.asList(rooms));
		List<Weapon> weaponCards = new ArrayList<Weapon>(Arrays.asList(weapons));
		
		game.cards.Character murderer;
		game.cards.Room murderRoom;
		game.cards.Weapon murderWeapon;

		// Choosing the character solution
		Collections.shuffle(characterCards);
		murderer = characterCards.get(0);
		characterCards.remove(0);

		// Choosing the room solution
		Collections.shuffle(roomCards);
		murderRoom = roomCards.get(0);
		roomCards.remove(0);

		// Choosing the weapon solution;
		Collections.shuffle(weaponCards);
		murderWeapon = weaponCards.get(0);
		weaponCards.remove(0);
		
		//Create the solution Suggestion object
		this.solutionCards = new Suggestion(murderRoom, murderer, murderWeapon);

		// add all cards to one list
		List<Card> allCards = new ArrayList<>();
		allCards.addAll(characterCards);
		allCards.addAll(roomCards);
		allCards.addAll(weaponCards);

		Collections.shuffle(allCards);
		
		//Deals the cards to the players
		while (!allCards.isEmpty()) {
			for (Player p : this.players) { 
				if (!allCards.isEmpty()) { 
					p.addCardToHand(allCards.get(0));
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
			Player player = players.get(round % players.size()); // Player index is based on the round number and number of players

			if(player.getHasAcused()) { // If player has accused, skip them
				System.out.println("Skipping spectator: " + player.getCharacter().getName());
				++round;
				continue;
			}

			game.cards.Character character = player.getCharacter();
			Turn turn = new Turn(player);

			board.print();
            System.out.println(character.getName() + " you're up!");
            System.out.println("Press enter to begin your turn...");

            // Waits for player to input something into the console
            try {
				System.in.read();
			} catch(IOException ex) {}

            System.out.println("Your dice roll was " + turn.getDiceRoll());
            System.out.println("Your character is number " + character.getNumber() + " and is located at " + character.getLocation().position.toString());
            System.out.println(turn.getPlayer().printCards());
            
			int moves = turn.getDiceRoll();

			// Sets of visited places to prevent players going back on them
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

				// Check that player has not already been to cell in this turn
				Cell newCell = character.getLocation().getNeighbour(move);
				if(newCell != null && visitedCells.contains(newCell)) {
					System.out.println("You have already been there!");
					continue;
				}

				// Check that player has not already been in room in this turn
				if(newCell != null && !character.getLocation().isRoom(newCell) && visitedRooms.contains(newCell.getRoom())) { // Check if new cell is a different room, and player has visited that room
					System.out.println("You have already been in that room!");
					continue;
				}

				if (board.moveCharacter(character, move)) {
					visitedCells.add(character.getLocation());

					if(!character.getLocation().isRoom(hallway)) { // Check if room is hallway or not
						visitedRooms.add(character.getLocation().getRoom()); // Can enter hallway more than once in a turn so don't add to visited
					}
					--moves;
				} else {
					System.out.println("You can't move there!");
				}
			}

			// Ask player for suggestion
			Suggestion suggestion = askSuggestion(player, character.getLocation().getRoom(), character.getLocation().isRoom(hallway)); // Get suggestion, forcing accusation if in hallway

			if(suggestion != null) {
				// Checks if suggestion is accusation, and if it is correct
				if (suggestion.isAcusation() && suggestion.equals(solutionCards)) {
					System.out.println("The solution cards are: " + solutionCards.toString());
					System.out.println("Congratulations you won the game!");
					winner = player;
					break;
				}
				// If the acusation is wrong this player can no longer make suggestions/acusations
				else if (suggestion.isAcusation()) {
					player.setHasAcused();
					System.out.println("Your accusation was incorrect! You are now a spectator of the game.");
					System.out.println("The solution cards are: " + solutionCards.toString());
				}
				// If the suggestion isnt an accusation ask other players for refutations.
				else {
					// Move the suggested player into a random spot in the room
					Room moveRoom = player.getCharacter().getLocation().getRoom();
					System.out.println("Moving " + suggestion.getCharacter().getName() + " to the " + moveRoom.getName());

					if(!board.moveCharacterToRoom(suggestion.getCharacter(), moveRoom))
						System.out.println("Failed to move the suggested player into the room! Are there available spaces away from any door?");

					askRefutations(suggestion);
				}
			}

            ++round;
        }

		System.out.println(winner.getCharacter().getName() + " has won the game in " + round + " rounds!");
	}

	/**
	 * Goes round all players except the suggestor and asks for them to refute the suggestion
	 * @param suggestion
	 */
	private void askRefutations(Suggestion suggestion) {
		System.out.println(suggestion.player.getCharacter().getName() + " has made a suggestion, refutations will now begin.");
		System.out.println(suggestion.printCards());

		for (Player p : players) {
			if(p == suggestion.player) continue; // Skip suggestion

			List<Card> hand = p.getHand();

			List<Card> availableRefutations = new ArrayList<>();

			if(hand.contains(suggestion.getCharacter()))
				availableRefutations.add(suggestion.getCharacter());

			if(hand.contains(suggestion.getWeapon()))
				availableRefutations.add(suggestion.getWeapon());

			if(hand.contains(suggestion.getRoom()))
				availableRefutations.add(suggestion.getRoom());

			if(availableRefutations.size() == 0) {// Skip player if they cannot make any refutations
				System.out.println(p.getCharacter().getName() + " You have no suitable refutation cards. Skip.");
				continue;
			}

			System.out.println(p.getCharacter().getName() + " please refute the suggestion:");
			Card refutation = askCard(availableRefutations.toArray(new Card[0]), "refutations");
			System.out.println("The card: " + refutation.getName() + " has been refuted.");
			return;
		}
		System.out.println("No refutations were made.");
	}

	/**
	 * Asks user for a suggestion or accusation or skip.
	 * @param forceAccusation Disallows player from providing a suggestion
	 * @return
	 */
	private Suggestion askSuggestion(Player player, Room suggestionRoom, boolean forceAccusation) {
		while (true) {
			// Only allows suggestions in rooms, not hallway
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

						// Ask for character and weapon, the room is already chosen because it's a suggestion
						character = (game.cards.Character)askCard(characters, "character");
						weapon = (game.cards.Weapon)askCard(weapons, "weapon");

						return new Suggestion(suggestionRoom, character, weapon, player, false);
					case "A":
						// Ask for all 3 because this is accusation
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
		// Print available cards
		for(int i = 0; i < cards.length; ++i) {
			System.out.print(i + 1);
			System.out.println(": " + cards[i].getName());
		}

		// Ask for input
		while (true) {
			try {
				System.out.println("Please choose a " + type + " (1-" + cards.length + "):");
				int input = INPUT_SCANNER.nextInt();
				if(input < 1 || input > cards.length) // Ensure card number is within bounds
					continue;

				return cards[input - 1];
			} catch (InputMismatchException ex) {} // Error occurs when player does not enter integer

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
			} catch (InputMismatchException ex) {} // Error occurs if input format is wrong, just asks again

			System.out.println("Invalid input.");
		}
	}
	
	//Setters to be used for testing whole game implementation
	public void setBoard(Board board) {
		this.board = board;
	}

    /**
     * Makes request to player for some information
     * @param request
     */
	private <T extends PlayerRequest> T makeRequest(T request) {
	    setChanged(); // Set changed to indicate game is in request state
        notifyObservers(request);
        return request;
    }
}