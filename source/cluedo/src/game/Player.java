package game;

import game.board.Cell;
import game.cards.Card;

import java.util.Collections;
import java.util.List;

/**
 * Represents the actual players in the game (1-6) and links the character they are playing as to the Player object.
 * Manages the cards the player has been dealt and executes the players turns.
 * Also keeps track of where the player is on the board and if the player has made thier one acusation.
 * 
 * Colaborates directly with Character, Card, Turn and Cell.
 * 
 * @author abbey
 */

class Player {

	// Player Attributes
	private boolean hasAcused = false;

	// Player Associations
	private List<game.cards.Card> hand;
	private List<Turn> turn;
	private CluedoGame game;
	private game.cards.Character character;

	/**
	 * Constructs a new Player object with a link to the character being represented
	 * by the player, the game and the starting cell of the player (fixed).
	 * 
	 * @param character
	 * @param game
	 */
	public Player(game.cards.Character character, CluedoGame game) {
		this.character = character;
		this.game = game;
	}

	// Getters and setters from Umple (sort through)
	public boolean getHasAcused()
	{
		return hasAcused;
	}

	/* Code from template association_GetMany */
	public game.cards.Card getHand(int index) {
		game.cards.Card aHand = hand.get(index);
		return aHand;
	}

	public List<game.cards.Card> getHand() {
		List<game.cards.Card> newHand = Collections.unmodifiableList(hand);
		return newHand;
	}
	
	public void addCardToHand(Card c) {
		this.hand.add(c);
	}

	public int numberOfHand() {
		int number = hand.size();
		return number;
	}

	public boolean hasHand() {
		boolean has = hand.size() > 0;
		return has;
	}

	public int indexOfHand(game.cards.Card aHand) {
		int index = hand.indexOf(aHand);
		return index;
	}
	/* Code from template association_GetMany */
	public Turn getTurn(int index) {
		Turn aTurn = turn.get(index);
		return aTurn;
	}

	public List<Turn> getTurn() {
		List<Turn> newTurn = Collections.unmodifiableList(turn);
		return newTurn;
	}

	public int numberOfTurn() {
		int number = turn.size();
		return number;
	}

	public boolean hasTurn() {
		boolean has = turn.size() > 0;
		return has;
	}

	public int indexOfTurn(Turn aTurn) {
		int index = turn.indexOf(aTurn);
		return index;
	}
	/* Code from template association_GetOne */
	public CluedoGame getGame()
	{
		return game;
	}
	/* Code from template association_GetOne */
	public game.cards.Character getCharacter()
	{
		return character;
	}

	// To string method from Umple
	public String toString() {
	  return super.toString() + "["+
			  "hasAcused" + ":" + getHasAcused()+ "]" + System.getProperties().getProperty("line.separator") +
			  "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
			  "  " + "character = "+(getCharacter()!=null?Integer.toHexString(System.identityHashCode(getCharacter())):"null");
	}
}