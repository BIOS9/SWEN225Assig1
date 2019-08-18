package gui.request;


import game.Player;
import game.cards.Character;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class PlayerSetupRequest extends PlayerRequest<Player> {
    public final Collection<game.cards.Character> characters;
    public final Set<game.cards.Character> chosenCharacters;
    public final Set<String> chosenNames;

    public PlayerSetupRequest(Collection<game.cards.Character> characters, Set<game.cards.Character> chosenCharacters, Set<String> chosenNames) {
        this.characters = Collections.unmodifiableCollection(characters);
        this.chosenCharacters = Collections.unmodifiableSet(chosenCharacters);
        this.chosenNames = Collections.unmodifiableSet(chosenNames);
    }
}
