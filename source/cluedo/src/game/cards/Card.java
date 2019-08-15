package game.cards;

/**
 * Responsible for managing the various types of cards for use within the game.
 * 
 * Collaborates with Character, Weapon and Room.
 * @author abbey
 *
 */
public interface Card {
    String getName();
    String getImage();
}