package game;

import game.cards.Card;
import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;

/**
 * Responsible for taking a players 3 suggestion or accusation cards and checking that they are 1 of each
 * type (Room, Character, Weapon).
 * <p>
 * Collaborates with Turn, Card and Player
 *
 * @author abbey
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
    public Suggestion(game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon, Player player, boolean isAcusation) {
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
    public Suggestion(game.cards.Room room, game.cards.Character character, game.cards.Weapon weapon) {
        this.room = room;
        this.character = character;
        this.weapon = weapon;
    }

    /**
     * Prints a string representing the cards in a suggestion object.
     * @return
     */
    public String printCards() {
        return "Suggestion: [" + room.getName() + "] [" + weapon.getName() + "] [" + character.getName() + "]";
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
    
    /**
     * Checks if the given card is part of the suggestion.
     * @param card
     * @return
     */
    public boolean containsCard(Card card) {
    	if(weapon != null && weapon.equals(card))
    	    return true;
        if(room != null && room.equals(card))
            return true;
        return character != null && character.equals(card);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj.getClass() != getClass())
            return false;
        if (obj == this)
            return true;

        Suggestion suggestion = (Suggestion) obj;

        if (!room.equals(suggestion.room))
            return false;

        if (!character.equals(suggestion.character))
            return false;

        return weapon.equals(suggestion.weapon);
    }

    public boolean isAcusation() {
        return isAcusation;
    }

    @Override
    public String toString() {
        return printCards();
    }
}
