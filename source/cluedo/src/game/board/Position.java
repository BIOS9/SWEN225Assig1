package game.board;

/**
 * Class representing the physical x,y position of the cell objects that make up the board.
 * 
 * Associated with cell.
 * 
 * @author abbey
 *
 */
public class Position {
	
	//Position Attributes
    public int x, y;
    
    /**
     * Constructor
     * @param x position
     * @param y position
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Checks if two positions are direct neighbours, diagonal neighbours do not count.
     * 
     * @param pos1
     * @param pos2
     * @return
     */
    public static boolean areNeighbours(Position pos1, Position pos2) {
        if(Math.abs(pos1.x - pos2.x) == 0 && Math.abs(pos1.y - pos2.y) == 1)
            return true;
        return Math.abs(pos1.x - pos2.x) == 1 && Math.abs(pos1.y - pos2.y) == 0;

    }

    /**
     * Checks if position is a neighbour of this position.
     * 
     * @param position
     * @return
     */
    public boolean isNeighbour(Position position) {
        return areNeighbours(this, position);
    }

    /**
     * Creates new position one unit up relative to this position.
     */
    public Position up() {
        return new Position(x, y - 1);
    }

    /**
     * Creates new position one unit down relative to this position.
     */
    public Position down() {
        return new Position(x, y + 1);
    }

    /**
     * Creates new position one unit left relative to this position.
     */
    public Position left() {
        return new Position(x - 1, y);
    }

    /**
     * Creates new position one unit right relative to this position.
     */
    public Position right() {
        return new Position(x + 1, y);
    }

    @Override
    public String toString() {
        String pos = "" + (char)('A' + y);
        pos += (x + 1);
        return pos;
    }

    @Override
    public int hashCode() {
        final int prime = 163;
        int result = 1;

        result = prime * result + x;
        result = prime * result + y;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        if(obj == this)
            return true;

        Position pos = (Position)obj;
        return pos.x == x && pos.y == y;
    }
}
