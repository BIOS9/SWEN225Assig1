package gui.Update;

import game.cards.Card;

import java.util.Collections;
import java.util.List;

/**
 * Represents a update of the cards on the game board.
 * @author abbey
 */
public class HandUpdate extends GameUpdate {
    public final List<Card> hand;

    /**
     * Constructor for a hand update.
     * @param hand
     */
    public HandUpdate(List<Card> hand) {
        this.hand = Collections.unmodifiableList(hand);
    }
}
