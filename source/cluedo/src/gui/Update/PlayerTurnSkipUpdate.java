package gui.Update;

import game.Player;

/**
 * Represents a player turn skip update, basic message for when a player cannot play their turn for any reason
 * and is therefore considered skipped.
 * e.g. made an incorrect assumption and therefore may no longer move
 * @author abbey
 * 
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
