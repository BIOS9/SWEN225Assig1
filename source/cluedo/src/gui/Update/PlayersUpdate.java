package gui.Update;

import game.Player;

import java.util.Collections;
import java.util.List;

/**
 * Represents an update of the players of the game
 */
public class PlayersUpdate extends GameUpdate{
	public final List<Player> players;

	/**
	 * Constructs a new players update
	 */
	public PlayersUpdate(List<Player> players) {
		this.players = Collections.unmodifiableList(players);
	}
}
