package gui.request;


import game.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PlayerSetupRequest extends PlayerRequest<Player> {
    public final Collection<game.cards.Character> characters;
    public final Collection<game.cards.Character> chosenCharacters;

    public PlayerSetupRequest(Collection<game.cards.Character> characters, Collection<game.cards.Character> chosenCharacters) {
        this.characters = Collections.unmodifiableCollection(characters);
        this.chosenCharacters = chosenCharacters;
    }
}
