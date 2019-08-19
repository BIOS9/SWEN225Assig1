package gui.Update;

import game.Player;
import game.Suggestion;

/**
 * Represents a game won update
 * @author abbey
 */
public class GameWonUpdate extends GameUpdate {
    public final Player winner;
    public final boolean winByDefault;
    public final Suggestion solution;

    /**
     * Constructor for a game won update
     */
    public GameWonUpdate(Player winner, Suggestion solution, boolean winByDefault) {
        this.winner = winner;
        this.solution = solution;
        this.winByDefault = winByDefault;
    }
}
