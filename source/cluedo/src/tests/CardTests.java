package tests;

import game.board.Board;
import game.board.Cell;
import game.board.Position;
import game.cards.Character;
import game.cards.Room;
import game.cards.Weapon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardTests {

    private final game.cards.Character[] characters = {
            new game.cards.Character("Miss Scarlett", 1),
            new game.cards.Character("Rev. Green", 2),
            new game.cards.Character("Colonel Mustard", 3),
            new game.cards.Character("Professor Plum", 4),
            new game.cards.Character("Mrs. Peacock", 5),
            new game.cards.Character("Mrs. White", 6)};

    // Rooms used in card generation
    private final Room[] rooms = {
            new Room("Kitchen", 'K'),
            new Room("Ball Room", 'B'),
            new Room("Conservatory", 'C'),
            new Room("Billiard Room", 'P'),
            new Room("Dining Room", 'D'),
            new Room("Library", 'L'),
            new Room("Hall", 'R'),
            new Room("Lounge", 'T'),
            new Room("Study", 'S') };

    // Weapons used in card generation
    private final game.cards.Weapon[] weapons = {
            new game.cards.Weapon("Candlestick"),
            new game.cards.Weapon("Dagger"),
            new game.cards.Weapon("Lead Pipe"),
            new game.cards.Weapon("Revolver"),
            new game.cards.Weapon("Rope"),
            new game.cards.Weapon("Spanner") };

    @Test
    void testCardGetName() {
        Weapon w = weapons[0];
        game.cards.Character c = characters[0];
        Room r = rooms[0];

        assertEquals(w.getName(), "Candlestick");
        assertEquals(c.getName(), "Miss Scarlett");
        assertEquals(r.getName(), "Kitchen");
    }

    @Test
    void testCharacterEquals() {
        assertTrue(characters[0].equals(new Character("Miss Scarlett", 1)));

        assertTrue(characters[0].equals(characters[0]));
        assertFalse(characters[0].equals(characters[1]));
        assertFalse(characters[0].equals(null));

        // Character number should have no effect on equality
        assertTrue(characters[0].equals(new Character("Miss Scarlett", 99)));
    }

    @Test
    void testRoomEquals() {
        assertTrue(rooms[0].equals(new Room("Kitchen", 'K')));

        assertTrue(rooms[0].equals(rooms[0]));
        assertFalse(rooms[0].equals(rooms[1]));
        assertFalse(rooms[0].equals(null));

        // Room prefix should have no effect on equality
        assertTrue(rooms[0].equals(new Room("Kitchen", 'A')));
    }

    @Test
    void testWeaponEquals() {
        assertTrue(weapons[0].equals(new Weapon("Candlestick")));

        assertTrue(weapons[0].equals(weapons[0]));
        assertFalse(weapons[0].equals(weapons[1]));
        assertFalse(weapons[0].equals(null));
    }
}
