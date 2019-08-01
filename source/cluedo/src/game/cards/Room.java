package game.cards;
/**
 * 
 * Class representing the Room cards.
 * 
 * Represented by a Card.
 * 
 * @author abbey
 *
 */
public class Room implements Card {
    private final String name;
    private final char prefix;

    public Room(String name, char prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        if(obj == this)
            return true;

        Room room = (Room)obj;
        if(name == null)
            return false;

        return name.equals(room.name);
    }

    public char getPrefix() {
        return prefix;
    }

    @Override
    public String getName() {
        return name;
    }

}