
package game;

import game.board.Board;
import game.board.Cell;
import game.cards.Card;
import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;
import gui.GameWindow;
import gui.Update.*;
import gui.request.*;
import javafx.util.Pair;

import java.awt.*;
import java.util.List;
import java.util.*;


/**
 * Responsible for contrusting and initiating the game cards and board. And manages the frame of gamplay
 * CluedoGame is ovservable
 * 
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
    private boolean hasSuggestedThisTurn = false;
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

	/**
	 * Moves the given player on the board, manages what they can do at different stages of thier turn
	 * e.g suggestions/acusations/skip move.
	 * @param direction
	 */
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

		if(!character.getLocation().isRoom(hallway)) {
			allowSuggestion = true;
			allowFinishTurn = true;
			allowAccusation = true;
		} else if(movesLeft <= 0) {
        	allowFinishTurn = true;
        	allowAccusation = true;
		} else { // Room is hallway but still have moves left
			allowSuggestion = false;
			allowFinishTurn = false;
			allowAccusation = false;
		}

		if(hasSuggestedThisTurn) // Only allow one suggestion per turn
			allowSuggestion = false;

		// If player is stuck, allow them to skip the rest of their turn
		boolean canMove = false;
		for(Cell.Direction d : Cell.Direction.getList()) {
			Cell neighbour = character.getLocation().getNeighbour(d);
			if(neighbour != null && !visitedCells.contains(neighbour) && !neighbour.isOccupied() && (!visitedRooms.contains(newCell) || newCell.isRoom(hallway))) {
				canMove = true;
				break;
			}
		}

		if(!canMove) {
			allowFinishTurn = true;
			updateGui(new MessageUpdate("It appears that you're stuck! You can skip the rest of your turn."));
		}

		updateGui(new AllowedActionsUpdate(allowFinishTurn, allowSuggestion, allowAccusation));
        // Check if run out of moves, update suggestion, accusation and skip buttons
    }

	/**
	 * Evaluates a players acusation, drawing a conclusion against the soloution
	 * cards and updating relevant objects.
	 * 
	 */
    public void makeAccusation() {
		if(currentPlayer == null || gameWon || !allowAccusation) return;

		Suggestion accusation = makeRequest(new PlayerAccusationRequest(Arrays.asList(characters), Arrays.asList(rooms), Arrays.asList(weapons), currentPlayer)).waitResponse();
		
		//Check against soloution cards
		if(accusation.equals(solutionCards)) {
			winGame(false);
			return;
		}

		currentPlayer.setHasAcused(); //can only make one acusation
		updateGui(new PlayersUpdate(players)); // Update players
		updateGui(new MessageUpdate("Sorry " + currentPlayer.getPlayerName() + ", your accusation was incorrect! You have been disqualified."));
		updateGui(new WrongAccusationUpdate(currentPlayer, solutionCards));

		nextTurn();
	}

    /**
     * 
     * Manages the suggestion process:making the suggestion request and asking for refutations,
     * updating relevant objects with results.
     * 
     */
	public void makeSuggestion() {
		if(currentPlayer == null || gameWon || !allowSuggestion) return;

		Suggestion suggestion = makeRequest(new PlayerSuggestionRequest(Arrays.asList(characters), Arrays.asList(rooms), Arrays.asList(weapons), currentPlayer.getCharacter().getLocation().getRoom(), currentPlayer)).waitResponse();
		allowSuggestion = false;
		hasSuggestedThisTurn = true;
		updateGui(new AllowedActionsUpdate(allowFinishTurn, allowSuggestion, allowAccusation));

		Room moveRoom = currentPlayer.getCharacter().getLocation().getRoom(); // room to move other players too
		updateGui(new MessageUpdate("Moving " + suggestion.getCharacter().getName() + " to the " + moveRoom.getName()));

		if(!board.moveCharacterToRoom(suggestion.getCharacter(), moveRoom))
			updateGui(new MessageUpdate("Failed to move the suggested player into the room! Are there available spaces away from any door?"));

		updateGui(new BoardUpdate(board)); // Update board to display character/weapon movement
		updateGui(new HandUpdate(new ArrayList<>())); // Hide the hand from players during the refutations

		Card refutationCard = makeRequest(new PlayerRefutationRequest(suggestion, currentPlayer, players)).waitResponse();
		if(refutationCard == null)
			updateGui(new MessageUpdate("Refutations are over, no refutations were made. You may finish your turn or make an accusation."));
		else
			updateGui(new MessageUpdate("Refutations are over."));

		updateGui(new HandUpdate(currentPlayer.getHand())); // Restore the hand
	}

	/**
	 * 
	 * Manages the next turn process incrimenting rounds, ensuring players who are disqualified are skipped
	 * clearing information from the last players turn and updating relevant objects and display.
	 * 
	 */
    public void nextTurn() {
	    if(gameWon) return;

	    allowMove = false;
	    allowFinishTurn = false;
	    allowAccusation = false;
	    allowSuggestion = false;
	    hasSuggestedThisTurn = false;
		updateGui(new AllowedActionsUpdate(allowFinishTurn, allowSuggestion, allowAccusation));

	    ++round;
        currentPlayer = players.get(round % players.size());

        if(currentPlayer.getHasAcused()) { // If player has accused, skip them
            updateGui(new PlayerTurnSkipUpdate(currentPlayer));
            nextTurn();
            return;
        }

		if(players.stream().filter(x -> !x.getHasAcused()).count() == 1) {
			winGame(true);
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


		updateGui(new HandUpdate(new ArrayList<>()));
        makeRequest(new PlayerBeginTurnRequest(currentPlayer)).waitResponse(); // Waits for player to begin turn

        Turn turn = new Turn(currentPlayer);
        Pair<Integer, Integer> dice = turn.rollDice();
        movesLeft = turn.getDiceRoll();

        updateGui(new MovesLeftUpdate(movesLeft));
        updateGui(new DiceUpdate(dice.getKey(), dice.getValue()));
        updateGui(new HandUpdate(currentPlayer.getHand()));

		allowMove = true;
    }

    /**
     * Sets the win game boolean and updates the display
     * @param winByDefault
     */
    public void winGame(boolean winByDefault) {
		gameWon = true;
		updateGui(new GameWonUpdate(currentPlayer, solutionCards, winByDefault));
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
			} catch (InputMismatchException ignored) {}

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
			} catch (InputMismatchException ignored) {} // Error occurs when player does not enter integer

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
			} catch (InputMismatchException ignored) {} // Error occurs if input format is wrong, just asks again

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