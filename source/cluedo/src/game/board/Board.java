package game.board;

import game.cards.Room;
import javafx.geometry.Pos;
import sun.misc.CEFormatException;

import java.util.HashMap;
import java.util.Map;

/**
 * Board generates and manages the cells on the board. 
 * It also manages the string that represents the board for the display.
 * 
 * Collaborates directs with Cell.
 * @author abbey
 * 
 */
public class Board {
	//Board Attributes
    public static final int CHARACTER_COUNT = 6;
    public final int BOARD_WIDTH;
    public final int BOARD_HEIGHT;

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

    // Key for board characters to room names
    private static final Map<java.lang.Character, Room> rooms = new HashMap<java.lang.Character, Room>() {{
        put('k', new Room("Kitchen", 'k'));
        put('b', new Room("Ball Room", 'b'));
        put('c', new Room("Conservatory", 'c'));
        put('p', new Room("Billiard Room", 'p'));
        put('h', new Room("Hallway", 'h'));
        put('d', new Room("Dining Room", 'd'));
        put('l', new Room("Library", 'l'));
        put('r', new Room("Hall", 'r'));
        put('t', new Room("Lounge", 't'));
        put('s', new Room("Study", 's'));
    }};

    // Board co-ordinates of each character starting position
    private static final Position[] startingPositions = {
        new Position(9, 0),
        new Position(14, 0),
        new Position(23, 6),
        new Position(23, 19),
        new Position(7, 24),
        new Position(0, 17),
    };

    // Capital letters represent doorways CAREFUL OF DOUBLE WIDTH DOORWAYS
    private static final char[][] BOARD = {
            "         h    h         ".toCharArray(),
            "kkkkkk hhhbbbbhhh cccccc".toCharArray(),
            "kkkkkkhhbbbbbbbbhhcccccc".toCharArray(),
            "kkkkkkhhbbbbbbbbhhcccccc".toCharArray(),
            "kkkkkkhhbbbbbbbbhhCccccc".toCharArray(),
            "kkkkkkhHBbbbbbbBHhHcccc ".toCharArray(),
            " kkkKkhhbbbbbbbbhhhhhhhh".toCharArray(),
            "hhhhHhhhbBbbbbBbhhhhhhh ".toCharArray(),
            " hhhhhhhhHhhhhHhhhpppppp".toCharArray(),
            "dddddhhhhhhhhhhhhHPppppp".toCharArray(),
            "ddddddddhh     hhhpppppp".toCharArray(),
            "ddddddddhh     hhhpppppp".toCharArray(),
            "dddddddDHh     hhhppppPp".toCharArray(),
            "ddddddddhh     hhhhhHhH ".toCharArray(),
            "ddddddddhh     hhhllLll ".toCharArray(),
            "ddddddddhh     hhlllllll".toCharArray(),
            " hhhhhhhhh     hHLllllll".toCharArray(),
            "hhhhhhhhhhhHHhhhhlllllll".toCharArray(),
            " hhhhhHhhrrRRrrhhhlllll ".toCharArray(),
            "ttttttThhrrrrrrhhhhhhhhh".toCharArray(),
            "ttttttthhrrrrrRHhHhhhhh ".toCharArray(),
            "ttttttthhrrrrrrhhSssssss".toCharArray(),
            "ttttttthhrrrrrrhhsssssss".toCharArray(),
            "ttttttthhrrrrrrhhsssssss".toCharArray(),
            "tttttt h rrrrrr h ssssss".toCharArray()
    };
    
    //Board Associations
    private Map<Position, Cell> cells = new HashMap<>();

    public Board() {
        BOARD_HEIGHT = BOARD.length;
        int widest = 0;
        for(char[] row : BOARD)
            widest = Math.max(widest, row.length);

        BOARD_WIDTH = widest;
    }

    /**
     * Generates board by connecting relevant cells using the board layout string
     * @param characters
     */
    public void generateBoard(game.cards.Character... characters) {
        if(characters.length != CHARACTER_COUNT) {
            throw new IllegalArgumentException("Character count must be " + CHARACTER_COUNT);
        }

        // Generate all cells with corresponding rooms and positions
        for(int y = 0; y < BOARD_HEIGHT; ++y) {
            for(int x = 0; x < BOARD_WIDTH; ++x) {

                // Skip cells with nothing in them/space character
                if(!rooms.containsKey(lowChar(BOARD[y][x])))
                    continue;

                Position position = new Position(x, y);
                Room room = rooms.get(lowChar(BOARD[y][x]));
                Cell cell = new Cell(room, position, java.lang.Character.isUpperCase(BOARD[y][x]));
                cells.put(position, cell);

                // Link vertically if required
                if(cells.containsKey(position.up())) {
                    Cell neighbour = cells.get(position.up());

                    if(needsLink(cell, neighbour))
                        linkCellsVertical(neighbour, cell);
                }

                // Link horizontally if required
                if(cells.containsKey(position.left())) {
                    Cell neighbour = cells.get(position.left());

                    if(needsLink(cell, neighbour))
                        linkCellsHorizontal(neighbour, cell);
                }
            }
        }

        // Put players in starting positions
        for(int i = 0; i < CHARACTER_COUNT; ++i) {
            Cell cell = cells.get(startingPositions[i]);
            moveCharacterForce(characters[i], cell);
        }
    }

