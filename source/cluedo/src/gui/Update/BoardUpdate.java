package gui.Update;

import game.board.Board;

/**
 * Represents an update of the board during the game.
 * @author abbey
 */
public class BoardUpdate extends GameUpdate{
	public final Board board;
	
	/**
	 * Constructs a new board update
	 */
	public BoardUpdate (Board board) {
		this.board = board;
	}
}
