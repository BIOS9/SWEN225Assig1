package game.cards;
/**
 * Represents an in game character that is part of the game regardless of whether a player is the character.
 * 	e.g can have only 3 players but still have 6 characters.
 * 
 * Responsible for linking the actual players to the character cards. (holding position also)
 * 
 * Collaborates with Player, Cell.
 * @author abbey
 *
 */

import game.board.Cell;

/**
 * Umple : 
 * 
 * 
 * @author abbey
 *
 */
public class Character implements Card {
    private final String name;
    private final int number;
    private Cell location;

    public Character(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setLocation(Cell location) {
        this.location = location;
    }

    public Cell getLocation() {
        return location;
    }

    public int getNumber() {
        return number;
    }
}