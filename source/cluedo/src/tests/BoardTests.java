package tests;

import game.board.Board;
import static org.junit.jupiter.api.Assertions.*;

import game.board.Cell;
import org.junit.jupiter.api.Test;

public class BoardTests {

    private final game.cards.Character[] characters = {
            new game.cards.Character("Miss Scarlett", 1),
            new game.cards.Character("Rev. Green", 2),
            new game.cards.Character("Colonel Mustard", 3),
            new game.cards.Character("Professor Plum", 4),
            new game.cards.Character("Mrs. Peacock", 5),
            new game.cards.Character("Mrs. White", 6) };

    @Test
    void testBoardGenerate() {
        Board board = new Board(characters);
        String expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - 1 - - - - 2 - - - - - - - - -  A\n" +
                "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                "F  k k k k k k . . > b b b b b b < . . . c c c c -  F\n" +
                "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
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
                "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                "F  k k k k k k . . > b b b b b b < . . . c c c c -  F\n" +
                "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
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
                "B  k k k k k k - . . 1 b b b b . . . - c c c c c c  B\n" +
                "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                "F  k k k k k k . . > b b b b b b < . . . c c c c -  F\n" +
                "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
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
                "B  k k k k k k - . . 1 b b b b 2 . . - c c c c c c  B\n" +
                "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                "F  k k k k k k . . > b b b b b b < . . . c c c c -  F\n" +
                "G  - k k k ^ k . . b b b b b b b b . . . . . . 3 .  G\n" +
                "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                "R  . 6 . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                "T  t t t t t t V . . r r r r r r . . . . . . . 4 .  T\n" +
                "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                "X  t t t t t t t 5 . r r r r r r . . s s s s s s s  X\n" +
                "Y  t t t t t t - . - r r r r r r - . - s s s s s s  Y\n" +
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
                        "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                        "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                        "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                        "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                        "F  k k k k k k . . 1 b b b b b b < . . . c c c c -  F\n" +
                        "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                        "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                        "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                        "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                        "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                        "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                        "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                        "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                        "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                        "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                        "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                        "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                        "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                        "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                        "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                        "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                        "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                        "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                        "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
                        "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.EAST);
        assertTrue(result, "Move should have succeeded");

        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                "F  k k k k k k . . > 1 b b b b b < . . . c c c c -  F\n" +
                "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.WEST);
        assertTrue(result, "Move should have succeeded");

        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                        "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                        "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                        "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                        "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                        "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                        "F  k k k k k k . . 1 b b b b b b < . . . c c c c -  F\n" +
                        "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                        "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                        "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                        "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                        "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                        "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                        "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                        "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                        "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                        "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                        "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                        "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                        "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                        "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                        "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                        "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                        "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                        "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                        "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
                        "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.WEST);
        assertTrue(result, "Move should have succeeded");

        expected =
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  \n" +
                        "A  - - - - - - - - - . - - - - 2 - - - - - - - - -  A\n" +
                        "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                        "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                        "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                        "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                        "F  k k k k k k . 1 > b b b b b b < . . . c c c c -  F\n" +
                        "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                        "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                        "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                        "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                        "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                        "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                        "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                        "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                        "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                        "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                        "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                        "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                        "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                        "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                        "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                        "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                        "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                        "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                        "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
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
                "B  k k k k k k - . . . b b b b . . . - c c c c c c  B\n" +
                "C  k k k k k k . . b b b b b b b b . . c c c c c c  C\n" +
                "D  k k k k k k . . b b b b b b b b . . c c c c c c  D\n" +
                "E  k k k k k k . . b b b b b b b b . . ^ c c c c c  E\n" +
                "F  k k k k k k . . > b b 1 2 b b < . . . c c c c -  F\n" +
                "G  - k k k ^ k . . b b b b b b b b . . . . . . . 3  G\n" +
                "H  . . . . . . . . b ^ b b b b ^ b . . . . . . . -  H\n" +
                "I  - . . . . . . . . . . . . . . . . . p p p p p p  I\n" +
                "J  d d d d d . . . . . . . . . . . . . > p p p p p  J\n" +
                "K  d d d d d d d d . . - - - - - . . . p p p p p p  K\n" +
                "L  d d d d d d d d . . - - - - - . . . p p p p p p  L\n" +
                "M  d d d d d d d < . . - - - - - . . . p p p p ^ p  M\n" +
                "N  d d d d d d d d . . - - - - - . . . . . . . . -  N\n" +
                "O  d d d d d d d d . . - - - - - . . . l l V l l -  O\n" +
                "P  d d d d d d ^ d . . - - - - - . . l l l l l l l  P\n" +
                "Q  - . . . . . . . . . - - - - - . . > l l l l l l  Q\n" +
                "R  6 . . . . . . . . . . . . . . . . l l l l l l l  R\n" +
                "S  - . . . . . . . . r r V V r r . . . l l l l l -  S\n" +
                "T  t t t t t t V . . r r r r r r . . . . . . . . 4  T\n" +
                "U  t t t t t t t . . r r r r r < . . . . . . . . -  U\n" +
                "V  t t t t t t t . . r r r r r r . . V s s s s s s  V\n" +
                "W  t t t t t t t . . r r r r r r . . s s s s s s s  W\n" +
                "X  t t t t t t t . . r r r r r r . . s s s s s s s  X\n" +
                "Y  t t t t t t - 5 - r r r r r r - . - s s s s s s  Y\n" +
                "   1 2 3 4 5 6 7 8 9 10  12  14  16  18  20  22  24  ";
        assertEquals(expected, board.toString(), "Test setup failed");

        boolean result = board.moveCharacter(characters[1], Cell.Direction.WEST);
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");

        result = board.moveCharacter(characters[0], Cell.Direction.EAST);
        assertFalse(result, "Move should fail");
        assertEquals(expected, board.toString(), "Board does not match expected");
    }
}
