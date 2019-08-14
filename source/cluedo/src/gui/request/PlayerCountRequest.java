package gui.request;

import game.CluedoGame;

public class PlayerCountRequest extends PlayerRequest<Integer> {
    public final String conditions = "(3 - 6)"; // Condition string to present to player when asking for input

    @Override
    public boolean isInputValid(Integer input) {
        return input >= CluedoGame.MIN_PLAYERS && input <= CluedoGame.MAX_PLAYERS;
    }
}
