package game.board;

public class Board {
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
}
