package gui.Update;

import game.Player;

/**
 * Represents a player turn skip update, basic message for when a player cannot play thier turn for any reason
 * and is therefore considered skipped.
 * e.g. made an incorrect refutation and therefore may no longer move
 * @author abbey
 *
 *!!!!! have i got the right gist here?? (am just going through wrapping my head around all your stuff)
 */
public class PlayerTurnSkipUpdate extends GameUpdate {
    public final Player player;

    /**
     * Constructor for a player turn skip update.
     * @param player who is being skipped.
     */
    public PlayerTurnSkipUpdate(Player player) {
        this.player = player;
    }
}
