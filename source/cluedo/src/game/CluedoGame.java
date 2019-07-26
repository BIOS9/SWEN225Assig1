package game;

import game.board.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for running the game and managing the active game objects.
 * 
 * Collaborates directly with Players, Cards and Characters.
 * @author abbey
 *
 */
class CluedoGame {
    private int playerTurnIndex = 0; // Whos turn it is
    private Board board;

    private final game.cards.Character[] characters = {
            new game.cards.Character("Miss Scarlett"),
            new game.cards.Character("Rev. Green"),
            new game.cards.Character("Colonel Mustard"),
            new game.cards.Character("Professor Plum"),
            new game.cards.Character("Mrs. Peacock"),
            new game.cards.Character("Mrs. White")
    };

    /**
     * Entry point
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new CluedoGame().initGame();
    }

    /**
     * Initialises the game state, players and board.
     */
    public void initGame() {
        board = new Board();
        board.generateBoard(characters);
        System.out.println(board.toString());
    }

    /**
     * Generates the cards, selects a solution and deals the cards to players
     */
    public void initCards() {
        // Create three stacks of cards, one for each card type
        // Get random card of each type to make solution
        // Shuffle remaining cars into one stack
        // Deal cards to players
    }
}