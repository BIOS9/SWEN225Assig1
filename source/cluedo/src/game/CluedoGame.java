
package game;

import java.awt.*;
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
import gui.Update.*;
import gui.request.PlayerBeginTurnRequest;
import gui.request.PlayerCountRequest;
import gui.request.PlayerSetupRequest;
import gui.request.PlayerRequest;
import javafx.util.Pair;


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
    private int round = -1; // Start at -1 because the first round will increment
    private int movesLeft;
    private Player currentPlayer;
    private boolean gameWon = false;
    private boolean allowMove = false;
	private boolean allowFinishTurn = false;
	private boolean allowAccusation = false;
	private boolean allowSuggestion = false;


    // Sets of visited places to prevent players going back on them
    private Set<Cell> visitedCells = new HashSet<>();
    private Set<Room> visitedRooms = new HashSet<>();

	// Characters used in card generation
	private final game.cards.Character[] characters = { 
			new game.cards.Character("Miss Scarlett", 1, Color.decode("#F80300")),
			new game.cards.Character("Mr Green", 2, Color.decode("#7AAF09")),
			new game.cards.Character("Colonel Mustard", 3, Color.decode("#F7F004")),
			new game.cards.Character("Professor Plum", 4, Color.decode("#77016F")),
			new game.cards.Character("Mrs. Peacock", 5, Color.decode("#51AACC")),
			new game.cards.Character("Mrs. White", 6, Color.white) };

	// Rooms used in card generation
	private final game.cards.Room[] rooms = {
			new Room("Kitchen", 'K'),
	        new Room("Ball Room", 'B'),
	        new Room("Conservatory", 'C'),
	        new Room("Billiard Room", 'P'),
	        new Room("Dining Room", 'D'),
	        new Room("Library", 'L'),
	        new Room("Hall", 'R'),
	        new Room("Lounge", 'T'),
	        new Room("Study", 'S')};

	// Weapons used in card generation
	private final game.cards.Weapon[] weapons = { 
			new game.cards.Weapon("Candlestick"),
			new game.cards.Weapon("Knife"),
			new game.cards.Weapon("Lead Pipe"),
			new game.cards.Weapon("Revolver"),
			new game.cards.Weapon("Rope"),
			new game.cards.Weapon("Wrench") };

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
        nextTurn();
