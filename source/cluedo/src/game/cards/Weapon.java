package game.cards;
/**
 * Responsible for representing the weapon objects in the game.
 * 
 * Collaborates directly with Card.
 * @author abbey
 *
 */
public class Weapon implements Card {
    private final String name;

    public Weapon(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}