package game.board;

import java.util.*;
import java.util.Collections;
import java.util.Map;
import game.cards.Room;


/** 
 * 
 * 
 * 
 * @author abbey
 *
 */
public class Cell {
    public enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private final Room room;
    private final Map<Direction, Cell> neighbours = new HashMap<>();
    private game.cards.Character occupant; // umple has player here
    public final Position position;
    public final boolean isDoor;
    //private Turn turn .. umple

    /**
     * Constructor
     * @param room The room type of this cell
     */
    public Cell(Room room, Position position, boolean isDoor) {
        this.room = room;
        this.position = position;
        this.isDoor = isDoor;
    }

    /**
     * Checks if specified room matches the current room of the cell
     * @param room
     * @return
     */
    public boolean isRoom(Room room) {
        return this.room.equals(room);
    }

    /**
     * Checks if specified cell's room type matches the room type of this cell
     * @param cell
     * @return
     */
    public boolean isRoom(Cell cell) {
        return isRoom(cell.room);
    }

    /**
     * Checks if the cell is occupied by a player
     * @return
     */
    public boolean isOccupied() {
        return occupant != null;
    }
    
    /** 
     * Returns the occupant of the cell
     * @return
     */
    public game.cards.Character getOccupant() {
    	return occupant;
    }

    public void setOccupant(game.cards.Character occupant) {
        this.occupant = occupant;
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
     * Returns unmodifiable version of neighbours map
     * @return
     */
    public Map<Direction, Cell> getNeighbours() {
        return Collections.unmodifiableMap(neighbours);
    }

    /**
     * Checks if specified cell is connected to this cell
     * @param cell Cell to check against
     * @return true if connected, false if not connected
     */
    public boolean isConnected(Cell cell) {
        return neighbours.containsValue(cell);
    }

    public boolean hasNeighbour(Direction direction) {
        return neighbours.containsKey(direction);
    }

    public Cell getNeighbour(Direction direction) { return  neighbours.get(direction); }

    public Room getRoom() {
        return room;
    }

    @Override
    public int hashCode() {
        return position.hashCode();
    }
}