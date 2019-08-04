package game.board;

import java.util.*;
import java.util.Collections;
import java.util.Map;
import game.cards.Room;

/**
 * 
 * Represents the cells that make up the board, Cells can acess neighbours
 * through a mapping of direction to cell
 * 
 * Associated with Room, and Position.
 * 
 * @author abbey
 *
 */
public class Cell {
	
	//Cell Attributes
	public enum Direction {
		NORTH, SOUTH, EAST, WEST
	}
	public final boolean isDoor;

	//Cell Assosciations
	private final Room room;
	private final Map<Direction, Cell> neighbours = new HashMap<>();
	private game.cards.Character occupant; 
	public final Position position;

	/**
	 * Constructor.
	 * 
	 * @param room The room type of this cell
	 */
	public Cell(Room room, Position position, boolean isDoor) {
		this.room = room;
		this.position = position;
		this.isDoor = isDoor;
	}

	/**
	 * Checks if specified room matches the current room of the cell.
	 * 
	 * @param room
	 * @return
	 */
	public boolean isRoom(Room room) {
		return this.room.equals(room);
	}

	/**
	 * Checks if specified cell's room type matches the room type of this cell.
	 * 
	 * @param cell
	 * @return
	 */
	public boolean isRoom(Cell cell) {
		return isRoom(cell.room);
	}

	/**
	 * Checks if the cell is occupied by a player.
	 * 
	 * @return
	 */
	public boolean isOccupied() {
		return occupant != null;
	}

	/**
	 * Returns the occupant of the cell.
	 * 
	 * @return a character card.
	 */
	public game.cards.Character getOccupant() {
		return occupant;
	}

	/**
	 * Sets this cells occupant to the specified character.
	 * 
	 * @param occupant
	 */
	public void setOccupant(game.cards.Character occupant) {
		this.occupant = occupant;
	}

	/**
	 * Sets neighbouring cell in the specified direction One dimensional, does not
	 * update neighbor of specified cell.
	 * 
	 * @param direction Neighbouring direction relative to this cell
	 * @param cell      The cell to add as the neighbour
	 */
	public void setNeighbour(Direction direction, Cell cell) {
		neighbours.put(direction, cell);
	}

	/**
	 * Returns unmodifiable version of neighbours map.
	 * 
	 * @return
	 */
	public Map<Direction, Cell> getNeighbours() {
		return Collections.unmodifiableMap(neighbours);
	}

	/**
	 * Checks if specified cell is connected to this cell.
	 * 
	 * @param cell Cell to check against
	 * @return true if connected, false if not connected
	 */
	public boolean isConnected(Cell cell) {
		return neighbours.containsValue(cell);
	}

	/**
	 * Checks for a neighbour in the given direction.
	 * 
	 * @param direction
	 * @return true if there is a nighbour.
	 */
	public boolean hasNeighbour(Direction direction) {
		return neighbours.containsKey(direction);
	}

	/**
	 * Returns the cell in the given direction.
	 * 
	 * @param direction
	 * @return
	 */
	public Cell getNeighbour(Direction direction) {
		return neighbours.get(direction);
	}
	
	/**
	 * Returns the room assciated with this cell.
	 * 
	 * @return
	 */
	public Room getRoom() {
		return room;
	}

	@Override
	public int hashCode() {
		return position.hashCode();
	}
}