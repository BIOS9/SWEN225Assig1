package gui.request;

import game.CluedoGame;

/**
 * Represents a new player count request for the start of the game. response must be between 3-6.
 * @author abbey
 *
 */
public class PlayerCountRequest extends PlayerRequest<Integer> {
    public final String conditions = "(3 - 6)"; // Condition string to present to player when asking for input

    /**
     * Checks validity of input, must be between 3(min players) and 6(max players).
     */
    @Override
    public boolean isInputValid(Integer input) {
        return input >= CluedoGame.MIN_PLAYERS && input <= CluedoGame.MAX_PLAYERS;
    }
}
