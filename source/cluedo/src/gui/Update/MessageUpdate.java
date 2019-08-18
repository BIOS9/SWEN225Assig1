package gui.Update;

import game.Player;

/**
 * Represents a message update in the game.
 * @author abbey
 */
public class MessageUpdate extends GameUpdate {
    public final String message;

    /**
     * Constructor for a message update.
     * @param message to be displayed.
     */
    public MessageUpdate(String message) {
        this.message = message;
    }
}
