package gui.Update;

import game.Player;

public class PlayerTurnUpdate extends GameUpdate {
    public final Player player;
    public final int round;

    public PlayerTurnUpdate(Player player, int round) {
        this.player = player;
        this.round = round;
    }
}
