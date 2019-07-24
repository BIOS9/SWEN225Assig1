package game.board;

import game.cards.Room;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public static final int CHARACTER_COUNT = 6;

    // k = Kitchen
    // b = Ball room
    // c = Conservatory
    // p = Billiard/Pool room
    // h = Hallway
    // d = Dining room
    // l = Library
    // r = Hall
    // t = Lounge
    // s = Study
    // space = nothing
    // 0-6 = Character starting positions

    private static final Map<java.lang.Character, String> roomNames = new HashMap<>() {{
        put('k', "Kitchen");
        put('b', "Ball Room");
        put('c', "Conservatory");
        put('p', "Billiard Room");
        put('h', "Hallway");
        put('d', "Dining Room");
        put('l', "Library");
        put('r', "Hall");
        put('t', "Lounge");
        put('s', "Study");
    }};

    private static final Position[] startingPositions = {
        new Position(9, 0),
        new Position(14, 0),
        new Position(23, 6),
        new Position(23, 19),
        new Position(7, 24),
        new Position(0, 17),
    };

    // Capital letters represent doorways CAREFUL OF DOUBLE WIDTH DOORWAYS
    private static final String BOARD =
            "         0    1         \n" +
            "kkkkkk hhhbbbbhhh cccccc\n" +
            "kkkkkkhhbbbbbbbbhhcccccc\n" +
            "kkkkkkhhbbbbbbbbhhcccccc\n" +
            "kkkkkkhhbbbbbbbbhhCccccc\n" +
            "kkkkkkhHBbbbbbbBHhHcccc \n" +
            " kkkKkhhbbbbbbbbhhhhhhh2\n" +
            "hhhhHhhhbBbbbbBbhhhhhhh \n" +
            " hhhhhhhhHhhhhHhhhpppppp\n" +
            "dddddhhhhhhhhhhhhHPppppp\n" +
            "ddddddddhh     hhhpppppp\n" +
            "ddddddddhh     hhhpppppp\n" +
            "dddddddDHh     hhhppppPp\n" +
            "ddddddddhh     hhhhhHhH \n" +
            "ddddddddhh     hhhllLll \n" +
            "ddddddddhh     hhlllllll\n" +
            " hhhhhhhhh     hHLllllll\n" +
            "5hhhhhhhhhhHHhhhhlllllll\n" +
            " hhhhhHhhrrRRrrhhhlllll \n" +
            "ttttttThhrrrrrrhhhhhhhh3\n" +
            "ttttttthhrrrrrRHhHhhhhh \n" +
            "ttttttthhrrrrrrhhSssssss\n" +
            "ttttttthhrrrrrrhhsssssss\n" +
            "ttttttthhrrrrrrhhsssssss\n" +
            "tttttt 4 rrrrrr h ssssss\n";

    private Cell[] startingCells = new Cell[CHARACTER_COUNT];

    public void generateBoard(game.cards.Character... characters) {
        if(characters.length != CHARACTER_COUNT) {
            throw new IllegalArgumentException("Character count must be " + CHARACTER_COUNT);
        }

        for(int i = 0; i < CHARACTER_COUNT; ++i) {
            Room room = new Room(roomNames.get('h'));
            Cell cell = new Cell(room, startingPositions[i]);
            cell.setOccupant(characters[i]);
            startingCells[i] = cell;

        }
    }

}
