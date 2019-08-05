package tests;

import game.CluedoGame;
import game.board.Board;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GameTests {
	
	private final game.cards.Character[] characters = {
            new game.cards.Character("Miss Scarlett", 1),
            new game.cards.Character("Rev. Green", 2),
            new game.cards.Character("Colonel Mustard", 3),
            new game.cards.Character("Professor Plum", 4),
            new game.cards.Character("Mrs. Peacock", 5),
            new game.cards.Character("Mrs. White", 6) };
	CluedoGame game = new CluedoGame();

	
	

}
