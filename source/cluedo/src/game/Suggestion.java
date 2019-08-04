package game;

import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;

/**
 *
 * Responsible for taking a pplayers 3 suggestion or acusation cards and checking that they are 1 of each
 * type (Room, Character, Weapon).
 *
 * Collaborates with Turn, Card and Player
 *
 * @author abbey
 *
 */
public class Suggestion {

	//Suggestion Attributes
	private boolean isAcusation = false;

	//Suggestion Associations
    private game.cards.Room room;
    private game.cards.Character character;
    private game.cards.Weapon weapon;
    Player player;

    /**
     * Constructs a new Suggestion object with the three required cards (Room, Character, Weapon) the player making the suggestion and a
     * boolean indicating if the player is making an acusation.
     *
     * @param room
     * @param character
     * @param weapon
     * @param player
     * @param isAcusation
     */
    public Suggestion (game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon, Player player, boolean isAcusation) {
    	this.room = room;
    	this.character = character;
    	this.weapon = weapon;
    	this.player = player;
    	this.isAcusation = isAcusation;
    }

    /**
     * Constructs a new Suggestion object with only the three required cards (Room, Character, Weapon), useful for making the soloution.
  	 *
     * @param room
     * @param character
     * @param weapon
     */
    public Suggestion (game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon) {
    	this.room = room;
    	this.character = character;
    	this.weapon = weapon;
    }

	public String printCards() {
		String s = "Suggestion: [" + room.getName() + "] [" + weapon.getName() + "] [" + character.getName() + "]";
		return s;
	}

	public Character getCharacter() {
		return character;
	}

	public Room getRoom() {
		return room;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		if(obj.getClass() != getClass())
			return false;
		if(obj == this)
			return true;

		Suggestion suggestion = (Suggestion)obj;

		if(!room.equals(suggestion.room))
			return false;

		if(!character.equals(suggestion.character))
			return false;

		if(!weapon.equals(suggestion.weapon))
			return false;

		return true;
	}

	public boolean isAcusation() {
		return isAcusation;
	}
}