//        Turn t = new Turn(null);
//        Pair<Integer, Integer> dice = t.rollDice();
//        System.out.println(t.getDiceRoll());
//        updateGui(new DiceUpdate(dice.getKey(), dice.getValue()));
//        updateGui(new HandUpdate(players.get(1).getHand()));

        //runGame();
    }

	/**
	 * Initialises the game state, players and board.
	 */
	private void initGame() {
		board = new Board(characters);
		updateGui(new BoardUpdate(board));

		// Ask user how many players will be playing
		int playerCount = makeRequest(new PlayerCountRequest()).waitResponse();

		// Chosen characters that cant be chosen by next players
		Set<game.cards.Character> chosenCharacters = new HashSet<>();
		Set<String> chosenNames = new HashSet<>();

		// Add new players
		for(int i = 0; i < playerCount; ++i) {
			// Ask user for player info
			Player player = makeRequest(new PlayerSetupRequest(Arrays.asList(characters), chosenCharacters, chosenNames)).waitResponse();
			
			// Add character to list of used characters
			chosenCharacters.add(player.getCharacter());
			chosenNames.add(player.getPlayerName());
			players.add(player);
		}
		updateGui(new PlayersUpdate(players));
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

	public void moveCurrentPlayer(Cell.Direction direction) {
        if(currentPlayer == null || gameWon || !allowMove || movesLeft <= 0) return;

        game.cards.Character character = currentPlayer.getCharacter();

        // Check that player has not already been to cell in this turn
        Cell newCell = character.getLocation().getNeighbour(direction);
        if(newCell != null && visitedCells.contains(newCell)) {
            updateGui(new MessageUpdate("You have already been there!"));
            return;
        }

        // Check that player has not already been in room in this turn
        if(newCell != null && !character.getLocation().isRoom(newCell) && visitedRooms.contains(newCell.getRoom())) { // Check if new cell is a different room, and player has visited that room
            updateGui(new MessageUpdate("You have already been in that room!"));
            return;
        }

        if (board.moveCharacter(character, direction)) {
            visitedCells.add(character.getLocation());

            if(!character.getLocation().isRoom(hallway)) { // Check if room is hallway or not
                visitedRooms.add(character.getLocation().getRoom()); // Can enter hallway more than once in a turn so don't add to visited
            }

            --movesLeft;
            updateGui(new MovesLeftUpdate(movesLeft));
        } else {
            updateGui(new MessageUpdate("You can't move there!"));
        }

        // If the player is out of moves, they can accuse or finish their turn
        if(movesLeft <= 0) {
        	allowFinishTurn = true;
        	allowAccusation = true;
		}

        // If the player is in a room they can skip, accuse or suggest
        if(!character.getLocation().isRoom(hallway)) {
			allowSuggestion = true;
			allowFinishTurn = true;
			allowAccusation = true;
		}
		updateGui(new AllowedActionsUpdate(allowFinishTurn, allowSuggestion, allowAccusation));
        // Check if run out of moves, update suggestion, accusation and skip buttons
    }

    public void nextTurn() {
	    if(gameWon) return;

	    allowMove = false;
	    allowFinishTurn = false;
	    allowAccusation = false;
	    allowSuggestion = false;
		updateGui(new AllowedActionsUpdate(allowFinishTurn, allowSuggestion, allowAccusation));

	    ++round;
        currentPlayer = players.get(round % players.size());

        if(currentPlayer.getHasAcused()) { // If player has accused, skip them
            updateGui(new PlayerTurnSkipUpdate(currentPlayer));
            return;
        }

        game.cards.Character character = currentPlayer.getCharacter();

        // Clears the visited cells for the new turn
        visitedCells.clear();
        visitedRooms.clear();
        visitedCells.add(character.getLocation());

        if(!character.getLocation().isRoom(hallway)) { // Check if room is hallway
            visitedRooms.add(character.getLocation().getRoom());

            // If the player is in a room they can skip their turn, accuse or suggest
            allowFinishTurn = true;
            allowAccusation = true;
            allowSuggestion = true;

            updateGui(new AllowedActionsUpdate(allowFinishTurn, allowSuggestion, allowAccusation));
        }

        //Beginning of turn updates
        updateGui(new PlayerTurnUpdate(currentPlayer, round));
        updateGui(new MessageUpdate(currentPlayer.getPlayerName() + " / " + character.getName() + " you're up!"));
        updateGui(new BoardUpdate(board)); 

        makeRequest(new PlayerBeginTurnRequest(currentPlayer)).waitResponse(); // Waits for player to begin turn

        Turn turn = new Turn(currentPlayer);
        Pair<Integer, Integer> dice = turn.rollDice();
        movesLeft = turn.getDiceRoll();

        updateGui(new MovesLeftUpdate(movesLeft));
        updateGui(new DiceUpdate(dice.getKey(), dice.getValue()));
        updateGui(new HandUpdate(currentPlayer.getHand()));

		allowMove = true;

        //updateGui(new HandUpdate(new ArrayList<>())); // Clear hand
    }

	/**
	 * Runs game, main loop for turns and user input
	 */
	private void runGame() {
		Player winner;

		//while (true) {

            //System.out.println("Your character is number " + character.getNumber() + " and is located at " + character.getLocation().position.toString());

			// Move player until they run out of moves or skip
//			while(moves > 0) {
//				System.out.println("You have " + moves + " moves remaining.");
//				System.out.print("Where would you like to move? ");
//
//
//				if(move == null) // Skip rest of moves is user skips
//					break;
//
//
//			}

			// Ask player for suggestion
//			Suggestion suggestion = askSuggestion(player, character.getLocation().getRoom(), character.getLocation().isRoom(hallway)); // Get suggestion, forcing accusation if in hallway
//
//			if(suggestion != null) {
//				// Checks if suggestion is accusation, and if it is correct
//				if (suggestion.isAcusation() && suggestion.equals(solutionCards)) {
//					System.out.println("The solution cards are: " + solutionCards.toString());
//					System.out.println("Congratulations you won the game!");
//					winner = player;
//					break;
//				}
//				// If the acusation is wrong this player can no longer make suggestions/acusations
//				else if (suggestion.isAcusation()) {
//					player.setHasAcused();
//					System.out.println("Your accusation was incorrect! You are now a spectator of the game.");
//					System.out.println("The solution cards are: " + solutionCards.toString());
//				}
//				// If the suggestion isnt an accusation ask other players for refutations.
//				else {
//					// Move the suggested player into a random spot in the room
//					Room moveRoom = player.getCharacter().getLocation().getRoom();
//					System.out.println("Moving " + suggestion.getCharacter().getName() + " to the " + moveRoom.getName());
//
//					if(!board.moveCharacterToRoom(suggestion.getCharacter(), moveRoom))
//						System.out.println("Failed to move the suggested player into the room! Are there available spaces away from any door?");
//
//					askRefutations(suggestion);
//				}
//			}

            //++round;
        //}

		//System.out.println(winner.getCharacter().getName() + " has won the game in " + round + " rounds!");
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

    private void updateGui(GameUpdate update) {
	    setChanged();
	    notifyObservers(update);
    }
}