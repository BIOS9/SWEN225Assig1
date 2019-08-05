package tests;

import game.board.Board;
import game.board.Cell;
import game.board.Position;
import game.cards.Room;
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
}
