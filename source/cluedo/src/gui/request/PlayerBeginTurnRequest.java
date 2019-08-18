package gui.request;

import game.Player;

/**
 * Represents a player begin turn request.
 * @author abbey
 */
public class PlayerBeginTurnRequest extends PlayerRequest<Object> {
    public final Player player;

    /**
     * Constructor for a player begin turn request.
     * @param player beginning turn.
     */
    public PlayerBeginTurnRequest(Player player) {
        this.player = player;
    }
}
