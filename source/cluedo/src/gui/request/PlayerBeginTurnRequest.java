package gui.request;

import game.Player;

public class PlayerBeginTurnRequest extends PlayerRequest<Object> {
    public final Player player;

    public PlayerBeginTurnRequest(Player player) {
        this.player = player;
    }
}
