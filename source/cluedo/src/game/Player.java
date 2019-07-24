package game;

import game.board.Cell;

import java.util.Collections;
import java.util.List;

/**
 * Responsible for managin the physical players interactions with the game
 * (links to characters and moves)
 * 
 * Colaborates directly with CluedoGame, Card, Cell, Character.
 * 
 * @author abbey
 *
 *
 *UPDATE: copied over what appears to be relevant from umple.
 *+ Attributes and Asssociations
 *+ Constructor
 *+ Some getters and setters that didnt have ridiculour booleans in it.
 *+ A to string method.
 * (was on the bus so will sort later and there are typos)
 */
class Player {
	// Link to suggestion??

	// Player Attributes - Umple
	private boolean hasAcused = false;

	// Player Associations - Umple
	private List<game.cards.Card> hand;
	private List<Turn> turn;
	private CluedoGame game;
	private Cell cell;
	private game.cards.Character character;

	/**
	 * Constructs a new Player object with a link to the character being represented
	 * by the player, the game and the starting cell of the player (fixed).
	 * 
	 * @param character
	 * @param game
	 * @param cell
	 */
	public Player(game.cards.Character character, CluedoGame game, Cell cell) {
		this.character = character;
		this.game = game;
		this.cell = cell;
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
	public Cell getCell()
	{
		return cell;
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
			  "  " + "cell = "+(getCell()!=null?Integer.toHexString(System.identityHashCode(getCell())):"null") + System.getProperties().getProperty("line.separator") +
			  "  " + "character = "+(getCharacter()!=null?Integer.toHexString(System.identityHashCode(getCharacter())):"null");
	}
}