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

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        if(obj == this)
            return true;

        Weapon weapon = (Weapon) obj;
        if(name == null)
            return false;

        return name.equals(weapon.name);
    }
}