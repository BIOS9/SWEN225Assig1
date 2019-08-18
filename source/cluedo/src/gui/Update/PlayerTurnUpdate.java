package gui.Update;

import game.Player;

/**
 * Represents a player turn update in the game.
 * @author abbey
 */
public class PlayerTurnUpdate extends GameUpdate {
    public final Player player;
    public final int round;

    /**
     * Constructor for a new player turn update.
     * @param player
     * @param round
     */
    public PlayerTurnUpdate(Player player, int round) {
        this.player = player;
        this.round = round;
    }
}
