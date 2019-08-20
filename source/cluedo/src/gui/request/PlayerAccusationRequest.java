package gui.request;

import game.Player;
import game.Suggestion;
import game.cards.Room;
import game.cards.Weapon;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a request to ask the user how many players are playing
 */
public class PlayerAccusationRequest extends PlayerRequest<Suggestion> {
    public final Collection<game.cards.Character> characters;
    public final Collection<Room> rooms;
    public final Collection<Weapon> weapons;
    public final Player player;

    /**
     * Constructs a new player acusation request.
     * @param characters
     * @param rooms
     * @param weapons
     * @param player
     */
    public PlayerAccusationRequest(Collection<game.cards.Character> characters, Collection<Room> rooms, Collection<Weapon> weapons, Player player) {
        this.characters = Collections.unmodifiableCollection(characters);
        this.rooms = Collections.unmodifiableCollection(rooms);
        this.weapons = Collections.unmodifiableCollection(weapons);
        this.player = player;
    }
}
