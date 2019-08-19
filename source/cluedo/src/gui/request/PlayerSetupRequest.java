package gui.request;


import game.Player;
import game.cards.Character;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Represents a request to ask the player for their information such as name and which character they would like to use
 */
public class PlayerSetupRequest extends PlayerRequest<Player> {
    public final Collection<game.cards.Character> characters;
    public final Set<game.cards.Character> chosenCharacters;
    public final Set<String> chosenNames;

    /**
     * Constructor for the request
     * @param characters Characters the player can choose
     * @param chosenCharacters Character that have already been used and cannot be chosen again
     * @param chosenNames Names that have already been used and cannot be chosen again
     */
    public PlayerSetupRequest(Collection<game.cards.Character> characters, Set<game.cards.Character> chosenCharacters, Set<String> chosenNames) {
        this.characters = Collections.unmodifiableCollection(characters);
        this.chosenCharacters = Collections.unmodifiableSet(chosenCharacters);
        this.chosenNames = Collections.unmodifiableSet(chosenNames);
    }
}
