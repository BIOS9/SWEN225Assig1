package game;

import java.util.*;
import game.cards.Room;

/**
 * Graph of cells, where walls are represented by Null and doors are identified by a change of cell type.
 * 
 * Responsible for the shape of the board/rooms and the navigation and position of the players/characters.
 * 
 * Collaborates with Character, Room
 * @author abbey
 *
 */
class Cell {
    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private final Room room;
    private final Map<Direction, Cell> neighbours = new HashMap<>();
    private Character occupant;

    /**
     * Constructor
     * @param room The room type of this cell
     */
    public Cell(Room room) {
        this.room = room;
    }

    /**
     * Checks if specified room matches the current room of the cell
     * @param room
     * @return
     */
    public boolean isRoom(Room room) {
        return this.room == room;
    }

    /**
     * Checks if the cell is occupied by a player
     * @return
     */
    public boolean isOccupied() {
        return occupant != null;
    }

    /**
     * Sets neighbouring cell in the specified direction
     * One dimensional, does not update neighbor of specified cell
     * @param direction Neighbouring direction relative to this cell
     * @param cell The cell to add as the neighbour
     */
    public void setNeighbour(Direction direction, Cell cell) {
        neighbours.put(direction, cell);
    }

    /**
     * Checks if specified cell is connected to this cell
     * @param cell Cell to check against
     * @return true if connected, false if not connected
     */
    public boolean isConnected(Cell cell) {
        return neighbours.containsValue(cell);
    }
}