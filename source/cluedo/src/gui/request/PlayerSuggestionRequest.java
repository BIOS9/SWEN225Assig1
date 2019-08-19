package gui.request;

import game.Player;
import game.Suggestion;
import game.cards.Room;
import game.cards.Weapon;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a request to ask the player for a suggestion
 */
public class PlayerSuggestionRequest extends PlayerRequest<Suggestion> {
    public final Collection<game.cards.Character> characters;
    public final Collection<Room> rooms;
    public final Collection<Weapon> weapons;
    public final Room suggestedRoom;
    public final Player player;


    public PlayerSuggestionRequest(Collection<game.cards.Character> characters, Collection<Room> rooms, Collection<Weapon> weapons, Room suggestedRoom, Player player) {
        this.characters = Collections.unmodifiableCollection(characters);
        this.rooms = Collections.unmodifiableCollection(rooms);
        this.weapons = Collections.unmodifiableCollection(weapons);
        this.player = player;
        this.suggestedRoom = suggestedRoom;
    }
}
