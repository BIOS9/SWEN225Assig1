package tests;

import game.board.Board;

import static org.junit.jupiter.api.Assertions.*;

import game.board.Cell;
import game.board.Position;
import game.cards.Room;
import org.junit.jupiter.api.Test;

public class BoardTests {

    private final game.cards.Character[] characters = {
            new game.cards.Character("Miss Scarlett", 1),
            new game.cards.Character("Rev. Green", 2),
            new game.cards.Character("Colonel Mustard", 3),
            new game.cards.Character("Professor Plum", 4),
            new game.cards.Character("Mrs. Peacock", 5),
            new game.cards.Character("Mrs. White", 6)};

    // Rooms used in card generation
    private final game.cards.Room[] rooms = {
            new game.cards.Room("Kitchen", 'K'),
            new game.cards.Room("Ball Room", 'B'),
            new game.cards.Room("Conservatory", 'C'),
            new game.cards.Room("Billiard Room", 'P'),
            new game.cards.Room("Dining Room", 'D'),
            new game.cards.Room("Library", 'L'),
            new game.cards.Room("Hall", 'R'),
            new game.cards.Room("Lounge", 'T'),
            new game.cards.Room("Study", 'S') };

    @Test
    void testBoardGenerate() {
        Board board = new Board(characters);
        String expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - 1 - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . > B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString());
    }

    @Test
    void testBoardMoveInvalid() {
        Board board = new Board(characters);

        boolean result = board.moveCharacter(characters[0], Cell.Direction.NORTH); // Shouldn't move
        assertFalse(result, "Move should fail");
        String expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - 1 - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . > B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.WEST); // Shouldn't move
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.EAST); // Shouldn't move
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        assertTrue(result, "Move should succeed");
        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . 1 B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . > B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.SOUTH); // Shouldn't move
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");
    }

    @Test
    void testBoardMoveValid() {
        Board board = new Board(characters);

        boolean result = board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        assertTrue(result, "Move should have succeeded");

        result = board.moveCharacter(characters[1], Cell.Direction.SOUTH);
        assertTrue(result, "Move should have succeeded");

        result = board.moveCharacter(characters[2], Cell.Direction.WEST);
        assertTrue(result, "Move should have succeeded");

        result = board.moveCharacter(characters[3], Cell.Direction.WEST);
        assertTrue(result, "Move should have succeeded");

        result = board.moveCharacter(characters[4], Cell.Direction.NORTH);
        assertTrue(result, "Move should have succeeded");

        result = board.moveCharacter(characters[5], Cell.Direction.EAST);
        assertTrue(result, "Move should have succeeded");

        String expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - . - - - - - - - - -  A\n" +
                "B  K K K K K K - . . 1 B B B B 2 . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . > B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . 3 .  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  . 6 . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . 4 .  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T 5 . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - . - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");
    }

    @Test
    void testBoardMoveDoor() {
        Board board = new Board(characters);

        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.WEST);
        board.moveCharacter(characters[0], Cell.Direction.WEST);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        boolean result = board.moveCharacter(characters[0], Cell.Direction.EAST);
        assertTrue(result, "Move should have succeeded");

        String expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . 1 B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.EAST);
        assertTrue(result, "Move should have succeeded");

        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . > 1 B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.WEST);
        assertTrue(result, "Move should have succeeded");

        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . 1 B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.WEST);
        assertTrue(result, "Move should have succeeded");

        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . 1 > B B B B B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");
    }

    @Test
    void testBoardCellOccupiedMove() {
        Board board = new Board(characters);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.WEST);
        board.moveCharacter(characters[0], Cell.Direction.WEST);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.SOUTH);
        board.moveCharacter(characters[0], Cell.Direction.EAST);
        board.moveCharacter(characters[0], Cell.Direction.EAST);
        board.moveCharacter(characters[0], Cell.Direction.EAST);
        board.moveCharacter(characters[0], Cell.Direction.EAST);

        board.moveCharacter(characters[1], Cell.Direction.SOUTH);
        board.moveCharacter(characters[1], Cell.Direction.EAST);
        board.moveCharacter(characters[1], Cell.Direction.EAST);
        board.moveCharacter(characters[1], Cell.Direction.SOUTH);
        board.moveCharacter(characters[1], Cell.Direction.SOUTH);
        board.moveCharacter(characters[1], Cell.Direction.SOUTH);
        board.moveCharacter(characters[1], Cell.Direction.SOUTH);
        board.moveCharacter(characters[1], Cell.Direction.WEST);
        board.moveCharacter(characters[1], Cell.Direction.WEST);
        board.moveCharacter(characters[1], Cell.Direction.WEST);
        board.moveCharacter(characters[1], Cell.Direction.WEST);

        String expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - . - - - - - - - - -  A\n" +
                "B  K K K K K K - . . . B B B B . . . - C C C C C C  B\n" +
                "C  K K K K K K . . B B B B B B B B . . C C C C C C  C\n" +
                "D  K K K K K K . . B B B B B B B B . . C C C C C C  D\n" +
                "E  K K K K K K . . B B B B B B B B . . ^ C C C C C  E\n" +
                "F  K K K K K K . . > B B 1 2 B B < . . . C C C C -  F\n" +
                "G  - K K K ^ K . . B B B B B B B B . . . . . . . 3  G\n" +
                "H  . . . . . . . . B ^ B B B B ^ B . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . P P P P P P  I\n" +
                "J  D D D D D . . . . . . . . . . . . . > P P P P P  J\n" +
                "K  D D D D D D D D . . - - - - - . . . P P P P P P  K\n" +
                "L  D D D D D D D D . . - - - - - . . . P P P P P P  L\n" +
                "M  D D D D D D D < . . - - - - - . . . P P P P ^ P  M\n" +
                "N  D D D D D D D D . . - - - - - . . . . . . . . -  N\n" +
                "O  D D D D D D D D . . - - - - - . . . L L V L L -  O\n" +
                "P  D D D D D D ^ D . . - - - - - . . L L L L L L L  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > L L L L L L  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . L L L L L L L  R\n" +
                "S  - . . . . . . . . R R V V R R . . . L L L L L -  S\n" +
                "T  T T T T T T V . . R R R R R R . . . . . . . . 4  T\n" +
                "U  T T T T T T T . . R R R R R < . . . . . . . . -  U\n" +
                "V  T T T T T T T . . R R R R R R . . V S S S S S S  V\n" +
                "W  T T T T T T T . . R R R R R R . . S S S S S S S  W\n" +
                "X  T T T T T T T . . R R R R R R . . S S S S S S S  X\n" +
                "Y  T T T T T T - 5 - R R R R R R - . - S S S S S S  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Test setup failed");

        boolean result = board.moveCharacter(characters[1], Cell.Direction.WEST);
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.EAST);
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");
    }

    @Test
    void testMoveCharacterToRoom() {
        Board board = new Board(characters);
        for(int i = 0; i < 100; ++i) { // Test lots to ensure random covers wide range of possibilities
            for (Room room : rooms) {
                for (game.cards.Character character : characters) {
                    board.moveCharacterToRoom(character, room);

                    Cell cell = character.getLocation();
                    assertTrue(cell.isRoom(room));
                    assertFalse(cell.isDoor);
                }
            }
        }
    }

    @Test
    void testLinkInvalid() {
        Board board = new Board(characters);

        boolean result = board.needsLink(null, null);
        assertFalse(result);

        result = board.needsLink(board.getCell(new Position(0, 0)), null);
        assertFalse(result);

        result = board.needsLink(null, board.getCell(new Position(0, 0)));
        assertFalse(result);
    }

    @Test
    void testLinkRoom() {
        Board board = new Board(characters);

        // Same room
        boolean result = board.needsLink(new Cell(rooms[0], null, false), new Cell(rooms[0], null, false));
        assertTrue(result);

        // Same room, neighbours
        result = board.needsLink(new Cell(rooms[0], new Position(0, 0), false), new Cell(rooms[0], new Position(0, 1), false));
        assertTrue(result);

        // Neighbours but different rooms
        result = board.needsLink(new Cell(rooms[0], new Position(0, 0), false), new Cell(rooms[1], new Position(0, 1), false));
        assertFalse(result);
    }

    @Test
    void testLinkDoor() {
        Board board = new Board(characters);

        // Same room, doors, neighbours
        boolean result = board.needsLink(new Cell(rooms[0], new Position(0, 0), true), new Cell(rooms[0], new Position(0, 1), true));
        assertTrue(result);

        // Different room, neighbours, doors
        result = board.needsLink(new Cell(rooms[0], new Position(0, 0), true), new Cell(rooms[1], new Position(0, 1), true));
        assertTrue(result);

        // Doors, not neighbours, different rooms
        result = board.needsLink(new Cell(rooms[0], new Position(0, 0), true), new Cell(rooms[1], new Position(0, 2), true));
        assertFalse(result);
    }

    @Test
    void testLinkHorizontal() {
        Board board = new Board(characters);
        Cell a = new Cell(null, null, false);
        Cell b = new Cell(null, null, false);

        board.linkCellsHorizontal(a, b);

        assertEquals(a.getNeighbour(Cell.Direction.EAST), b);
        assertEquals(b.getNeighbour(Cell.Direction.WEST), a);

        assertNotEquals(a.getNeighbour(Cell.Direction.WEST), b);
        assertNotEquals(b.getNeighbour(Cell.Direction.EAST), a);
    }

    @Test
    void testLinkVertical() {
        Board board = new Board(characters);
        Cell a = new Cell(null, null, false);
        Cell b = new Cell(null, null, false);

        board.linkCellsVertical(a, b);

        assertEquals(a.getNeighbour(Cell.Direction.SOUTH), b);
        assertEquals(b.getNeighbour(Cell.Direction.NORTH), a);

        assertNotEquals(a.getNeighbour(Cell.Direction.NORTH), b);
        assertNotEquals(b.getNeighbour(Cell.Direction.SOUTH), a);
    }
}
