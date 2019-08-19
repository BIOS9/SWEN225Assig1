package gui.Update;

import game.Player;
import game.Suggestion;

/**
 * Represents a wrong accusation message
 * @author abbey
 */
public class WrongAccusationUpdate extends GameUpdate {
    public final Player player;
    public final Suggestion solution;

    /**
     * Constructor for a wrong accusation message
     */
    public WrongAccusationUpdate(Player player, Suggestion solution) {
        this.player = player;
        this.solution = solution;
    }
}
