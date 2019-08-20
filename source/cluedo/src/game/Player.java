package game;

import game.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the actual players in the game (1-6) and links the character they are playing as to the Player object.
 * Manages the cards the player has been dealt and executes the players turns.
 * Also keeps track of where the player is on the board and if the player has made their one accusation.
 * 
 * Collaborates directly with Character, Card, Turn and Cell.
 * 
 * @author abbey
 */
public class Player {

	// Player Attributes
	private boolean hasAcused = false;
	private String playerName;

	// Player Associations
	private List<game.cards.Card> hand;
	private game.cards.Character character;

	/**
	 * Constructs a new Player object with a link to the character being represented
	 * by the player, the game and the starting cell of the player (fixed).
	 * 
	 * @param character
	 */
	public Player(game.cards.Character character, String name) {
		this.character = character;
		this.hand = new ArrayList<game.cards.Card>();
		this.playerName = name;
	}
	
	// For testing without the player name (allows for A1 tests to pass)
	public Player(game.cards.Character character) {
		this.character = character;
		this.hand = new ArrayList<game.cards.Card>();
	}

	// Getters and setters from Umple (sort through)
	public boolean getHasAcused(){
		return hasAcused;
	}
	
	public void setHasAcused() {
		this.hasAcused = true;
	}
	
	/**
	 * Prints out friendly representation of players hand
	 * @return
	 */
	public String printCards() {
		String s = "Your hand: ";
		for(game.cards.Card c : hand) {
			s += (" [" + c.getName() + "] ");
		}
		return s;
	}	
	
	/**
	 * Returns a list of cards.
	 * @return unmodifiable list of cards in players hand.
	 */
	public List<game.cards.Card> getHand() {
		return Collections.unmodifiableList(hand);
	}
	
	public String getPlayerName() {
		return playerName;
	}

	public void addCardToHand(Card c) {
		this.hand.add(c);
	}

	/* Code from template association_GetOne */
	public game.cards.Character getCharacter()
	{
		return character;
	}
}