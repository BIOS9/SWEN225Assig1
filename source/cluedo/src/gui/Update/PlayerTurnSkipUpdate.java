package gui.Update;

import game.Player;

public class PlayerTurnSkipUpdate extends GameUpdate {
    public final Player player;

    public PlayerTurnSkipUpdate(Player player) {
        this.player = player;
    }
}
