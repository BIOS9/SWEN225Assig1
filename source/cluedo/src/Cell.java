
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
    private final Room room;
    private final Cell north, south, east, west;
    private Character occupant;

    /**
     * Constructor
     * @param room The room type of this cell
     * @param north North neighbouring cell, or null
     * @param south South neighbouring cell, or null
     * @param east East neighbouring cell, or null
     * @param west West neighbouring cell, or null
     */
    public Cell(Room room, Cell north, Cell south, Cell east, Cell west) {
        this.room = room;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
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
     * Checks if specified cell is connected to this cell
     * @param cell Cell to check against
     * @return true if connected, false if not connected
     */
    public boolean isConnected(Cell cell) {

    }
}