    /**
     * Moves character to specified cell on the board if the move is valid
     * @param character Character to move
     * @param cell Cell to move character to
     * @return True if move was valid and completed, false if move was invalid or failed to move
     */
    public boolean moveCharacter(game.cards.Character character, Cell cell) {
        // CHECK IF VALID HERE!!
        moveCharacterForce(character, cell);

        return true; // Valid move
    }

    /**
     * Moves character to specified cell without checks, used for setting up board
     * @param character Character to move
     * @param cell Cell to move character to
     */
    private void moveCharacterForce(game.cards.Character character, Cell cell) {
        if(character.getLocation() != null)
            character.getLocation().setOccupant(null); // Remove character from old position
        cell.setOccupant(character);
        character.setLocation(cell);
    }

    private boolean needsLink(Cell cell1, Cell cell2) {
        if(cell1 == null || cell2 == null)
            return false;

        if(cell1.isRoom(cell2)) // Room type is same
            return true;

        if(!cell1.position.isNeighbour(cell2.position))
            return false;

        return cell1.isDoor && cell2.isDoor;
    }

    /**
     * Links two cells vertically
     * @param north The north-most of the two cells
     * @param south The south-most of the two cells
     */
    private void linkCellsVertical(Cell north, Cell south) {
        north.setNeighbour(Cell.Direction.SOUTH, south);
        south.setNeighbour(Cell.Direction.NORTH, north);
    }

    /**
     * Links two cells horizontally
     * @param west The west-most of the two cells
     * @param east The east-most of the two cells
     */
    private void linkCellsHorizontal(Cell west, Cell east) {
        west.setNeighbour(Cell.Direction.EAST, east);
        east.setNeighbour(Cell.Direction.WEST, west);
    }

    /**
     * Converts character to lower case
     * @param c
     * @return
     */
    private char lowChar(char c) {
        return java.lang.Character.toLowerCase(c);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // Prepend column header with numbers
        builder.append("   ");
        for(int i = 0; i <= BOARD_WIDTH; ++i) {
            if(i < 9) {
                builder.append(i + 1);
                builder.append(' ');
            }
            else if(i % 2 == 0){
                builder.append(i);
                builder.append("  ");
            }
        }

        builder.append('\n');
        for(int y = 0; y < BOARD_HEIGHT; ++y) {

            // Prepend row header with letters
            builder.append((char)('A' + y));
            builder.append("  ");

            for(int x = 0; x < BOARD_WIDTH; ++x) {
                Position position = new Position(x, y);

                if(cells.containsKey(position)) {
                    Cell cell = cells.get(position);

                    if(cell.isOccupied())
                        builder.append("# ");
                    else if(cell.getRoom().getPrefix() == 'h')
                        builder.append("  ");
                    else if(cell.isDoor) {
                        Cell.Direction neighbouringDirection = Cell.Direction.NORTH;

                        for(Map.Entry<Cell.Direction, Cell> neighbour : cell.getNeighbours().entrySet()) {
                            if(neighbour.getValue().isDoor && !neighbour.getValue().isRoom(cell)) {
                                neighbouringDirection = neighbour.getKey();
                                break;
                            }
                        }

                        switch (neighbouringDirection) {
                            case EAST:
                                builder.append("< ");
                                break;
                            case WEST:
                                builder.append("> ");
                                break;
                            case NORTH:
                                builder.append("V ");
                                break;
                            case SOUTH:
                                builder.append("^ ");
                                break;
                        }
                    }
                    else {
                        builder.append(cell.getRoom().getPrefix());
                        builder.append(' ');
                    }
                }
                else
                    builder.append("- ");
            }

            // Append row header with letters
            builder.append(" ");
            builder.append((char)('A' + y));

            builder.append('\n');
        }

        // Append column header with numbers
        builder.append("   ");
        for(int i = 0; i <= BOARD_WIDTH; ++i) {
            if(i < 9) {
                builder.append(i + 1);
                builder.append(' ');
            }
            else if(i % 2 == 0){
                builder.append(i);
                builder.append("  ");
            }
        }

        return builder.toString();
    }
}
