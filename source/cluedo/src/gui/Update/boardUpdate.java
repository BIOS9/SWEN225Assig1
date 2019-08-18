package gui.Update;

import game.board.Board;

/**
 * Represents an update of the board during the game.
 * @author abbey
 */
public class boardUpdate extends GameUpdate{
	public final Board board;
	
	/**
	 * Constructs a new board update
	 * @param updated board
	 */
	public boardUpdate (Board board) {
		this.board = board;
	}
}
