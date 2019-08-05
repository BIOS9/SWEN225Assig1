package tests;

import com.sun.media.jfxmedia.events.PlayerEvent;
import game.CluedoGame;
import game.Player;
import game.board.Board;
import static org.junit.jupiter.api.Assertions.*;

import game.cards.Card;
import game.cards.Character;
import org.junit.jupiter.api.Test;

import java.lang.management.PlatformLoggingMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class GameTests {
	
	private final game.cards.Character[] characters = {
            new game.cards.Character("Miss Scarlett", 1),
            new game.cards.Character("Rev. Green", 2),
            new game.cards.Character("Colonel Mustard", 3),
            new game.cards.Character("Professor Plum", 4),
            new game.cards.Character("Mrs. Peacock", 5),
            new game.cards.Character("Mrs. White", 6) };
	CluedoGame game = new CluedoGame();

	
	@Test
	public void cardDealTest() {
		CluedoGame game = new CluedoGame();
		try {
			Field playersField = game.getClass().getDeclaredField("players");
			playersField.setAccessible(true);
			List<game.Player> playerList = (List<Player>)playersField.get(game);

			playerList.add(new game.Player(new game.cards.Character("Miss Scarlett", 1), game));
			playerList.add(new game.Player(new game.cards.Character("Rev. Green", 2), game));

			Method initCards = game.getClass().getDeclaredMethod("initCards");
			initCards.setAccessible(true);
			initCards.invoke(game);

			List<Card> player1Hand = playerList.get(0).getHand();
			List<Card> player2Hand = playerList.get(0).getHand();

			assertTrue(player1Hand.size() > 0);
			assertTrue(player2Hand.size() > 0);
		} catch (Exception ex) {
			fail(ex);
		}
	}

}
