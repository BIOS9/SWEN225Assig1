package gui.request;

import game.Player;
import game.Suggestion;
import game.cards.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a request to ask the players for a refutation card
 * The result can be null if there are no refutation cards, that would imply that the suggestion is correct
 */
public class PlayerRefutationRequest extends PlayerRequest<Card> {
    public final Suggestion suggestion;
    public final Player player;
    public final List<Player> playerList;

    public PlayerRefutationRequest(Suggestion suggestion, Player player, List<Player> playerList) {
        this.suggestion = suggestion;
        this.player = player;
        this.playerList = new ArrayList<>(playerList);

        // Ensures the player list does not contain the player because a player cannot refute their own suggestion
        if(this.playerList.contains(player))
            this.playerList.remove(player);
    }
}
