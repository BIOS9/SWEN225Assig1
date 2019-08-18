package game.cards;
import game.board.Cell;

import java.awt.*;

/**
 * Represents an in game character that is part of the game regardless of whether a player is linked to the character.
 * 	e.g can have only 3 players but will still have 6 characters.
 * 
 * Responsible for linking the actual players to the character cards. (holding position also)
 * 
 * Collaborates with Player, Cell.
 * @author abbey
 *
 */
public class Character implements Card {
	
	//Card Attributes
    private final String name;
    private final int number;
    private String image;
    private Color color;

    //Card Associations
    private Cell location;
    
    // For testing without the image string
    public Character(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public Character(String name, int number, Color color) {
        this.name = name;
        this.number = number;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
        return false;
        if(obj.getClass() != getClass())
            return false;
        if(obj == this)
            return true;

        Character character = (Character)obj;
        if(name == null)
            return false;

        return name.equals(character.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
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