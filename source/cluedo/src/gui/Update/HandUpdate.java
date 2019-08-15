package gui.Update;

import game.cards.Card;

import java.util.Collections;
import java.util.List;

public class HandUpdate extends GameUpdate {
    public final List<Card> hand;

    public HandUpdate(List<Card> hand) {
        this.hand = Collections.unmodifiableList(hand);
    }
}
