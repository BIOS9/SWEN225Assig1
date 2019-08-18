package gui.Update;

/**
 * Class representing a dice update in the game.
 * @author abbey
 *
 */
public class DiceUpdate extends GameUpdate {
    public final int FirstDie;
    public final int SecondDie;
    
    /**
     * Constructor updating the two dice in the game.
     * @param firstDie
     * @param secondDie
     */
    public DiceUpdate(int firstDie, int secondDie) {
        this.FirstDie = firstDie;
        this.SecondDie = secondDie;
    }
}
