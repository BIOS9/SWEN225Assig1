package gui.Update;

/**
 * Represents a moves left update on the display.
 * @author abbey
 */
public class MovesLeftUpdate extends GameUpdate {
    public final int movesLeft;

    /**
     * Constructor for moves left update.
     * @param movesLeft
     */
    public MovesLeftUpdate(int movesLeft) {
        this.movesLeft = movesLeft;
    }
}
