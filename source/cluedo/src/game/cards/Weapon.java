package game.cards;

/**
 * Responsible for representing the weapon objects in the game.
 * 
 * @author abbey
 */
public class Weapon implements Card {
	
	//Weapon Attributes
    private final String name;
    private String image;

    /**
     * Constructor
     * @param name of weapon
     */
    public Weapon(String name, String image) {
        this.name = name;
        this.image = image;
    }
    
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

	@Override
	public String getImage() {
		return image;
	}
}