package game;

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
     * @param a
     */
    public Suggestion (game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon, Player player, boolean a) {
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
}
