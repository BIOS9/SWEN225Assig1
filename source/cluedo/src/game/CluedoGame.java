package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import game.board.Board;
import game.cards.Card;

/**
 * CluedoGame is responsible for initiating the game board, cards and players and managing the framework of the gameplay.
 * 
 * Collaborates directly with Board, Card, Player and Turn.
 * @author abbey
 *
 */
class CluedoGame {
	//CluedoGame Attributes
    private int playerTurnIndex = 0; // Whos turn it is
   
    //CluedoGame Associations
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
     * Generates the cards, selects a solution (3 cards) shuffles remaing cards and 
     * deals them to the players.
     */
    public void initCards() {
    	// Create three stack of cards, one for each card type, Sets since order doesnt matter and no dups?
    	HashSet<Card> charCards = new HashSet<Card>();
    	HashSet<Card> weaponCards = new HashSet<Card>();
    	HashSet<Card> roomCards = new HashSet<Card>();
    	
    	//pass to Card to populate the sets? then merge into one set.
    	
        // Get random card of each type to make solution
        // Shuffle remaining cars into one stack
        // Deal cards to players
    	
    }
}